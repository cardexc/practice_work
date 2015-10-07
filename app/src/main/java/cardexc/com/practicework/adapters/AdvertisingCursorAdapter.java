package cardexc.com.practicework.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.lang.ref.WeakReference;

import cardexc.com.practicework.sqlite.DBContract.*;
import cardexc.com.practicework.R;
import cardexc.com.practicework.data.Util;

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

        /*File photo = new File(cursor.getString(cursor.getColumnIndexOrThrow(AdvertisingEntry.IMAGEPATH)));
        Uri mImageUri = Uri.fromFile(photo);
        try {
            Util.setImageToView(context, item_list_image, mImageUri, 4);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        new LoadImageTask(item_list_image, context).execute(cursor.getString(cursor.getColumnIndexOrThrow(AdvertisingEntry.IMAGEPATH)));

    }


    class LoadImageTask extends AsyncTask<String, Void, Bitmap> {

        private final WeakReference<ImageView> imageViewReference;
        private final Context context;

        public LoadImageTask(ImageView imageView, Context context) {

            this.context = context;
            this.imageViewReference = new WeakReference<ImageView>(imageView);

        }

        @Override
        protected Bitmap doInBackground(String... params) {

            String filepath = params[0];

            try {

                File photo = new File(filepath);
                Uri mImageUri = Uri.fromFile(photo);
                return Util.getBitmapFromUri(context, mImageUri, 4);

            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {

            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                if (imageView != null)
                    imageView.setImageBitmap(bitmap);

            }

        }
    }
}
