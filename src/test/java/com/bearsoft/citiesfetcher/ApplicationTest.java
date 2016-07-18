package com.bearsoft.citiesfetcher;

import com.bearsoft.citiesfetcher.feed.BadCitiesJsonException;
import com.bearsoft.citiesfetcher.model.PartialCityJsonException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.function.Function;
import java.util.stream.Stream;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Fetcher test suite.
 *
 * @author mg
 */
public final class ApplicationTest {

    /**
     * CSV structure check predicate.
     *
     * @return CSV structure checking predicate.
     */
    private Function<String, Integer> csvCheck() {
        return (String aNextLine) -> {
            if (!aNextLine.isEmpty()) {
                String[] fields = aNextLine.split(",");
                if (fields.length == 5) {
                    return 1;
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
        };
    }

    /**
     * Tests a case with simple city name.
     *
     * @throws IOException if it is thrown in {@code Fetcher} code.
     * @throws BadArgumentsException if settings are bad.
     * @throws PartialCityJsonException If some part of mandatory data is
     * absent.
     * @throws BadCitiesJsonException if some bad structure discovered while
     * parsing process.
     * @throws FileAlreadyExistsException if file we have to write to already
     * exists.
     */
    @Test
    public void whenSimpleCity() throws IOException,
            BadArgumentsException,
            PartialCityJsonException,
            BadCitiesJsonException,
            FileAlreadyExistsException {
        int fetched = Application.run(new String[]{
            "Berlin"
        });
        assertTrue(fetched > 0);
        File expectedToBeWritten = new File("Berlin.csv");
        assertTrue(expectedToBeWritten.exists());
        try (Stream<String> lines = Files.lines(expectedToBeWritten.toPath(), StandardCharsets.UTF_8)) {
            int read = lines.map(csvCheck()).reduce(0, (Integer aAccumulated, Integer aNextValue) -> {
                return aAccumulated + aNextValue;
            });
            assertEquals(read, fetched);
        }
        expectedToBeWritten.delete();
    }

    /**
     * Tests a case with complex city name.
     *
     * @throws IOException if it is thrown in {@code Fetcher} code.
     * @throws BadArgumentsException if settings are bad.
     * @throws PartialCityJsonException If some part of mandatory data is
     * absent.
     * @throws BadCitiesJsonException if some bad structure discovered while
     * parsing process.
     * @throws FileAlreadyExistsException if file we have to write to already
     * exists.
     */
    @Test
    public void whenComplexCity() throws IOException,
            BadArgumentsException,
            PartialCityJsonException,
            BadCitiesJsonException,
            FileAlreadyExistsException {
        int fetched = Application.run(new String[]{
            "Frankfurt am Main"
        });
        assertTrue(fetched > 0);
        File expectedToBeWritten = new File("Frankfurt_am_Main.csv");
        assertTrue(expectedToBeWritten.exists());
        try (Stream<String> lines = Files.lines(expectedToBeWritten.toPath(), StandardCharsets.UTF_8)) {
            int read = lines.map(csvCheck()).reduce(0, (Integer aAccumulated, Integer aNextValue) -> {
                return aAccumulated + aNextValue;
            });
            assertEquals(read, fetched);
        }
        expectedToBeWritten.delete();
    }

    /**
     * Tests a case with unexistent city.
     *
     * @throws IOException if it is thrown in {@code Fetcher} code.
     * @throws BadArgumentsException if settings are bad.
     * @throws PartialCityJsonException If some part of mandatory data is
     * absent.
     * @throws BadCitiesJsonException if some bad structure discovered while
     * parsing process.
     * @throws FileAlreadyExistsException if file we have to write to already
     * exists.
     */
    @Test
    public void whenUnexistentCity() throws IOException,
            BadArgumentsException,
            PartialCityJsonException,
            BadCitiesJsonException,
            FileAlreadyExistsException {
        int fetched = Application.run(new String[]{
            "Some unexistent city :)"
        });
        assertEquals(0, fetched);
        File expectedToBeWritten = new File("Some_unexistent_city__).csv");
        assertTrue(expectedToBeWritten.exists());
        try (Stream<String> lines = Files.lines(expectedToBeWritten.toPath(), StandardCharsets.UTF_8)) {
            assertFalse(lines.findAny().isPresent());
        }
        expectedToBeWritten.delete();
    }

    /**
     * Tests a case with unexistent city and explicitely specified file without
     * an extension.
     *
     * @throws IOException if some problem with IO occurs.
     */
    @Test
    public void whenSpecifiedFileWithoutExtension()
            throws IOException {
        Application.main(new String[]{
            "Some unexistent city :)", "out-file"
        });
        File expectedToBeWritten = new File("out-file.csv");
        assertTrue(expectedToBeWritten.exists());
        try (Stream<String> lines = Files.lines(expectedToBeWritten.toPath(), StandardCharsets.UTF_8)) {
            assertFalse(lines.findAny().isPresent());
        }
        expectedToBeWritten.delete();
    }

    /**
     * Tests a case with unexistent city and explicitely specified file with an
     * extension.
     *
     * @throws IOException if some problem with IO occurs.
     */
    @Test
    public void whenSpecifiedFileWithExtension()
            throws IOException {
        Application.main(new String[]{
            "Some unexistent city :)", "out-file.csv"
        });
        File expectedToBeWritten = new File("out-file.csv");
        assertTrue(expectedToBeWritten.exists());
        try (Stream<String> lines = Files.lines(expectedToBeWritten.toPath(),
                StandardCharsets.UTF_8)) {
            assertFalse(lines.findAny().isPresent());
        }
        expectedToBeWritten.delete();
    }

    /**
     * Tests a case with unexistent city and explicitely specified file with an
     * extension.
     *
     * @throws IOException if some problem with IO occurs.
     */
    @Test
    public void whenSpecifiedFileWithExtensionAndComplexPath()
            throws IOException {
        Application.main(new String[]{
            "Some unexistent city :)", "../out-file.csv"
        });
        File expectedToBeWritten = new File("../out-file.csv");
        assertTrue(expectedToBeWritten.exists());
        try (Stream<String> lines = Files.lines(expectedToBeWritten.toPath(),
                StandardCharsets.UTF_8)) {
            assertFalse(lines.findAny().isPresent());
        }
        expectedToBeWritten.delete();
    }

    /**
     * Tests a case with laready existent file.
     *
     * @throws IOException if it is thrown in {@code Fetcher} code.
     * @throws BadArgumentsException if settings are bad.
     * @throws PartialCityJsonException If some part of mandatory data is
     * absent.
     * @throws BadCitiesJsonException if some bad structure discovered while
     * parsing process.
     * @throws FileAlreadyExistsException if file we have to write to already
     * exists.
     */
    @Test(expected = FileAlreadyExistsException.class)
    public void whenFileAlreadyExists() throws IOException,
            BadArgumentsException,
            PartialCityJsonException,
            BadCitiesJsonException,
            FileAlreadyExistsException {
        int fetched = Application.run(new String[]{
            "Berlin"
        });
        assertTrue(fetched > 0);
        File expectedToBeWritten = new File("Berlin.csv");
        assertTrue(expectedToBeWritten.exists());
        try (Stream<String> lines = Files.lines(expectedToBeWritten.toPath(), StandardCharsets.UTF_8)) {
            int read = lines.map(csvCheck()).reduce(0, (Integer aAccumulated, Integer aNextValue) -> {
                return aAccumulated + aNextValue;
            });
            assertEquals(read, fetched);
        }
        try {
            Application.run(new String[]{
                "Berlin"
            });
        } finally {
            expectedToBeWritten.delete();
        }
    }

}
