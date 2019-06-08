/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thesis2;

import java.util.Random;

public class Layer
{

    private String layerType, activation;
    private int layerSize, nextLayerSize;
    private double bias;
    private double[] inputData, layerError;
    private double[][] weightedOutput, gradient;

    private NodeList nodeList;

    Random rand = new Random();

    public Layer(String layerType, int layerSize, String activation)
    {
        this.layerType = layerType;
        this.layerSize = layerSize;
        this.activation = activation;

        bias = rand.nextGaussian();
        
        createNodeList(layerType, activation);
    }

    public Layer(
            String layerType, int layerSize,
            int nextLayerSize, String activation)
    {
        this.layerType = layerType;
        this.layerSize = layerSize;
        this.nextLayerSize = nextLayerSize;        
        this.activation = activation;
        
        gradient = new double[layerSize][nextLayerSize];
        
        bias = rand.nextGaussian();
        
        createNodeList(layerType, nextLayerSize, activation);
    }

    private void createNodeList(String layerType, String activation)
    {
        nodeList = new NodeList();     //instantiate the nodeList 

        for (int i = 0; i < layerSize; i++)    //populate the nodeList
        {
            nodeList.add(new Node(layerType, activation));
        }
    }
    
    private void createNodeList(
            String layerType, int nextLayerSize, String activation)
    {
        nodeList = new NodeList();     //instantiate the nodeList 

        for (int i = 0; i < layerSize; i++)    //populate the nodeList
        {
            nodeList.add(new Node(layerType, nextLayerSize, activation));
        }
    }

    public void setInput(double[] inputArray)
    {
        inputData = new double[inputArray.length];
        System.arraycopy(inputArray, 0, inputData, 0, inputArray.length);
    }

    /**
     * This method feeds the ingested data into the nodes; if the the layer is
     * an "input", it feeds the data from the imageInput[].
     */
    public void feedDataThroughNodes()
    {
        for (int i = 0; i < nodeList.size(); i++)
        {
            nodeList.get(i).setLayerInputValue(inputData[i]);
        }

        for (Node aNode : nodeList)
        {
            aNode.applyActivation(aNode.getInputValue());
            aNode.applyWeights();
        }

    }
    
    public void getWeightedOutput()
    {
        if(!layerType.equals("output"))
        {
            weightedOutput = new double[nextLayerSize][layerSize];
            
            for(int i = 0; i < nextLayerSize; i++)
            {
                for(int j = 0; j < layerSize; j++)
                {
                    weightedOutput[i][j] = nodeList.get(j).getOutputAtIndex(i);
                }
            }
        } else
        {
            weightedOutput = new double[layerSize][layerSize];
            
            for(int i = 0; i < layerSize; i++)
            {
                weightedOutput[i] = nodeList.get(i).getOutput();
            }
        }
    }

    public double[] applySummation()
    {
        double[] output = new double[weightedOutput.length];
        double sum;
        for (int i = 0; i < weightedOutput.length; i++)
        {
            sum = 0;
            for (int j = 0; j < weightedOutput[i].length; j++)
            {
                sum += weightedOutput[i][j];
            }
            output[i] = Math.round(sum * 10000)/10000.0;
        }

        return output;
    }

    public Node getNode(int index)
    {
        return nodeList.get(index);
    }

    public String getLayerType()
    {
        return layerType;
    }
    
    public String getActivation()
    {
        return activation;
    }

    public int getLayersize()
    {
        return layerSize;
    }
    
    /*=========================================================================
                           Methods for back-propagation
    ==========================================================================*/
    public void setErrorArray(double[] layerError)
    {
        this.layerError = layerError;
    }

    /**
     * This method changes the values of the weights according to the gradient;
     * The gradient is the product of the activated value store in the node and
     * the node's error contained in the layerError array.
     */
    public void computeGradientDecreaseWeights()
    {
        double activatedValue;
        for (int i = 0; i < layerSize; i++)
        {
            //get the node value where activation was applied
            activatedValue = nodeList.get(i).getActivationOutput();

            for (int j = 0; j < nodeList.get(i).getWeightListSize(); j++)
            {
                double grad = activatedValue * layerError[j];

                gradient[i][j] = Math.round(grad * 10000) / 10000.0;
                nodeList
                        .get(i)
                        .getWeightFromList(j)
                        .decreaseWeightValue(gradient[i][j]);

            }
        }
    }
    
    public void computeGradientIncreaseWeights()
    {
        double activatedValue;
        for (int i = 0; i < layerSize; i++)
        {
            //get the node value where activation was applied
            activatedValue = nodeList.get(i).getActivationOutput();

            for (int j = 0; j < nodeList.get(i).getWeightListSize(); j++)
            {
                double grad = activatedValue * layerError[j];

                gradient[i][j] = Math.round(grad * 10000) / 10000.0;
                nodeList
                        .get(i)
                        .getWeightFromList(j)
                        .increaseWeightValue(gradient[i][j]);

            }
        }
    }
    
    @Override
    public String toString()
    {
        String returnMe = "";

        for (int i = 0; i < nodeList.size(); i++)
        {
            returnMe += "\tNode " + i + ": \n";
            returnMe += nodeList.get(i).toString();
        }

        return returnMe;
    }
}
