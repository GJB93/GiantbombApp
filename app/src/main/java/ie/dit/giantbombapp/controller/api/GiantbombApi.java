package ie.dit.giantbombapp.controller.api;

import ie.dit.giantbombapp.model.pojos.PromosContainer;
import ie.dit.giantbombapp.model.pojos.ReviewsContainer;
import ie.dit.giantbombapp.model.pojos.SearchContainer;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Author: Graham Byrne
 *
 * Created: 14/11/2016
 * Modified: 25/11/2016
 *
 * This interface controls the endpoints for the Giantbomb API. It uses special Retrofit annotations
 * to encode details about the parameters and request method. The return parameters will always be
 * Call<ResponseBody>, where ResponseBody is equivalent to the object that the call will be parsed
 * into.
 *
 * Equivalent urls are provided above each endpoint for clarity.
 */

public interface GiantbombApi {

    //Equivalent url: /promos/?api_key=apiKey&format=format&limit=limit
    @GET("promos/")
    Call<PromosContainer> getInitialPromos(@Query("api_key") String apiKey, @Query("format") String format,
                                           @Query("limit") int limit);

    //Equivalent url: /promos/?api_key=apiKey&format=format&limit=limit&offset=offset
    @GET("promos/")
    Call<PromosContainer> getMorePromos(@Query("api_key") String apiKey, @Query("format") String format,
                                        @Query("limit") int limit, @Query("offset") int offset);

    //Equivalent url: /reviews/?api_key=apiKey&format=format&sort=sort&limit=limit
    @GET("reviews/")
    Call<ReviewsContainer> getInitialReviews(@Query("api_key") String apiKey, @Query("format") String format, @Query("sort") String sort, @Query("limit") int limit);

    //Equivalent url: /reviews/?api_key=apiKey&format=format&sort=sort&limit=limit&offset=offset
    @GET("reviews/")
    Call<ReviewsContainer> getMoreReviews(@Query("api_key") String apiKey, @Query("format") String format, @Query("sort") String sort,
                                          @Query("limit") int limit, @Query("offset") int offset);

    //Equivalent url: /search/?api_key=apiKey&format=format&query=query&resources=resources
    @GET("search/")
    Call<SearchContainer> searchForQuery(@Query("api_key") String apiKey, @Query("format") String format, @Query("query") String query,
                                         @Query("resources") String resources);

    //Equivalent url: /resource/?api_key=apiKey&format=format&filter=query
    @GET("{resource}/")
    Call<SearchContainer> searchResource(@Path("resource") String resource, @Query("api_key") String apiKey, @Query("format") String format, @Query("filter") String query);
}
