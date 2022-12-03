package com.airportinfo.view.dialog;

import com.airportinfo.view.ComponentView;

import javax.swing.*;
import java.awt.*;

/**
 * Abstract dialog view.
 *
 * @author lalaalal
 */
public abstract class DialogView extends ComponentView {
    protected JDialog dialog = new JDialog();

    public DialogView() {
        dialog.setModal(true);
    }

    /**
     * Show dialog center of following component.
     *
     * @param component Component to position dialog
     */
    public void showDialogLocationRelativeTo(Component component) {
        dialog.setLocationRelativeTo(component);
        dialog.setVisible(true);
    }
}
