/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thesis2;

/**
 *
 * @author erichoppe
 */
public class Functions
{

    private Functions()
    {
    }

    /*=========================================================================
                            Rectified Linear Unit
    =========================================================================*/
    public static double leakyRectifiedLinearUnit(double sum)
    {
        double output;

        if (sum > 0)
        {
            output = sum;
        } else
        {
            output = 0.02;
        }

        return output;
    }

    //=========================SoftMax function===================================
    public static double[] applySoftMax(double[] inputArray)
    {
        double[] pocket;
        double temp[] = new double[inputArray.length];
        double solution[] = new double[inputArray.length];
        double exponent, quotient, roundedQuotient;
        double total = 0;

        pocket = reduceByMax(inputArray);

        for (int i = 0; i < temp.length; i++)
        {
            exponent = calculateExponential(pocket[i]);
            if(exponent < 0.0001)
            {
                exponent = 0.0001;
            }
            
            temp[i] = exponent;
            total += temp[i];
        }

        for (int i = 0; i < inputArray.length; i++)
        {
            quotient = temp[i] / total;
            roundedQuotient = Math.round(quotient * 10000) / 10000.00;
            
            solution[i] = roundedQuotient;
        }
        return solution;
    }

    private static double[] reduceByMax(double[] inputArray)
    {
        double[] reducedArray = new double[inputArray.length];
        double maxValue = findMax(inputArray);
        for (int i = 0; i < inputArray.length; i++)
        {
            reducedArray[i] = inputArray[i] - maxValue;
        }
        return reducedArray;
    }

    private static double findMax(double[] inputArray)
    {
        double max = inputArray[0];
        for (int i = 0; i < inputArray.length; i++)
        {
            if (max < inputArray[i])
            {
                max = inputArray[i];
            }
        }
        return max;
    }

    private static double calculateExponential(double sum)
    {
        return Math.exp(sum);
    }

    /*=========================================================================
                            Tanh Function
    ==========================================================================*/
    /**
     *
     * @param x
     * @return the output of (e^x - e^-x)/(e^x + e^-x)
     */
    public static double applyTanh(double x)
    {
        return (Math.pow(Math.E, x) - Math.pow(Math.E, -x))
                / (Math.pow(Math.E, x) + Math.pow(Math.E, -x));
    }

    /*=========================================================================
                            Methods for Cross Entropy
    =========================================================================*/
    public static double crossEntropy(double[] trueDist, double[] activatedData)
    {
        double totalError = 0;
        int n = trueDist.length;

        for (int i = 0; i < n; i++)
        {
            totalError += getLogError(trueDist[i], activatedData[i]);
        }
        return totalError / n;
    }

    private static double getLogError(double target, double output)
    {
        double error, finalError;

        if (target == 1)
        {
            error = target * Math.log(output);
        } else
        {
            error = Math.log(1 - output);
        }
        finalError = Math.round(-error * 10000) / 10000.0;
        return finalError;
    }

    /*=========================================================================
                            Methods for Linear Algebra
    =========================================================================*/
    /**
     * Utilizes the naive multiplication algorithm; Since the second formal
     * argument is a vector and not a matrix, only a nested for-loop is required
     * and thus, it has O(n^2) runtime as opposed to the normal O(n^3).
     *
     * @param weightMatrix
     * @param errorVector
     * @return
     */
    public static double[] matrixVectorMultiply(double[][] weightMatrix,
            double[] errorVector)
    {
        double[] layerError = new double[weightMatrix.length];

        for (int i = 0; i < weightMatrix.length; i++)
        {
            double dotProduct = 0;
            for (int j = 0; j < weightMatrix[i].length; j++)
            {
                dotProduct += weightMatrix[i][j] * errorVector[j];
            }
            layerError[i] = dotProduct;
        }

        return layerError;
    }

    public static double findMin(double[][] matrix)
    {
        double min = 1000;
        for (double[] row : matrix)
        {
            for (double num : row)
            {
                if (min > num)
                {
                    min = num;
                }
            }

        }
        return min;
    }

    public static double findMax(double[][] matrix)
    {
        double max = 0;
        for (double[] row : matrix)
        {
            for (double num : row)
            {
                if (max < num)
                {
                    max = num;
                }
            }

        }
        return max;
    }

    /*=========================================================================
                            Methods for Debugging
    =========================================================================*/
    public static String arrayToString(double[] array)
    {
        String returnMe = "";

        for (double num : array)
        {
            returnMe += num + "\n";
        }
        return returnMe;
    }

    public static String matrixToString(double[][] matrix)
    {
        String returnMe = "";
        for (double[] row : matrix)
        {
            for (double value : row)
            {
                returnMe += value + " ";
            }
            returnMe += "\n";
        }

        return returnMe;
    }

    //=========================================================================
    public static Functions getInstance()
    {
        return FunctionsHolder.INSTANCE;
    }

    private static class FunctionsHolder
    {

        private static final Functions INSTANCE = new Functions();
    }
}
