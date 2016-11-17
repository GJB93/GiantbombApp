package ie.dit.giantbombapp.controller.api;

import java.util.List;

import ie.dit.giantbombapp.model.pojos.PromoResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Graham on 14-Nov-16.
 */

public interface GiantbombApi {

    @GET("promos/")
    Call<List<PromoResult>> getAllPromos(@Query("api_key") String apiKey, @Query("format") String format);

    @GET("promo/{id}/")
    Call<PromoResult> getPromo(@Path("id") int id, @Query("api_key") String apiKey, @Query("format") String format);
}
