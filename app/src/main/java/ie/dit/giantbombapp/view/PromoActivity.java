package ie.dit.giantbombapp.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ie.dit.giantbombapp.R;
import ie.dit.giantbombapp.controller.ApiManager;
import ie.dit.giantbombapp.model.PromoResponse;
import ie.dit.giantbombapp.model.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PromoActivity extends AppCompatActivity {

    private static final String TAG = "PromoActivity";
    List<Result> promos = new ArrayList<>();
    PromoResponse resp;
    ApiManager mApi = new ApiManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promo_activity);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        Call<PromoResponse> call = mApi.getApi().getPromo(12229, getString(R.string.api_key), getString(R.string.format));
        call.enqueue(new Callback<PromoResponse>() {
            @Override
            public void onResponse(Call<PromoResponse> call, Response<PromoResponse> response) {
                int statusCode = response.code();
                resp = response.body();
                Log.d(TAG, resp.getError());
            }

            @Override
            public void onFailure(Call<PromoResponse> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }
}
