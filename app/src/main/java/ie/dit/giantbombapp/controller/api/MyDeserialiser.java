package ie.dit.giantbombapp.controller.api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by graha on 16/11/2016.
 */

class MyDeserialiser<T> implements JsonDeserializer<T>
{
    private static final String TAG = "MyDeserialiser";

    @Override
    public T deserialize(JsonElement je, Type type, JsonDeserializationContext jdc)
        throws JsonParseException
    {
        Log.d(TAG, "Getting the JSONElement...");
        JsonElement results = je.getAsJsonObject().get("results");

        Log.d(TAG, "Deserialising...");

        return new Gson().fromJson(results, type);
    }
}
