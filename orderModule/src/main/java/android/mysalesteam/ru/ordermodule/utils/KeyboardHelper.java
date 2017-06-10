package android.mysalesteam.ru.ordermodule.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by Alexey Nekrasov on 05.12.16.
 */

public final class KeyboardHelper {

    public static void showKeyboard(Context context, EditText editText) {
        if (editText == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void hideKeyboard(Context context, View focusedView) {
        if (focusedView == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
    }
}
