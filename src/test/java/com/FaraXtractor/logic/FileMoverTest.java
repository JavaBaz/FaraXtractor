package com.FaraXtractor.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileMoverTest {

    private FileMover fileMover;
    private Path tempSource;
    private Path tempDestination;

    @BeforeEach
    void setUp() throws Exception {
        fileMover = new FileMover();
        tempSource = Files.createTempDirectory("sourceDir");
        tempDestination = Files.createTempDirectory("destinationDir");
    }


    @Test
    @DisplayName("Moving Files From Invalid Source Directory Should face Error")
    void testMoveFiles_withInvalidSourceDirectory() {
        String invalidSource = "/non/existent/path";
        fileMover.moveFiles(invalidSource, tempDestination.toString());
        assertFalse(Files.exists(Path.of(invalidSource)));
    }


    @Test
    @DisplayName("Moving Files From Empty Source Directory Should face Error")
    void testMoveFiles_withEmptySourceDirectory() {
        fileMover.moveFiles(tempSource.toString(), tempDestination.toString());
        assertEquals(0, tempDestination.toFile().list().length);
    }
}
