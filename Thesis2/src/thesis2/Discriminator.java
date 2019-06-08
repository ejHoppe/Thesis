package thesis2;

import java.util.Random;

public class Discriminator
{

    NeuralNetwork neuralNet;
    ImageInput trainInput;

    double[][] imageData;
    double[] input, output, zOutput, xOutput,  genInput, trueDist;

    int index; //used by the discriminator when training X times
    
    Random rand = new Random();
    
    public Discriminator()
    {
        trainInput = new ImageInput("train2");
        trueDist = new double[10];
        zOutput = new double[10];
        xOutput = new double[10];
        
        
        neuralNet = new NeuralNetwork();
        neuralNet.addLayer("input", 784, 128, "none");
        neuralNet.addLayer("hidden", 128, 10, "leakyRelu");
        neuralNet.addLayer("output", 10, "softmax");

        index = 0;
    }

    public void trainDisc()
    {
        for (int epoch = 0; epoch < 3000; epoch++)
        {
            setTrainInput(epoch);
            train();
        }
    }

    public void testDisc(int index)
    {
        setTrainInput(index);
        train();
    }

    public void trainDiscXTimes(int intervals)
    {
        for (int i = 0; i < intervals; i++)
        {
            imageData = trainInput.getImageData(index);
            //System.out.println(Functions.matrixToString(imageData));
            input = flattenImage(imageData);
            trueDist = trainInput.getList().get(index).getTrueDistribution();

            //one-side label smoothing
            if (trueDist[i] == 1)
            {
                trueDist[i] = 0.9;
            }

            train();
            increaseIndex();
            System.out.println("index = " + index);
        }
    }
    
    public void trainDiscWithGenImage(double[] genInput)
    {
        zeroizeTrueDist(); //creates an array of all 0s
        
        neuralNet.setInput(genInput);
        neuralNet.forwardPropagate();
        output = neuralNet.getOutput();
        
        System.out.println("error: " + getError());
        if (getError() > 0.1)
        {
        }
    }
    
    private void zeroizeTrueDist()
    {
        
        for(int i = 0; i < trueDist.length; i++)
        {
            trueDist[i] = 0;
        }
    }

    public void sampleGeneratedImage(double[] genInput)
    {
        trueDist = new double[]{1,0,0,0,0,0,0,0,0,0};

        this.genInput = genInput; //will be used in backPropagation
        
        neuralNet.setInput(genInput);
        neuralNet.forwardPropagate();
        zOutput = neuralNet.getOutput();

        if (zOutput[0] == 0.0)
        {
            zOutput[0] = 0.01;
        }
    }
    
    public void sampleRealImage()
    {
        index = rand.nextInt(10000);
        
        setTrainInput(index);
        increaseIndex();
        
        neuralNet.setInput(input);
        neuralNet.forwardPropagate();
        xOutput = neuralNet.getOutput();
    }
    
    public void upDateDiscriminator()
    {
        neuralNet.ascendGradient(xOutput, zOutput, genInput, trueDist);
    }
    
    public double[] backPropForGenerator()
    {
        return neuralNet.backPropThroughDisc(zOutput, genInput);
    }

    private void setTrainInput(int index)
    {
        imageData = trainInput.getImageData(index); //sets a default image
        input = flattenImage(imageData);
        trueDist = trainInput.getList().get(index).getTrueDistribution();
    }

    private void train()
    {
        neuralNet.setInput(input); //set the input data
        neuralNet.forwardPropagate(); // propagate data forward
        output = neuralNet.getOutput(); //get output
        
        System.out.println("error: " + getError());
        if (getError() > 0.1)
        {
            neuralNet.backPropagate(output, trueDist, "softmax");
        }
    }

    private double[] flattenImage(double[][] image)
    {
        double[] flatImage = new double[image.length * image[0].length];

        int localIndex = 0;
        for (int i = 0; i < image.length; i++)
        {
            for (int j = 0; j < image[i].length; j++)
            {
                flatImage[localIndex] = image[i][j];
                localIndex++;
            }
        }

        return flatImage;
    }

    private void increaseIndex()
    {
        index++;

        if (index == getInputListSize())
        {
            index = 0;
        }
    }

    private double getError()
    {
        return Functions.crossEntropy(trueDist, output);
    }

    public double[][] getImageData()
    {
        return imageData;
    }

    public int getInputListSize()
    {
        return trainInput.getList().size();
    }
    
    public double[] getZOutput()
    {
        return zOutput;
    }

    public int getOutput()
    {
        int index = 0;
        double max = output[index];
        for(int i = 1; i < output.length; i++)
        {
            if(max < output[i])
            {
                max = output[i];
                index = i;
            }
        }
        return index;
    }

}
