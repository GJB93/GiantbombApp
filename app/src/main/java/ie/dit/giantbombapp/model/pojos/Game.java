package ie.dit.giantbombapp.model.pojos;

import com.google.gson.annotations.SerializedName;

/**
 * Created by graha on 22/11/2016.
 */

public class Game
{
    @SerializedName("api_detail_url")
    private String apiDetailUrl;
    private int id;
    private String name;
    @SerializedName("site_detail_url")
    private String siteDetailUrl;

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

    public String getSiteDetailUrl()
    {
        return siteDetailUrl;
    }

    public void setSiteDetailUrl(String siteDetailUrl)
    {
        this.siteDetailUrl = siteDetailUrl;
    }
}
