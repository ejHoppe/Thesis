package thesis2;

import java.util.Random;

public class Weight
{

    Random rand = new Random();
    private double weightValue;
    private int weightNum;
    private double sigma;
    private final double LEARNING_RATE = .01;

    /**
     * This constructor utilizes Xavier's Method to initialize the weight value
     * @param sizeOfWeightList
     * @param activation 
     */
    public Weight(int sizeOfWeightList, String activation)
    {
        this.weightNum = sizeOfWeightList;
        sigma = Math.sqrt(1.0 / weightNum);
        weightValue = rand.nextGaussian() * sigma;
        weightValue = Math.round(weightValue * 10000) / 10000.0;

    }

    public void decreaseWeightValue(double nuValue)
    {
        weightValue = weightValue - (LEARNING_RATE * nuValue);
        weightValue = Math.round(weightValue * 10000) / 10000.0;
    }
    
    public void increaseWeightValue(double nuValue)
    {
        weightValue = weightValue + (LEARNING_RATE * nuValue);
        weightValue = Math.round(weightValue * 10000) / 10000.0;
    }

    public double getWeightValue()
    {
        return weightValue;
    }

    @Override
    public String toString()
    {
        return "" + weightValue;
    }
}
