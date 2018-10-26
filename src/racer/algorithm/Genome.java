package racer.algorithm;

import java.util.ArrayList;

/**
 * Genome object.
 * Keeping track of fitness of genome and sigmoid weight value.
 */
public class Genome {
    /**
     * Id to identify genome
     */
    int ID;


    /**
     * Fitness to determine progress of genome.
     */
    double fitness;


    /**
     * Sigmoid weight of genome
     */
    ArrayList<Double> weights;
}
