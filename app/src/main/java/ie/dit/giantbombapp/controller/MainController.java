package ie.dit.giantbombapp.controller;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.util.Log;

import java.util.List;

import ie.dit.giantbombapp.R;
import ie.dit.giantbombapp.controller.api.ApiManager;
import ie.dit.giantbombapp.model.database.DatabaseManager;
import ie.dit.giantbombapp.model.pojos.PromoResult;

import ie.dit.giantbombapp.model.pojos.ResultsContainer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by graha on 15/11/2016.
 */

public class MainController
{
    private static final String TAG = "MainController";
    private ApiManager mApi = new ApiManager();
    private DatabaseManager db;
    private Context ctx;
    private String apiKey, format;

    public MainController(Context ctx, String apiKey, String format)
    {
        this.ctx = ctx;
        this.apiKey = apiKey;
        this.format = format;
        db = new DatabaseManager(ctx);
        db.open();
    }

    public Cursor fetchPromo(int id)
    {
        Cursor cursor = null;


        Call<ResultsContainer> call = mApi.getApi().getPromo(id, apiKey, format);
        call.enqueue(new Callback<ResultsContainer>() {
            @Override
            public void onResponse(Call<ResultsContainer> call, Response<ResultsContainer> response) {
                //int statusCode = response.code();
                ResultsContainer resultsContainer = response.body();
                PromoResult promoResult = resultsContainer.getResults().get(0);
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
            public void onFailure(Call<ResultsContainer> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
        cursor = db.getPromoByPromoId(id);


        return cursor;
    }

    public Cursor fetchAllPromos()
    {
        Cursor cursor = null;
        Call<ResultsContainer> call = mApi.getApi().getAllPromos(apiKey, format);
        call.enqueue(new Callback<ResultsContainer>() {
            @Override
            public void onResponse(Call<ResultsContainer> call, Response<ResultsContainer> response) {
                Log.d(TAG, "Response was successful");
                //int statusCode = response.code();
                ResultsContainer resultsContainer = response.body();
                List<PromoResult> results = resultsContainer.getResults();
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
            public void onFailure(Call<ResultsContainer> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
        cursor = db.getAllPromos();


        return cursor;
    }
}
