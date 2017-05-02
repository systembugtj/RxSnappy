package io.supercharge.rxsnappy2.exception;

/**
 * Created by richardradics on 28/11/15.
 */
public class MissingDataException extends RxSnappyException {

    public MissingDataException() {
        super("Data is missing from database!");
    }
}
