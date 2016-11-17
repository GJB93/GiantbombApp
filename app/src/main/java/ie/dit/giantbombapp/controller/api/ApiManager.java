package ie.dit.giantbombapp.controller.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import ie.dit.giantbombapp.model.pojos.PromoResult;
import ie.dit.giantbombapp.model.pojos.PromoResults;
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
            Type listType = new TypeToken<List<PromoResult>>(){}.getType();
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .registerTypeAdapter(PromoResult.class, new MyDeserialiser<PromoResult>())
                    .registerTypeAdapter(listType, new ResultsDeserialiser())
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
