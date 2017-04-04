package Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nirmal.ku on 12/21/2016.
 */
public class LoginResponse {

    @SerializedName("data")
    @Expose
    private Data data;

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    @SerializedName("errors")
    @Expose
    private List<String> errors;

    public LoginResponse(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
