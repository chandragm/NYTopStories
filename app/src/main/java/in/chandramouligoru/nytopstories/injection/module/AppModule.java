package in.chandramouligoru.nytopstories.injection.module;


import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import in.chandramouligoru.nytopstories.BuildConfig;
import in.chandramouligoru.nytopstories.app.NYTopStoriesApplicaiton;
import in.chandramouligoru.nytopstories.injection.ApplicationContext;
import in.chandramouligoru.nytopstories.model.MultiMediaList;
import in.chandramouligoru.nytopstories.model.StringListDeserializer;
import in.chandramouligoru.nytopstories.service.RetrofitService;
import in.chandramouligoru.nytopstories.utils.NetworkConnectionUtils;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;

@Module
public class AppModule {

    private NYTopStoriesApplicaiton mApplicaiton;

    public AppModule(NYTopStoriesApplicaiton applicaiton) {
        mApplicaiton = applicaiton;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .registerTypeAdapter(MultiMediaList.class, new StringListDeserializer())
                .create();
    }

    @Provides
    @Singleton
    RetrofitService provideRetrofitService(OkHttpClient okHttpClient, Gson gson) {
        // Build Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.END_POINT)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
        return retrofit.create(RetrofitService.class);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient
                .Builder()
                .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .cache(new Cache(mApplicaiton.getCacheDir(), 1024 * 1024 * 10))
                .build();
    }

    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = chain -> {
        Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder()
                .header("Cache-Control", String.format("max-age=%d, only-if-cached, max-stale=%d", 120, 0))
                .build();
    };

    @Provides
    @Singleton
    NetworkConnectionUtils provideNetworkConnectionUtils() {
        return new NetworkConnectionUtils(mApplicaiton);
    }
}
