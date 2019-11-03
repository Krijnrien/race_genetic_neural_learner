package racer.algorithm;

import java.util.ArrayList;

/**
 * @Description: NeuronsLayer contains a list Neurons. It evaluates these neurons to give a decision.
 */
class NeuronsLayer {
    // Bias variable
    private static final float BIAS = -1.0f;
    // Total number of neurons in list
    private int totalNeurons;
    // List of neurons, aka neuron layer
    private ArrayList<Neuron> neurons;

    /**
     * Evaluate the inputs from sensors or HiddenLayer and give out the output
     */
    void evaluate(ArrayList<Double> inputs, ArrayList<Double> outputs) {
        int inputIndex = 0;
        for (int i = 0; i < totalNeurons; i++) {
            float activation = 0.0f;
            int numOfInputs = neurons.get(i).numberOfInputs;
            Neuron neuron = neurons.get(i);
            // sum the weights up to numberOfInputs-1 and add the bias
            for (int j = 0; j < numOfInputs - 1; j++) {
                if (inputIndex < inputs.size()) {
                    activation += inputs.get(inputIndex) * neuron.weights.get(j);
                    inputIndex++;
                }
            }
            // Add bias
            activation += neuron.weights.get(numOfInputs) * BIAS;
            outputs.add(HelperFunction.Sigmoid(activation, 1.0f));
            inputIndex = 0;
        }
    }

    /**
     * Sets total neuron class variable and the neurons itself.
     * @param neurons list of neurons
     */
    void loadLayer(ArrayList<Neuron> neurons) {
        totalNeurons = neurons.size();
        this.neurons = neurons;
    }
}
