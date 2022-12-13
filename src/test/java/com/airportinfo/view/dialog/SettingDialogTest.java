package com.airportinfo.view.dialog;

import com.airportinfo.Main;
import com.airportinfo.Setting;
import com.airportinfo.controller.AirportController;
import com.airportinfo.controller.UserController;
import com.airportinfo.view.TestFrame;

public class SettingDialogTest {
    public static void main(String[] args) {
        Main.setSystemProperties();
        TestFrame testFrame = new TestFrame();
        Setting setting = Setting.getInstance();
        setting.attach(() -> System.out.println("show chart label is now " + setting.isShowChartLabel()), Setting.CHART_CHANGE);
        setting.attach(() -> System.out.println("show histogram guideline is now " + setting.isShowHistogramGuideLine()), Setting.HISTOGRAM_CHANGE);
        setting.attach(() -> System.out.println("histogram legend interval is now " + setting.getHistogramLegendInterval()), Setting.HISTOGRAM_CHANGE);
        UserController userController = new UserController();
        AirportController airportController = new AirportController();
        SettingDialogView settingDialogView = new SettingDialogView(airportController, userController);
        settingDialogView.showDialogLocationRelativeTo(testFrame.getPanel());
        settingDialogView.load();
    }
}