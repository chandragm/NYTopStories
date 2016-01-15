package in.chandramouligoru.nytopstories.injection.component;

import javax.inject.Singleton;

import dagger.Component;
import in.chandramouligoru.nytopstories.activity.ArticleActivity;
import in.chandramouligoru.nytopstories.activity.BaseActivity;
import in.chandramouligoru.nytopstories.activity.SplashActivity;
import in.chandramouligoru.nytopstories.fragments.TopStoriesFragment;
import in.chandramouligoru.nytopstories.injection.module.AppModule;
import in.chandramouligoru.nytopstories.service.RetrofitService;
import okhttp3.OkHttpClient;

@Singleton
@Component(modules = {
        AppModule.class
})
public interface AppComponent {

    void inject(TopStoriesFragment fragment);

    void inject(BaseActivity activity);

    void inject(SplashActivity activity);

    void inject(ArticleActivity activity);

    RetrofitService provideRetrofitService();

    OkHttpClient provideOkHttpClient();
}
