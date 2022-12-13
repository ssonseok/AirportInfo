package com.airportinfo.view;

import java.io.File;

/**
 * Able to store.
 *
 * @author lalaalal
 */
public interface Storable {
    void store();
    void store(File file);

    String getFileExtension();
}
