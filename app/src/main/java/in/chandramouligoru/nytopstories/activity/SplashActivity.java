package in.chandramouligoru.nytopstories.activity;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import in.chandramouligoru.nytopstories.BuildConfig;
import in.chandramouligoru.nytopstories.R;
import in.chandramouligoru.nytopstories.app.NYTopStoriesApplicaiton;
import in.chandramouligoru.nytopstories.model.Article;
import in.chandramouligoru.nytopstories.service.RetrofitService;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SplashActivity extends BaseActivity {

    private static final String TAG = "SplashActivity";

    @Inject
    RetrofitService mRetrofitService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ((NYTopStoriesApplicaiton) getApplicationContext()).getAppComponent().inject(this);
        if(isNetworkAvailable())
            loadTopStories();
        else
            showNoNetworkError();
    }


    private void loadTopStories() {
        //Initially load section home.
        Subscription subscription = mRetrofitService
                .getTopStories("home", "json", BuildConfig.API_KEY)
                .map(topStoriesResponse -> {
                    if (topStoriesResponse.status.equalsIgnoreCase("OK")) {
                        return topStoriesResponse.results;
                    } else {
                        List<Article> articles = new ArrayList<>(1);
                        return articles;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(articles -> {
                    //Now that response is cached lets launch the TopStoriesActivity to show it.
                    Intent intent = new Intent(this, TopStoriesActivity.class);
                    startActivity(intent);
                    finish();
                });
        compositeSubscription.add(subscription);
    }
}
