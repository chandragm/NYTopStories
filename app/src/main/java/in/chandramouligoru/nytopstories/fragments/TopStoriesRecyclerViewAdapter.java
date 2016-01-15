package in.chandramouligoru.nytopstories.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.chandramouligoru.nytopstories.R;
import in.chandramouligoru.nytopstories.activity.ArticleActivity;
import in.chandramouligoru.nytopstories.model.Article;
import in.chandramouligoru.nytopstories.model.MultiMedia;

/**
 * TODO: Replace the implementation with code for your data type.
 */
public class TopStoriesRecyclerViewAdapter extends RecyclerView.Adapter<TopStoriesRecyclerViewAdapter.ViewHolder> {

    private final List<Article> mValues;
    private Context mContext;

    public TopStoriesRecyclerViewAdapter(List<Article> items, Context context) {
        mValues = items;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_article, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Article article = mValues.get(position);
        holder.mArticleTitle.setText(article.title);
        holder.mArticleDate.setText(getDateAndAuthor(article.published_date, article.byline));
        holder.mArticleAbstract.setText(article.articleAbstract);

        List<MultiMedia> mm = article.multimedia;
        if (mm != null && mm.size() > 0 && mm.get(0).url != null)
            Glide.with(mContext)
                    .load(mValues.get((position)).multimedia.get(0).url)
                    .into(holder.mArticleThumbnail);

        holder.mView.setOnClickListener(v -> {
            //Not responsive enough.
            Intent intent = new Intent(mContext, ArticleActivity.class);
            intent.putExtra(ArticleActivity.INTENT_ARG_ARTICLE_URL, article.url);
            mContext.startActivity(intent);

//            Uri webpage = Uri.parse(article.url);
//            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
//            mContext.startActivity(intent);
        });
    }

    private String getDateAndAuthor(String date, String author) {
        Date parsed = null;
        try {
            parsed = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder(author + "\n");
        if (parsed != null)
            sb.append(android.text.format.DateFormat.format("MMMM dd, yyyy", parsed.getTime()));
        else
            sb.append(date);

        return sb.toString();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.article_title)
        protected TextView mArticleTitle;

        @Bind(R.id.article_date)
        protected TextView mArticleDate;

        @Bind(R.id.article_abstract)
        protected TextView mArticleAbstract;

        @Bind(R.id.article_thumbnail)
        protected ImageView mArticleThumbnail;

        public final View mView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mView = view;
        }
    }
}
