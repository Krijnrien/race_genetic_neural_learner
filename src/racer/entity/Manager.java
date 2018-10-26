package racer.entity;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import racer.algorithm.GeneticAlgorithm;
import racer.algorithm.Genome;
import racer.algorithm.NeuralNetwork;
import racer.graphics.block.BlockPos;
import racer.graphics.Screen;
import racer.graphics.map.RaceMap;

/**
 * Description: This class manages important entities such as: Car, GeneticAlgorithm
 * and NeuralNetwork
 */
public class Manager {
    private static final int NN_OUTPUT_COUNT = 2;

    public enum NeuralNetOuputs {
        NN_OUTPUT_RIGHT_FORCE, NN_OUTPUT_LEFT_FORCE,
    }

    private static final int HIDDEN_LAYER_NEURONS = 8;
    private static final int MAX_GENOME_POPULATION = 20;
    private static final float CHECK_POINT_BONUS = 15.0f;
    private static final double DEFAULT_ROTATION = -Math.PI / 3;
    private static BlockPos tileCoor = new BlockPos(16, 35);
    private static Point2D DEFAULT_POSITION = new Point2D.Double(
            tileCoor.getX() - 12, tileCoor.getY() - 12);
    // fitness is used to measure how far the car could go without collision
    private double currentAgentFitness;
    private double bestFitness;
    private Car car;
    private ArrayList<Checkpoint> checkpoints;
    private NeuralNetwork neuralNet;
    private RaceMap raceMap;
    private GeneticAlgorithm genAlg;

    /**
     * Constructor of Entity Manager. Inside this, we initiate the algorithm,
     * neural network, car and raceMap
     */
    public Manager() {
        int totalWeights = Car.FEELER_COUNT * HIDDEN_LAYER_NEURONS + HIDDEN_LAYER_NEURONS * NN_OUTPUT_COUNT + HIDDEN_LAYER_NEURONS + NN_OUTPUT_COUNT;
        currentAgentFitness = 0.0;
        bestFitness = 0.0;
        genAlg = new GeneticAlgorithm();
        genAlg.generateNewPopulation(MAX_GENOME_POPULATION, totalWeights);
        neuralNet = new NeuralNetwork();
        neuralNet.fromGenome(genAlg.getNextGenome(), Car.FEELER_COUNT, HIDDEN_LAYER_NEURONS, NN_OUTPUT_COUNT);
        raceMap = new RaceMap("/map/map.png");
        checkpoints = loadCheckPoints();
        car = new Car((int) DEFAULT_POSITION.getX(), (int) DEFAULT_POSITION.getY(), DEFAULT_ROTATION, raceMap);
        car.attach(neuralNet);
    }

    /**
     * load predefined checkpoints of map, checkpoints could be optional. I may
     * help the car learn faster.
     */
    private ArrayList<Checkpoint> loadCheckPoints() {
        BufferedReader br = null;
        String line;
        String splitBy = ",";
        ArrayList<Checkpoint> result = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(RaceMap.class.getResource("/checkpoints.csv").getFile()));
            while ((line = br.readLine()) != null) {
                String[] points = line.split(splitBy);
                BlockPos startCoor = new BlockPos(
                        (int) Double.parseDouble(points[0]),
                        (int) Double.parseDouble(points[1]));
                BlockPos endCoor = new BlockPos(
                        (int) Double.parseDouble(points[2]),
                        (int) Double.parseDouble(points[3]));
                Point2D start = new Point2D.Float();
                Point2D end = new Point2D.Float();
                start.setLocation(startCoor.getX(), startCoor.getY());
                end.setLocation(endCoor.getX(), endCoor.getY());
                result.add(new Checkpoint(start, end, true));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * Generate new test subject after the car fails. The new genome will be fed
     * in for neural network.
     */
    private void nextTestSubject() {
        genAlg.setGenomeFitness(currentAgentFitness, genAlg.getCurrentGenomeIndex());
        currentAgentFitness = 0;
        Genome genome = genAlg.getNextGenome();
        neuralNet.fromGenome(genome, Car.FEELER_COUNT, HIDDEN_LAYER_NEURONS, NN_OUTPUT_COUNT);
        car = new Car((int) DEFAULT_POSITION.getX(), (int) DEFAULT_POSITION.getY(), DEFAULT_ROTATION, raceMap);
        car.attach(neuralNet);
        car.clearFailure();
        for (Checkpoint checkpoint : checkpoints) {
            checkpoint.setActive(true);
        }
    }


    /**
     * Make new population after the old population failed to help the car finishing the track.
     */
    private void evolveGenomes() {
        genAlg.breedPopulation();
        nextTestSubject();
    }

    public void update() {
        if (car.hasFailed()) {
            if (genAlg.getCurrentGenomeIndex() == MAX_GENOME_POPULATION - 1) {

                evolveGenomes();
            } else {
                // System.out.println("fitness: " + currentAgentFitness);
                nextTestSubject();
            }
        }
        car.update();
        currentAgentFitness += car.getDistanceDelta() / 50.0;
        if (currentAgentFitness > bestFitness) {
            bestFitness = currentAgentFitness;
        }
        // Test the agent against the active checkpoints.
        for (Checkpoint checkpoint : checkpoints) {
            // See if the checkpoint has already been hit.
            if (!checkpoint.isActive())
                continue;
            Rectangle2D rec = car.getCarBound();
            if (checkpoint.checkIntersect(rec)) {
                // Each time a check point hit, the fitness gets a bonus of 15
                currentAgentFitness += CHECK_POINT_BONUS;
                checkpoint.setActive(false);
                if (currentAgentFitness > bestFitness) {
                    bestFitness = currentAgentFitness;
                }
                return;
            }
        }

    }

//    /**
//     * Force to create new genomes in case the car is stucked moving in circle.
//     */
//    public void forceToNextAgent() {
//        if (genAlg.getCurrentGenomeIndex() == MAX_GENOME_POPULATION - 1) {
//            evolveGenomes();
//            return;
//        }
//        nextTestSubject();
//    }

    /**
     * The map is manipulated by individual pixels.
     */
    public void renderByPixels(Screen screen) {
        // The car will actually stay at a fixed position, just only screen
        // move. That why we need to set offset for screen when the car "move"
        int xScroll = car.getX() - screen.getWidth() / 2;
        int yScroll = car.getY() - screen.getHeight() / 2;
        raceMap.render(xScroll, yScroll, screen);
    }

    /**
     * Except the map, all other entities are using built in functions of java
     * graphics to render.
     */
    public void renderByGraphics(Screen screen) {
        car.render(screen);
        for (Checkpoint checkpoint : checkpoints) {
            if (checkpoint.isActive()) {
                checkpoint.render(screen);
            }
        }
    }

}
