package android.mysalesteam.ru.ordermodule.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Alexey Nekrasov on 14.12.16.
 */

public class PriceFormatter {

    private static PriceFormatter instance;

    private DecimalFormat decimalFormat;
    private TypefaceSpanRub typefaceSpanRub;

    public static PriceFormatter getInstance(Context context) {
        if (instance == null) {
            instance = new PriceFormatter(context);
        }
        return instance;
    }

    private PriceFormatter(Context context) {

        Typeface roubleSupportedTypeface =
                Typeface.createFromAsset(context.getAssets(), "rouble.ttf");

        typefaceSpanRub = new TypefaceSpanRub(roubleSupportedTypeface);

        decimalFormat = (DecimalFormat) NumberFormat.getCurrencyInstance(new Locale("ru", "RU"));
        DecimalFormatSymbols decimalFormatSymbols = decimalFormat.getDecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("\u20BD");
        decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);
    }

    public SpannableStringBuilder format(float price) {
        SpannableStringBuilder resultSpan = new SpannableStringBuilder(decimalFormat.format(price));
        resultSpan.setSpan(typefaceSpanRub, resultSpan.length() - 1, resultSpan.length(), 0);

        return resultSpan;
    }
}
