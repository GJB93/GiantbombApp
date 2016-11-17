package ie.dit.giantbombapp.model.pojos;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Graham on 15-Nov-16.
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
