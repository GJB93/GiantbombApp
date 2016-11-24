package ie.dit.giantbombapp.controller.api;

import ie.dit.giantbombapp.model.pojos.PromosContainer;
import ie.dit.giantbombapp.model.pojos.ReviewsContainer;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Graham on 14-Nov-16.
 */

public interface GiantbombApi {

    @GET("promos/")
    Call<PromosContainer> getInitialPromos(@Query("api_key") String apiKey, @Query("format") String format,
                                           @Query("limit") int limit);

    @GET("promos/")
    Call<PromosContainer> getMorePromos(@Query("api_key") String apiKey, @Query("format") String format,
                                        @Query("limit") int limit, @Query("offset") int offset);

    @GET("promo/{id}/")
    Call<PromosContainer> getPromo(@Path("id") int id, @Query("api_key") String apiKey, @Query("format") String format);

    @GET("reviews/")
    Call<ReviewsContainer> getAllReviews(@Query("api_key") String apiKey, @Query("format") String format, @Query("sort") String sort, @Query("limit") int limit);

    @GET("review/{id}/")
    Call<ReviewsContainer> getReview(@Path("id") int id, @Query("api_key") String apiKey, @Query("format") String format);
}
