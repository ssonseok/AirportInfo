package com.airportinfo.view.dialog;

import com.airportinfo.view.ComponentView;

import javax.swing.*;
import java.awt.*;

public abstract class DialogView extends ComponentView {
    protected JDialog dialog = new JDialog();

    public DialogView() {
        dialog.setModal(true);
    }

    public void showDialogLocationRelativeTo(Component c) {
        dialog.setLocationRelativeTo(c);
        dialog.setVisible(true);
    }
}
