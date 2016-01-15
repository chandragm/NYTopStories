package in.chandramouligoru.nytopstories.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.chandramouligoru.nytopstories.BuildConfig;
import in.chandramouligoru.nytopstories.R;
import in.chandramouligoru.nytopstories.app.NYTopStoriesApplicaiton;
import in.chandramouligoru.nytopstories.model.Article;
import in.chandramouligoru.nytopstories.service.RetrofitService;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A fragment representing a list of NYT top stories.
 */
public class TopStoriesFragment extends BaseFragment {

    private static final String TAG = "TopStoriesFragment";
    private static final String ARG_SECTION = "argument_section";

    @Bind(R.id.list)
    protected RecyclerView mRecyclerView;

    @Inject
    RetrofitService mRetrofitService;

    private String mSection;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TopStoriesFragment() {
    }

    public static TopStoriesFragment newInstance(String section) {
        TopStoriesFragment fragment = new TopStoriesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SECTION, section);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntentParameters();
        ((NYTopStoriesApplicaiton) getActivity().getApplicationContext()).getAppComponent().inject(this);
    }

    private void getIntentParameters() {
        mSection = getArguments().getString(ARG_SECTION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_list, container, false);
        ButterKnife.bind(this, view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        loadTopStories();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void loadTopStories() {
        Subscription subscription = mRetrofitService
                .getTopStories(mSection, "json", BuildConfig.API_KEY)
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
                    onDataFetched(articles);
                });
        compositeSubscription.add(subscription);
    }

    private void onDataFetched(List<Article> fetchedArticles) {
        mRecyclerView.setAdapter(new TopStoriesRecyclerViewAdapter(fetchedArticles, getActivity()));
    }
}
