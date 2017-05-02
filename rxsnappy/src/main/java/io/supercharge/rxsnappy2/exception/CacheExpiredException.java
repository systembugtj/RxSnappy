package io.supercharge.rxsnappy2.exception;

/**
 * Created by richardradics on 28/11/15.
 */
public class CacheExpiredException extends RxSnappyException {

    public CacheExpiredException() {
        super("Data in cache validity is expired!");
    }
}
