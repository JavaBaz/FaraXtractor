package com.FaraXtractor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class Main extends JFrame {

    private JLabel sourceLabel;
    private JLabel destinationLabel;
    private JTextField sourceField;
    private JTextField destinationField;
    private JButton sourceBrowseButton;
    private JButton destinationBrowseButton;
    private JButton moveButton;

    public Main() {
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
        sourceField.setEditable(false);
        addDropSupport(sourceField);

        destinationField = new JTextField(20);
        addDropSupport(destinationField);

        sourceBrowseButton = new JButton("Browse");
        sourceBrowseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                browseButtonActionPerformed(sourceField);
            }
        });

        destinationBrowseButton = new JButton("Browse");
        destinationBrowseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                browseButtonActionPerformed(destinationField);
            }
        });

        moveButton = new JButton("Move Files");
        moveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveButtonActionPerformed();
            }
        });
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

    private void addDropSupport(JTextField textField) {
        textField.setTransferHandler(new TransferHandler() {
            @Override
            public boolean canImport(TransferSupport support) {
                return support.isDataFlavorSupported(DataFlavor.stringFlavor);
            }

            @Override
            public boolean importData(TransferSupport support) {
                try {
                    Transferable transferable = support.getTransferable();
                    String text = (String) transferable.getTransferData(DataFlavor.stringFlavor);
                    textField.setText(text);
                    return true;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return false;
                }
            }
        });

        textField.setDropTarget(new DropTarget() {
            @Override
            public synchronized void drop(DropTargetDropEvent event) {
                try {
                    event.acceptDrop(DnDConstants.ACTION_COPY);
                    List<File> files = (List<File>) event.getTransferable()
                            .getTransferData(DataFlavor.javaFileListFlavor);
                    if (files.size() > 0) {
                        textField.setText(files.get(0).getAbsolutePath());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void browseButtonActionPerformed(JTextField textField) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Directory");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            textField.setText(selectedFile.getAbsolutePath());
        }
    }

    private void moveButtonActionPerformed() {
        String sourceDirectory = sourceField.getText();
        String destinationDirectory = destinationField.getText();

        if (sourceDirectory.isEmpty() || destinationDirectory.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select source and destination directories.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        moveFiles(sourceDirectory, destinationDirectory);

        JOptionPane.showMessageDialog(this, "Files moved successfully!",
                "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void moveFiles(String sourceDirectory, String destinationDirectory) {
        File sourceDir = new File(sourceDirectory);

        if (!sourceDir.exists() || !sourceDir.isDirectory()) {
            JOptionPane.showMessageDialog(this, "Invalid source directory.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        File[] subDirectories = sourceDir.listFiles(File::isDirectory);
        if (subDirectories == null || subDirectories.length == 0) {
            JOptionPane.showMessageDialog(this, "No subdirectories found in the source directory.",
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

    public static void main(String[] args) {
        try {
            // Set the FlatLaf look and feel
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
}
