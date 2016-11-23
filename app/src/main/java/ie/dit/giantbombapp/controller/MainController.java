package ie.dit.giantbombapp.controller;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import ie.dit.giantbombapp.controller.api.ApiManager;
import ie.dit.giantbombapp.model.database.DatabaseManager;
import ie.dit.giantbombapp.model.pojos.Promo;

import ie.dit.giantbombapp.model.pojos.PromosContainer;
import ie.dit.giantbombapp.model.pojos.Review;
import ie.dit.giantbombapp.model.pojos.ReviewsContainer;
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
    private String apiKey, format, sort;
    private ConnectivityManager connManager;
    private NetworkInfo networkInfo;

    public MainController(Context ctx, String apiKey, String format, String sort)
    {
        this.ctx = ctx;
        this.apiKey = apiKey;
        this.format = format;
        this.sort = sort;
        db = new DatabaseManager(ctx);
        db.open();
        connManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public Cursor fetchPromo(int id)
    {
        return db.getPromoByPromoId(id);
    }

    public Cursor fetchAllPromos()
    {
        return db.getAllPromos();
    }

    public Cursor fetchReview(int id)
    {
        return db.getReviewByGameId(id);
    }

    public Cursor fetchAllReviews()
    {
        return db.getAllPromos();
    }

    public void getReviewData()
    {
        networkInfo = connManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected())
        {
            db.wipePromos();
            Call<ReviewsContainer> call = mApi.getApi().getAllReviews(apiKey, format, sort);
            ReviewsContainer container;
            try
            {
                container = call.execute().body();
                List<Review> results = container.getResults();

                for (Review result : results)
                {

                    long dbInsert = db.insertReview(result);

                    if (dbInsert < 0)
                    {
                        Log.d(TAG, "There was an error when inserting the promo data into the database");
                    }
                }

            } catch (IOException e)
            {
                Log.d(TAG, "IOException when attempting to execute API call");
            }
        }
    }

    public void getPromoData()
    {
        networkInfo = connManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected())
        {
            db.wipePromos();
            Call<PromosContainer> call = mApi.getApi().getAllPromos(apiKey, format);
            PromosContainer container;
            try
            {
                container = call.execute().body();
                List<Promo> results = container.getResults();

                for (Promo result : results)
                {

                    long dbInsert = db.insertPromo(result);

                    if (dbInsert < 0)
                    {
                        Log.d(TAG, "There was an error when inserting the promo data into the database");
                    }
                }

            } catch (IOException e)
            {
                Log.d(TAG, "IOException when attempting to execute API call");
            }
        }
    }
}
