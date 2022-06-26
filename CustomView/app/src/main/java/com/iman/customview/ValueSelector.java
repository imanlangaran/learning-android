package com.iman.customview;

import android.content.Context;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.logging.ConsoleHandler;

public class ValueSelector extends LinearLayout implements View.OnClickListener, View.OnLongClickListener, View.OnTouchListener {

    View rootView;
    TextView valueText;
    View buttonPlus;
    View buttonMinus;

    int minValue = Integer.MIN_VALUE;
    int maxValue = Integer.MAX_VALUE;

    private boolean isPlusButtonPressed = false;
    private boolean isMinusButtonPressed = false;

    private static final int TIME_INTERVAL = 100;

    private Handler handler;

    public ValueSelector(Context context) {
        super(context);
        init(context);
    }

    public ValueSelector(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        setSaveEnabled(true);

        rootView = inflate(context, R.layout.value_selector, this);
        valueText = rootView.findViewById(R.id.value_number);
        buttonMinus = rootView.findViewById(R.id.btn_minus);
        buttonPlus = rootView.findViewById(R.id.btn_plus);

        buttonPlus.setOnClickListener(this);
        buttonMinus.setOnClickListener(this);

        buttonPlus.setOnLongClickListener(this);
        buttonMinus.setOnLongClickListener(this);

        buttonMinus.setOnTouchListener(this);
        buttonPlus.setOnTouchListener(this);

        handler = new Handler();
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public int getValue(){
        String text = valueText.getText().toString();
        if (text.isEmpty()){
            valueText.setText("0");
            return 0;
        }
        return Integer.valueOf(text);
    }

    public void setValue(int newValue){
        if (newValue >maxValue){
            valueText.setText(String.valueOf(maxValue));
        }else if (newValue < minValue){
            valueText.setText(String.valueOf(minValue));
        }else{
            valueText.setText(String.valueOf(newValue));
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == buttonMinus.getId()) {
            decrementValue();
        } else if (v.getId() == buttonPlus.getId()) {
            incrementValue();
        }
    }

    private void incrementValue() {
        int value = getValue();
        setValue(value+1);
    }

    private void decrementValue() {
        int value = getValue();
        setValue(value-1);
    }

    @Override
    public boolean onLongClick(View v) {
        if (v.getId() == buttonMinus.getId()) {
            isMinusButtonPressed = true;
            handler.postDelayed(new AutoDecrementer(), TIME_INTERVAL);
        } else if (v.getId() == buttonPlus.getId()) {
            isPlusButtonPressed = true;
            handler.postDelayed(new AutoIncrementer(), TIME_INTERVAL);
        }
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL){
            if (v.getId() == buttonPlus.getId()){
                isPlusButtonPressed = false;
            }else if (v.getId() == buttonMinus.getId()){
                isMinusButtonPressed = false;
            }
        }
        return false;
    }




    private class AutoDecrementer implements Runnable{

        @Override
        public void run() {
            if (isMinusButtonPressed){
                decrementValue();
                handler.postDelayed(this, TIME_INTERVAL);
            }
        }
    }

    private class AutoIncrementer implements Runnable{

        @Override
        public void run() {
            if (isPlusButtonPressed){
                incrementValue();
                handler.postDelayed(this, TIME_INTERVAL);
            }
        }
    }


    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        ValueSelectorSavedState ss = new ValueSelectorSavedState(super.onSaveInstanceState());
        ss.maxValue = this.maxValue;
        ss.minValue = this.minValue;
        ss.currentValue = getValue();
        return ss;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        ValueSelectorSavedState ss = (ValueSelectorSavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setValue(ss.currentValue);
        setMinValue(ss.minValue);
        setMaxValue(ss.maxValue);
    }

    public static class ValueSelectorSavedState extends BaseSavedState{

        int currentValue;
        int minValue;
        int maxValue;

        public ValueSelectorSavedState(Parcel source) {
            super(source);
            currentValue = source.readInt();
            minValue = source.readInt();
            maxValue = source.readInt();
        }

        public ValueSelectorSavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(currentValue);
            out.writeInt(minValue);
            out.writeInt(maxValue);
        }

        public static final Parcelable.Creator<ValueSelectorSavedState> CREATOR = new Creator<ValueSelectorSavedState>() {
            @Override
            public ValueSelectorSavedState createFromParcel(Parcel source) {
                return new ValueSelectorSavedState(source);
            }

            @Override
            public ValueSelectorSavedState[] newArray(int size) {
                return new ValueSelectorSavedState[size];
            }
        };

    }


}
