package thesis2;

import java.io.File;

public class ImageInput
{

    FileReader trainReader;
    PatternList trainList;

    public ImageInput(String directory)
    {
        trainReader = new FileReader(directory);
        trainList = new PatternList();

        trainReader.fillList(trainList);
    }

    /**
     * This method retrieves the image pattern from the specified patternList at
     * the specified index;
     *
     * @param index
     * @return double[][] populated with 0s or 1s based on the retrieved
     * pattern.
     */
    public double[][] getImageData(int index)
    {
        Pattern thePattern = getPatternAtIndex(index);

        double[][] aList = new double[28][28];

        for (int i = 0; i < 28; i++)
        {
            String[] result = thePattern.getStringPattern(i).split("\\s");
            int j = 0;
            for (String result1 : result)
            {
                aList[i][j] = Double.parseDouble(result1);
                j++;
            }
        }
        return aList;
    }

    private Pattern getPatternAtIndex(int index)
    {
        return trainList.get(index);
    }

    public PatternList getList()
    {
        return trainList;
    }

    private static class FileReader
    {

        private final String USER_DIR, NUM_DIR;
        private File dataDir;

        /**
         * The FileReader constructor initializes the String USER_DIR with the
         * value of the current project directory; It the initializes the String
         * NUM_DIR with the concatenation of USER_DIR + "/NumberData/" so the
         * FileReader is set up to access the currentDirectory-NumberData
         * folder.
         */
        public FileReader(String dirName)
        {
            USER_DIR = System.getProperty("user.dir");
            NUM_DIR = USER_DIR + "/" + dirName;
        }

        /**
         * This method opens a folder specified by dirName and populates the
         * specified PatternList with the files inside that folder; It skips the
         * file named ".DS_Store".
         *
         * @param dirName
         * @param pList
         */
        public void fillList(PatternList pList)
        {
            dataDir = new File(NUM_DIR);
            java.io.File[] list = dataDir.listFiles();
            for (File aFile : list)
            {
                if (!aFile.getName().equals(".DS_Store"))
                {
                    pList.add(new Pattern(aFile));
                }
            }
        }
    }
}
