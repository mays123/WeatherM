package com.madfooat.task.modelLayer.network;

import com.madfooat.task.helpers.Constants;

import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MaysAtari on 6/22/2018.
 */

public class RetrofitClient {

    private static Retrofit retrofit = null;


    private static Retrofit getClient(String baseUrl) {
        if (retrofit == null) {

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            X509TrustManager trustManager = getTrustManager();

            try {
                OkHttpClient client = new OkHttpClient.Builder()
                        .addInterceptor(interceptor)
                        .sslSocketFactory(new TLSSocketFactory(), trustManager)
                        .build();

                retrofit = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build();

            } catch (KeyManagementException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            return retrofit;
        }

        return retrofit;
    }

    private static X509TrustManager getTrustManager() {
        TrustManagerFactory trustManagerFactory = null;
        try {
            trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

            trustManagerFactory.init((KeyStore) null);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        TrustManager[] trustManagers = new TrustManager[0];
        if (trustManagerFactory != null) {
            trustManagers = trustManagerFactory.getTrustManagers();
        }
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }

    static APIs getRetrofitApis() {
        return RetrofitClient.getClient(Constants.DEFAULT_BASE_URL).create(APIs.class);
    }
}
