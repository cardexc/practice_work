package cardexc.com.practicework.sqlite;

import android.provider.BaseColumns;

public class DBContract {

    public static class AdvertisingEntry implements BaseColumns{

        public static final String TABLE_NAME = "Advertising";
        public static final String DATETIME = "datetime";
        public static final String PLACE = "place";
        public static final String IMAGE = "image";
        public static final String DISTRICT = "district";
        public static final String IMAGEPATH = "path_to_image";

        public static final String[] COLUMNS = {DATETIME, PLACE, IMAGE, DISTRICT, IMAGEPATH};

    }

}
