package ie.dit.giantbombapp.controller;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import ie.dit.giantbombapp.controller.api.ApiManager;
import ie.dit.giantbombapp.model.database.DatabaseManager;
import ie.dit.giantbombapp.model.pojos.Promo;

import ie.dit.giantbombapp.model.pojos.PromosContainer;
import ie.dit.giantbombapp.model.pojos.Review;
import ie.dit.giantbombapp.model.pojos.ReviewsContainer;
import ie.dit.giantbombapp.model.pojos.Search;
import ie.dit.giantbombapp.model.pojos.SearchContainer;
import retrofit2.Call;

/**
 * Author: Graham Byrne
 *
 * Created: 15/11/2016
 * Modified: 25/11/2016
 *
 * This is the primary controller for the activities. It links each activity to the
 * SQLiteDatabase, as well as giving them a way to query the API
 */

public class MainController
{
    private static final String TAG = "MainController";
    private int totalPromoResults;
    private int totalReviewResults;
    private final ApiManager mApi = new ApiManager();
    private final DatabaseManager db;
    private final String apiKey, format, sort;
    private final ConnectivityManager connManager;
    private NetworkInfo networkInfo;
    private final int initialLimit;

    /**
     * Main constructor for MainController
     * @param ctx: Context, needed for creating the database
     * @param apiKey: API Key to use to verify each API call
     * @param format: Format by which to retrieve each API call
     * @param sort: The sorting type to use when querying the Reviews resource
     */
    public MainController(Context ctx, String apiKey, String format, String sort)
    {
        this.apiKey = apiKey;
        this.format = format;
        this.sort = sort;
        db = new DatabaseManager(ctx);
        openDatabase();
        initialLimit = 10;
        connManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public void closeDatabase()
    {
        db.close();
    }

    public void openDatabase()
    {
        db.open();
    }

    /**
     * Fetch a single review from the database using a given Game Id
     * **/
    public Cursor fetchReview(int id)
    {
        return db.getReviewByGameId(id);
    }

    /**
     * Fetch every review in the database
     * **/
    public Cursor fetchAllReviews()
    {
        return db.getAllReviews();
    }

    /**
     * If the network is available and connected, this method gets the initial
     * number of Review objects given an initial limit, and enters each Review into
     * the local SQLLiteDatabase.
     *
     * It will also wipe the database each time it is called. This is to reduce the
     * amount of space the app takes up on the phone, as it is called each time the
     * app is launched.
     *
     * It uses a synchronised Retrofit call, so this method needs to be run off the
     * main UI thread
     */
    public void getInitialReviewData()
    {
        networkInfo = connManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected())
        {
            db.wipeReviews();
            Call<ReviewsContainer> call = mApi.getApi().getInitialReviews(apiKey, format, sort, initialLimit);
            ReviewsContainer container = null;
            try
            {
                container = call.execute().body();
            }
            catch (IOException e)
            {
                Log.d(TAG, "IOException when attempting to execute API call");
            }

            if(container != null)
            {
                List<Review> results = container.getResults();
                totalReviewResults = container.getNumberOfTotalResults();
                for (Review result : results)
                {

                    long dbInsert = db.insertReview(result);

                    if (dbInsert < 0)
                    {
                        Log.d(TAG, "There was an error when inserting the promo data into the database");
                    }
                }
                Log.d(TAG, "Entered initial Review data to database");
            }
        }
    }

    /**
     * This method is identical to the GetInitialReviews() method, except it returns each Review found
     * after a given offset, and within the limit specified.
     *
     * @param limit: Number of review items to retrieve
     * @param offset: Offset from which to search the API
     */
    public void getMoreReviewData(int limit, int offset)
    {
        networkInfo = connManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected())
        {
            Call<ReviewsContainer> call = mApi.getApi().getMoreReviews(apiKey, format, sort, limit, offset);
            ReviewsContainer container = null;
            try
            {
                container = call.execute().body();
            }
            catch (IOException e)
            {
                Log.d(TAG, "IOException when attempting to execute API call");
            }

            if(container != null)
            {
                List<Review> results = container.getResults();

                for (Review result : results)
                {

                    long dbInsert = db.insertReview(result);

                    if (dbInsert < 0)
                    {
                        Log.d(TAG, "There was an error when inserting the promo data into the database");
                    }
                }

                Log.d(TAG, "Extra Review data entered to database");
            }
        }
    }

    public int getTotalReviewResults()
    {
        return totalReviewResults;
    }

    /**
     * See fetchReview()
     */
    public Cursor fetchPromo(int id)
    {
        return db.getPromoByPromoId(id);
    }

    /**
     * See fetchAllReviews()
     */
    public Cursor fetchAllPromos()
    {
        return db.getAllPromos();
    }

    /**
     * See getInitialReviewData()
     */
    public void getInitialPromoData()
    {
        networkInfo = connManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected())
        {
            db.wipePromos();
            Call<PromosContainer> call = mApi.getApi().getInitialPromos(apiKey, format, initialLimit);
            PromosContainer container = null;
            try
            {
                container = call.execute().body();
            } catch (IOException e)
            {
                Log.d(TAG, "IOException when attempting to execute API call");
            }

            if(container != null)
            {
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
                Log.d(TAG, "Initial Promo data entered to database");
            }
        }
    }

    /**
     * See getMoreReviewData()
     */
    public void getMorePromoData(int limit, int offset)
    {
        networkInfo = connManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected())
        {
            Call<PromosContainer> call = mApi.getApi().getMorePromos(apiKey, format, limit, offset);
            PromosContainer container = null;
            try
            {
                container = call.execute().body();
            } catch (IOException e)
            {
                Log.d(TAG, "IOException when attempting to execute API call");
            }

            if(container != null)
            {
                List<Promo> results = container.getResults();

                for (Promo result : results)
                {

                    long dbInsert = db.insertPromo(result);

                    if (dbInsert < 0)
                    {
                        Log.d(TAG, "There was an error when inserting the promo data into the database");
                    }
                }
                Log.d(TAG, "Extra Promo data added to database");
            }
        }
    }

    public int getTotalPromoResults()
    {
        return totalPromoResults;
    }

    /**
     * This method is slightly different from the Review and Promo methods in that
     * it returns a list of Search objects instead of a Cursor. I think it would be
     * overkill to create a database table to hold every search result found for the
     * given query and resource
     *
     * @param query: The query for which to search
     * @param resource: The resource within which to search for the given query above
     *
     * @return: A list of Search objects
     */
    public List<Search> searchForData(String query, String resource)
    {
        networkInfo = connManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected())
        {
            query = "name:" + query;
            resource = resource.toLowerCase();
            resource = resource.replace(" ", "_");
            Call<SearchContainer> call = mApi.getApi().searchResource(resource, apiKey, format, query);
            SearchContainer container;
            try
            {
                container = call.execute().body();
            } catch (IOException e)
            {
                return null;
            }

            if(container != null)
            {
                return container.getResults();
            }
        }

        return null;

    }
}
