package io.supercharge.rxsnappy2;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.snappydb.SnappydbException;

import io.supercharge.rxsnappy2.mock.DataGenerator;
import io.supercharge.rxsnappy2.mock.DummyData;
import io.supercharge.rxsnappy2.exception.CacheExpiredException;

/**
 * Created by richardradics on 27/11/15.
 */
public class TimeBasedCacheTest extends AndroidTestCase {

    RxSnappyClient rxSnappyClient;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        RxSnappy.init(getContext());
        rxSnappyClient = new RxSnappyClient();
    }

    @SmallTest
    public void testDataShouldBeAlwaysCachedOnce() throws SnappydbException {
        String key = "asd";

        DummyData dummyData = DataGenerator.generateNewDummyData();
        DummyData dummyData2 = DataGenerator.generateNewDummyData();

        rxSnappyClient.setObject(key, dummyData)
                .blockingFirst();

        String[] keys = rxSnappyClient.db.findKeys(key);

        assertEquals(1, keys.length);


        rxSnappyClient.setObject(key, dummyData2)
                .blockingFirst();

        String[] keys2 = rxSnappyClient.db.findKeys(key);

        assertEquals(1, keys2.length);


        DummyData actual = rxSnappyClient.getObject(keys2[0], DummyData.class)
                .blockingFirst();


        assertEquals(dummyData2, actual);

    }

    @SmallTest
    public void testDataCacheIsInvalid() throws SnappydbException {
        String key = "asd";
        boolean exceptioThrown = false;

        DummyData dummyData = DataGenerator.generateNewDummyData();

        rxSnappyClient.setObject(key, dummyData)
                .blockingFirst();

        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {

        }

        try {
            rxSnappyClient.getObject(key, 1000L, DummyData.class)
                    .blockingFirst();
        } catch (CacheExpiredException e) {
            exceptioThrown = true;
        }

        assertEquals(true, exceptioThrown);
    }

    @SmallTest
    public void testDataCacheIsValid() throws SnappydbException {
        String key = "asd";

        DummyData dummyData = DataGenerator.generateNewDummyData();

        rxSnappyClient.setObject(key, dummyData)
                .blockingFirst();

        try {
            Thread.sleep(2500L);
        } catch (InterruptedException e) {

        }

        DummyData actual = rxSnappyClient.getObject(key, 5000L, DummyData.class)
                .blockingFirst();

        assertEquals(dummyData, actual);
    }

    @SmallTest
    public void testKeyGenerator() throws Exception {

        String key = RxSnappyUtils.generateKey("123", "14123", DataGenerator.generateNewDummyData(), "adasd", DataGenerator.generateNewDummyData());

        assertNotNull(key);

        DummyData expected = DataGenerator.generateNewDummyData();

        rxSnappyClient.setObject(key, expected)
                .blockingFirst();

        DummyData actual = rxSnappyClient.getObject(key, DummyData.class)
                .blockingFirst();

        assertEquals(expected, actual);
    }


    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        RxSnappy.closeDatabase();
        RxSnappy.destroyDatabase();
    }
}
