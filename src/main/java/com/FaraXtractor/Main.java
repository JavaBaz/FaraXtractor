package com.FaraXtractor;

import com.FaraXtractor.logic.FileMover;
import com.FaraXtractor.ui.helpers.DirectoryChooser;
import com.FaraXtractor.ui.helpers.FileDropHandler;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    private JLabel sourceLabel;
    private JLabel destinationLabel;
    private JTextField sourceField;
    private JTextField destinationField;
    private JButton sourceBrowseButton;
    private JButton destinationBrowseButton;
    private JButton moveButton;
    private FileMover fileMover;

    public Main(FileMover fileMover) {
        this.fileMover = fileMover;

        setTitle("FaraXtractor");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        initComponents();
        layoutComponents();

        pack();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        sourceLabel = new JLabel("Source Directory:");
        destinationLabel = new JLabel("Destination Directory:");

        sourceField = new JTextField(20);
        FileDropHandler.addDropSupport(sourceField);

        destinationField = new JTextField(20);
        FileDropHandler.addDropSupport(destinationField);

        sourceBrowseButton = new JButton("Browse");
        sourceBrowseButton.addActionListener(e -> DirectoryChooser.browseDirectory(this, sourceField));

        destinationBrowseButton = new JButton("Browse");
        destinationBrowseButton.addActionListener(e -> DirectoryChooser.browseDirectory(this, destinationField));

        moveButton = new JButton("Move Files");
        moveButton.addActionListener(e -> moveButtonActionPerformed());
    }

    private void layoutComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 5, 5, 5);

        panel.add(sourceLabel, constraints);

        constraints.gridy = 1;
        panel.add(destinationLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        panel.add(sourceField, constraints);

        constraints.gridy = 1;
        panel.add(destinationField, constraints);

        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0;
        panel.add(sourceBrowseButton, constraints);

        constraints.gridy = 1;
        panel.add(destinationBrowseButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 3;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(moveButton, constraints);

        add(panel);
    }

    private void moveButtonActionPerformed() {
        String sourceDirectory = sourceField.getText();
        String destinationDirectory = destinationField.getText();

        if (sourceDirectory.isEmpty() || destinationDirectory.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select source and destination directories.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        fileMover.moveFiles(sourceDirectory, destinationDirectory);

        JOptionPane.showMessageDialog(this, "Files moved successfully!",
                "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FileMover fileMover = new FileMover();
            new Main(fileMover).setVisible(true);
        });
    }
}
