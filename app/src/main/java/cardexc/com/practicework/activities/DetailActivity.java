package cardexc.com.practicework.activities;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import cardexc.com.practicework.R;
import cardexc.com.practicework.adapters.DetailTabsPagerAdapter;
import cardexc.com.practicework.data.Place;
import cardexc.com.practicework.fragments.TabLayoutDetailFragment;
import cardexc.com.practicework.sqlite.AdvertisingDBHelper;

public class DetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<Place> placesList;
    private ArrayList<String> titles;

    private int place_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        /////////////////////////////////////////////////////////////////////////
        Bundle bundle = getIntent().getExtras();
        place_id = bundle.getInt("id");


        /////////////////////////////////////////////////////////////////////////
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        /////////////////////////////////////////////////////////////////////////
        placesList = new AdvertisingDBHelper(getApplicationContext()).getPlacesList();
        titles = new AdvertisingDBHelper(getApplicationContext()).getPlacesListTitles();


        /////////////////////////////////////////////////////////////////////////
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);


        /////////////////////////////////////////////////////////////////////////
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {

        DetailTabsPagerAdapter adapter = new DetailTabsPagerAdapter(getSupportFragmentManager(), placesList, titles);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(place_id);

    }

}
