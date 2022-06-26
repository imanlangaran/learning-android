package com.iman.circleprogress;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

public class CircleProgress extends View {

    private int min = 0;
    private int max = 100;
    private int progress = 0;
    private int color = Color.DKGRAY;
    private float strokeWidth = 4;

    private boolean autoColored = false;
    private boolean showPercent = true;

    private Paint backgroundPaint;
    private Paint foregroundPaint;
    private RectF rectF;
    private Paint percentPaint;
    private Rect bounds;


    public CircleProgress(Context context) {
        super(context);
        init(context);
    }

    public CircleProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CircleProgress, defStyleAttr, 0);
        min = ta.getInteger(R.styleable.CircleProgress_cp_min, 0);
        max = ta.getInteger(R.styleable.CircleProgress_cp_max, 100);
        if (max < min) max = min;
        progress = ta.getInteger(R.styleable.CircleProgress_cp_progress, min);
        if (progress < min) progress = min;
        else if (progress > max) progress = max;
        color = ta.getColor(R.styleable.CircleProgress_cp_color, Color.DKGRAY);
        strokeWidth = ta.getDimension(R.styleable.CircleProgress_cp_stroke_width, 4);
        autoColored = ta.getBoolean(R.styleable.CircleProgress_cp_auto_colored, false);
        showPercent = ta.getBoolean(R.styleable.CircleProgress_cp_show_percent, true);
        ta.recycle();

        init(context);
    }


    private void init(Context context) {
        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(adjustAlpha(color, 0.2f));
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(strokeWidth);

        foregroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        foregroundPaint.setColor(color);
        foregroundPaint.setStyle(Paint.Style.STROKE);
        foregroundPaint.setStrokeWidth(strokeWidth);

        rectF = new RectF();

        bounds = new Rect();

        percentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        percentPaint.setColor(color);
        percentPaint.setTextAlign(Paint.Align.CENTER);
        percentPaint.setTypeface(Typeface.create(Typeface.MONOSPACE, Typeface.BOLD));
        float density = context.getResources().getDisplayMetrics().density;
        percentPaint.setTextSize(24 * density);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        int min = Math.min(width, height);
        setMeasuredDimension(min, min);
        rectF.set(strokeWidth / 2, strokeWidth / 2, min - strokeWidth / 2, min - strokeWidth / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int percent = (progress - min) * 100 / (max - min);
        if (autoColored){
            int r = (100 - percent) * 255 / 100;
            int g = percent * 255 / 100;
            int b = 30;
            int nColor = Color.rgb(r, g, b);
            backgroundPaint.setColor(adjustAlpha(nColor, 0.2F));
            foregroundPaint.setColor(nColor);
            percentPaint.setColor(nColor);
        } else {
            backgroundPaint.setColor(adjustAlpha(color, 0.2F));
            foregroundPaint.setColor(color);
            percentPaint.setColor(color);
        }


        canvas.drawOval(rectF, backgroundPaint);

        float sweepAngle = (float) (progress - min) * 360 / (max - min);
        canvas.drawArc(rectF, -90, sweepAngle, false, foregroundPaint);

        if (showPercent) {
            String percentLabel = String.valueOf(percent);
            float x = getPaddingLeft() + (float) (getWidth() - getPaddingLeft() - getPaddingRight()) / 2;
            float y = getPaddingTop() + (float) (getHeight() + getPaddingBottom() - getPaddingTop()) / 2;
            bounds = new Rect();
            percentPaint.getTextBounds(percentLabel, 0, percentLabel.length(), bounds);
            y += (float) bounds.height() / 2;
            canvas.drawText(percentLabel, x, y, percentPaint);
        }
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public int getProgress() {
        return progress;
    }

    public void setMin(int min) {
        this.min = (min > 0) ? min : 0;
        invalidate();
    }

    public void setMax(int max) {
        this.max = (max > min) ? max : min;
        invalidate();
    }

    public void setProgress(int value) {
        this.progress = (value > max) ? max :
                (value < min) ? min :
                        value;
        invalidate();
    }

    public void setStrokeWidth(float strokeWidth) {
        if (strokeWidth < 0 || strokeWidth == this.strokeWidth) return;
        this.strokeWidth = strokeWidth;
        foregroundPaint.setStrokeWidth(strokeWidth);
        backgroundPaint.setStrokeWidth(strokeWidth);
        invalidate();
        requestLayout();
    }

    public void setColor(@ColorInt int color) {
        this.color = color;
        backgroundPaint.setColor(adjustAlpha(color, 0.2f));
        foregroundPaint.setColor(color);
        invalidate();
    }

    public boolean isAutoColored() {
        return autoColored;
    }

    public void setAutoColored(boolean autoColored) {
        if (this.autoColored == autoColored)return;
        this.autoColored = autoColored;
        invalidate();
    }

    public boolean isShowPercent() {
        return showPercent;
    }

    public void setShowPercent(boolean showPercent) {
        if (this.showPercent == showPercent) return;
        this.showPercent = showPercent;
        invalidate();
    }

    private int adjustAlpha(int color, float factor) {
        if (factor > 1.0f || factor < 0.0f) return color;

        float alpha = Math.round(Color.alpha(color) * factor);

        return Color.argb((int) alpha, Color.red(color), Color.green(color), Color.blue(color));
    }


    public void setProgressWithAnimation(int value){
        ObjectAnimator animator = ObjectAnimator.ofInt(this, "progress", value);
        long duration = Math.abs(value - progress) * 2000L / (max - min);
        animator.setDuration(duration);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }
}
