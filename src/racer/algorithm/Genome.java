package racer.algorithm;

import java.util.ArrayList;

/**
 * Genome object.
 * Keeping track of fitness score of genome and sigmoid weight value.
 */
public class Genome {
    // ID to identify genome
    public int ID;
    // Fitness to determine progress of genome.
    public double fitness;
    // Sigmoid weight of genome
    public ArrayList<Double> weights;
}
