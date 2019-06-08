package thesis2;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Evaluator
{

    Main main;
    Discriminator disc;
    Generator gen;

    double[][] imageData;
    double[] genImage;

    private int index;

    boolean firstTime = true;

    public Evaluator(Main main)
    {
        index = 0;

        this.main = main;
        disc = new Discriminator();
        gen = new Generator();
    }

    public void handleButtonClicked(Button btn)
    {
        switch (btn.getText())
        {
            case "Train NN":
                disc.trainDisc();
                break;

            case "Test NN":
                disc.testDisc(index);
                imageData = disc.getImageData();
                drawNumber(20, 20);
                getResult();
                increaseIndex();
                break;

            case "Generate":

                for (int i = 0; i < 1; i++)
                {
                    //sample generated image 
                    gen.generateNoiseVector();
                    gen.generateImage();
                    disc.sampleGeneratedImage(gen.getOutputImage());

                    //sample real image
                    disc.sampleRealImage();

                    disc.upDateDiscriminator();

                }

                gen.generateNoiseVector();
                gen.generateImage();
                disc.sampleGeneratedImage(gen.getOutputImage());

                gen.updateGenerator(disc.backPropForGenerator());

                //reshape image to so it can be drawn
                imageData = reshapeImage(gen.getOutputImage());
                normalizeImage(imageData);
                //System.out.println(Functions.matrixToString(imageData));
                drawImage(300, 20);
                break;

            case "Reset":
                disc = new Discriminator();
                gen = new Generator();
        }
    }

    private void normalizeImage(double[][] matrix)
    {
        //find the max and min value of the image
        double max = Functions.findMax(matrix);
        double min = Functions.findMin(matrix);
        convertMatrix(matrix, max, min);
    }

    private void convertMatrix(double[][] matrix, double max, double min)
    {
        for (double[] row : matrix)
        {
            for (int j = 0; j < matrix.length; j++)
            {
                double x = row[j];
                double nuX = (x - min) / (max - min);
                row[j] = Math.round(nuX * 10) / 10.0;
            }
        }
    }

    private void getResult()
    {
        switch (disc.getOutput())
        {
            case 0:
                main.result.setText("Neural Network Label: 0");
                break;
            case 1:
                main.result.setText("Neural Network Label: 1");
                break;
            case 2:
                main.result.setText("Neural Network Label: 2");
                break;
            case 3:
                main.result.setText("Neural Network Label: 3");
                break;
            case 4:
                main.result.setText("Neural Network Label: 4");
                break;
            case 5:
                main.result.setText("Neural Network Label: 5");
                break;
            case 6:
                main.result.setText("Neural Network Label: 6");
                break;
            case 7:
                main.result.setText("Neural Network Label: 7");
                break;
            case 8:
                main.result.setText("Neural Network Label: 8");
                break;
            case 9:
                main.result.setText("Neural Network Label: 9");
                break;
        }
    }

    private void increaseIndex()
    {
        index++;

        if (index == disc.getInputListSize())
        {
            index = 0;
        }
    }

    private double[][] reshapeImage(double[] array)
    {
        index = 0;
        double[][] image = new double[28][28];

        for (int i = 0; i < 28; i++)
        {
            for (int j = 0; j < 28; j++)
            {
                image[i][j] = array[index++];

            }
        }
        return image;
    }

    /*=========================================================================
                        Methods used to draw graphics
    =========================================================================*/
    private void drawImage(int startX, int startY)
    {
        for (int row = 0; row < imageData.length; row++)
        {
            for (int col = 0; col < imageData.length; col++)
            {
                int width = col * 10;
                int height = row * 10;

//                if (imageData[row][col] < 0.50)
//                {
//                    drawBlackSquare(height, width);
//                } else
//                {
//                    drawWhiteSquare(height, width);
//                }
                drawSquare(row, col, startX + width, startY + height);
            }
        }

    }

    private void drawSquare(int row, int col, int startX, int startY)
    {
        Rectangle square = new Rectangle(
                10, 10,
                Color.gray(imageData[row][col]));

        square.setX(startX);
        square.setY(startY);

        main.root.getChildren().add(square);
    }

    private void drawNumber(int startX, int startY)
    {
        for (int row = 0; row < imageData.length; row++)
        {
            for (int col = 0; col < imageData.length; col++)
            {
                int width = col * 10;
                int height = row * 10;

                if (imageData[row][col] > 0)
                {
                    drawWhiteSquare(startX + width, startY + height);
                } else
                {
                    drawBlackSquare(startX + width, startY + height);
                }
            }
        }
    }

    private void drawWhiteSquare(int startX, int startY)
    {
        Rectangle square = new Rectangle(10, 10, Color.WHITE);
        square.setX(startX);
        square.setY(startY);

        main.root.getChildren().add(square);
    }

    private void drawBlackSquare(int startX, int startY)
    {
        Rectangle square = new Rectangle(10, 10, Color.BLACK);
        square.setX(startX);
        square.setY(startY);

        main.root.getChildren().add(square);
    }

}
