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
        if (listOfFiles.length == 0){
            return null;
        }
        LblTree lt1 = buildTree(listOfFiles[listOfFiles.length - 1]);
        double[] res = new double[listOfFiles.length-1];
        for (int i = 0; i < listOfFiles.length-1; i++)
        {
            String fs2 = this.astDir.resolve(listOfFiles[i].getName()).toString() ;
            try {
                LblTree lt2 = LblTree.fromString((new BufferedReader(new FileReader(fs2))).readLine());
                res[i] = new EditDist(true).nonNormalizedTreeDist(lt1,lt2);
            } catch (Exception e) {
                throw new Exception(fs2 + " argument has wrong format " + e.toString());
            }
        }
        return res;
    }

}
