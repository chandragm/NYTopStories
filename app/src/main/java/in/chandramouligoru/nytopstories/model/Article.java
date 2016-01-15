package in.chandramouligoru.nytopstories.model;

import com.google.gson.annotations.SerializedName;

public class Article {

    public String title;

    @SerializedName("abstract")
    public String articleAbstract;

    public String url;
    public String byline;
    public String published_date;

    public MultiMediaList multimedia;
}


