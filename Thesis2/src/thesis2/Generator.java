package thesis2;

import java.util.Random;

public class Generator
{

    private double[] noiseVector, outputImage, zOutput;
    private final double[] TRUE_DIST = new double[]{1,0,0,0,0,0,0,0,0,0};
    
    Random rand;
    NeuralNetwork neuralNet;

    public Generator()
    {
        rand = new Random();

        neuralNet = new NeuralNetwork();
        neuralNet.addLayer("input", 100, 128, "none");
        neuralNet.addLayer("hidden", 128, 784, "leakyRelu");
        neuralNet.addLayer("output", 784, "tanh");
    }

    public void generateNoiseVector()
    {
        noiseVector = new double[100];
        double coinFlip;

        for (int i = 0; i < 100; i++)
        {
            coinFlip = rand.nextDouble();

            if (coinFlip >= 0.5)
            {
                noiseVector[i] = rand.nextDouble();
            } else
            {
                noiseVector[i] = -rand.nextDouble();
            }
        }
    }

    public void generateImage()
    {
        neuralNet.setInput(noiseVector);
        neuralNet.forwardPropagate();
        outputImage = neuralNet.getOutput();
    }

    public double[] getOutputImage()
    {
        return outputImage;
    }

    public void updateGenerator(double[] errorArray)
    {
        neuralNet.descendGradient(errorArray);
    }
}
