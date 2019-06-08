package thesis2;

public class NeuralNetwork extends Network
{
    private Propagation backProp;
    
    public NeuralNetwork(){}

    public void setInput(double[] inputArray)
    {
        input = new double[inputArray.length];
        System.arraycopy(inputArray, 0, input, 0, inputArray.length);
    }

    public void forwardPropagate()
    {
        for(Layer aLayer : layerList)
        {
            aLayer.setInput(input);
            aLayer.feedDataThroughNodes();
            aLayer.getWeightedOutput();
            input = aLayer.applySummation();
            
            if(aLayer.getActivation().equals("softmax"))
            {
                input = Functions.applySoftMax(input);
            }
        }
        output = input;
    }
    
    public double[] getOutput()
    {
        return output;
    }
    
    public void backPropagate(
            double[] outputData, double[] trueDist, String activation)
    {
        backProp = new Propagation(outputData, trueDist, activation);
        backProp.setLayerAndWeights(layerFromList(2), layerFromList(1));
        backProp.populateErrorArray();

        layerFromList(1).setErrorArray(backProp.getErrorArray());
        layerFromList(0).setErrorArray(backProp.calcLayerMinusOneError());

        layerFromList(1).computeGradientDecreaseWeights();
        layerFromList(0).computeGradientDecreaseWeights();
    }
    
    public void ascendGradient(
            double[] xOutput, 
            double[] zOutput, 
            double[] genInput, 
            double[] trueDist)
    {
        backProp = new Propagation(xOutput, zOutput, genInput, trueDist);        
        backProp.setLayerAndWeights(layerFromList(2), layerFromList(1));
        backProp.populateErrorArray("disc");
        
        layerFromList(1).setErrorArray(backProp.getErrorArray());
        layerFromList(0).setErrorArray(backProp.calcLayerMinusOneError());
        
        layerFromList(1).computeGradientIncreaseWeights();
        layerFromList(0).computeGradientIncreaseWeights();
    }
    
    public double[] backPropThroughDisc(double[] zOutput, double[] genInput)
    {
        backProp = new Propagation(zOutput, genInput);
        backProp.setLayerAndWeights(
                layerFromList(2), layerFromList(1), layerFromList(0));
        
        backProp.populateErrorArray("gen");        
        backProp.calcLayerMinusOneError();
        
        return backProp.calcLayerMinusTwoError();
    }
    
    public void descendGradient(double[] errorArray)
    {
        backProp = new Propagation(errorArray);
        backProp.setLayerAndWeights(layerFromList(2), layerFromList(1));
        
        layerFromList(1).setErrorArray(backProp.getErrorArray());
        layerFromList(0).setErrorArray(backProp.calcLayerMinusOneError());
        
        layerFromList(1).computeGradientDecreaseWeights();
        layerFromList(0).computeGradientDecreaseWeights();
    }
    
}
