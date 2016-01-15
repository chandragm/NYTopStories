package in.chandramouligoru.nytopstories.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.chandramouligoru.nytopstories.R;
import rx.subscriptions.CompositeSubscription;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {

    protected CompositeSubscription compositeSubscription;

    public BaseFragment() {
        // Required empty public constructor
        compositeSubscription = new CompositeSubscription();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(R.string.loading);
        return textView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (compositeSubscription != null) {
            compositeSubscription.unsubscribe();
            compositeSubscription = null;
        }
    }
}
