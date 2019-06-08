package thesis2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Pattern
{

    private StringList sl;
    private Scanner scan;
    private double[] trueDistribution;
    private String line;

    /**
     * The Pattern constructor initializes an empty StringList; It creates
     * scanner with the specified file path;
     *
     * @param filePath
     */
    public Pattern(File filePath)
    {
       trueDistribution = new double[10];
        
        sl = new StringList();

        createScanner(filePath);
        getHeader();
        addDataLineToStringList();
        scan.close();
    }

    private void createScanner(File filePath)
    {
        try
        {
            scan = new Scanner(filePath);
        } 
        catch (FileNotFoundException e)
        {
            System.out.println("File not found");
        }
    }

    /**
     * This method retrieves the true distribution from the first line of the
     * data file and skips the next blank line; the true distribution is int[]
     * with element of 1 in the index of the correct number of the image.
     */
    private void getHeader()
    {
        line = scan.nextLine();
        String[] header = line.split(" ");
        
        for(int i = 0; i < header.length; i++)
        {
            trueDistribution[i] = Double.parseDouble(header[i]);
        }
        scan.nextLine();
    }

    /**
     * This method adds each line as a string to the initialized StringList
     * while the data file has more data.
     */
    private void addDataLineToStringList()
    {
        while (scan.hasNext())
        {
            sl.add(scan.nextLine());
        }
    }

    public double[] getTrueDistribution()
    {
        return trueDistribution;
    }

    /**
     *
     * @return the image length to determine data array size
     */
    public int getImageLength()
    {
        return sl.size();
    }

    public int getImageWidth()
    {
        return sl.get(0).length();
    }

    public String getStringPattern(int index)
    {
        return sl.get(index);
    }

    /**
     *
     * @return the entire pattern inside the individual file
     */
    @Override
    public String toString()
    {
        String returnMe = "";

        for (String aString : sl)
        {
            returnMe += aString + "\n";
        }

        return returnMe;
    }
}
