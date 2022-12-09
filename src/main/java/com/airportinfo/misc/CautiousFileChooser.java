package com.airportinfo.misc;

import com.airportinfo.util.Translator;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.function.Consumer;

/**
 * File chooser confirm overwrite.
 *
 * @author lalaalal
 */
public class CautiousFileChooser extends JFileChooser {
    public void showFileChooser(Component parentComponent, Consumer<File> consumer) {
        setPreferredSize(new Dimension(700, 500));
        setDialogTitle(Translator.getBundleString("save"));
        if (showSaveDialog(parentComponent) == JFileChooser.APPROVE_OPTION
                && canWrite(parentComponent, getSelectedFile())) {
            consumer.accept(getSelectedFile());
        }
    }

    private boolean canWrite(Component parentComponent, File file) {
        if (file.exists()) {
            String title = Translator.getBundleString("alert");
            String message = Translator.getBundleString("confirm_overwrite");
            int result = JOptionPane.showConfirmDialog(parentComponent, message, title, JOptionPane.OK_CANCEL_OPTION);
            return result == JOptionPane.OK_OPTION;
        }
        return true;
    }
}
