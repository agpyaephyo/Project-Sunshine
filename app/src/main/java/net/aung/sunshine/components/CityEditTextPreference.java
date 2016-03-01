package net.aung.sunshine.components;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;

import net.aung.sunshine.R;

/**
 * Created by aung on 2/21/16.
 */
public class CityEditTextPreference extends EditTextPreference {

    private static final int DEFAULT_MINIMUM_CITY_LENGTH = 3;

    private int mMinLength;

    public CityEditTextPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CityEditTextPreference, 0, 0);

        try {
            mMinLength = array.getInteger(R.styleable.CityEditTextPreference_minLength, DEFAULT_MINIMUM_CITY_LENGTH);
        } finally {
            array.recycle();
        }

    }

    @Override
    protected void showDialog(Bundle state) {
        super.showDialog(state);

        EditText et = getEditText();
        et.setSelection(et.getText().toString().length());
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Dialog dialog = getDialog();
                if (dialog instanceof AlertDialog) {
                    AlertDialog alertDialog = (AlertDialog) dialog;
                    Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    positiveButton.setEnabled(!(s.length() < mMinLength));
                }
            }
        });
    }
}
