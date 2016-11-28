package ie.dit.giantbombapp.controller.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ie.dit.giantbombapp.model.pojos.PromosContainer;
import ie.dit.giantbombapp.model.pojos.ReviewsContainer;
import ie.dit.giantbombapp.model.pojos.SearchContainer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author: Graham Byrne
 *
 * Created: 14/11/2016
 * Modified: 25/11/2016
 *
 * This class is used to create a Retrofit instance. The base url for the API
 * is supplied, along with a Gson library that is used to parse the incoming
 * JSON responses. The deserialisers in this case are custom deserialisers that
 * parse JSON to each specified class in a specific way.
 *
 * The Retrofit instance is also created as a singleton instance, so only one
 * Retrofit instance can exist at any one time.
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
                    .registerTypeAdapter(PromosContainer.class, new PromoDeserialiser())
                    .registerTypeAdapter(ReviewsContainer.class, new ReviewDeserialiser())
                    .registerTypeAdapter(SearchContainer.class, new SearchDeserialiser())
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
