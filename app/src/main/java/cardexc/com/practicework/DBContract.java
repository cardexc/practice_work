package cardexc.com.practicework;

import android.provider.BaseColumns;

public class DBContract {

    static class AdvertisingEntry implements BaseColumns{

        public static final String TABLE_NAME = "Advertising";
        public static final String DATETIME = "datetime";
        public static final String PLACE = "place";
        public static final String IMAGE = "image";

        public static final String[] COLUMNS = {DATETIME, PLACE, IMAGE};

    }

}
