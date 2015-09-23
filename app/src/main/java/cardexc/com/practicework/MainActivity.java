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

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, viewFragment)
                .commit();

    }

    @Override
    public void OnAddFinish(String text) {

    }

    @Override
    public void OnAddFinish(Intent intent) {

        viewFragment.addPlace(intent);
        getSupportFragmentManager().popBackStack();

    }

    @Override
    public void DetailFragmentOnClose() {

        getSupportFragmentManager().popBackStack();

    }
}
