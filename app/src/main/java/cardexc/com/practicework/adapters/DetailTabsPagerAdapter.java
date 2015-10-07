package cardexc.com.practicework.adapters;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

import cardexc.com.practicework.data.Place;
import cardexc.com.practicework.fragments.TabLayoutDetailFragment;

public class DetailTabsPagerAdapter extends FragmentStatePagerAdapter{

    private int count = 0;
    private ArrayList<Place> placesList;
    private ArrayList<String> titles;

    public DetailTabsPagerAdapter(FragmentManager fm, ArrayList<Place> placesList, ArrayList<String> titles) {
        super(fm);

        this.placesList = placesList;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {

        Place place = placesList.get(position);

        TabLayoutDetailFragment frg = TabLayoutDetailFragment.newInstance(place);

        return frg;

    }

    @Override
    public int getCount() {
        return this.placesList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
