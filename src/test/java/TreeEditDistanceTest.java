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
        assertEquals(null, new TreeEditDistanceArray(home_dir, "python").compute());
    }
}
