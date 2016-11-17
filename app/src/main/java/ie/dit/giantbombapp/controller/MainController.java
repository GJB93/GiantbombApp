package ie.dit.giantbombapp.controller;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.util.Log;

import ie.dit.giantbombapp.R;
import ie.dit.giantbombapp.model.database.DatabaseManager;
import ie.dit.giantbombapp.model.pojos.Results;
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
    private ApiManager mApi = new ApiManager();
    private DatabaseManager db;
    private Context ctx;
    PromoResponse promoResponse;

    public MainController(Context ctx)
    {
        this.ctx = ctx;
        db = new DatabaseManager(ctx);
        db.open();
    }

    public Cursor fetchPromo(int id)
    {
        Cursor cursor = null;
        try {
            cursor = db.getPromoByPromoId(id);
            Log.d(TAG, "Cursor retrieved successfully");
        }
        catch (NullPointerException e)
        {
            Log.d(TAG, "Null pointer exception when getting cursor");
        }

        if(cursor == null) {
            Call<PromoResponse> call = mApi.getApi().getPromo(id, res.getString(R.string.api_key), res.getString(R.string.format));
            call.enqueue(new Callback<PromoResponse>() {
                @Override
                public void onResponse(Call<PromoResponse> call, Response<PromoResponse> response) {
                    int statusCode = response.code();
                    PromoResponse promoResponse = response.body();
                    Results results = promoResponse.getResults();
                    long dbInsert = db.insertPromo(results.getDateAdded(), results.getDeck(), results.getId(), results.getName(),
                            results.getResourceType(), results.getUser());

                    if(dbInsert < 0)
                    {
                        Log.d(TAG, "There was an error when inserting the data into the database");
                    }
                    else
                    {
                        Log.d(TAG, "The data was entered into the database");
                    }
                }

                @Override
                public void onFailure(Call<PromoResponse> call, Throwable t) {
                    Log.d(TAG, t.getMessage());
                }
            });
            db.getPromoByPromoId(id);
        }

        return cursor;
    }
}
