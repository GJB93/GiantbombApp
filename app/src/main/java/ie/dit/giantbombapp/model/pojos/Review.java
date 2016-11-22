package ie.dit.giantbombapp.model.pojos;

import com.google.gson.annotations.SerializedName;

/**
 * Created by graha on 22/11/2016.
 */

public class Review
{
    @SerializedName("api_detail_url")
    private String apiDetailUrl;
    private String deck;
    private String description;
    private Game game;
    @SerializedName("publish_date")
    private String publishDate;
    private Release release;
    private String reviewer;
    private int score;
    @SerializedName("site_detail_url")
    private String siteDetailUrl;
    private String platforms;

    public String getApiDetailUrl()
    {
        return apiDetailUrl;
    }

    public void setApiDetailUrl(String apiDetailUrl)
    {
        this.apiDetailUrl = apiDetailUrl;
    }

    public String getDeck()
    {
        return deck;
    }

    public void setDeck(String deck)
    {
        this.deck = deck;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Game getGame()
    {
        return game;
    }

    public void setGame(Game game)
    {
        this.game = game;
    }

    public String getPublishDate()
    {
        return publishDate;
    }

    public void setPublishDate(String publishDate)
    {
        this.publishDate = publishDate;
    }

    public Release getRelease()
    {
        return release;
    }

    public void setRelease(Release release)
    {
        this.release = release;
    }

    public String getReviewer()
    {
        return reviewer;
    }

    public void setReviewer(String reviewer)
    {
        this.reviewer = reviewer;
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public String getSiteDetailUrl()
    {
        return siteDetailUrl;
    }

    public void setSiteDetailUrl(String siteDetailUrl)
    {
        this.siteDetailUrl = siteDetailUrl;
    }

    public String getPlatforms()
    {
        return platforms;
    }

    public void setPlatforms(String platforms)
    {
        this.platforms = platforms;
    }
}
