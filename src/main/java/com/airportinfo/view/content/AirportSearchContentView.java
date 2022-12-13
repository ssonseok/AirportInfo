package com.airportinfo.view.content;

import com.airportinfo.Setting;
import com.airportinfo.controller.AirportController;
import com.airportinfo.controller.UserController;
import com.airportinfo.view.airport.AirportAttributeSelector;
import com.airportinfo.swing.BorderedTextField;
import com.airportinfo.swing.CautiousFileChooser;
import com.airportinfo.model.Airport;
import com.airportinfo.model.MouseReleaseListener;
import com.airportinfo.util.Translator;
import com.airportinfo.util.filewriter.AirportWriter;
import com.airportinfo.view.AirportFrame;
import com.airportinfo.view.Storable;
import com.airportinfo.view.airport.AirportTableView;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Airport search ContentView. Storable table to csv, json.
 *
 * @author lalaalal
 */
public class AirportSearchContentView extends ContentView implements Storable {
    private JPanel panel;
    private JComboBox<AirportAttributeSelector> attributeComboBox;
    private JTextField searchTextField;
    private JButton searchButton;
    private JPanel airportTablePanel;
    private final CautiousFileChooser fileChooser = new CautiousFileChooser();
    private final AirportTableView airportTableView = new AirportTableView();
    private final AirportController airportController;

    public AirportSearchContentView(AirportFrame mainFrame) {
        super(mainFrame);

        $$$setupUI$$$();
        airportController = mainFrame.getAirportController();
        UserController userController = mainFrame.getUserController();

        addLocaleChangeListener((locale) -> {
            attributeComboBox.removeAllItems();
            for (String localizedAttributeName : Airport.ATTRIBUTE_NAMES) {
                AirportAttributeSelector attributeSelector = new AirportAttributeSelector(localizedAttributeName);
                attributeComboBox.addItem(attributeSelector);
            }
            searchButton.setText(Translator.getBundleString("search"));
        });
        searchButton.addMouseListener(new MouseReleaseListener(mouseEvent -> search()));
        airportTableView.addMouseClickAction((mouseEvent) -> {
            if (mouseEvent.getClickCount() == 2) {
                Airport selected = airportTableView.getSelectedAirport();
                airportController.selectAirport(selected);
                userController.addRecent(selected);
                mainFrame.setContentView(AirportFrame.AIRPORT_DETAIL_VIEW);
            }
        });
        searchTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    search();
            }
        });

        addComponentView(airportTableView);
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void load() {
        airportTableView.setAirports(List.of(airportController.getAirports()));
    }

    @Override
    public void store() {
        String defaultExtension = Setting.getInstance().getAirportTableExtension();
        fileChooser.resetChoosableFileFilters();
        for (String extension : Setting.SUPPORTED_AIRPORT_TABLE_SAVE_EXTENSIONS) {
            if (!extension.equals(defaultExtension))
                fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(extension, extension));
        }
        fileChooser.setFileFilter(new FileNameExtensionFilter(defaultExtension, defaultExtension));
        fileChooser.showFileChooser(mainFrame.getPanel(), this::store);
    }

    @Override
    public void store(File file) {
        String path = ensureExtension(file);
        try (AirportWriter writer = AirportWriter.create(path)) {
            writer.write(airportTableView.getAirports());
        } catch (IllegalArgumentException e) {
            String title = Translator.getBundleString("error");
            String message = Translator.getBundleString("unsupported_extension");
            JOptionPane.showMessageDialog(mainFrame.getPanel(), message, title, JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            String title = Translator.getBundleString("error");
            String message = Translator.getBundleString("save_error");
            JOptionPane.showMessageDialog(mainFrame.getPanel(), message, title, JOptionPane.ERROR_MESSAGE);
        }
    }

    private String ensureExtension(File file) {
        String path = file.getPath();
        String selectedExtension = fileChooser.getFileFilter().getDescription();
        if (Arrays.stream(Setting.SUPPORTED_AIRPORT_TABLE_SAVE_EXTENSIONS).anyMatch(s -> path.endsWith("." + s)))
            return path;

        if (List.of(Setting.SUPPORTED_AIRPORT_TABLE_SAVE_EXTENSIONS).contains(selectedExtension))
            return path + "." + selectedExtension;
        return path + "." + Setting.getInstance().getAirportTableExtension();
    }

    private void search() {
        String searchTerm = searchTextField.getText();
        AirportAttributeSelector attributeSelector = (AirportAttributeSelector) attributeComboBox.getSelectedItem();
        if (attributeSelector == null)
            return;

        ArrayList<Airport> searchAirports = airportController.search((airport)
                -> attributeSelector.getAttributeValue(airport).contains(searchTerm));
        airportTableView.setAirports(searchAirports);
    }

    private void createUIComponents() {
        attributeComboBox = new JComboBox<>();
        airportTablePanel = airportTableView.getPanel();
        searchTextField = new BorderedTextField();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        panel = new JPanel();
        panel.setLayout(new GridLayoutManager(2, 1, new Insets(15, 15, 15, 15), -1, 10));
        panel.add(airportTablePanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), 10, -1));
        panel.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        panel1.add(attributeComboBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(150, -1), null, null, 0, false));
        panel1.add(searchTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(150, -1), null, 0, false));
        searchButton = new JButton();
        this.$$$loadButtonText$$$(searchButton, this.$$$getMessageFromBundle$$$("string", "search"));
        panel1.add(searchButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    }

    private static Method $$$cachedGetBundleMethod$$$ = null;

    private String $$$getMessageFromBundle$$$(String path, String key) {
        ResourceBundle bundle;
        try {
            Class<?> thisClass = this.getClass();
            if ($$$cachedGetBundleMethod$$$ == null) {
                Class<?> dynamicBundleClass = thisClass.getClassLoader().loadClass("com.intellij.DynamicBundle");
                $$$cachedGetBundleMethod$$$ = dynamicBundleClass.getMethod("getBundle", String.class, Class.class);
            }
            bundle = (ResourceBundle) $$$cachedGetBundleMethod$$$.invoke(null, path, thisClass);
        } catch (Exception e) {
            bundle = ResourceBundle.getBundle(path);
        }
        return bundle.getString(key);
    }

    /**
     * @noinspection ALL
     */
    private void $$$loadButtonText$$$(AbstractButton component, String text) {
        StringBuffer result = new StringBuffer();
        boolean haveMnemonic = false;
        char mnemonic = '\0';
        int mnemonicIndex = -1;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '&') {
                i++;
                if (i == text.length()) break;
                if (!haveMnemonic && text.charAt(i) != '&') {
                    haveMnemonic = true;
                    mnemonic = text.charAt(i);
                    mnemonicIndex = result.length();
                }
            }
            result.append(text.charAt(i));
        }
        component.setText(result.toString());
        if (haveMnemonic) {
            component.setMnemonic(mnemonic);
            component.setDisplayedMnemonicIndex(mnemonicIndex);
        }
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }

}
