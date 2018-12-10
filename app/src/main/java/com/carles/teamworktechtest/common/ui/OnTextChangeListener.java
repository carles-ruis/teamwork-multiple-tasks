package com.carles.teamworktechtest.common.ui;

import android.text.Editable;
import android.text.TextWatcher;

public abstract class OnTextChangeListener implements TextWatcher {

    public abstract void onTextChanged(String text);

    @Override
    public final void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // nothing to do here
    }

    @Override
    public final void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // nothing to do here
    }

    @Override
    public final void afterTextChanged(Editable editable) {
        onTextChanged(editable.toString());
    }
}
