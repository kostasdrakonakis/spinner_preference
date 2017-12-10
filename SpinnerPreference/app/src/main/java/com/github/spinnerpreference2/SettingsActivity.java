package com.github.spinnerpreference2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;

import com.github.kostasdrakonakis.SpinnerPreference;

public class SettingsActivity extends AppCompatPreferenceActivity {

    public static Intent createIntent(Context context) {
        return new Intent(context, SettingsActivity.class);
    }

    public static void startActivity(Activity activity) {
        activity.startActivity(createIntent(activity));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);
        setupActionBar();

        SpinnerPreference spinnerPreference = (SpinnerPreference)
                findPreference(getString(R.string.key_spinner_preference));
        spinnerPreference.setLayoutBackgroundColor(R.color.colorAccent);
        spinnerPreference.setTextColor(R.color.colorPrimary);
        spinnerPreference.setAllCaps(false);
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

}
