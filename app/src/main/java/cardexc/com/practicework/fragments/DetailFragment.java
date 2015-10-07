package cardexc.com.practicework.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import cardexc.com.practicework.sqlite.AdvertisingDBHelper;
import cardexc.com.practicework.sqlite.DBContract.*;
import cardexc.com.practicework.R;
import cardexc.com.practicework.data.Util;

public class DetailFragment extends Fragment {

    private ImageView image;
    private TextView place;
    private TextView datetime;

    private Cursor cursor;
    DetailFragmentOnClose mActivity;

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    public interface DetailFragmentOnClose {
        void DetailFragmentOnClose();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (DetailFragmentOnClose) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail_layout, container, false);

        setHasOptionsMenu(true);

        image = (ImageView) view.findViewById(R.id.detail_image);
        place = (TextView) view.findViewById(R.id.detail_layout_place);
        datetime = (TextView) view.findViewById(R.id.detail_layout_datetime);

        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            File photo = new File(cursor.getString(cursor.getColumnIndexOrThrow(AdvertisingEntry.IMAGEPATH)));
            Uri mImageUri = Uri.fromFile(photo);
            Util.setImageToView(getActivity().getApplicationContext(), image, mImageUri, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        datetime.setText(cursor.getString(cursor.getColumnIndexOrThrow(AdvertisingEntry.DATETIME)));
        place.setText(cursor.getString(cursor.getColumnIndexOrThrow(AdvertisingEntry.PLACE)));

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detail_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.action_delete): {

                new AdvertisingDBHelper(getActivity().getApplicationContext())
                        .deleteAd(cursor.getInt(cursor.getColumnIndexOrThrow(AdvertisingEntry._ID)));

                mActivity.DetailFragmentOnClose();
                return true;

            }
        }
        return true;
    }
}
