package com.airportinfo.view.dialog;

import com.airportinfo.misc.TestSubject;
import com.airportinfo.view.TestFrame;

public class ProgressDialogViewTest {
    public static void main(String[] args) throws InterruptedException {
        TestFrame testFrame = new TestFrame();
        testFrame.showFrame();
        testFrame.load();

        ProgressDialogView progressDialogView = new ProgressDialogView("Progress Test", 10);
        TestSubject testSubject = new TestSubject();
        testSubject.attach(progressDialogView);

        progressDialogView.showDialogLocationRelativeTo(testFrame.getPanel());
        progressDialogView.load();
        for (int i = 0; i < 10; i++) {
            Thread.sleep(100);
            testSubject.notice();
        }
    }
}