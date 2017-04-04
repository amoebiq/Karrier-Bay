package Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nirmal.ku on 12/21/2016.
 */
public class SignUpResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private SignUpdata data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SignUpdata getData() {
        return data;
    }

    public void setData(SignUpdata data) {
        this.data = data;
    }

    public SignUpResponse(String status, SignUpdata data) {

        this.status = status;
        this.data = data;
    }

}
