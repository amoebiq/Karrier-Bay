package Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vel on 12/2/17.
 */

public class SenderOrderListResponse {
    SenderOrder senderOrder[];

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    @SerializedName("errors")
    @Expose
    private List<String> errors;
}
