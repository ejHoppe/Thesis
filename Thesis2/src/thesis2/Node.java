/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thesis2;

public class Node
{

    private String layerType, activation;
    private int weightListSize;
    private double inputValue, activationOutput;
    private double[] output;

    private WeightList weightList;

    /**
     * This constructor is used for the output layer, which doesn't require
     * weights.
     *
     * @param layerType
     * @param activation
     */
    public Node(String layerType, String activation)
    {
        this.layerType = layerType;
        this.activation = activation;
        weightListSize = 0;
    }

    public Node(String layerType, int nextLayerSize, String activation)
    {
        this.layerType = layerType;
        weightListSize = nextLayerSize;
        this.activation = activation;

        createWeightList();
    }

    /**
     * This method creates the a list of weights based on the size of the next
     * layer.
     */
    private void createWeightList()
    {
        weightList = new WeightList();

        for (int i = 0; i < weightListSize; i++)
        {
            weightList.add(new Weight(weightListSize, activation));
        }
    }

    public void setLayerInputValue(double value)
    {
        inputValue = value;
    }

    public double getInputValue()
    {
        return inputValue;
    }

    public void applyActivation(double value)
    {
        switch (activation)
        {
            case "leakyRelu":
                activationOutput = Functions.leakyRectifiedLinearUnit(value);
                break;
            case "tanh":
                activationOutput = Functions.applyTanh(value);
                break;
            default:
                activationOutput = value;
        }
    }

    public void applyWeights()
    {
        double weight, product;

        if (!layerType.equals("output"))
        {
            output = new double[weightList.size()];
            for (int i = 0; i < weightList.size(); i++)
            {
                weight = weightList.get(i).getWeightValue();
                product = weight * activationOutput;

                output[i] = Math.round(product * 100) / 100.00;
                
            }
        } else
        {
            output = new double[]{activationOutput};
        }
    }
    
    public double getOutputAtIndex(int index)
    {
        return output[index];
    }
    
    public double getActivationOutput()
    {
        return activationOutput;
    }
    
    public double[] getOutput()
    {
        return output;
    }
    
    public int getWeightListSize()
    {
        return weightList.size();
    }
    
    public Weight getWeightFromList(int index)
    {
        return weightList.get(index);
    }
    
}
