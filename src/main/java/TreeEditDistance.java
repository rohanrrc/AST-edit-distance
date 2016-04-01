import tree.LblTree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;

public class TreeEditDistance {
    protected Path astDir;

    public TreeEditDistance(Path homeDir, String lang) {
        if (lang.equals("ruby")) {
            this.astDir = homeDir.resolve("jast");
        } else {
            this.astDir = homeDir.resolve("ast");
        }
    }

    protected LblTree buildTree(File f) throws Exception {
        String filename = this.astDir.resolve(f.getName()).toString();
        try {
            return LblTree.fromString((new BufferedReader(new FileReader(filename))).readLine());
        } catch (Exception e) {
            throw new Exception("TREE1 argument has wrong format "+e.toString());
        }
    }

    protected File[] getFiles() throws Exception {
        String fs2;
        File folder = new File(this.astDir.toUri());
        File[] listOfFiles = folder.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith("ast");
            }
        });

        try {
            Arrays.sort(listOfFiles,
                    new Comparator<File>() {
                        public int compare(File item1, File item2) {
                            return ((Integer) Integer.parseInt(item1.getName().split("[._a-z]")[0]))
                                    .compareTo((Integer) Integer.parseInt(item2.getName().split("[._a-z]")[0]));
                        }
                    }
            );
        } catch (NullPointerException e) {
            throw new Exception("No AST files found.");
        }
        return listOfFiles;
    }

    public Path getDir(){
        return this.astDir;
    }
}
