package com.airportinfo.view;

import com.airportinfo.controller.AirportController;
import com.airportinfo.controller.UserController;
import com.airportinfo.util.FontManager;
import com.airportinfo.util.Translator;
import com.airportinfo.view.dialog.SettingDialogView;
import com.airportinfo.view.menubar.AirportMenuBar;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;

/**
 * Main frame of AirportInfo.
 *
 * @author lalaalal
 */
public class AirportFrame extends MainFrame {
    public static final String AIRPORT_DETAIL_VIEW = "airport_detail_view";
    public static final String AIRPORT_SEARCH_VIEW = "airport_search_view";
    private JPanel contentPanel;
    private JPanel toolbarPanel;
    private JPanel sidebarPanel;
    private JPanel rootPanel;
    private final AirportMenuBar menuBar = new AirportMenuBar();
    private final AirportToolbar airportToolBar = new AirportToolbar();
    private final AirportSidebar airportSideBar;
    private final AirportController airportController = new AirportController();
    private final UserController userController = new UserController();
    private final SettingDialogView settingDialogView = new SettingDialogView(userController);

    public AirportFrame() {
        super(Translator.getBundleString("application_name"), 1280, 720);

        airportSideBar = new AirportSidebar(this);
        $$$setupUI$$$();

        JLabel loadingLabel = new JLabel(Translator.getBundleString("loading"), SwingConstants.CENTER);
        loadingLabel.setFont(FontManager.getFont(FontManager.HEADER_FONT_SIZE).deriveFont(Font.BOLD));
        frame.add(loadingLabel);
        initToolbar();
        initMenuBar();

        addComponentView(airportToolBar);
        addComponentView(airportSideBar);
        addComponentView(settingDialogView);
    }

    @Override
    public JPanel getPanel() {
        return rootPanel;
    }

    private void initToolbar() {
        airportToolBar.addLabel("search", (event) -> setContentView(AIRPORT_SEARCH_VIEW));
        JLabel saveLabel = airportToolBar.addLabel("save", (event) -> storeContent());
        airportToolBar.addLabelRight("toggle_theme", (event) -> toggleTheme());
        airportToolBar.addLabelRight("english", (event) -> changeLocale(Locale.ENGLISH));
        airportToolBar.addLabelRight("korean", (event) -> changeLocale(Locale.KOREAN));
        // TODO : Fill toolbar.

        addContentChangeListener((selected) -> saveLabel.setEnabled(selected instanceof Storable));
    }

    private void initMenuBar() {
        menuBar.addMenu("file");
        JMenuItem saveMenuItem = menuBar.addMenuItem("file", "save", (event) -> storeContent());
        menuBar.addMenuItem("file", "setting", (event) -> settingDialogView.showDialogLocationRelativeTo(frame));
        menuBar.addMenuSeparator("file");
        menuBar.addMenuItem("file", "exit", (event) -> frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)));
        menuBar.addMenu("view");
        menuBar.addMenuItem("view", "search", (event) -> setContentView(AIRPORT_SEARCH_VIEW));
        // TODO : Fill menus.

        frame.setJMenuBar(menuBar);

        addContentChangeListener((selected) -> saveMenuItem.setEnabled(selected instanceof Storable));
    }

    private void storeContent() {
        if (currentContentView instanceof Storable storable)
            storable.store();
    }

    /**
     * Call after showFrame().
     */
    @Override
    public void load() {
        frame.setTitle(Translator.getBundleString("application_name"));
        String title = Translator.getBundleString("error");
        try {
            userController.load();
            airportController.loadFromDB();
        } catch (FileNotFoundException ignored) {

        } catch (SQLException | IOException e) {
            String message = Translator.getBundleString("cannot_load");
            JOptionPane.showMessageDialog(frame, message, title, JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            String message = Translator.getBundleString("contact_developer");
            JOptionPane.showMessageDialog(frame, message, title, JOptionPane.ERROR_MESSAGE);
        }
        frame.setContentPane(rootPanel);
        SwingUtilities.invokeLater(() -> setTheme(AppTheme.Lite));
    }

    @Override
    protected void destroy() {
        try {
            userController.save();
        } catch (IOException e) {
            String title = Translator.getBundleString("error");
            String message = Translator.getBundleString("save_error");
            JOptionPane.showMessageDialog(frame, message, title, JOptionPane.ERROR_MESSAGE);
        }
    }

    public AirportController getAirportController() {
        return airportController;
    }

    public UserController getUserController() {
        return userController;
    }

    @Override
    public void setTheme(AppTheme theme) {
        super.setTheme(theme);
        if (menuBar != null)
            menuBar.updateTheme();
    }

    @Override
    public void changeLocale(Locale locale) {
        super.changeLocale(locale);
        if (menuBar != null)
            menuBar.onLocaleChange();
        frame.setTitle(Translator.getBundleString("application_name"));
    }

    @Override
    protected void changeContent(JPanel content) {
        contentPanel.removeAll();
        contentPanel.add(content, BorderLayout.CENTER);
    }

    private void createUIComponents() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        sidebarPanel = airportSideBar.getPanel();
        toolbarPanel = airportToolBar.getPanel();
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
        rootPanel = new JPanel();
        rootPanel.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), 0, 0));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        rootPanel.add(panel1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel1.add(toolbarPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        panel1.add(contentPanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        rootPanel.add(sidebarPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, 1, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }

}
