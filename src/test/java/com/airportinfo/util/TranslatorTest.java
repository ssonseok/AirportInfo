package com.airportinfo.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class TranslatorTest {
    @Test
    public void testTranslate() throws IOException {
        String text = Translator.translate("ko", "en", "남미");
        System.out.println(text);
    }
}