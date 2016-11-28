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

import ie.dit.giantbombapp.model.pojos.Promo;
import ie.dit.giantbombapp.model.pojos.PromosContainer;

/**
 * Author: Graham Byrne
 *
 * Created: 16/11/2016
 * Modified: 25/11/2016
 *
 * This deserialiser parses JSON code into the PromosContainer class, that contains
 * a list of Promo results, and an integer variable to hold the number of total results.
 *
 * JSON responses for the Results object can come back as a single JSON object or as an array of
 * JSON objects, so that is the reason for the isJsonObject and isJsonArray checks.
 *
 * This class formed the basis for the other two deserialisers, and each deserialiser is almost
 * identical to this one, bar the classes that the JSON is parsed into
 */

class PromoDeserialiser implements JsonDeserializer<PromosContainer>
{
    private static final String TAG = "PromoDeserialiser";

    @Override
    public PromosContainer deserialize(JsonElement je, Type type, JsonDeserializationContext jdc)
        throws JsonParseException
    {
        PromosContainer promosContainer = new PromosContainer();
        promosContainer.setNumberOfTotalResults(je.getAsJsonObject().get("number_of_total_results").getAsInt());

        //Check if the results object returned is a single object or an array
        if(je.getAsJsonObject().get("results").isJsonArray())
        {
            Log.d(TAG, "JsonElement is an array");
            JsonElement jsonElement = je.getAsJsonObject().get("results");
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            List<Promo> results = new ArrayList<>();

            //Parse each JSON object into a new Promo object and add the promo
            //object to the list
            for(int i=0; i<jsonArray.size(); i++)
            {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                Promo promo = new Gson().fromJson(jsonObject, Promo.class);
                results.add(promo);
            }
            promosContainer.setResults(results);
            return promosContainer;
        }
        else
        {
            //If the result object is a single JSON object, then it is added
            //to a list of size one, then passed to a PromosContainer object
            Log.d(TAG, "JsonElement is an object");
            JsonElement jsonElement = je.getAsJsonObject().get("results");
            Promo promo = jdc.deserialize(jsonElement, Promo.class);
            List<Promo> promoList = new ArrayList<>(1);
            promoList.add(promo);
            promosContainer.setResults(promoList);
            return promosContainer;
        }
    }
}
