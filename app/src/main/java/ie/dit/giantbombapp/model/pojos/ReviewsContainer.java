package ie.dit.giantbombapp.model.pojos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by graha on 22/11/2016.
 */

public class ReviewsContainer
{
    @SerializedName("number_of_total_results")
    int numberOfTotalResults;
    List<Review> results;

    public List<Review> getResults()
    {
        return results;
    }

    public void setResults(List<Review> results)
    {
        this.results = results;
    }

    public int getNumberOfTotalResults() {
        return numberOfTotalResults;
    }

    public void setNumberOfTotalResults(int numberOfTotalResults) {
        this.numberOfTotalResults = numberOfTotalResults;
    }
}
