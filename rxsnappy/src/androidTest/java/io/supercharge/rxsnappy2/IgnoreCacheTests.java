package io.supercharge.rxsnappy2;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import java.util.List;

import io.supercharge.rxsnappy2.mock.DataGenerator;
import io.supercharge.rxsnappy2.mock.DummyData;
import io.reactivex.Observable;

/**
 * Created by richardradics on 19/03/16.
 */
public class IgnoreCacheTests extends AndroidTestCase {

    private static final String test_key = "test-asd";


    RxSnappyClient rxSnappyClient;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        RxSnappy.init(getContext());
        rxSnappyClient = new RxSnappyClient();
    }


    @SmallTest
    public void testDataHandlingWithoutCacheSupport() {
        String key = "asdkey";
        String key1 = RxSnappyUtils.generateKey(key, 1);
        String key2 = RxSnappyUtils.generateKey(key, 2);


        DummyData dummyData = DataGenerator.generateNewDummyData();
        DummyData dummyData1 = DataGenerator.generateNewDummyData();

        rxSnappyClient.setObject(key1, dummyData, true).blockingFirst();
        int cnt = rxSnappyClient.countKeys(key).blockingFirst();
        assertTrue(cnt == 1);
        rxSnappyClient.setObject(key1, dummyData1, true).blockingFirst();

        DummyData s = rxSnappyClient.getObject(key1, DummyData.class).blockingFirst();
        assertTrue(s.equals(dummyData1));

        cnt = rxSnappyClient.countKeys(key).blockingFirst();
        assertTrue(cnt == 1);

        boolean exists = rxSnappyClient.exists(key1).blockingFirst();
        boolean notExists = rxSnappyClient.exists(key2).blockingFirst();

        assertTrue(exists);
        assertFalse(notExists);

        rxSnappyClient.setObject(key2, dummyData, true).blockingFirst();
        cnt = rxSnappyClient.countKeys(key).blockingFirst();
        assertTrue(cnt == 2);

        s = rxSnappyClient.getObject(key2, DummyData.class).blockingFirst();
        assertTrue(s.equals(dummyData));

        List<DummyData> datas =
                rxSnappyClient.findKeys(key).flatMap(strings -> Observable.fromArray(strings))
                    .flatMap(value -> rxSnappyClient.getObject(value, DummyData.class)).toList().blockingGet();

        assertTrue(datas.size() == 2);

        rxSnappyClient.deleteCache(key).blockingFirst();

        cnt = rxSnappyClient.countKeys(key).blockingFirst();
        assertTrue(cnt == 0);
    }


    @Override
    protected void tearDown() throws Exception {
        super.tearDown();

        RxSnappy.closeDatabase();
        RxSnappy.destroyDatabase();
    }

}
