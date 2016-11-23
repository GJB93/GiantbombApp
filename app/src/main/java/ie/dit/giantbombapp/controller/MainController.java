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
        Cursor cursor = db.getPromoByPromoId(id);


        /*
        networkInfo = connManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected() && cursor != null && cursor.getCount() == 0)
        {
            Call<PromosContainer> call = mApi.getApi().getPromo(id, apiKey, format);
            call.enqueue(new Callback<PromosContainer>()
            {
                @Override
                public void onResponse(Call<PromosContainer> call, Response<PromosContainer> response)
                {
                    int statusCode = response.code();
                    PromosContainer promosContainer = response.body();
                    Promo promo = null;
                    try
                    {
                        promo = promosContainer.getResults().get(0);
                        long dbInsert = db.insertPromo(promo);

                        if (dbInsert < 0)
                        {
                            Log.d(TAG, "There was an error when inserting the Promo data into the database");
                        } else
                        {
                            Log.d(TAG, "The Promo data was entered into the database");
                        }
                    }
                    catch (IndexOutOfBoundsException e)
                    {
                        Log.d(TAG, "There was an index out of bounds exception in fetch promo");
                    }
                }

                @Override
                public void onFailure(Call<PromosContainer> call, Throwable t)
                {
                    Log.d(TAG, t.getMessage());
                }
            });
            cursor = db.getPromoByPromoId(id);
        }
        */


        return cursor;
    }

    public Cursor fetchAllPromos()
    {
        Cursor cursor = null;

        /*
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
                Log.d(TAG, "All promo data was entered into the database");

            }
            catch (IOException e)
            {
                Log.d(TAG, "IOException when attempting to execute API call");
            }

            /*
            call.enqueue(new Callback<PromosContainer>()
            {
                @Override
                public void onResponse(Call<PromosContainer> call, Response<PromosContainer> response)
                {
                    if(response.isSuccessful())
                    {
                        Log.d(TAG, "Response was successful");
                    }
                    int statusCode = response.code();
                    PromosContainer promosContainer = response.body();
                    List<Promo> results = promosContainer.getResults();

                    for (Promo result : results)
                    {

                        long dbInsert = db.insertPromo(result);

                        if (dbInsert < 0)
                        {
                            Log.d(TAG, "There was an error when inserting the promo data into the database");
                        }
                    }
                    Log.d(TAG, "All promo data was entered into the database");
                }

                @Override
                public void onFailure(Call<PromosContainer> call, Throwable t)
                {
                    Log.d(TAG, t.getMessage());
                }
            });
            Log.d(TAG, "We got past the enqueue");

        }
    */
        Log.d(TAG, "Getting a cursor");
        cursor = db.getAllPromos();

        Log.d(TAG, "Cursor is being returned now");

        return cursor;
    }

    public class GetPromosTask extends AsyncTask<Void, Void, String>
    {
        protected String doInBackground(Void... params)
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
                return "All promo data was entered into the database";
            }
            else
            {
                return "Couldn't connect to network";
            }
        }

        protected void onPostExecute(String result)
        {
            if(result != null)
            {
                Log.d(TAG, result);
            }
        }
    }

    public Cursor fetchReview(int id)
    {
        Cursor cursor = db.getReviewByGameId(id);

        networkInfo = connManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected() && cursor != null && cursor.getCount() == 0)
        {
            Call<ReviewsContainer> call = mApi.getApi().getReview(id, apiKey, format);
            call.enqueue(new Callback<ReviewsContainer>()
            {
                @Override
                public void onResponse(Call<ReviewsContainer> call, Response<ReviewsContainer> response)
                {
                    int statusCode = response.code();
                    ReviewsContainer reviewsContainer = response.body();
                    Review review = reviewsContainer.getResults().get(0);
                    long dbInsert = db.insertReview(review);

                    if (dbInsert < 0)
                    {
                        Log.d(TAG, "There was an error when inserting the review data into the database");
                    } else
                    {
                        Log.d(TAG, "The review data was entered into the database");
                    }
                }

                @Override
                public void onFailure(Call<ReviewsContainer> call, Throwable t)
                {
                    Log.d(TAG, t.getMessage());
                }
            });
            cursor = db.getReviewByGameId(id);
        }

        return cursor;
    }

    public Cursor fetchAllReviews()
    {
        Cursor cursor = null;

        networkInfo = connManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected())
        {
            db.wipeReviews();
            Call<ReviewsContainer> call = mApi.getApi().getAllReviews(apiKey, format, sort);
            call.enqueue(new Callback<ReviewsContainer>()
            {
                @Override
                public void onResponse(Call<ReviewsContainer> call, Response<ReviewsContainer> response)
                {
                    Log.d(TAG, "Response was successful");
                    //int statusCode = response.code();
                    ReviewsContainer reviewsContainer = response.body();
                    List<Review> results = reviewsContainer.getResults();

                    for (Review result : results)
                    {

                        long dbInsert = db.insertReview(result);

                        if (dbInsert < 0)
                        {
                            Log.d(TAG, "There was an error when inserting the data into the database");
                        }
                    }
                    Log.d(TAG, "All review data was entered into the database");

                }

                @Override
                public void onFailure(Call<ReviewsContainer> call, Throwable t)
                {
                    Log.d(TAG, t.getMessage());
                }
            });
        }
        cursor = db.getAllPromos();


        return cursor;
    }
}
