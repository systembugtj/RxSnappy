package io.supercharge.rxsnappy2.exception;

/**
 * Created by richardradics on 28/11/15.
 */
public class ValueIsNullException extends RxSnappyException {

    public ValueIsNullException() {
        super("Cannot save null value!");
    }
}
