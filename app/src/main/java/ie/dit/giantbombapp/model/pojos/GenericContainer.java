package ie.dit.giantbombapp.model.pojos;

import java.util.List;

/**
 * Created by graha on 22/11/2016.
 */

public class GenericContainer<T>
{
    List<T> results;

    public List<T> getResults()
    {
        return results;
    }

    public void setResults(List<T> results)
    {
        this.results = results;
    }
}
