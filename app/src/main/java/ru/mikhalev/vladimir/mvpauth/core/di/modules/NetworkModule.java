package ru.mikhalev.vladimir.mvpauth.core.di.modules;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.mikhalev.vladimir.mvpauth.core.network.api.RestService;
import ru.mikhalev.vladimir.mvpauth.utils.AppConfig;

/**
 * Developer Vladimir Mikhalev, 06.11.2016.
 */

@Module
public class NetworkModule {

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return createClient();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttp) {
        return createRetrofit(okHttp);
    }

    @Provides
    @Singleton
    RestService provideRestService(Retrofit retrofit) {
        return retrofit.create(RestService.class);
    }

    private OkHttpClient createClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(AppConfig.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(AppConfig.READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(AppConfig.WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .build();
        // TODO: 06.11.2016 add interceptors
    }

    private Retrofit createRetrofit(OkHttpClient okHttp) {
        return new Retrofit.Builder()
                .baseUrl(AppConfig.BASE_URL)
                .addConverterFactory(createConvertFactory())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttp)
                .build();
    }

    private Converter.Factory createConvertFactory() {
        return GsonConverterFactory.create();
    }
}
