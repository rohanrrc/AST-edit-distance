import distance.EditDist;
import org.junit.Test;
import tree.LblTree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class TreeEditDistanceTest {
    @Test
    public void treeEditDistanceBetweenIdenticalASTsShouldBeZero() throws Exception {
        String ast = "resources/python/test_subset/ast/1.ast";
        LblTree lt = LblTree.fromString((new BufferedReader(new FileReader(ast))).readLine());
        assertEquals(0, new EditDist(true).nonNormalizedTreeDist(lt,lt), 0.001);
    }

    @Test
    public void lengthOfTreeEditArrayShouldEqualNumberOfFilesInDirectoryMinusOne() throws Exception {
        Path home_dir = Paths.get("resources/python/test_subset/");
        int num_files = new File(home_dir.resolve("ast").toUri()).list().length;
        assertEquals(num_files - 1, new TreeEditDistanceArray(home_dir, "python").compute().length);
    }

    @Test
    public void treeEditDistanceArrayOfTestSubsetShouldEqualExpectedValues() throws Exception {
        Path directory = Paths.get("resources/python/test_subset/");
        double[] arr = new TreeEditDistanceArray(directory, "python").compute();
        double[] expected = {76, 73, 65, 70};
        assertArrayEquals(expected, arr, 0.001);
    }

    @Test
    public void systemShouldUseJastFolderForRubyASTs() throws Exception {
        Path directory = Paths.get("resources/ruby/test_subset/");
        Path expected = Paths.get("resources/ruby/test_subset/jast");
        assertEquals(expected, new TreeEditDistance(directory, "ruby").getDir());
    }

    @Test(expected = RuntimeException.class)
    public void malformedASTShouldThrowException() throws Exception{
        Path home_dir = Paths.get("resources/python/malformed_subset/");
        new TreeEditDistanceArray(home_dir, "python").compute();
    }

    @Test
    public void emptyDirectoriesShouldReturnNull() throws Exception{
        Path home_dir = Paths.get("resources/python/empty_subset/");
        TreeEditDistanceArray TEDa = new TreeEditDistanceArray(home_dir, "python");
        assertEquals(null, TEDa.compute());
    }

    @Test
    public void sizeOfEachSideOfDistanceMatrixShouldEqualToNumberOfItemsInDirectory() throws Exception {
        Path home_dir = Paths.get("resources/python/test_subset/");
        int num_files = new File(home_dir.resolve("ast").toUri()).list().length;
        double[][] matrix = new TreeEditDistanceMatrix(home_dir, "python").compute();
        assertEquals(num_files, matrix.length);
        assertEquals(num_files, matrix[0].length);
    }

    @Test
    public void elementIJofTEDMatrixShouldEqualElementJIofTEDMatrix() throws Exception {
        Path directory = Paths.get("resources/python/test_subset/");
        double[][] matrix = new TreeEditDistanceMatrix(directory, "python").compute();
        for (int i = 0; i < matrix.length; i++)
        {
            for (int j = i; j < matrix.length; j++)
            {
                assertEquals(matrix[i][j], matrix[j][i], 0.001);
            }
        }
    }

    @Test
    public void entriesAlongDiagonalOfDistanceMatrixShouldBeZero() throws Exception {
        Path directory = Paths.get("resources/python/test_subset/");
        double[][] matrix = new TreeEditDistanceMatrix(directory, "python").compute();
        for (int i = 0; i < matrix.length; i++){
            assertEquals(0, matrix[i][i], 0.001);
        }
    }

    @Test
    public void treeEditDistanceMatrixOfTestSubsetShouldEqualExpectedValues() throws Exception {
        Path directory = Paths.get("resources/python/test_subset/");
        double[][] matrix = new TreeEditDistanceMatrix(directory, "python").compute();
        double[][] expected = {{0, 49, 86, 34, 76},
                {49, 0, 87, 57, 73},
                {86, 87, 0, 102, 65},
                {34, 57, 102, 0, 70},
                {76, 73, 65, 70, 0}};
        for (int i = 0; i < matrix.length; i++){
            assertArrayEquals(expected[i], matrix[i], 0.001);
        }
    }
}
