package ie.dit.giantbombapp.model;

import java.util.List;

/**
 * Created by Graham on 15-Nov-16.
 */

public class PromoResponse {
    private String error;
    private int limit;
    private int offset;
    private int numberOfPageResults;
    private int numberOfTotalResults;
    private int statusCode;
    private Promo promo;
    private int version;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getNumberOfPageResults() {
        return numberOfPageResults;
    }

    public void setNumberOfPageResults(int numberOfPageResults) {
        this.numberOfPageResults = numberOfPageResults;
    }

    public int getNumberOfTotalResults() {
        return numberOfTotalResults;
    }

    public void setNumberOfTotalResults(int numberOfTotalResults) {
        this.numberOfTotalResults = numberOfTotalResults;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Promo getPromo() {
        return promo;
    }

    public void setPromo(Promo promo) {
        this.promo = promo;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
