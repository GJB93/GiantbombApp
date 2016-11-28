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

import ie.dit.giantbombapp.model.pojos.Review;
import ie.dit.giantbombapp.model.pojos.ReviewsContainer;

/**
 * Author: Graham Byrne
 *
 * Created: 16/11/2016
 * Modified: 25/11/2016
 *
 * See the PromoDeserialiser description for an explanation for this
 * deserialiser
 */

class ReviewDeserialiser implements JsonDeserializer<ReviewsContainer>
{
    private static final String TAG = "ReviewDeserialiser";

    @Override
    public ReviewsContainer deserialize(JsonElement je, Type type, JsonDeserializationContext jdc)
            throws JsonParseException
    {
        ReviewsContainer reviewsContainer = new ReviewsContainer();
        reviewsContainer.setNumberOfTotalResults(je.getAsJsonObject().get("number_of_total_results").getAsInt());
        if(je.getAsJsonObject().get("results").isJsonArray())
        {
            Log.d(TAG, "JsonElement is an array");
            JsonElement jsonElement = je.getAsJsonObject().get("results");
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            List<Review> results = new ArrayList<>();
            for(int i=0; i<jsonArray.size(); i++)
            {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                Review review = new Gson().fromJson(jsonObject, Review.class);
                results.add(review);
            }
            reviewsContainer.setResults(results);
            return reviewsContainer;
        }
        else
        {
            Log.d(TAG, "JsonElement is an object");
            JsonElement jsonElement = je.getAsJsonObject().get("results");
            Review review = jdc.deserialize(jsonElement, Review.class);
            List<Review> reviewList = new ArrayList<>(1);
            reviewList.add(review);
            reviewsContainer.setResults(reviewList);
            return reviewsContainer;
        }
    }
}
