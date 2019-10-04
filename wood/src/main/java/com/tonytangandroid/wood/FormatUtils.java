package com.tonytangandroid.wood;

import androidx.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


class FormatUtils {

    public static CharSequence formatTextHighlight(String text, String searchKey) {
        if (TextUtil.isNullOrWhiteSpace(text) || TextUtil.isNullOrWhiteSpace(searchKey)) {
            return text;
        } else {
            List<Integer> startIndexes = indexOf(text, searchKey);
            SpannableString spannableString = new SpannableString(text);
            applyHighlightSpan(spannableString, startIndexes, searchKey.length());
            return spannableString;
        }
    }

    @NonNull
    public static List<Integer> indexOf(CharSequence charSequence, String criteria) {
        String text = charSequence.toString().toLowerCase();
        criteria = criteria.toLowerCase();

        List<Integer> startPositions = new ArrayList<>();
        int index = text.indexOf(criteria);
        while (index >= 0) {
            startPositions.add(index);
            index = text.indexOf(criteria, index + 1);
        }
        return startPositions;
    }

    public static void applyHighlightSpan(Spannable spannableString, List<Integer> indexes, int length) {
        for (Integer position : indexes) {
            spannableString.setSpan(new HighlightSpan(WoodColorUtil.HIGHLIGHT_BACKGROUND_COLOR, WoodColorUtil.HIGHLIGHT_TEXT_COLOR, WoodColorUtil.HIGHLIGHT_UNDERLINE),
                    position, position + length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }


    public static CharSequence getShareText(Leaf transaction) {
        return transaction.body();
    }


    private static CharSequence v(CharSequence charSequence) {
        return (charSequence != null) ? charSequence : "";
    }

    public static List<Integer> highlightSearchKeyword(TextView textView, String searchKey) {

        CharSequence body = textView.getText();
        if (body instanceof Spannable) {
            Spannable spannableBody = (Spannable) body;
            // remove old HighlightSpans
            HighlightSpan spansToRemove[] = spannableBody.getSpans(0, spannableBody.length() - 1, HighlightSpan.class);
            for (Object span : spansToRemove) {
                spannableBody.removeSpan(span);
            }
            // add spans only if searchKey size is > 0
            if (searchKey != null && searchKey.length() > 0) {
                // get indices of new search
                List<Integer> startIndexes = indexOf(body.toString(), searchKey);
                // add spans
                applyHighlightSpan(spannableBody, startIndexes, searchKey.length());
                return startIndexes;
            }
        }

        return new ArrayList<>(0);
    }

    public static String timeDesc(long nowInMilliseconds) {
        Date date = new Date(nowInMilliseconds);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS MMM-dd", Locale.US);
        return formatter.format(date);
    }
}
