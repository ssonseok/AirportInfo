package com.airportinfo.misc;

import org.junit.jupiter.api.Test;

public class SubjectTest {
    @Test
    public void testNotice() {
        TestSubject subject = new TestSubject();
        subject.attach(new TestObject("general"));
        subject.attach(new TestObject("parent"), TestSubject.PARENT);
        subject.attach(new TestObject("child"), TestSubject.CHILD);

        System.out.println("=============");
        subject.notice();
        System.out.println("=============");
        subject.notice(TestSubject.PARENT);
        System.out.println("=============");
        subject.notice(TestSubject.CHILD);
    }
}