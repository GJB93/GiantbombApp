package ie.dit.giantbombapp.controller;

import android.content.res.Resources;
import android.util.Log;

import ie.dit.giantbombapp.R;
import ie.dit.giantbombapp.model.pojos.PromoResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by graha on 15/11/2016.
 */

public class MainController
{
    private static final String TAG = "MainController";
    private Resources res = Resources.getSystem();
    ApiManager mApi = new ApiManager();

    protected void fetchPromo(int id)
    {
        Call<PromoResponse> call = mApi.getApi().getPromo(id, res.getString(R.string.api_key), res.getString(R.string.format));
        call.enqueue(new Callback<PromoResponse>() {
            @Override
            public void onResponse(Call<PromoResponse> call, Response<PromoResponse> response) {
                int statusCode = response.code();
            }

            @Override
            public void onFailure(Call<PromoResponse> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }
}
