package Model;

/**
 * Created by skadavath on 5/10/17.
 */

public class UserUpdateRequest {

    private String address;
    private String image;

    public UserUpdateRequest(String address , String image) {

        this.address = address;
        this.image = image;

    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
