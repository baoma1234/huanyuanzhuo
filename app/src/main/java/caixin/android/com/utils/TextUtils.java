package caixin.android.com.utils;

import java.util.ArrayList;

public class TextUtils {

    public static String getLetter(String name) {
        final String DefaultLetter = "#";
        if (android.text.TextUtils.isEmpty(name)) {
            return DefaultLetter;
        }
        char char0 = name.toLowerCase().charAt(0);
        if (Character.isDigit(char0)) {
            return DefaultLetter;
        }
        ArrayList<HanziToPinyin.Token> l = HanziToPinyin.getInstance().get(name.substring(0, 1));
        if (l != null && l.size() > 0 && l.get(0).target.length() > 0) {
            HanziToPinyin.Token token = l.get(0);
            String letter = token.target.substring(0, 1).toUpperCase();
            char c = letter.charAt(0);
            if (c < 'A' || c > 'Z') {
                return DefaultLetter;
            }
            return letter;
        }
        return DefaultLetter;
    }
}
