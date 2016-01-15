package in.chandramouligoru.nytopstories.response;

import java.util.List;

import in.chandramouligoru.nytopstories.model.Article;


public class TopStoriesResponse {
    public String status;
    public String section;
    public int num_results;
    public List<Article> results;
}
