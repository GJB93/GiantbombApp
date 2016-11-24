package ie.dit.giantbombapp.model.pojos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by graha on 24/11/2016.
 */

public class SearchContainer
{
    @SerializedName("number_of_total_results")
    int numberOfTotalResults;
    List<Search> results;

    public List<Search> getResults()
    {
        return results;
    }

    public void setResults(List<Search> results)
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
