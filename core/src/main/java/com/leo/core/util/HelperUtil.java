package com.leo.core.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.leo.core.iapi.inter.ITextAction;

public class HelperUtil {

    public static void addMoneyTextChangedListener(EditText editText, Double max, ITextAction action) {
        if (editText != null) {
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence text, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence text, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable text) {
                    onAfterTextChanged(text, max, action);
                }
            });
        }
    }

    private static void onAfterTextChanged(Editable text, Double max, ITextAction action) {
        String temp = text.toString();
        int posDot = temp.indexOf(".");
        int endPosDot = temp.lastIndexOf(".");
        if (posDot >= 0 && endPosDot != posDot) {
            text.delete(endPosDot, endPosDot + 1);
            return;
        }
        if (posDot > 0) {
            if (temp.length() - posDot - 1 > 2) {
                text.delete(posDot + 3, posDot + 4);
                return;
            }
        } else if (posDot == 0) {
            text.insert(0, "0");
        }
        if (max != null) {
            float f = JavaTypeUtil.getfloat(temp, 0);
            if (f > max) {
                text.delete(temp.length() - 1, temp.length());
                return;
            }
        }
        if (action != null) {
            action.execute(temp);
        }
    }

}
