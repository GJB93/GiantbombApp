package ie.dit.giantbombapp.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Graham on 14-Nov-16.
 */

public class ApiManager {
    private static final String BASE_URL = "http://www.giantbomb.com/api/";
    private GiantbombApi mApi;

    public GiantbombApi getApi()
    {
        if(mApi == null)
        {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            mApi = retrofit.create(GiantbombApi.class);
        }
        return mApi;
    }
}
