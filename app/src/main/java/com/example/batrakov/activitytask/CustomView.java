package com.example.batrakov.activitytask;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.LinearLayout;

/**
 *
 * Created by batrakov on 03.10.17.
 */

public class CustomView extends LinearLayout {

    private static final int ELEVATION = 20;
    private static final int MARGIN_PADDING = 40;

    private Drawable mFirstButtonDrawable;
    private Drawable mSecondButtonDrawable;
    private Drawable mThirdButtonDrawable;
    private Drawable mBackgroundShape;
    private int mColor;
    private Boolean mOrientationPortrait;
    private ImageButton mFirstButton;
    private ImageButton mSecondButton;
    private ImageButton mThirdButton;

    /**
     * Constructor.
     * @param aContext context
     * @param aAttrs attributes from XML
     */
    public CustomView(Context aContext, AttributeSet aAttrs) {
        super(aContext, aAttrs);
        TypedArray array = aContext.getTheme().obtainStyledAttributes(aAttrs, R.styleable.CustomView, 0, 0);
        try {
            mFirstButtonDrawable = array.getDrawable(R.styleable.CustomView_firstButtonDrawable);
            mSecondButtonDrawable = array.getDrawable(R.styleable.CustomView_secondButtonDrawable);
            mThirdButtonDrawable = array.getDrawable(R.styleable.CustomView_thirdButtonDrawable);
            mOrientationPortrait = array.getBoolean(R.styleable.CustomView_orientationPortrait, true);
            mColor = array.getColor(R.styleable.CustomView_mainColor, ContextCompat.getColor(aContext, R.color.white));
            mBackgroundShape = array.getDrawable(R.styleable.CustomView_backgroundShape);
        } finally {
            array.recycle();
        }
        init();
    }

    /**
     * Visual initialization.
     */
    private void init() {
        mFirstButton = new ImageButton(getContext());
        mSecondButton = new ImageButton(getContext());
        mThirdButton = new ImageButton(getContext());

        mFirstButton.setImageDrawable(mFirstButtonDrawable);
        mSecondButton.setImageDrawable(mSecondButtonDrawable);
        mThirdButton.setImageDrawable(mThirdButtonDrawable);

        mFirstButton.setBackground(mBackgroundShape);
        mSecondButton.setBackground(mBackgroundShape);
        mThirdButton.setBackground(mBackgroundShape);

        mFirstButton.setBackgroundColor(mColor);
        mSecondButton.setBackgroundColor(mColor);
        mThirdButton.setBackgroundColor(mColor);

        mFirstButton.setElevation(ELEVATION);
        mSecondButton.setElevation(ELEVATION);
        mThirdButton.setElevation(ELEVATION);

        if (mOrientationPortrait) {
            setOrientation(HORIZONTAL);
            LinearLayout.LayoutParams firstParams =
                    new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
            firstParams.weight = 1;
            firstParams.setMarginStart(MARGIN_PADDING);
            firstParams.setMarginEnd(0);

            LinearLayout.LayoutParams secondParams =
                    new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
            secondParams.weight = 1;
            secondParams.setMarginStart(MARGIN_PADDING);
            secondParams.setMarginEnd(MARGIN_PADDING);

            LinearLayout.LayoutParams thirdParams =
                    new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
            thirdParams.weight = 1;
            thirdParams.setMarginStart(0);
            thirdParams.setMarginEnd(MARGIN_PADDING);

            mFirstButton.setLayoutParams(firstParams);
            mSecondButton.setLayoutParams(secondParams);
            mThirdButton.setLayoutParams(thirdParams);
        } else {
            setOrientation(VERTICAL);
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            params.setMargins(0, MARGIN_PADDING, 0, MARGIN_PADDING);

            mFirstButton.setLayoutParams(params);
            mSecondButton.setLayoutParams(params);
            mThirdButton.setLayoutParams(params);
        }
        addView(mFirstButton);
        addView(mSecondButton);
        addView(mThirdButton);

        setBackground(mBackgroundShape);
        setBackgroundColor(mColor);
        setElevation(ELEVATION);
    }

    /**
     *
     * @param aOnClickListener listener for the first button
     */
    public void setFirstButtonOnClickListener(OnClickListener aOnClickListener) {
        mFirstButton.setOnClickListener(aOnClickListener);
    }

    /**
     *
     * @param aOnClickListener listener for the second button
     */
    public void setSecondButtonOnClickListener(OnClickListener aOnClickListener) {
        mSecondButton.setOnClickListener(aOnClickListener);
    }

    /**
     *
     * @param aOnClickListener listener for the third button
     */
    public void setThirdButtonOnClickListener(OnClickListener aOnClickListener) {
        mThirdButton.setOnClickListener(aOnClickListener);
    }
}
