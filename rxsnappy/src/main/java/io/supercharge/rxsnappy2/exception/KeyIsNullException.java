package io.supercharge.rxsnappy2.exception;

/**
 * Created by richardradics on 28/11/15.
 */
public class KeyIsNullException extends RxSnappyException {

    public KeyIsNullException() {
        super("Cannot save null key!");
    }
}
