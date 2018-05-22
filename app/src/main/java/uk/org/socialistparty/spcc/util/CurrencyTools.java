package uk.org.socialistparty.spcc.util;

import java.util.Locale;

public class CurrencyTools {

    public static String convertToCurrency(float value){
        return "£" + String.format(Locale.getDefault(),"%.2f", value);
    }
}
