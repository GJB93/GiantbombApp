package ie.dit.giantbombapp.controller;

import java.util.List;

import ie.dit.giantbombapp.model.PromoResponse;
import ie.dit.giantbombapp.model.Result;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Graham on 14-Nov-16.
 */

public interface GiantbombApi {

    @GET("promos/")
    Call<List<Result>> getAllPromos(@Query("api_key") String apiKey, @Query("format") String format);

    @GET("promo/{id}/")
    Call<PromoResponse> getPromo(@Path("id") int id, @Query("api_key") String apiKey, @Query("format") String format);
}