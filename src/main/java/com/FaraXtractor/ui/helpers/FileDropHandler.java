package com.FaraXtractor.ui.helpers;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;

public class FileDropHandler {

    public static void addDropSupport(JTextField textField) {
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
}