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
 * Created by graha on 16/11/2016.
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
        if(je.getAsJsonObject().get("results").isJsonArray())
        {
            Log.d(TAG, "JsonElement is an array");
            JsonElement jsonElement = je.getAsJsonObject().get("results");
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            List<Promo> results = new ArrayList<>();
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
