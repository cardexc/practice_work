package cardexc.com.practicework.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import cardexc.com.practicework.R;
import cardexc.com.practicework.activities.DetailActivity;
import cardexc.com.practicework.adapters.AdvertisingCursorAdapter;
import cardexc.com.practicework.menu.Preferences;
import cardexc.com.practicework.sqlite.AdvertisingDBHelper;
import cardexc.com.practicework.sqlite.DBContract;


public class ViewFragment extends Fragment {

    AdvertisingCursorAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.fragment_view_layout, container, false);

        ListView mainListView = (ListView) view.findViewById(R.id.mainListView);
        mainListView.setOnItemClickListener(onListItemClick());

        adapter = new AdvertisingCursorAdapter(getActivity().getApplicationContext(),
                new AdvertisingDBHelper(getActivity().getApplicationContext()).getAdCursorAdapter(), false);

        mainListView.setAdapter(adapter);

        return view;

    }

    @NonNull
    private AdapterView.OnItemClickListener onListItemClick() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                boolean use_tablayout_for_details = PreferenceManager.getDefaultSharedPreferences(getActivity())
                        .getBoolean("use_tablayout_for_details", true);

                if (use_tablayout_for_details) {

                    Cursor cursor = (Cursor) parent.getItemAtPosition(position);

                    Bundle bundle = new Bundle();
                    bundle.putInt("id", position);

                    Intent intent = new Intent(getActivity().getApplicationContext(), DetailActivity.class);
                    intent.putExtras(bundle);

                    startActivity(intent);

                } else {


                    DetailFragment detailFragment = new DetailFragment();
                    detailFragment.setCursor((Cursor) parent.getItemAtPosition(position));

                    getActivity().getFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, detailFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        };
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.view_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case (R.id.action_add): {

                getActivity().getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new AddFragment())
                        .addToBackStack(null)
                        .commit();

                return true;
            }
            case (R.id.action_settings): {

                getActivity().getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new Preferences())
                        .addToBackStack(null)
                        .commit();

                return true;

            }
        }
        return true;

    }

    public void addPlace(Intent intent) {

        new AdvertisingDBHelper(getActivity().getApplicationContext()).insertAd(intent);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
}


