package in.chandramouligoru.nytopstories.activity;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import javax.inject.Inject;

import in.chandramouligoru.nytopstories.R;
import in.chandramouligoru.nytopstories.app.NYTopStoriesApplicaiton;
import in.chandramouligoru.nytopstories.utils.NetworkConnectionUtils;
import rx.subscriptions.CompositeSubscription;

public class BaseActivity extends AppCompatActivity {
    protected CompositeSubscription compositeSubscription;

    @Inject
    protected NetworkConnectionUtils mNetworkConnectionUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((NYTopStoriesApplicaiton) getApplicationContext()).getAppComponent().inject(this);
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(compositeSubscription != null) {
            compositeSubscription.unsubscribe();
            compositeSubscription = null;
        }

        mNetworkConnectionUtils = null;
    }

    public boolean isNetworkAvailable() {
        return mNetworkConnectionUtils.isConnected();
    }

    public void showNoNetworkError() {
        Snackbar.make(findViewById(android.R.id.content), getString(R.string.no_internet), Snackbar.LENGTH_LONG).show();
    }
}
