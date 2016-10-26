package com.betterda.betterdapay.callback;

import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by Administrator on 2016/8/3.
 */
public abstract class MyTextWatcher implements TextWatcher {
    private EditText textInputEditText;

    public MyTextWatcher(EditText textInputEditText) {
        this.textInputEditText = textInputEditText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (textInputEditText != null) {
            if (textInputEditText instanceof TextInputEditText) {
                textInputEditText.setError(null);
            }
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }


}
