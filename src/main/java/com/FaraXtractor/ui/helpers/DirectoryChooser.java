package com.FaraXtractor.ui.helpers;

import javax.swing.*;
import java.io.File;

public class DirectoryChooser {

    public static void browseDirectory(JFrame parent, JTextField textField) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Directory");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int result = fileChooser.showOpenDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            textField.setText(selectedFile.getAbsolutePath());
        }
    }
}
