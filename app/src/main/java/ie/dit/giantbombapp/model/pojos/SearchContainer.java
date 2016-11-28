package ie.dit.giantbombapp.model.pojos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Author: Graham Byrne
 *
 * Created: 24/11/2016
 * Modified: 25/11/2016
 *
 * POJO for retrieving each Search object in the results JSON array given
 * by the Giantbomb API
 *
 * SerialisedName annotation is used to specify the JSON form of the variable
 * name
 */

public class SearchContainer
{
    @SerializedName("number_of_total_results")
    private int numberOfTotalResults;
    private List<Search> results;

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
