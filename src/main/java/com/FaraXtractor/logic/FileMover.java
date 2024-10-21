package com.FaraXtractor.logic;

import javax.swing.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileMover {

    public void moveFiles(String sourceDirectory, String destinationDirectory) {
        File sourceDir = new File(sourceDirectory);

        if (!sourceDir.exists() || !sourceDir.isDirectory()) {
            JOptionPane.showMessageDialog(null, "Invalid source directory.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        File[] subDirectories = sourceDir.listFiles(File::isDirectory);
        if (subDirectories == null || subDirectories.length == 0) {
            JOptionPane.showMessageDialog(null, "No subdirectories found in the source directory.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Path destinationPath = Path.of(destinationDirectory);

        for (File subDir : subDirectories) {
            moveFilesRecursively(subDir, destinationPath);
        }
    }

    private void moveFilesRecursively(File directory, Path destinationPath) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                try {
                    if (file.isDirectory()) {
                        moveFilesRecursively(file, destinationPath.resolve(file.getName()));
                    } else {
                        Path sourcePath = file.toPath();
                        Files.move(sourcePath, destinationPath.resolve(sourcePath.getFileName()),
                                StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}