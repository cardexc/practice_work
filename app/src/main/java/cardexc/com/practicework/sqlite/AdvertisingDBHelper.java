package cardexc.com.practicework.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import cardexc.com.practicework.data.Place;

public class AdvertisingDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "AdvertisingDB";

    private final String Advertising_DROP_TABLE = "DROP TABLE IF EXISTS " + DBContract.AdvertisingEntry.TABLE_NAME;

    private final String Advertising_CREATE_TABLE = "CREATE TABLE "
            + DBContract.AdvertisingEntry.TABLE_NAME + " ("
            + DBContract.AdvertisingEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DBContract.AdvertisingEntry.DATETIME + " TEXT, "
            + DBContract.AdvertisingEntry.PLACE + " TEXT, "
            + DBContract.AdvertisingEntry.DISTRICT + " TEXT, "
            + DBContract.AdvertisingEntry.IMAGEPATH + " TEXT, "
            + DBContract.AdvertisingEntry.IMAGE + " BLOB "
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
        Cursor cursor = readableDatabase.rawQuery("SELECT * FROM " + DBContract.AdvertisingEntry.TABLE_NAME, null);

        return cursor;

    }

    public void insertAd(Intent intent) {

        Place place = (Place) intent.getParcelableExtra("place");

        ContentValues cv = new ContentValues();

        cv.put(DBContract.AdvertisingEntry.PLACE, place.getPlace());
        cv.put(DBContract.AdvertisingEntry.DATETIME, place.getDateTime());
        cv.put(DBContract.AdvertisingEntry.DISTRICT, place.getDistrict());
        cv.put(DBContract.AdvertisingEntry.IMAGEPATH, place.getImagePath());

        SQLiteDatabase writableDatabase = getWritableDatabase();

        writableDatabase.insert(DBContract.AdvertisingEntry.TABLE_NAME, DBContract.AdvertisingEntry.COLUMNS.toString(), cv);

    }

    public void deleteAd(int id) {

        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.delete(DBContract.AdvertisingEntry.TABLE_NAME, DBContract.AdvertisingEntry._ID + " = ?", new String[]{String.valueOf(id)});

    }

    public ArrayList<Place> getPlacesList() {

        Cursor cursor = getAdCursorAdapter();
        ArrayList<Place> places = new ArrayList<>();

        while (cursor.moveToNext()) {
            places.add(new Place(cursor));
        }

        cursor.close();

        return places;

    }

    public ArrayList<String> getPlacesListTitles() {

        Cursor cursor = getAdCursorAdapter();
        ArrayList<String> titles = new ArrayList<>();

        while (cursor.moveToNext()) {
            titles.add(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.AdvertisingEntry.PLACE)));
        }

        cursor.close();

        return titles;

    }


}
