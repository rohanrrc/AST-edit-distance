import distance.EditDist;
import org.junit.Test;
import tree.LblTree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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

}
