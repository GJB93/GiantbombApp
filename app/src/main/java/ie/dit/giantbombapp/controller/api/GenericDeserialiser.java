package ie.dit.giantbombapp.controller.api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ie.dit.giantbombapp.model.pojos.GenericContainer;

/**
 * Created by graha on 16/11/2016.
 */

class GenericDeserialiser<T> implements JsonDeserializer<GenericContainer<T>>
{
    private static final String TAG = "GenericDeserialiser";

    @Override
    public GenericContainer<T> deserialize(JsonElement je, Type type, JsonDeserializationContext jdc)
            throws JsonParseException
    {
        Log.d(TAG, "We're in the deserialiser");
        GenericContainer<T> genericContainer = new GenericContainer<>();
        Type resultsType = new TypeToken<T>() {}.getType();
        Log.d(TAG, resultsType.toString());
        if(je.getAsJsonObject().get("results").isJsonArray())
        {
            Log.d(TAG, "JsonElement is an array");
            JsonElement jsonElement = je.getAsJsonObject().get("results");
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            List<T> results = new ArrayList<>();
            for(int i=0; i<jsonArray.size(); i++)
            {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                T result = new Gson().fromJson(jsonObject, resultsType);
                results.add(result);
            }
            genericContainer.setResults(results);
            return genericContainer;
        }
        else
        {
            Log.d(TAG, "JsonElement is an object");
            JsonElement jsonElement = je.getAsJsonObject().get("results");
            T result = jdc.deserialize(jsonElement, resultsType);
            List<T> resultList = new ArrayList<>(1);
            resultList.add(result);
            genericContainer.setResults(resultList);
            return genericContainer;
        }
    }
}