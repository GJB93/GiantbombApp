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

import ie.dit.giantbombapp.model.pojos.Search;
import ie.dit.giantbombapp.model.pojos.SearchContainer;

/**
 * Author: Graham Byrne
 *
 * Created: 16/11/2016
 * Modified: 25/11/2016
 *
 * See the PromoDeserialiser description for an explanation for this
 * deserialiser
 */

class SearchDeserialiser implements JsonDeserializer<SearchContainer>
{
    private static final String TAG = "SearchDeserialiser";

    @Override
    public SearchContainer deserialize(JsonElement je, Type type, JsonDeserializationContext jdc)
            throws JsonParseException
    {
        SearchContainer searchContainer = new SearchContainer();
        searchContainer.setNumberOfTotalResults(je.getAsJsonObject().get("number_of_total_results").getAsInt());
        if(je.getAsJsonObject().get("results").isJsonArray())
        {
            Log.d(TAG, "JsonElement is an array");
            JsonElement jsonElement = je.getAsJsonObject().get("results");
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            List<Search> results = new ArrayList<>();
            for(int i=0; i<jsonArray.size(); i++)
            {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                Search search = new Gson().fromJson(jsonObject, Search.class);
                results.add(search);
            }

            searchContainer.setResults(results);
            return searchContainer;
        }
        else
        {
            Log.d(TAG, "JsonElement is an object");
            JsonElement jsonElement = je.getAsJsonObject().get("results");
            Search search = jdc.deserialize(jsonElement, Search.class);
            List<Search> reviewList = new ArrayList<>(1);
            reviewList.add(search);
            searchContainer.setResults(reviewList);
            return searchContainer;
        }
    }
}
