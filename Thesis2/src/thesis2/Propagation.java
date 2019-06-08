package thesis2;

public class Propagation
{

    private int currentLayerSize, layerMinusOneSize, layerMinusTwoSize;
    private double[] inputData, errorArray, layerMinusOneError, 
            layerMinusTwoError, trueDistribution, xOutput, zOutput, genInput;

    private double[][] weightMatrix, priorLayerWeightMatrix;
    String activation;

    private Layer currentLayer, layerMinusOne, layerMinusTwo;

    public Propagation(double[] inputArray, double[] trueDist, String activation)
    {
        this.inputData = inputArray;
        this.trueDistribution = trueDist;
        this.activation = activation;
    }

    public Propagation(
            double[] xOutput,
            double[] zOutput,
            double[] genInput,
            double[] trueDist)
    {
        this.xOutput = xOutput;
        this.zOutput = zOutput;
        this.genInput = genInput;
        this.trueDistribution = trueDist;
    }

    public Propagation(double[] zOutput, double[] genInput)
    {
        this.zOutput = zOutput;
        this.genInput = genInput;
    }
    
    public Propagation(double[] errorArray)
    {
        this.errorArray = new double[errorArray.length];
        System.arraycopy(errorArray, 0, this.errorArray, 0, errorArray.length);
    }
    

    /**
     * This method calls: setLayers(Layer currentLayer, Layer layerMinusOne),
     * populateErrorArray(), setWeightMatrix().
     *
     * @param currentLayer
     * @param layerMinusOne
     */
    public void setLayerAndWeights(Layer currentLayer, Layer layerMinusOne)
    {
        setLayers(currentLayer, layerMinusOne);
        setWeightMatrix();
    }
    
    public void setLayerAndWeights(Layer current, Layer minusOne, Layer minusTwo)
    {
        setLayers(current, minusOne, minusTwo);
        setWeightMatrix();        
        setPriorLayerWeightMatrix();
    }
    
    /**
     * This method creates the error array with index value = Output_i -
     * Target_i.
     */
    public void populateErrorArray()
    {
        errorArray = new double[currentLayerSize];

        if (activation.equals("softmax"))
        {
            for (int i = 0; i < inputData.length; i++)
            {
                errorArray[i] = inputData[i] - trueDistribution[i];
            }
        } else if (activation.equals("tanh"))
        {
            for (int i = 0; i < inputData.length; i++)
            {
                errorArray[i] = 1 - Math.pow(inputData[i], 2);
            }
        }

    }

    public void populateErrorArray(String networkType)
    {
        errorArray = new double[zOutput.length];

        if (networkType.equals("disc"))
        {
            for (int i = 0; i < zOutput.length; i++)
            {
                errorArray[i]
                        = (xOutput[i] - trueDistribution[i])
                        + (Math.log(1 - zOutput[i]));
            }
        }

        if (networkType.equals("gen"))
        {
            for (int i = 0; i < zOutput.length; i++)
            {
                errorArray[i] = (Math.log(1 - zOutput[i]));
            }
        }
    }

    public double[] calcLayerMinusOneError()
    {
        layerMinusOneError = new double[layerMinusOneSize];
        layerMinusOneError
                = Functions.matrixVectorMultiply(weightMatrix, errorArray);

        return layerMinusOneError;
    }
    
    public double[] calcLayerMinusTwoError()
    {
        layerMinusTwoError = new double[layerMinusTwoSize];
        layerMinusTwoError = Functions.matrixVectorMultiply(
                priorLayerWeightMatrix, 
                layerMinusOneError);
        
        return layerMinusTwoError;
    }

    private void setInput(double[] inputArray)
    {
        this.inputData = inputArray;
    }

    private void setTrueDistribution(double[] trueDist)
    {
        this.trueDistribution = trueDist;
    }

    private void setLayers(Layer currentLayer, Layer layerMinusOne)
    {
        this.currentLayer = currentLayer;
        this.layerMinusOne = layerMinusOne;

        this.currentLayerSize = currentLayer.getLayersize();
        this.layerMinusOneSize = layerMinusOne.getLayersize();
    }
    
    private void setLayers(Layer current, Layer minusOne, Layer minusTwo)
    {
        this.currentLayer = current;
        this.layerMinusOne = minusOne;
        this.layerMinusTwo = minusTwo;

        this.currentLayerSize = current.getLayersize();
        this.layerMinusOneSize = minusOne.getLayersize();
        this.layerMinusTwoSize = minusTwo.getLayersize();
    }
    
    /**
     * This method populates the weightMatrix array with the current layer's
     * list of weights; The weightMatrix will be combined with the current
     * layer's error array to produce the error array for the previous layer.
     */
    private void setWeightMatrix()
    {
        weightMatrix = new double[layerMinusOneSize][currentLayerSize];

        for (int i = 0; i < layerMinusOneSize; i++)
        {
            for (int j = 0; j < currentLayerSize; j++)
            {
                weightMatrix[i][j] = layerMinusOne
                        .getNode(i)
                        .getWeightFromList(j)
                        .getWeightValue();
            }

        }
    }
    
    private void setPriorLayerWeightMatrix()
    {
        priorLayerWeightMatrix = new double[layerMinusTwoSize][layerMinusOneSize];
        
        for(int i = 0; i < layerMinusTwoSize; i++)
        {
            for(int j = 0; j < layerMinusOneSize; j++)
            {
                priorLayerWeightMatrix[i][j] = layerMinusTwo
                        .getNode(i)
                        .getWeightFromList(j)
                        .getWeightValue();
            }
        }
    }

    public double[] getErrorArray()
    {
        return errorArray;
    }

    @Override
    public String toString()
    {
        String returnMe = "*******Back Propagation*******\n";

        for (double num : errorArray)
        {
            returnMe += "layer error: " + num + "\n";
        }

        returnMe += "Prior layer error: ";

        for (double num : layerMinusOneError)
        {
            returnMe += num + ",  ";
        }

        return returnMe;
    }

}
