package cardexc.com.practicework.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cardexc.com.practicework.R;
import cardexc.com.practicework.data.Place;

public class TabLayoutDetailFragment extends Fragment {

    private Place place;

    public static TabLayoutDetailFragment newInstance(Place place) {

        TabLayoutDetailFragment fragment = new TabLayoutDetailFragment();

        Bundle args = new Bundle();
        args.putParcelable("place", place);

        fragment.setArguments(args);

        return fragment;
    }

    public TabLayoutDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        place = (Place) getArguments().getParcelable("place");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_layout_detail, container, false);

        TextView textView_place = (TextView) view.findViewById(R.id.textView_place);
        textView_place.setText(place.getPlace());

        return view;

    }


}
