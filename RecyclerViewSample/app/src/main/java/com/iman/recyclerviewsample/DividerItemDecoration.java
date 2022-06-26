package com.iman.recyclerviewsample;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private static int [] ATTRS = {
            android.R.attr.listDivider
    };

    private int orientation;
    private Drawable divider;

    public DividerItemDecoration(Context context, int orientation){
        TypedArray arr = context.obtainStyledAttributes(ATTRS);
        divider = arr.getDrawable(0);
        arr.recycle();
        setOrientation(orientation);
    }

    private void setOrientation(int orientation) {
        if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL){
            throw new IllegalArgumentException("Wrong Orientation");
        }
        this.orientation = orientation;
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (orientation == LinearLayoutManager.VERTICAL){
            drawVertical(c, parent);
        }else if (orientation == LinearLayoutManager.HORIZONTAL){
            drawHorizontal(c,parent);
        }
        super.onDrawOver(c, parent, state);
    }

    private void drawHorizontal(Canvas canvas, RecyclerView recyclerView) {
        int top = recyclerView.getPaddingTop();
        int bottom = recyclerView.getHeight() - recyclerView.getPaddingBottom();
        for (int i = 0 ; i <  recyclerView.getChildCount() ; i++){
            View childView = recyclerView.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) childView.getLayoutParams();
            int left = childView.getRight() + params.rightMargin;
            int right = left + divider.getIntrinsicWidth();
            divider.setBounds(left, top, right, bottom);
            divider.draw(canvas);
        }
    }

    private void drawVertical(Canvas canvas, RecyclerView recyclerView) {
        int left = recyclerView.getPaddingLeft();
        int right = recyclerView.getWidth() - recyclerView.getPaddingRight();
        for (int i = 0 ; i <  recyclerView.getChildCount() ; i++){
            View childView = recyclerView.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) childView.getLayoutParams();
            int top = childView.getBottom() + params.bottomMargin;
            int bottom = top + divider.getIntrinsicHeight();
            divider.setBounds(left, top, right, bottom);
            divider.draw(canvas);
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (orientation == LinearLayoutManager.VERTICAL){
            outRect.set(0,0,0,divider.getIntrinsicHeight());
        }else if (orientation == LinearLayoutManager.HORIZONTAL){
            outRect.set(0, 0, divider.getIntrinsicWidth(), 0);
        }
        super.getItemOffsets(outRect, view, parent, state);
    }
}
