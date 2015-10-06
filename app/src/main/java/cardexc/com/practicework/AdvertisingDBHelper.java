package cardexc.com.practicework;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cardexc.com.practicework.DBContract.*;

public class AdvertisingDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "AdvertisingDB";

    private final String Advertising_DROP_TABLE = "DROP TABLE IF EXISTS " + AdvertisingEntry.TABLE_NAME;

    private final String Advertising_CREATE_TABLE = "CREATE TABLE "
            + AdvertisingEntry.TABLE_NAME + " ("
            + AdvertisingEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + AdvertisingEntry.DATETIME + " TEXT, "
            + AdvertisingEntry.PLACE + " TEXT, "
            + AdvertisingEntry.DISTRICT + " TEXT, "
            + AdvertisingEntry.IMAGEPATH + " TEXT, "
            + AdvertisingEntry.IMAGE + " BLOB "
            + " );";


    public AdvertisingDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Advertising_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(Advertising_DROP_TABLE);
        this.onCreate(db);

    }

    public Cursor getAdCursorAdapter() {

        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery("SELECT * FROM " + AdvertisingEntry.TABLE_NAME, null);

        return cursor;

    }

    public void insertAd(Intent intent) {

        Place place = (Place) intent.getParcelableExtra("place");

        ContentValues cv = new ContentValues();

        cv.put(AdvertisingEntry.PLACE, place.getPlace());
        cv.put(AdvertisingEntry.DATETIME, place.getDateTime());
        cv.put(AdvertisingEntry.DISTRICT, place.getDistrict());
        cv.put(AdvertisingEntry.IMAGEPATH, place.getImagePath());

        SQLiteDatabase writableDatabase = getWritableDatabase();

        writableDatabase.insert(AdvertisingEntry.TABLE_NAME, AdvertisingEntry.COLUMNS.toString(), cv);

    }

    public void deleteAd(int id) {

        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.delete(AdvertisingEntry.TABLE_NAME, AdvertisingEntry._ID + " = ?", new String[]{String.valueOf(id)});

    }

}
