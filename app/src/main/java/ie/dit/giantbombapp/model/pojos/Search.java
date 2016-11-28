package ie.dit.giantbombapp.model.pojos;

import com.google.gson.annotations.SerializedName;

/**
 * Author: Graham Byrne
 *
 * Created: 24/11/2016
 * Modified: 25/11/2016
 *
 * POJO for the search results found on the Giantbomb API
 *
 * SerialisedName annotation is used to specify the JSON form of the variable
 * name
 */

public class Search
{
    private String aliases;
    private String deck;
    private int id;
    private Image image;
    private String name;
    @SerializedName("site_detail_url")
    private String siteUrl;

    public String getAliases()
    {
        return aliases;
    }

    public void setAliases(String aliases)
    {
        this.aliases = aliases;
    }

    public String getDeck()
    {
        return deck;
    }

    public void setDeck(String deck)
    {
        this.deck = deck;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public Image getImage()
    {
        return image;
    }

    public void setImage(Image image)
    {
        this.image = image;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSiteUrl()
    {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl)
    {
        this.siteUrl = siteUrl;
    }
}
