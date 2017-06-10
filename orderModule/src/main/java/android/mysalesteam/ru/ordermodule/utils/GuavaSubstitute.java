package android.mysalesteam.ru.ordermodule.utils;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by Alexey Nekrasov on 17.11.16.
 */

public class GuavaSubstitute {

    public static boolean isNullOrEmpty(String url) {
        return url == null || url.isEmpty();
    }

    public static String join(final char on, @NonNull final List<String> what) {
        String[] array = new String[what.size()];
        what.toArray(array);
        return join(on, array);
    }

    public static String join(final char on, @NonNull final String[] what) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < what.length; i ++) {
            result.append(what[i]);
            result.append(on);
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }

    public static String nullToEmpty(String status) {
        return status == null ? "" : status;
    }


}
