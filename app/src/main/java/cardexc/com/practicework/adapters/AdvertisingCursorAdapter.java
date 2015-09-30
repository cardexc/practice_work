package cardexc.com.practicework.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import cardexc.com.practicework.DBContract.*;
import cardexc.com.practicework.R;
import cardexc.com.practicework.Util;

public class AdvertisingCursorAdapter extends CursorAdapter {

    public AdvertisingCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView item_list_firstText  = (TextView) view.findViewById(R.id.item_list_firstText);
        TextView item_list_secondText = (TextView) view.findViewById(R.id.item_list_secondText);
        ImageView item_list_image = (ImageView) view.findViewById(R.id.item_list_image);

        String place = cursor.getString(cursor.getColumnIndexOrThrow(AdvertisingEntry.PLACE));
        String district = cursor.getString(cursor.getColumnIndexOrThrow(AdvertisingEntry.DISTRICT));
        item_list_firstText.setText(String.format("%s (%s)", place, district));

        item_list_secondText.setText(cursor.getString(cursor.getColumnIndexOrThrow(AdvertisingEntry.DATETIME)));
        item_list_image.setImageBitmap(
                Util.byteArrayToBitmap(cursor.getBlob(cursor.getColumnIndexOrThrow(AdvertisingEntry.IMAGE)))
        );



    }
}
