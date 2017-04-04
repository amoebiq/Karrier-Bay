package Model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;

import com.yourapp.developer.karrierbay.BR;

/**
 * Created by vel on 24/1/17.
 */





public class User  extends BaseObservable{

        private String uid;

        private String id;

        private String phone;

        private String updated_at;

        private String email;

        private String nickname;

        private String name;

        private String created_at;

        private String image;

        private String provider;

        public String getUid ()
        {
            return uid;
        }

        public void setUid (String uid)
        {
            this.uid = uid;
        }

        public String getId ()
        {
            return id;
        }

        public void setId (String id)
        {
            this.id = id;
        }

        public String getPhone ()
        {
            return phone;
        }

        public void setPhone (String phone)
        {
            this.phone = phone;
        }

        public String getUpdated_at ()
        {
            return updated_at;
        }

        public void setUpdated_at (String updated_at)
        {
            this.updated_at = updated_at;
        }

        public String getEmail ()
        {
            return email;
        }

        public void setEmail (String email)
        {
            this.email = email;
        }






        public String getCreated_at ()
        {
            return created_at;
        }

        public void setCreated_at (String created_at)
        {
            this.created_at = created_at;
        }





        public String getProvider ()
        {
            return provider;
        }

        public void setProvider (String provider)
        {
            this.provider = provider;
        }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


//    private String firstName;
//    private String spinCategory="Luggage";
//    private int spinWantToSendIdx =1;
//    public User(String firstName) {
//        this.firstName = firstName;
//        text = new ObservableField<>();
//     }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getSpinCategory() {
//        return spinCategory;
//    }
//
//    public void setSpinCategory(String spinCategory) {
//        this.spinCategory = spinCategory;
//    }
//    @Bindable
//    public int getSpinWantToSendIdx() {
//        return spinWantToSendIdx;
//    }
//
//    public void setSpinWantToSendIdx(int spinWantToSendIdx) {
//        this.spinWantToSendIdx = spinWantToSendIdx;
//        notifyPropertyChanged(BR.spinWantToSendIdx);
//    }
//    private ObservableField<String> text;
//
//    public ObservableField<String> getText() {
//        return text;
//    }
}