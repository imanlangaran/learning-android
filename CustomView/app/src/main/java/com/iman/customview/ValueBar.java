package com.iman.customview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class ValueBar extends View {

    private int maxValue = 100;
    private int value = 0;


    private int barHeight;
    private int spaceAfterBar;
    private int baseColor;
    private int fillColor;
    private int circleRadius;
    private int circleTextSize;
    private int circleTextColor;
    private int labelTextSize;
    private int labelTextColor;
    private int maxValueTextSize;
    private int maxValueTextColor;
    private String labelText = "";


    private Paint labelPaint, circlePaint;
    private Paint barBasePaint, barFillPaint;
    private Paint currentValuePaint, maxValuePaint;

    private boolean animated = true;
    private ValueAnimator animator;
    private float valueToDraw;
    private long animatorDuration = 4000L;




    public ValueBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setSaveEnabled(true);


        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.valueBar, 0, 0);

        barHeight = ta.getDimensionPixelSize(R.styleable.valueBar_barHeight, 0);
        spaceAfterBar = ta.getDimensionPixelSize(R.styleable.valueBar_spaceAfterBar, 0);
        baseColor = ta.getColor(R.styleable.valueBar_baseColor, Color.BLACK);
        fillColor = ta.getColor(R.styleable.valueBar_fillColor, Color.RED);
        circleRadius = ta.getDimensionPixelSize(R.styleable.valueBar_circleRadius, 0);
        circleTextSize = ta.getDimensionPixelSize(R.styleable.valueBar_circleTextSize, 0);
        circleTextColor = ta.getColor(R.styleable.valueBar_circleTextColor, Color.WHITE);
        labelTextSize = ta.getDimensionPixelSize(R.styleable.valueBar_labelTextSize, 0);
        labelTextColor = ta.getColor(R.styleable.valueBar_labelTextColor, Color.BLACK);
        maxValueTextSize = ta.getDimensionPixelSize(R.styleable.valueBar_maxValueTextSize, 0);
        maxValueTextColor = ta.getColor(R.styleable.valueBar_maxValueTextColor, Color.BLACK);
        labelText = ta.getString(R.styleable.valueBar_labelText);

        ta.recycle();

        labelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        labelPaint.setTextSize(labelTextSize);
        labelPaint.setColor(labelTextColor);
        labelPaint.setTextAlign(Paint.Align.LEFT);
        labelPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        maxValuePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        maxValuePaint.setTextSize(maxValueTextSize);
        maxValuePaint.setColor(maxValueTextColor);
        maxValuePaint.setTextAlign(Paint.Align.RIGHT);
        maxValuePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        barBasePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        barBasePaint.setColor(baseColor);

        barFillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        barFillPaint.setColor(fillColor);

        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(fillColor);

        currentValuePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        currentValuePaint.setTextSize(circleTextSize);
        currentValuePaint.setColor(circleTextColor);
        currentValuePaint.setTextAlign(Paint.Align.CENTER);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLabel(canvas);
        drawBar(canvas);
        drawMaxValue(canvas);
    }

    private void drawLabel(Canvas canvas) {
        float x = getPaddingLeft();

        Rect bounds = new Rect();
        labelPaint.getTextBounds(labelText, 0, labelText.length(), bounds);
        float y = getPaddingTop() + bounds.height();
        canvas.drawText(labelText, x, y, labelPaint);
    }

    private float getBarCenter() {
        Rect labelRect = new Rect();
        labelPaint.getTextBounds(labelText, 0, labelText.length(), labelRect);

        float barCenter = (getHeight() - getPaddingTop() - getPaddingBottom() - labelRect.height()) / 2;
        barCenter += getPaddingTop() + labelRect.height() + 0.1f * getHeight();
        return barCenter;
    }

    private void drawMaxValue(Canvas canvas) {
        String maxValueText = String.valueOf(maxValue);
        Rect bounds = new Rect();
        maxValuePaint.getTextBounds(maxValueText, 0, maxValueText.length(), bounds);
        float x = getWidth() - getPaddingRight();
        float y = getBarCenter() + bounds.height() / 2;
        canvas.drawText(maxValueText, x, y, maxValuePaint);
    }

    private void drawBar(Canvas canvas) {
        String maxValueText = String.valueOf(maxValue);
        Rect maxValueRect = new Rect();
        maxValuePaint.getTextBounds(maxValueText, 0, maxValueText.length(), maxValueRect);
        int barLength = getWidth() - getPaddingLeft() - getPaddingRight() - maxValueRect.width() - spaceAfterBar - circleRadius;
        float left = getPaddingLeft();
        float barCenter = getBarCenter();
        float top = barCenter - barHeight / 2;
        float bottom = barCenter + barHeight / 2;
        float right = left + barLength;
        RectF rect = new RectF(left, top, right, bottom);
        canvas.drawRoundRect(rect, barHeight / 2, barHeight / 2, barBasePaint);

        float fillPercent = valueToDraw / (float) maxValue;
        float fillRight = getPaddingLeft() + fillPercent * barLength;
        RectF rectFill = new RectF(left, top, fillRight, bottom);
        canvas.drawRoundRect(rectFill, barHeight / 2, barHeight / 2, barFillPaint);

        canvas.drawCircle(fillRight, barCenter, circleRadius, circlePaint);

        String valueText = String.valueOf((int) valueToDraw);
        Rect valueRect = new Rect();
        currentValuePaint.getTextBounds(valueText, 0, valueText.length(), valueRect);
        canvas.drawText(valueText, fillRight, barCenter + valueRect.height() / 2, currentValuePaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureHeight(int measureSpec) {
        int size = getPaddingRight() + getPaddingLeft();
        size += labelPaint.getFontSpacing();
        float maxValueSpacing = maxValuePaint.getFontSpacing();
        size += Math.max(maxValueSpacing, Math.max(circleRadius * 2, barHeight));
        return resolveSizeAndState(size, measureSpec, 0);
    }

    private int measureWidth(int measureSpec) {
        int size = getPaddingRight() + getPaddingLeft();
        Rect bounds = new Rect();
        labelPaint.getTextBounds(labelText, 0, labelText.length(), bounds);
        size += bounds.width();

        bounds = new Rect();
        String maxValueText = String.valueOf(maxValue);
        labelPaint.getTextBounds(maxValueText, 0, maxValueText.length(), bounds);
        size += bounds.width();

        return resolveSizeAndState(size, measureSpec, 0);
    }


    public void setMaxValue(int maxValue) {
        this.maxValue = (maxValue > 0) ? maxValue : 100;
        invalidate();
        requestLayout();
    }

    public void setValue(int newValue) {
        int prevValue = value;

        this.value = (newValue > maxValue) ? maxValue : (newValue < 0) ? 0 : newValue;

        if (animator != null){
            animator.cancel();
        }

        if (animated){
            animator = ValueAnimator.ofFloat(prevValue, value);
            float delta = Math.abs(prevValue - value);
            long duration = (long) (delta / maxValue * animatorDuration);
            animator.setDuration(duration);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    valueToDraw = (float) animation.getAnimatedValue();
                    ValueBar.this.invalidate();
                }
            });
            animator.start();
        }else{
            valueToDraw = value;
        }

        invalidate(); //draw again for do changes
    }

    public int getMaxValue() {
        return maxValue;
    }

    public int getValue() {
        return value;
    }


    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        ValueBarSavedState ss = new ValueBarSavedState(super.onSaveInstanceState());
        ss.currentValue = getValue();
        ss.maxValue = getMaxValue();
        return ss;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        ValueBarSavedState ss = (ValueBarSavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        boolean isAnimated = animated;
        animated = false;
        setMaxValue(ss.maxValue);
        setValue(ss.currentValue);
        animated = isAnimated;
    }

    public static class ValueBarSavedState extends BaseSavedState {

        int currentValue;
        int maxValue;

        public ValueBarSavedState(Parcel source) {
            super(source);
            currentValue = source.readInt();
            maxValue = source.readInt();
        }

        public ValueBarSavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(currentValue);
            out.writeInt(maxValue);
        }

        public static final Parcelable.Creator<ValueBarSavedState> CREATOR = new Creator<ValueBarSavedState>() {
            @Override
            public ValueBarSavedState createFromParcel(Parcel source) {
                return new ValueBarSavedState(source);
            }

            @Override
            public ValueBarSavedState[] newArray(int size) {
                return new ValueBarSavedState[size];
            }
        };
    }




    public void setAnimated(boolean animated) {
        this.animated = animated;
    }

    public void setAnimatorDuration(long animatorDuration) {
        this.animatorDuration = animatorDuration;
    }


}
