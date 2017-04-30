package com.hacareem.finaldestination.webservice;

import android.content.Context;

import com.hacareem.finaldestination.R;
import com.hacareem.finaldestination.webservice.GooglePlaceSearchService;
import com.hacareem.finaldestination.webservice.converters.ServiceConverterFactory;

import java.io.IOException;
import java.net.ProtocolException;
import java.util.AbstractMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created on 4/6/17.
 */

public class ServiceFactory implements Interceptor{

    protected final Map<Class<?>, Object> instances = new ConcurrentHashMap<>();
    Retrofit retrofit;

    private GooglePlaceSearchService googlePlaceSearchService;
    private SuggestionsService suggestionsService;

    public final void initialize(Context context) {

        OkHttpClient okHttpClient = getOkHttpClient();

        retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.api_url))
                .client(okHttpClient)
                .addConverterFactory(new ServiceConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private OkHttpClient getOkHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient();
        OkHttpClient.Builder builder = okHttpClient.newBuilder();
        builder = builder.addNetworkInterceptor(this);
        return builder.build();
    }


    /**
     * Use this method to create and load your service interfaces
     *
     * @param serviceClass Class of Service that you want to load
     * @return Service implementation
     */

    private <S> S loadService(Class<S> serviceClass) {
        if (!instances.containsKey(serviceClass)) {
            instances.put(serviceClass, retrofit.create(serviceClass));
        }
        return (S) instances.get(serviceClass);
    }


    public GooglePlaceSearchService getGooglePlaceSearchService() {
        if(googlePlaceSearchService == null) {
            googlePlaceSearchService = loadService(GooglePlaceSearchService.class);
        }
        return googlePlaceSearchService;
    }

    public SuggestionsService getSuggestionsService() {
        if(suggestionsService == null) {
            suggestionsService = loadService(SuggestionsService.class);
        }
        return suggestionsService;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        Request.Builder requestBuilder = request.newBuilder()
                .method( request.method(), request.body() );

        requestBuilder = requestBuilder.addHeader( "Content-Type", "application/json" );
        request = requestBuilder.build();
        Response response;
        try {
            response = chain.proceed( request );
        } catch ( ProtocolException e ) {
            response = new Response.Builder()
                    .request( request )
                    .code( 204 )
                    .protocol( Protocol.HTTP_1_1 )
                    .build();
        }

        return response;
    }
}
