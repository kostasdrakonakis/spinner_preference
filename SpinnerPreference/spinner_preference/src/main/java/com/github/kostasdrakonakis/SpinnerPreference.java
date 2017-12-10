package com.github.kostasdrakonakis;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpinnerPreference extends Preference {
    private List<String> mItems = new ArrayList<>();
    private String mValue;

    private AdapterView.OnItemSelectedListener mListener;

    @SuppressWarnings("unused")
    public SpinnerPreference(Context context) {
        super(context);
        init(context, null);
    }

    @SuppressWarnings("unused")
    public SpinnerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    @SuppressWarnings("unused")
    public SpinnerPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        Spinner spinner = view.findViewById(R.id.spinner_dropdown);

        TextView textView = view.findViewById(R.id.spinner_title);
        textView.setText(getTitle());
        spinner.setAdapter(new ArrayAdapter<>(getContext(), R.layout.item_spinner_text, mItems));

        int selection = mItems.indexOf(mValue);
        if (selection >= 0) spinner.setSelection(selection);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setValue(mItems.get(position));
                if (mListener != null) mListener.onItemSelected(parent, view, position, id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                if (mListener != null) mListener.onNothingSelected(parent);
            }
        });
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getString(index);
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        super.onSetInitialValue(restorePersistedValue, defaultValue);
        setValue(restorePersistedValue ? getPersistedString("0") : (String) defaultValue);
    }

    public void setItems(List<String> items) {
        mItems = items;
        notifyChanged();
    }

    public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener listener) {
        mListener = listener;
    }

    private void init(Context context, AttributeSet attrs) {
        setLayoutResource(R.layout.preference_spinner);

        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SpinnerPreference);
        final int id = array.getResourceId(R.styleable.SpinnerPreference_spinnerValues, 0);

        if (id != 0) {
            if (mItems.isEmpty()) {
                mItems = Arrays.asList(context.getResources().getStringArray(id));
            }
        }

        array.recycle();
    }

    private void setValue(String value) {
        final boolean changed = !value.equals(mValue);
        if (changed) {
            mValue = value;
            persistString(value);
            notifyDependencyChange(shouldDisableDependents());
            notifyChanged();
        }
    }
}

