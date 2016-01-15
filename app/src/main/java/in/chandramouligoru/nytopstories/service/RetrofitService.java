package in.chandramouligoru.nytopstories.service;

import in.chandramouligoru.nytopstories.response.TopStoriesResponse;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface RetrofitService {

    @GET("/svc/topstories/v1/{section}.{response-format}")
    Observable<TopStoriesResponse> getTopStories(@Path("section") String section
            , @Path("response-format") String responseFormat, @Query("api-key") String apiKey);
}
