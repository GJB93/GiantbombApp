package ie.dit.giantbombapp.controller.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ie.dit.giantbombapp.model.pojos.Promo;

/**
 * Created by graha on 17/11/2016.
 */

public class ResultsDeserialiser implements JsonDeserializer<List<Promo>>
{
    @Override
    public List<Promo> deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException
    {
        JsonArray data = je.getAsJsonObject().getAsJsonArray("results");
        List<Promo> myList = new ArrayList<>();

        for(JsonElement e:data)
        {
            myList.add((Promo)jdc.deserialize(e, Promo.class));
        }

        return myList;
    }
}
