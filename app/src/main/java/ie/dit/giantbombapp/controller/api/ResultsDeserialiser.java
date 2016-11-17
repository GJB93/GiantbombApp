package ie.dit.giantbombapp.controller.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ie.dit.giantbombapp.model.pojos.PromoResult;

/**
 * Created by graha on 17/11/2016.
 */

public class ResultsDeserialiser implements JsonDeserializer<List<PromoResult>>
{
    @Override
    public List<PromoResult> deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException
    {
        JsonArray data = je.getAsJsonObject().getAsJsonArray("results");
        List<PromoResult> myList = new ArrayList<>();

        for(JsonElement e:data)
        {
            myList.add((PromoResult)jdc.deserialize(e, PromoResult.class));
        }

        return myList;
    }
}
