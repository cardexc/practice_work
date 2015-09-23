package cardexc.com.practicework;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


public class ViewFragment extends Fragment {

    AdvertisingCursorAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.view_layout, container, false);

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

                DetailFragment detailFragment = new DetailFragment();
                detailFragment.setCursor((Cursor) parent.getItemAtPosition(position));

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, detailFragment)
                        .addToBackStack(null)
                        .commit();

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

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new AddFragment())
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


}


