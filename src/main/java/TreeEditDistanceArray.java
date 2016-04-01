import java.io.*;
import java.nio.file.Path;
import distance.EditDist;
import tree.LblTree;

//job: understands how to compute the tree edit distance between a given AST and every other in a directory
public class TreeEditDistanceArray extends TreeEditDistance{

    public TreeEditDistanceArray(Path homeDir, String lang) throws IOException {
        super(homeDir, lang);
    }

    public double[] compute() throws Exception {
        File[] listOfFiles = getFiles();
        LblTree lt1 = buildTree(listOfFiles[listOfFiles.length - 1]);
        double[] res = new double[listOfFiles.length-1];
        return res;
    }

}
