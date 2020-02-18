package euromsg.com.euromobileandroid.carousalnotification;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Shailesh on 06/01/17.
 */

public class CarousalItem implements Parcelable {
    String id; //id can store a key id or entire object as a gson string. At developer discretion
    String title;
    String description;
    String photo_url;
    String image_file_location;
    String image_file_name;
    String type;



    public CarousalItem( String photo_url) {
        this(null, null, null, photo_url);
    }

    public CarousalItem() {
        this(null, null, null, null);
    }

    /**
     * Constructor with id. Recommended as it is easy to tell which item was clicked
     * @param id String id of the item. Later it can be checked if it is the item that was clicked.
     * @param title
     * @param description
     * @param photo_url
     */
    public CarousalItem(String id, String title, String description, String photo_url) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.photo_url = photo_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getImage_file_location() {
        return image_file_location;
    }

    public void setImage_file_location(String image_file_location) {
        this.image_file_location = image_file_location;
    }

    public String getImage_file_name() {
        return image_file_name;
    }

    public void setImage_file_name(String image_file_name) {
        this.image_file_name = image_file_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    protected CarousalItem(Parcel in) {
        id = in.readString();
        title = in.readString();
        description = in.readString();
        photo_url = in.readString();
        image_file_location = in.readString();
        image_file_name = in.readString();
        type = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(photo_url);
        dest.writeString(image_file_location);
        dest.writeString(image_file_name);
        dest.writeString(type);
    }

    @SuppressWarnings("unused")
    public static final Creator<CarousalItem> CREATOR = new Creator<CarousalItem>() {
        @Override
        public CarousalItem createFromParcel(Parcel in) {
            return new CarousalItem(in);
        }

        @Override
        public CarousalItem[] newArray(int size) {
            return new CarousalItem[size];
        }
    };
}