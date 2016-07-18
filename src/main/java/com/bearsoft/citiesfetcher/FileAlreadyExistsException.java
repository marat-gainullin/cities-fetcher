package com.bearsoft.citiesfetcher;

import java.io.File;

/**
 * This exception is thrown if file we have to write to already exists.
 *
 * @author mg
 */
public class FileAlreadyExistsException extends Exception {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 5058992489617301538L;

    /**
     * Constructs new exception about already existent file.
     *
     * @param aFile A file we care about.
     */
    public FileAlreadyExistsException(final File aFile) {
        super(String.format("Destination file already exists: %s",
                aFile.getAbsolutePath()));
    }
}
