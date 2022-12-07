package com.airportinfo.misc;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;

/**
 * Show text with compatible font.
 *
 * @author lalaalal
 */
public class FontCompatibleTextPane extends JTextPane {
    private final SimpleAttributeSet sansAttribute = new SimpleAttributeSet();

    private final SimpleAttributeSet serifAttribute = new SimpleAttributeSet();

    public FontCompatibleTextPane() {
        StyleConstants.setFontFamily(sansAttribute, getFont().getFamily());
        StyleConstants.setFontFamily(serifAttribute, "Sans");
    }

    @Override
    public void setText(String text) {
        super.setText("");
        try {
            setDocumentText(text);
        } catch (BadLocationException ignored) {

        }
    }

    private void setDocumentText(String text) throws BadLocationException {
        Document document = getDocument();
        Font font = getFont();
        while (text.length() > 0) {
            int start = font.canDisplayUpTo(text);
            if (start == -1) {
                document.insertString(document.getLength(), text, sansAttribute);
                break;
            }
            document.insertString(document.getLength(), text.substring(0, start), sansAttribute);
            text = text.substring(start);
            int end = getFirstDisplayableIndex(text, font);
            document.insertString(document.getLength(), text.substring(0, end), serifAttribute);

            text = text.substring(end);
        }
    }

    private int getFirstDisplayableIndex(String text, Font font) {
        int offset = 0;
        while (font.canDisplayUpTo(text) == 0) {
            text = text.substring(1);
            offset += 1;
        }

        return offset;
    }
}
