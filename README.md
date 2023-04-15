Spinner Preference [![Build](https://github.com/kostasdrakonakis/spinner_preference/actions/workflows/android.yml/badge.svg?branch=master)](https://github.com/kostasdrakonakis/spinner_preference/actions/workflows/android.yml)

A custom Preference that implements Spinner in PreferenceActivity in android.

![alt tag](https://github.com/kostasdrakonakis/spinner_preference/blob/master/spinner_dropdown.PNG)
![alt tag](https://github.com/kostasdrakonakis/spinner_preference/blob/master/spinner_dialog.PNG)


Download
--------

Download the latest JAR or grab via Maven:
```xml
<dependency>
  <groupId>com.github.kostasdrakonakis</groupId>
  <artifactId>spinner-preference</artifactId>
  <version>1.0.3</version>
</dependency>
```
or Gradle:
```groovy
implementation 'com.github.kostasdrakonakis:spinner-preference:1.0.3'
```

Usage
-----

In the prefs.xml add this:

```xml
<PreferenceScreen xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">
    <com.github.kostasdrakonakis.SpinnerPreference
        android:defaultValue="@string/app_name"
        android:key="@string/key_spinner_preference"
        android:title="@string/app_name"
        app:spinnerMode="dropdown"
        app:spinnerValues="@array/font_options" />
</PreferenceScreen>
```

and then in your PreferenceActivity

```kotlin
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.github.kostasdrakonakis.SpinnerPreference

class SettingsActivity : AppCompatActivity(),
    PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_main)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        } else {
            title = savedInstanceState.getCharSequence(TITLE_TAG)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onPreferenceStartFragment(
        caller: PreferenceFragmentCompat, pref: Preference): Boolean {
        val args = pref.extras
        val fragment = supportFragmentManager.fragmentFactory.instantiate(
            classLoader,
            pref.fragment
        ).apply {
            arguments = args
            setTargetFragment(caller, 0)
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.settings, fragment)
            .addToBackStack(null)
            .commit()
        title = pref.title
        return true
    }

    internal class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.prefs, rootKey)
            val spinnerPreference: SpinnerPreference? = findPreference(getString(R.string.app_name))
            spinnerPreference?.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                }

            })
            spinnerPreference?.setLayoutBackgroundColor(R.color.colorAccent)
            spinnerPreference?.setTextColor(R.color.colorPrimary)
            spinnerPreference?.setAllCaps(false)
        }
    }

    companion object {
        private fun createIntent(context: Context): Intent {
            return Intent(context, SettingsActivity::class.java)
        }

        @JvmStatic
        fun startActivity(activity: Activity) {
            activity.startActivity(createIntent(activity))
        }

        const val TITLE_TAG = "settingsActivityTitle"
    }
}
```

Customization
-------------

You can specify attributes in prefs.xml directly in the layout:

```xml
<com.github.kostasdrakonakis.SpinnerPreference
        app:spinnerMode="dropdown"
        app:preferenceVisibility="visible"
        app:preferenceTextColor="@color/colorPrimary"
        app:preferenceLayoutColor="@color/colorAccent"
        app:preferenceAllCaps="true"
        app:spinnerValues="@array/font_options" />
```

or programmatically like:

```kotlin
class SettingsActivity : AppCompatActivity(),
    PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {

    internal class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.prefs, rootKey)
            val spinnerPreference: SpinnerPreference? = findPreference(getString(R.string.app_name))
            spinnerPreference?.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                }

            })
            spinnerPreference?.setLayoutBackgroundColor(R.color.colorAccent)
            spinnerPreference?.setTextColor(R.color.colorPrimary)
            spinnerPreference?.setAllCaps(false)
        }
    }
}
```

License
-------

 Copyright 2017 Kostas Drakonakis

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
