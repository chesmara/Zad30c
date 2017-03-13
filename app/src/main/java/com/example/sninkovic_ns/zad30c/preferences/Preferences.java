package com.example.sninkovic_ns.zad30c.preferences;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import com.example.sninkovic_ns.zad30c.R;

/**
 * Created by SNinkovic_ns on 14.3.2017.
 */

public class Preferences extends PreferenceActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new Preferences.PrefsFragment()).commit();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    public static class PrefsFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.preferences);
        }
    }
}
