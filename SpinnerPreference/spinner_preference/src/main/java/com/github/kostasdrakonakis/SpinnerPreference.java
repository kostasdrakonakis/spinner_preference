package com.github.kostasdrakonakis;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.preference.Preference;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpinnerPreference extends Preference {
    private List<String> mItems = new ArrayList<>();
    private String mValue;

    private AdapterView.OnItemSelectedListener mListener;
    private boolean mAllCaps;
    private int mVisibility;
    private int mBackgroundColor;
    private int mTextColor;
    private int mSpinnerMode;

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
        RelativeLayout container = view.findViewById(R.id.spinner_container);
        container.setVisibility(getVisibility());
        if (mBackgroundColor != 0) container.setBackgroundColor(mBackgroundColor);

        TextView textView = view.findViewById(R.id.spinner_title);
        textView.setText(getTitle());
        textView.setAllCaps(mAllCaps);
        if (mTextColor != 0) textView.setTextColor(mTextColor);

        Spinner spinner = createSpinner(container);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getContext(), R.layout.item_spinner_text, mItems) {
                    @NonNull
                    @Override
                    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                        View view = convertView;

                        if (view == null) {
                            LayoutInflater inflater = LayoutInflater.from(getContext());
                            view = inflater.inflate(R.layout.item_spinner_text, parent, false);
                        }

                        TextView tv = view.findViewById(android.R.id.text1);
                        tv.setText(getItem(position));
                        if (mTextColor != 0) tv.setTextColor(mTextColor);
                        return view;
                    }
                };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            spinner.setLayoutMode(mSpinnerMode);
        }

        spinner.setAdapter(adapter);

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

    public void setVisibility(int visibility) {
        mVisibility = visibility;
    }

    public void setAllCaps(boolean allCaps) {
        mAllCaps = allCaps;
    }

    public void setLayoutBackgroundColor(@ColorRes int color) {
        mBackgroundColor = ContextCompat.getColor(getContext(), color);
    }

    public void setLayoutBackgroundColor(String color) {
        mBackgroundColor = Color.parseColor(color);
    }

    public void setTextColor(@ColorRes int color) {
        mTextColor = ContextCompat.getColor(getContext(), color);
    }

    public void setTextColor(String color) {
        mTextColor = Color.parseColor(color);
    }

    private void init(Context context, AttributeSet attrs) {
        setLayoutResource(R.layout.preference_spinner);

        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SpinnerPreference);
        final int id = array.getResourceId(R.styleable.SpinnerPreference_spinnerValues, 0);

        final int backgroundColor = array.
                getColor(R.styleable.SpinnerPreference_preferenceLayoutColor, Color.WHITE);

        final int textColor = array.
                getColor(R.styleable.SpinnerPreference_preferenceTextColor, 0);

        mSpinnerMode = array.getInt(R.styleable.SpinnerPreference_spinnerMode, 1);

        mAllCaps = array.
                getBoolean(R.styleable.SpinnerPreference_preferenceAllCaps, true);

        mVisibility = array.getInt(R.styleable.SpinnerPreference_preferenceVisibility, 0);

        if (id != 0) {
            if (mItems.isEmpty()) {
                mItems = Arrays.asList(context.getResources().getStringArray(id));
            }
        }

        if (backgroundColor != Color.WHITE) {
            mBackgroundColor = backgroundColor;
        }

        if (textColor != 0) {
            mTextColor = textColor;
        }

        array.recycle();
    }

    @NonNull
    private Spinner createSpinner(RelativeLayout container) {
        Spinner spinner = new Spinner(getContext(), mSpinnerMode);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_END);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.addRule(RelativeLayout.END_OF, R.id.spinner_title);
        params.addRule(RelativeLayout.RIGHT_OF, R.id.spinner_title);
        params.addRule(RelativeLayout.CENTER_VERTICAL | RelativeLayout.CENTER_HORIZONTAL);
        params.rightMargin = -150;
        spinner.setLayoutParams(params);

        container.addView(spinner);
        return spinner;
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

    private int getVisibility() {
        switch (mVisibility) {
            case 0:
                return View.VISIBLE;
            case 1:
                return View.INVISIBLE;
            case 2:
                return View.GONE;
            default:
                throw new IllegalArgumentException("Not supported visibility");
        }
    }
}

