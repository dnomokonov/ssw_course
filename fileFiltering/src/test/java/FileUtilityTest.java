import org.example.FileUtility;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.nio.file.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class FileUtilityTest {
    private FileUtility fileUtility;

    @BeforeEach
    void setUp() {
        fileUtility = new FileUtility();
    }

    @Test
    @DisplayName("testParseArgs")
    void testParseArgs() {
        String[] args = {"/src/test/resources/testFile1.txt", "/src/test/resources/testFile2.txt", "-o", "/src/test/resources/output/", "-p", "test_"};
        Set<String> inputFiles = new HashSet<>();

        fileUtility.parseArgs(args, inputFiles);

        assertTrue(inputFiles.contains("/src/test/resources/testFile1.txt"));
        assertTrue(inputFiles.contains("/src/test/resources/testFile2.txt"));
    }

    @Test
    @DisplayName("testParseArgsInvalidOption")
    void testParseArgsInvalidOption() {
        String[] args = {"/src/test/resources/testFile1.txt", "-o"};
        Set<String> inputFiles = new HashSet<>();

        fileUtility.parseArgs(args, inputFiles);

        assertFalse(inputFiles.contains("-o"));
    }

//      Windows ...\AppData\Local\Temp\testFile3966714511589827006.txt
//      Linux

    @Test
    @DisplayName("testFilterFiles")
    void testFilterFiles() throws IOException {
        File tempFile = File.createTempFile("testFile", ".txt"); // Create new temp file

        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("42\n");
            writer.write("3.14\n");
            writer.write("Hello\n");
        }

        Set<String> inputFiles = new HashSet<>();
        inputFiles.add(tempFile.getAbsolutePath());

        List<Integer> integers = new ArrayList<>();
        List<Double> floats = new ArrayList<>();
        List<String> strings = new ArrayList<>();

        fileUtility.filterFiles(inputFiles, integers, floats, strings);

        assertEquals(1, integers.size());
        assertEquals(1, floats.size());
        assertEquals(1, strings.size());

        tempFile.delete();
    }

    @Test
    @DisplayName("testWriterResultOnFiles")
    void testWriterResultOnFiles() throws IOException {
        List<String> data = Arrays.asList("Hello", "World");
        String filename = "test_output.txt";

        fileUtility.writerResultOnFiles(filename, data);

        Path path = Paths.get(System.getProperty("user.dir"), filename);
        assertTrue(Files.exists(path));

        List<String> lines = Files.readAllLines(path);
        assertEquals(data, lines);

        Files.delete(path);
    }

}
