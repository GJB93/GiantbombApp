package ie.dit.giantbombapp.model.pojos;

import com.google.gson.annotations.SerializedName;

/**
 * Author: Graham Byrne
 *
 * Created: 22/11/2016
 * Modified: 25/11/2016
 *
 * POJO for the Release JSON object found on the Giantbomb API
 *
 * SerialisedName annotation is used to specify the JSON form of the variable
 * name
 */

public class Release
{
    @SerializedName("api_detail_url")
    private String apiDetailUrl;
    private int id;
    private String name;

    public String getApiDetailUrl()
    {
        return apiDetailUrl;
    }

    public void setApiDetailUrl(String apiDetailUrl)
    {
        this.apiDetailUrl = apiDetailUrl;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
