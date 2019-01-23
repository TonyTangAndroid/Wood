package com.tonytangandroid.wood.internal.support;

import android.support.annotation.ColorInt;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;

public class HighlightSpan extends CharacterStyle implements UpdateAppearance {
    private final int mBackgroundColor;
    private final int mTextColor;
    private final boolean mUnderLineText;
    private final boolean mApplyBackgroundColor;
    private final boolean mApplyTextColor;

    HighlightSpan(int backgroundColor, @ColorInt int textColor, boolean underLineText) {
        super();
        this.mBackgroundColor = backgroundColor;
        this.mTextColor = textColor;
        this.mUnderLineText = underLineText;
        this.mApplyBackgroundColor = backgroundColor != 0;
        this.mApplyTextColor = textColor != 0;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        if (mApplyTextColor)
            ds.setColor(mTextColor);
        if (mApplyBackgroundColor)
            ds.bgColor = mBackgroundColor;
        ds.setUnderlineText(mUnderLineText);
    }

}
