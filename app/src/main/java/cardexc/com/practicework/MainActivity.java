package cardexc.com.practicework;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity
    implements AddFragment.OnAddFinish, DetailFragment.DetailFragmentOnClose{

    ViewFragment viewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewFragment = new ViewFragment();

        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, viewFragment)
                .commit();

    }

    @Override
    public void OnAddFinish(Intent intent) {

        viewFragment.addPlace(intent);
        getFragmentManager().popBackStack();

    }

    @Override
    public void DetailFragmentOnClose() {

        getFragmentManager().popBackStack();

    }


    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }

    }
}
