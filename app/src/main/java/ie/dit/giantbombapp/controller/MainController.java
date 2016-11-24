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
    private int totalPromoResults;
    private ApiManager mApi = new ApiManager();
    private DatabaseManager db;
    private Context ctx;
    private String apiKey, format, sort;
    private ConnectivityManager connManager;
    private NetworkInfo networkInfo;
    private int initialLimit;

    public MainController(Context ctx, String apiKey, String format, String sort)
    {
        this.ctx = ctx;
        this.apiKey = apiKey;
        this.format = format;
        this.sort = sort;
        db = new DatabaseManager(ctx);
        db.open();
        initialLimit = 10;
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
        return db.getAllReviews();
    }

    public void getReviewData()
    {
        networkInfo = connManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected())
        {
            db.wipeReviews();
            Call<ReviewsContainer> call = mApi.getApi().getAllReviews(apiKey, format, sort, initialLimit);
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
            Log.d(TAG, "Entered Review Data to Database");
        }
    }

    public void getInitialPromoData()
    {
        networkInfo = connManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected())
        {
            db.wipePromos();
            Call<PromosContainer> call = mApi.getApi().getInitialPromos(apiKey, format, initialLimit);
            PromosContainer container;
            try
            {
                container = call.execute().body();
                totalPromoResults = container.getNumberOfTotalResults();
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

    public void getMorePromoData(int limit, int offset)
    {
        networkInfo = connManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected())
        {
            Call<PromosContainer> call = mApi.getApi().getMorePromos(apiKey, format, limit, offset);
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
            Log.d(TAG, "Extra promo data added to database");
        }
    }

    public int getTotalPromoResults()
    {
        return totalPromoResults;
    }
}
