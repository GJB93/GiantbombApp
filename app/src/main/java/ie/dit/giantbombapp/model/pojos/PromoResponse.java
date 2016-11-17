package ie.dit.giantbombapp.model.pojos;

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
    private Results results;
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

    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String toString()
    {
        return getError() + " " + getLimit() + " " + getNumberOfPageResults() + " " +
                getNumberOfTotalResults() + " " + getResults().toString() + " " + Integer.toString(getVersion());
    }
}
