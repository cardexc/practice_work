package cardexc.com.practicework;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Place implements Parcelable {

    private String place;


    private String imagepath;
    private String district;
    private String dateTime;

    private Bitmap image;

    public Place(String place, String district, String dateTime, Bitmap image) {

        this.place = place;
        this.district = district;
        this.dateTime = dateTime;
        this.image = image;

    }

    public Place(String place, String district, String dateTime, String imagepath) {

        this.place = place;
        this.imagepath = imagepath;
        this.district = district;
        this.dateTime = dateTime;

    }


    public Place() {

    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }

    public byte[] getImageArray() {
        return Util.bitmapToBYteArray(image);
    }

    public String getDistrict() {
        return district;
    }

    public String getPlace() {
        return place;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getImagePath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.place);
        dest.writeString(this.district);
        dest.writeString(this.dateTime);
        dest.writeString(this.imagepath);

        byte[] bytes = Util.bitmapToBYteArray(getImage());
        dest.writeInt(bytes.length);
        dest.writeByteArray(bytes);

    }

    private Place(Parcel in) {
        this.place = in.readString();
        this.district = in.readString();
        this.dateTime = in.readString();
        this.imagepath = in.readString();

        byte[] imageArray = new byte[in.readInt()];
        in.readByteArray(imageArray);

        this.image = Util.byteArrayToBitmap(imageArray);
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        public Place createFromParcel(Parcel source) {
            return new Place(source);
        }

        public Place[] newArray(int size) {
            return new Place[size];
        }
    };
}
