package ie.dit.giantbombapp.model.pojos;

import com.google.gson.annotations.SerializedName;

/**
 * Created by graha on 24/11/2016.
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
