package ie.dit.giantbombapp.controller.api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ie.dit.giantbombapp.model.pojos.PromoResult;
import ie.dit.giantbombapp.model.pojos.ResultsContainer;

/**
 * Created by graha on 16/11/2016.
 */

class PromoDeserialiser implements JsonDeserializer<ResultsContainer>
{
    private static final String TAG = "PromoDeserialiser";

    @Override
    public ResultsContainer deserialize(JsonElement je, Type type, JsonDeserializationContext jdc)
        throws JsonParseException
    {
        ResultsContainer resultsContainer = new ResultsContainer();
        if(je.getAsJsonObject().get("results").isJsonArray())
        {
            Log.d(TAG, "JsonElement is an array");
            JsonElement jsonElement = je.getAsJsonObject().get("results");
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            List<PromoResult> results = new ArrayList<>();
            for(int i=0; i<jsonArray.size(); i++)
            {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                PromoResult promoResult = new Gson().fromJson(jsonObject, PromoResult.class);
                results.add(promoResult);
            }
            resultsContainer.setResults(results);
            return resultsContainer;
        }
        else
        {
            Log.d(TAG, "JsonElement is an object");
            JsonElement jsonElement = je.getAsJsonObject().get("results");
            PromoResult promoResult = jdc.deserialize(jsonElement, PromoResult.class);
            List<PromoResult> promoResultList = new ArrayList<>(1);
            promoResultList.add(promoResult);
            resultsContainer.setResults(promoResultList);
            return resultsContainer;
        }
    }
}
