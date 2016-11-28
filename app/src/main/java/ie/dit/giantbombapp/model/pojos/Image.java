package ie.dit.giantbombapp.model.pojos;

import com.google.gson.annotations.SerializedName;

/**
 * Author: Graham Byrne
 *
 * Created:15/11/2016
 * Modified: 25/11/2016
 *
 * POJO for the Image JSON object found on the Giantbomb API
 *
 * SerialisedName annotation is used to specify the JSON form of the variable
 * name
 */

public class Image
{
    @SerializedName("thumb_url")
    private String thumbUrl;
    @SerializedName("small_url")
    private String smallUrl;

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getSmallUrl() {
        return smallUrl;
    }

    public void setSmallUrl(String smallUrl) {
        this.smallUrl = smallUrl;
    }
}
