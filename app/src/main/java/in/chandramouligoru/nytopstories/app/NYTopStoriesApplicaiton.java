package in.chandramouligoru.nytopstories.app;

import android.app.Application;

import in.chandramouligoru.nytopstories.injection.component.AppComponent;
import in.chandramouligoru.nytopstories.injection.module.AppModule;


public class NYTopStoriesApplicaiton extends Application {

    AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = in.chandramouligoru.nytopstories.injection.component.DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }


    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
