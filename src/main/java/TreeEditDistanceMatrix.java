import java.io.*;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;

import distance.EditDist;
import tree.LblTree;

//job: understands how to compute a tree edit distance matrix
public class TreeEditDistanceMatrix extends TreeEditDistance{

    public TreeEditDistanceMatrix(Path homeDir, String lang) throws IOException {
        super(homeDir, lang);
    }

    public double[][] compute() throws Exception {
        File[] listOfFiles = getFiles();
        LblTree[] trees = new LblTree[listOfFiles.length];
        for (int i = 0; i < listOfFiles.length; i++)
        {
            trees[i] = buildTree(listOfFiles[i]);
        }
        double[][] distmatrix = new double[listOfFiles.length][listOfFiles.length];
        for (int i = 0; i < listOfFiles.length; i++)
        {
            for (int j = i; j < listOfFiles.length; j++)
            {
                double dist = Math.random(); //to do: implement TED here
                if (trees[i] == trees[j]){
                    dist = 0;
                }
                distmatrix[i][j] = dist;
                distmatrix[j][i] = dist;
            }
        }
        return  distmatrix;
    }

}