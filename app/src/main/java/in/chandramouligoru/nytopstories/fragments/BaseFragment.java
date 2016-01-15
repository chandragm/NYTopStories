package in.chandramouligoru.nytopstories.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import in.chandramouligoru.nytopstories.R;
import rx.subscriptions.CompositeSubscription;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {

    protected CompositeSubscription compositeSubscription;

    protected ProgressBar mProgressBar;

    public BaseFragment() {
        // Required empty public constructor
        compositeSubscription = new CompositeSubscription();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mProgressBar = (ProgressBar) inflater.inflate(R.layout.base_fragment_layout, container, false);
        return mProgressBar;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (compositeSubscription != null) {
            compositeSubscription.unsubscribe();
            compositeSubscription = null;
        }
    }

    public void showHideLoading(boolean showHide) {
        if (showHide)
            mProgressBar.setVisibility(View.VISIBLE);
        else
            mProgressBar.setVisibility(View.GONE);
    }
}
