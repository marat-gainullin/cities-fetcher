package com.bearsoft.citiesfetcher;

import java.io.File;

/**
 * This exception is thrown if file we have to write to already exists.
 *
 * @author mg
 */
public class FileAlreadyExistsException extends Exception {

    /**
     * Constructs new exception about already existent file.
     *
     * @param aFile A file we care about.
     */
    public FileAlreadyExistsException(File aFile) {
        super(String.format("File %s already exists.", aFile.getAbsolutePath()));
    }
}
