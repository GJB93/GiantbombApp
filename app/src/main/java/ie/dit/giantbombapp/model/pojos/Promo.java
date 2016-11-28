package ie.dit.giantbombapp.model.pojos;

import com.google.gson.annotations.SerializedName;

/**
 * Author: Graham Byrne
 *
 * Created: 15/11/2016
 * Modified: 25/11/2016
 *
 * POJO for the Promo JSON object found on the Giantbomb API
 *
 * SerialisedName annotation is used to specify the JSON form of the variable
 * name
 */

public class Promo
{
    @SerializedName("api_detail_url")
    private String apiDetailUrl;
    @SerializedName("date_added")
    private String dateAdded;
    private String deck;
    private int id;
    private Image image;
    private String link;
    private String name;
    @SerializedName("resource_type")
    private String resourceType;
    private String user;

    public String getApiDetailUrl() {
        return apiDetailUrl;
    }

    public void setApiDetailUrl(String apiDetailUrl) {
        this.apiDetailUrl = apiDetailUrl;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getDeck() {
        return deck;
    }

    public void setDeck(String deck) {
        this.deck = deck;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String toString()
    {
        return getDeck() + " " + getName() + " " + getUser();
    }
}
