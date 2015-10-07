package cardexc.com.practicework.data;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class Util {

    public static byte[] bitmapToBYteArray(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();

    }

    public static Bitmap byteArrayToBitmap(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public static File createTemporaryFile(String part, String ext) throws Exception {
        File tempDir = Environment.getExternalStorageDirectory();
        tempDir = new File(tempDir.getAbsolutePath() + "/.temp/");
        if (!tempDir.exists()) {
            tempDir.mkdir();
        }
        return File.createTempFile(part, ext, tempDir);
    }

    public static void setImageToView(Context context, ImageView imageView, Uri mImageUri, int inSampleSize) throws Exception {

        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //if OutOfMemory Exception appears - read http://stackoverflow.com/questions/24135445/pre-guess-size-of-bitmap-from-the-actual-uri-before-scale-loading

        AssetFileDescriptor fileDescriptor = null;
        fileDescriptor = context.getContentResolver().openAssetFileDescriptor(mImageUri, "r");

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;

        Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor.getFileDescriptor(), null, options);
        imageView.setImageBitmap(bitmap);

    }

    public static Bitmap getBitmapFromUri(Context context, Uri mImageUri, int inSampleSize) throws Exception {

        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //if OutOfMemory Exception appears - read http://stackoverflow.com/questions/24135445/pre-guess-size-of-bitmap-from-the-actual-uri-before-scale-loading

        AssetFileDescriptor fileDescriptor = null;
        fileDescriptor = context.getContentResolver().openAssetFileDescriptor(mImageUri, "r");

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;

        return BitmapFactory.decodeFileDescriptor(fileDescriptor.getFileDescriptor(), null, options);

    }




}
