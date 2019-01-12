package com.ashokvarma.gander.internal.support;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;

import com.ashokvarma.gander.R;
import com.ashokvarma.gander.internal.data.HttpTransaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Class description
 *
 * @author ashok
 * @version 1.0
 * @since 03/06/18
 */
public class FormatUtils {

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
            spannableString.setSpan(new HighlightSpan(GanderColorUtil.HIGHLIGHT_BACKGROUND_COLOR, GanderColorUtil.HIGHLIGHT_TEXT_COLOR, GanderColorUtil.HIGHLIGHT_UNDERLINE),
                    position, position + length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }


    public static CharSequence getShareText(Context context, HttpTransaction transaction) {
        SpannableStringBuilder text = new SpannableStringBuilder();
        text.append(context.getString(R.string.gander_method)).append(": ").append(v(transaction.getTag())).append("\n");
        text.append(context.getString(R.string.gander_request_time)).append(": ").append(transaction.getDate().toString()).append("\n");
        text.append(context.getString(R.string.gander_request_size)).append(": ").append(String.valueOf(transaction.length())).append("\n");
        text.append(context.getString(R.string.gander_body_content_truncated)).append(": ").append(String.valueOf(transaction.body())).append("\n");
        text.append("---------- ").append(context.getString(R.string.gander_request)).append(" ----------\n\n");
        return text;
    }


    private static CharSequence v(CharSequence charSequence) {
        return (charSequence != null) ? charSequence : "";
    }
}
