package cardexc.com.practicework.menu;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import cardexc.com.practicework.R;

public class Preferences extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.main_preferences);
    }

}
