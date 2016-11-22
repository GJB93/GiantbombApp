package ie.dit.giantbombapp.controller.api;

import ie.dit.giantbombapp.model.pojos.GenericContainer;
import ie.dit.giantbombapp.model.pojos.PromoResult;
import ie.dit.giantbombapp.model.pojos.ResultsContainer;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Graham on 14-Nov-16.
 */

public interface GiantbombApi {

    @GET("promos/")
    Call<ResultsContainer> getAllPromos(@Query("api_key") String apiKey, @Query("format") String format);

    @GET("promo/{id}/")
    Call<ResultsContainer> getPromo(@Path("id") int id, @Query("api_key") String apiKey, @Query("format") String format);

    @GET("promos/")
    Call<GenericContainer<PromoResult>> getAllPromosG(@Query("api_key") String apiKey, @Query("format") String format);

    @GET("promo/{id}/")
    Call<GenericContainer<PromoResult>> getPromoG(@Path("id") int id, @Query("api_key") String apiKey, @Query("format") String format);
}
