package io.supercharge.rxsnappy2;

import android.test.AndroidTestCase;
import android.support.test.filters.SmallTest;

import com.google.gson.Gson;

import java.net.Inet4Address;
import java.net.InetAddress;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.supercharge.rxsnappy2.mock.DataGenerator;
import io.supercharge.rxsnappy2.mock.DummyData;

import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import io.reactivex.Observable;

/**
 * Created by richardradics on 28/11/15.
 */
public class WorkingWithRetrofitTest extends AndroidTestCase {

    MockWebServer mockWebServer;
    RxSnappyClient rxSnappyClient;
    TestRestAdapter testRestAdapter;
    Gson gson;

    private interface TestRestAdapter {

        @POST("/{brand}")
        Observable<DummyData> getDummyData(@Header("Auth") String token, @Body DummyData requestData);
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        RxSnappy.init(getContext());
        mockWebServer = new MockWebServer();
        mockWebServer.start(9812);
        gson = new Gson();

        OkHttpClient okHttpClient = new OkHttpClient();
        Retrofit restAdapter = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(mockWebServer.url("/").toString())
                .build();

        testRestAdapter = restAdapter.create(TestRestAdapter.class);

        rxSnappyClient = new RxSnappyClient();
    }

    @SmallTest
    public void testDataIsMissingThanDownload() throws Exception {
        mockWebServer.enqueue(new MockResponse().setBody("{\"id\":11231,\"nestedData\":{\"id\":1,\"name\":\"NesteData 1\"},\"title\":\"DummyData 1\"}"));

        DummyData requestObj = DataGenerator.generateNewDummyData();

        final String key = RxSnappyUtils.generateKey("test", requestObj);


        rxSnappyClient.getObject(key, 3000L, DummyData.class)
                .onErrorResumeNext(testRestAdapter.getDummyData("test", requestObj)
                .doOnNext(new Consumer<DummyData>() {
                    @Override
                    public void accept(@NonNull DummyData dummyData) throws Exception {
                        rxSnappyClient.setObject(key, dummyData)
                                .blockingFirst();
                    }
                })).blockingFirst();


        DummyData dummyData2 = rxSnappyClient.getObject(key, 10000L, DummyData.class)
                .onErrorResumeNext(testRestAdapter.getDummyData("test", requestObj))
                .doOnNext(new Consumer<DummyData>() {
                    @Override
                    public void accept(@NonNull DummyData dummyData) throws Exception {
                        rxSnappyClient.setObject(key, dummyData);
                    }
                }).blockingFirst();

        assertEquals(11231L, dummyData2.getId().longValue());
        assertEquals(mockWebServer.getRequestCount(), 1);
    }

    @SmallTest
    public void testDataIsMissingThanDownloadAndCacheInvalid() throws Exception {
        mockWebServer.enqueue(new MockResponse().setBody("{\"id\":11231,\"nestedData\":{\"id\":1,\"name\":\"NesteData 1\"},\"title\":\"DummyData 1\"}"));
        mockWebServer.enqueue(new MockResponse().setBody("{\"id\":123,\"nestedData\":{\"id\":1,\"name\":\"NesteData 1\"},\"title\":\"DummyData 1\"}"));

        DummyData requestObj = DataGenerator.generateNewDummyData();

        final String key = RxSnappyUtils.generateKey("test", requestObj);


        rxSnappyClient.getObject(key, 3000L, DummyData.class)
                .onErrorResumeNext(testRestAdapter.getDummyData("test", requestObj))
                .doOnNext(new Consumer<DummyData>() {
                    @Override
                    public void accept(@NonNull DummyData dummyData) throws Exception {
                        rxSnappyClient.setObject(key, dummyData)
                                .blockingFirst();
                    }
                }).blockingFirst();


        Thread.sleep(3000L);

        DummyData dummyData2 = rxSnappyClient.getObject(key, 1000L, DummyData.class)
                .onErrorResumeNext(testRestAdapter.getDummyData("test", requestObj))
                .doOnNext(new Consumer<DummyData>() {
                    @Override
                    public void accept(@NonNull DummyData dummyData) throws Exception {
                        rxSnappyClient.setObject(key, dummyData);
                    }
                }).blockingFirst();

        assertEquals(123L, dummyData2.getId().longValue());
        assertEquals(mockWebServer.getRequestCount(), 2);
    }


    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        mockWebServer.shutdown();
        RxSnappy.closeDatabase();
        RxSnappy.destroyDatabase();
    }
}
