package racer.algorithm;

import java.util.ArrayList;

/**
 * @Description: NeuralNetwork is used to make decision for the car; decide that it
 * should turn right or turn left or go straight. It may contain many hidden NeuronsLayers 
 * and 1 output NeuronsLayer. These layers will constantly update to get new values from genomes
 * each time the car crashes into the wall. The network will use new values from the Genetic class to make
 * decision for the next car population.
 */
public class NeuralNetwork {
	// Property for input layer does not exist as it's solely a input arrayList

	// HiddenLayer produces the input for OutputLayer
	private ArrayList<NeuronsLayer> hiddenLayers;
	// OutputLayer will receive input from HiddenLayer
	private NeuronsLayer outputLayer;
	// List of Double inputs entered into the next layer
	private ArrayList<Double> inputs;
	// List of Double outputs exiting the layer; bias and weight applied
	private ArrayList<Double> outputs;
	// Output amount is a holding variable to evaluate the neurons how much to steer to the left and right
	private int outputAmount;

	public NeuralNetwork() {
		inputs = new ArrayList<>();
		outputs = new ArrayList<>();
	}

	/**
	 * receive input from sensors of car which is normalized distance from
	 * center of car to the wall.
	 */
	public void setInput(double[] normalizedDepths) {
		inputs.clear();
        for (double normalizedDepth : normalizedDepths) {
            inputs.add(normalizedDepth);
        }
	}

	/**
	 * Update method loops over each hidden layer and applying the inputs and outputs variables to eveluate the neurons.
	 * Every next layer receives the output from the former layer as input and evaluates it as output to the next layer.
	 * After looping all hidden layers the final hidden layer output is cloned as input to the output layer.
	 * The output is evaluated.
	 */
	public void update() {
		outputs.clear();
		for (int i = 0; i < hiddenLayers.size(); i++) {
			if (i > 0) {
				inputs = outputs;
			}
			// Each hidden layer calculates the outputs based on inputs from 5 sensors of the car
			hiddenLayers.get(i).evaluate(inputs, outputs);
			System.out.println("Hidden layers: " + outputs.toString());
		}
		// the outputs of HiddenLayers will be used as input for OutputLayer
		inputs = (ArrayList<Double>) outputs.clone();
		// The output layer will give out the final outputs
		outputLayer.evaluate(inputs, outputs);
	}

	/**
	 * Getter for the outputAmount variable.
	 * Which determines the turn strength for either left or right turning.
	 * @param index position of which output neuron
	 * @return Double value turning strength
	 */
	public double getOutput(int index) {
		if (index >= outputAmount)
			return 0.0f;
		return outputs.get(index);
	}

	private void releaseNet() {
		// inputLayer = null;
		outputLayer = null;
		hiddenLayers = null;
	}

	/**
	 * Neural network receives weights from genome to make new HiddenLayers and an OutputLayer.
	 * For every new genome the previous genome is considered, where the weights from the previous
	 * genome are copied into the new created neural network.
	 */
	public void fromGenome(Genome genome, int numOfInputs, int neuronsPerHidden, int numOfOutputs) {
		if (genome == null) {
			return;
		}
		outputLayer = null;
		hiddenLayers = null;
		hiddenLayers = new ArrayList<>();
		outputAmount = numOfOutputs;

		NeuronsLayer hidden = new NeuronsLayer();
		ArrayList<Neuron> neurons = new ArrayList<>();
		for (int i = 0; i < neuronsPerHidden; i++) {
			ArrayList<Double> weights = new ArrayList<>();
			for (int j = 0; j < numOfInputs + 1; j++) {
				weights.add(genome.weights.get(i * neuronsPerHidden + j));
			}
			Neuron n = new Neuron();
			n.init(weights, numOfInputs);
			neurons.add(n);
		}
		hidden.loadLayer(neurons);
		hiddenLayers.add(hidden);

		// Clear weights and reassign the weights to the output.
		ArrayList<Neuron> neuronsOut = new ArrayList<>();
		for (int i = 0; i < numOfOutputs; i++) {
			ArrayList<Double> weights = new ArrayList<>();
			for (int j = 0; j < neuronsPerHidden + 1; j++) {
				weights.add(genome.weights.get(i * neuronsPerHidden + j));
			}
			Neuron n = new Neuron();
			n.init(weights, neuronsPerHidden);
			neuronsOut.add(n);
		}
		outputLayer = new NeuronsLayer();
		outputLayer.loadLayer(neuronsOut);
	}


}
