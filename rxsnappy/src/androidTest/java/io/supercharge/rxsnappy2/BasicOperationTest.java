package io.supercharge.rxsnappy2;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.snappydb.SnappydbException;

import java.util.List;

import io.supercharge.rxsnappy2.mock.DataGenerator;
import io.supercharge.rxsnappy2.mock.DummyData;
import io.supercharge.rxsnappy2.mock.MockedResponse;
import io.supercharge.rxsnappy2.exception.RxSnappyException;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class BasicOperationTest extends AndroidTestCase {

    private static final String test_key = "test-asd";


    RxSnappyClient rxSnappyClient;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        RxSnappy.init(getContext());
        rxSnappyClient = new RxSnappyClient();
    }

    @SmallTest
    public void testResetDatabase() throws SnappydbException {
        String key = "asd";

        DummyData dummyData = DataGenerator.generateNewDummyData();

        rxSnappyClient.setObject(key, dummyData)
                .blockingFirst();


        String[] keys = rxSnappyClient.db.findKeys(key);

        assertEquals(1, keys.length);


        RxSnappy.resetDatabase(getContext());

        keys = rxSnappyClient.db.findKeys(key);

        assertEquals(0, keys.length);

    }


    @SmallTest
    public void testBooleanValue() throws Exception {
        Boolean expected = Boolean.TRUE;


        rxSnappyClient.setBoolean(test_key, expected)
                .blockingFirst();

        Boolean actual = rxSnappyClient.getBoolean(test_key)
                .blockingFirst();

        assertEquals(expected, actual);
    }

    @SmallTest
    public void testSaveNullBoolean() throws Exception {
        boolean isExceptionThrown = false;

        try {
            rxSnappyClient.setBoolean(test_key, null)
                    .blockingFirst();
        } catch (RxSnappyException ex) {
            isExceptionThrown = true;
        }
        assertEquals(true, isExceptionThrown);
    }

    @SmallTest
    public void testSaveNullKeyBoolean() throws Exception {
        boolean isExceptionThrown = false;

        try {
            rxSnappyClient.setBoolean(null, true)
                    .blockingFirst();
        } catch (RxSnappyException ex) {
            isExceptionThrown = true;
        }
        assertEquals(true, isExceptionThrown);
    }

    @SmallTest
    public void testBooleanMissingFromCache() throws Exception {
        boolean isExceptionThrown = false;

        try {
            rxSnappyClient.getBoolean("test123")
                    .blockingFirst();
        } catch (RxSnappyException ex) {
            isExceptionThrown = true;
        }
        assertEquals(true, isExceptionThrown);
    }

    @SmallTest
    public void testStringValue() throws Exception {
        String expected = "test string";


        rxSnappyClient.setString(test_key, expected)
                .blockingFirst();

        String actual = rxSnappyClient.getString(test_key)
                .blockingFirst();

        assertEquals(expected, actual);

    }

    @SmallTest
    public void testSaveNullString() throws Exception {
        boolean isExceptionThrown = false;

        try {
            rxSnappyClient.setString(test_key, null)
                    .blockingFirst();
        } catch (RxSnappyException ex) {
            isExceptionThrown = true;
        }
        assertEquals(true, isExceptionThrown);
    }

    @SmallTest
    public void testSaveNullKeyString() throws Exception {
        boolean isExceptionThrown = false;

        try {
            rxSnappyClient.setString(null, "asd")
                    .blockingFirst();
        } catch (RxSnappyException ex) {
            isExceptionThrown = true;
        }
        assertEquals(true, isExceptionThrown);
    }

    @SmallTest
    public void testStringMissingFromCache() throws Exception {
        boolean isExceptionThrown = false;

        try {
            rxSnappyClient.getString("test123")
                    .blockingFirst();
        } catch (RxSnappyException ex) {
            isExceptionThrown = true;
        }
        assertEquals(true, isExceptionThrown);
    }

    @SmallTest
    public void testLongValue() throws Exception {
        Long expected = 12314124L;

        rxSnappyClient.setLong(test_key, expected)
                .blockingFirst();

        Long actual = rxSnappyClient.getLong(test_key)
                .blockingFirst();

        assertEquals(expected, actual);

    }

    @SmallTest
    public void testSaveNullKeyLong() throws Exception {
        boolean isExceptionThrown = false;

        try {
            rxSnappyClient.setLong(null, 1231L)
                    .blockingFirst();
        } catch (RxSnappyException ex) {
            isExceptionThrown = true;
        }
        assertEquals(true, isExceptionThrown);
    }

    @SmallTest
    public void testSaveNullLong() throws Exception {
        boolean isExceptionThrown = false;

        try {
            rxSnappyClient.setLong(test_key, null)
                    .blockingFirst();
        } catch (RxSnappyException ex) {
            isExceptionThrown = true;
        }
        assertEquals(true, isExceptionThrown);
    }

    @SmallTest
    public void testLongMissingFromCache() throws Exception {
        boolean isExceptionThrown = false;

        try {
            rxSnappyClient.getLong("test123")
                    .blockingFirst();
        } catch (RxSnappyException ex) {
            isExceptionThrown = true;
        }
        assertEquals(true, isExceptionThrown);
    }

    @SmallTest
    public void testIntegerValue() throws Exception {
        Integer expected = 12314;


        rxSnappyClient.setInteger(test_key, expected)
                .blockingFirst();

        Integer actual = rxSnappyClient.getInteger(test_key)
                .blockingFirst();

        assertEquals(expected, actual);


    }


    @SmallTest
    public void testSaveNullKeyInteger() throws Exception {
        boolean isExceptionThrown = false;

        try {
            rxSnappyClient.setInteger(null, 123)
                    .blockingFirst();
        } catch (RxSnappyException ex) {
            isExceptionThrown = true;
        }
        assertEquals(true, isExceptionThrown);
    }

    @SmallTest
    public void testSaveNullInteger() throws Exception {
        boolean isExceptionThrown = false;

        try {
            rxSnappyClient.setInteger(test_key, null)
                    .blockingFirst();
        } catch (RxSnappyException ex) {
            isExceptionThrown = true;
        }
        assertEquals(true, isExceptionThrown);
    }

    @SmallTest
    public void testIntegerMissingFromCache() throws Exception {
        boolean isExceptionThrown = false;

        try {
            rxSnappyClient.getInteger("test123")
                    .blockingFirst();
        } catch (RxSnappyException ex) {
            isExceptionThrown = true;
        }
        assertEquals(true, isExceptionThrown);
    }

    @SmallTest
    public void testStringListValue() throws Exception {
        List<String> expected = DataGenerator.stringListGenerator(10);


        rxSnappyClient.setStringList(test_key, expected)
                .blockingFirst();

        List<String> actual = rxSnappyClient.getStringList(test_key)
                .blockingFirst();

        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);


    }

    @SmallTest
    public void testSaveNullKeyStringList() throws Exception {
        boolean isExceptionThrown = false;

        try {
            rxSnappyClient.setStringList(null, DataGenerator.stringListGenerator(10))
                    .blockingFirst();
        } catch (RxSnappyException ex) {
            isExceptionThrown = true;
        }
        assertEquals(true, isExceptionThrown);
    }

    @SmallTest
    public void testSaveNullStringList() throws Exception {
        boolean isExceptionThrown = false;

        try {
            rxSnappyClient.setStringList(test_key, null)
                    .blockingFirst();
        } catch (RxSnappyException ex) {
            isExceptionThrown = true;
        }
        assertEquals(true, isExceptionThrown);
    }

    @SmallTest
    public void testStringListMissingFromCache() throws Exception {
        boolean isExceptionThrown = false;

        try {
            rxSnappyClient.getStringList("test123")
                    .blockingFirst();
        } catch (RxSnappyException ex) {
            isExceptionThrown = true;
        }
        assertEquals(true, isExceptionThrown);
    }

    @SmallTest
    public void testListValue() throws Exception {
        List<DummyData> expected = DataGenerator.dummyDataGenerator(10);


        rxSnappyClient.setList(test_key, expected)
                .blockingFirst();

        List<DummyData> actual = rxSnappyClient.getList(test_key, DummyData.class)
                .blockingFirst();

        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);


    }

    @SmallTest
    public void testSaveNullKeyObjectList() throws Exception {
        boolean isExceptionThrown = false;

        try {
            rxSnappyClient.setList(null, DataGenerator.dummyDataGenerator(3))
                    .blockingFirst();
        } catch (RxSnappyException ex) {
            isExceptionThrown = true;
        }
        assertEquals(true, isExceptionThrown);
    }

    @SmallTest
    public void testSaveNullObjectList() throws Exception {
        boolean isExceptionThrown = false;

        try {
            rxSnappyClient.setList(test_key, null)
                    .blockingFirst();
        } catch (RxSnappyException ex) {
            isExceptionThrown = true;
        }
        assertEquals(true, isExceptionThrown);
    }

    @SmallTest
    public void testObjectListMissingFromCache() throws Exception {
        boolean isExceptionThrown = false;

        try {
            rxSnappyClient.getList("test123", DummyData.class)
                    .blockingFirst();
        } catch (RxSnappyException ex) {
            isExceptionThrown = true;
        }
        assertEquals(true, isExceptionThrown);
    }

    @SmallTest
    public void testObjectValue() throws Exception {
        DummyData expected = DataGenerator.generateNewDummyData();


        rxSnappyClient.setObject(test_key, expected)
                .blockingFirst();

        DummyData actual = rxSnappyClient.getObject(test_key, DummyData.class)
                .blockingFirst();

        assertEquals(expected, actual);

    }

    @SmallTest
    public void testComplesObjectSave() throws Exception {
        MockedResponse mockedResponse = new MockedResponse();

        MockedResponse actual = rxSnappyClient.setObject(test_key, mockedResponse)
                .blockingFirst();

        assertEquals(mockedResponse, actual);

    }


    @SmallTest
    public void testSaveNullObject() throws Exception {
        boolean isExceptionThrown = false;

        try {
            rxSnappyClient.setObject(test_key, null)
                    .blockingFirst();
        } catch (RxSnappyException ex) {
            isExceptionThrown = true;
        }
        assertEquals(true, isExceptionThrown);
    }

    @SmallTest
    public void testSaveNullKeyObject() throws Exception {
        boolean isExceptionThrown = false;

        try {
            rxSnappyClient.setObject(null, DataGenerator.generateNewDummyData())
                    .blockingFirst();
        } catch (RxSnappyException ex) {
            isExceptionThrown = true;
        }
        assertEquals(true, isExceptionThrown);
    }

    @SmallTest
    public void testObjectMissingFromCache() throws Exception {
        boolean isExceptionThrown = false;

        try {
            rxSnappyClient.getObject("test123", DummyData.class)
                    .blockingFirst();
        } catch (RxSnappyException ex) {
            isExceptionThrown = true;
        }
        assertEquals(true, isExceptionThrown);
    }

    @SmallTest
    public void testCountFindExistsRemoveCache() {
        String key = "asdasd";

        rxSnappyClient.setObject(key, "asd").blockingFirst();

        boolean exists = rxSnappyClient.isCached(key).blockingFirst();
        boolean notexists = rxSnappyClient.isCached("testnot").blockingFirst();

        assertTrue(exists);
        assertFalse(notexists);

        int cnt = rxSnappyClient.countKeys(key).blockingFirst();
        assertTrue(cnt == 1);

        rxSnappyClient.deleteCache(key).blockingFirst();

        String[] cnt1 = rxSnappyClient.findKeys(key).blockingFirst();

        assertTrue(cnt1.length == 0);

    }


    @Override
    protected void tearDown() throws Exception {
        super.tearDown();

        RxSnappy.closeDatabase();
        RxSnappy.destroyDatabase();


    }
}