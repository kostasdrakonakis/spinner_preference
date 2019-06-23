Spinner Preference [![Maven Central](https://img.shields.io/badge/Maven%20Central-spinner--preference-brightgreen.svg)](http://search.maven.org/#search%7Cga%7C1%7Ckostasdrakonakis) [ ![Download](https://api.bintray.com/packages/kdrakonakis/maven/spinner-preference/images/download.svg) ](https://bintray.com/kdrakonakis/maven/spinner-preference/_latestVersion) [![Build Status](https://travis-ci.org/kostasdrakonakis/spinner_preference.svg?branch=master)](https://travis-ci.org/kostasdrakonakis/spinner_preference)


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
  <version>1.0.1</version>
</dependency>
```
or Gradle:
```groovy
implementation 'com.github.kostasdrakonakis:spinner-preference:1.0.1'
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

```java
import android.os.Bundle;

import com.github.kostasdrakonakis.SpinnerPreference;

public class SettingsActivity extends AppCompatPreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);

        SpinnerPreference spinnerPreference = (SpinnerPreference)
                findPreference(getString(R.string.key_spinner_preference));
    }

}
```

Customization
-------------

You can specify attributes in prefs.xml directly in the layout:

```xml
<com.github.kostasdrakonakis.SpinnerPreference
        ...
        app:spinnerMode="dropdown"
        app:preferenceVisibility="visible"
        app:preferenceTextColor="@color/colorPrimary"
        app:preferenceLayoutColor="@color/colorAccent"
        app:preferenceAllCaps="true"
        app:spinnerValues="@array/font_options" />
```

or programmatically like:

```java
SpinnerPreference spinnerPreference = (SpinnerPreference) findPreference(getString(R.string.key_spinner_preference));
        spinnerPreference.setLayoutBackgroundColor(R.color.colorAccent);
        spinnerPreference.setTextColor(R.color.colorPrimary);
        spinnerPreference.setAllCaps(false);
        spinnerPreference.setVisibility(View.VISIBLE);
        spinnerPreference.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        
        spinnerPreference.setItems(Arrays.asList(getResources().getStringArray(R.array.font_options)));
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
