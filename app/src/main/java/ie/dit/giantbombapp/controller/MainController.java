package ie.dit.giantbombapp.controller;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.util.Log;

import java.util.List;

import ie.dit.giantbombapp.controller.api.ApiManager;
import ie.dit.giantbombapp.model.database.DatabaseManager;
import ie.dit.giantbombapp.model.pojos.PromoResult;

import ie.dit.giantbombapp.model.pojos.PromoResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by graha on 15/11/2016.
 */

public class MainController
{
    private static final String TAG = "MainController";
    private Resources resources = Resources.getSystem();
    private ApiManager mApi = new ApiManager();
    private DatabaseManager db;
    private Context ctx;

    public MainController(Context ctx)
    {
        this.ctx = ctx;
        resources = Resources.getSystem();
        db = new DatabaseManager(ctx);
        db.open();
    }

    public Cursor fetchPromo(int id)
    {
        Cursor cursor = null;


        Call<PromoResult> call = mApi.getApi().getPromo(id, "8481d27bba6dbd03cb21734fea664a72d6436747", "json");
        call.enqueue(new Callback<PromoResult>() {
            @Override
            public void onResponse(Call<PromoResult> call, Response<PromoResult> response) {
                //int statusCode = response.code();
                PromoResult promoResult = response.body();
                long dbInsert = db.insertPromo(promoResult.getDateAdded(), promoResult.getApiDetailUrl(), promoResult.getDeck(),
                        promoResult.getId(), promoResult.getLink(), promoResult.getName(), promoResult.getResourceType(), promoResult.getUser());

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
            public void onFailure(Call<PromoResult> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
        cursor = db.getPromoByPromoId(id);


        return cursor;
    }

    public Cursor fetchAllPromos()
    {
        Cursor cursor = null;
        Call<List<PromoResult>> call = mApi.getApi().getAllPromos("8481d27bba6dbd03cb21734fea664a72d6436747", "json");
        call.enqueue(new Callback<List<PromoResult>>() {
            @Override
            public void onResponse(Call<List<PromoResult>> call, Response<List<PromoResult>> response) {
                Log.d(TAG, "Response was successful");
                //int statusCode = response.code();
                List<PromoResult> results;
                results = response.body();

                Log.d(TAG, results.toString());

                for(PromoResult result:results)
                {

                    long dbInsert = db.insertPromo(result.getDateAdded(), result.getApiDetailUrl(), result.getDeck(),
                            result.getId(), result.getLink(), result.getName(), result.getResourceType(), result.getUser());

                    if (dbInsert < 0)
                    {
                        Log.d(TAG, "There was an error when inserting the data into the database");
                    } else
                    {
                        Log.d(TAG, "The data was entered into the database");
                    }
                }

            }

            @Override
            public void onFailure(Call<List<PromoResult>> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
        cursor = db.getAllPromos();


        return cursor;
    }
}
