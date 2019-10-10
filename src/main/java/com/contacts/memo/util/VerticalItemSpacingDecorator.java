package com.contacts.memo.util;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

//this is class for responsible customize recycller view objects..
public class VerticalItemSpacingDecorator extends RecyclerView.ItemDecoration {

    private final  int verticalhieight;

    public VerticalItemSpacingDecorator(int verticalhieight) {
        this.verticalhieight = verticalhieight;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.bottom = verticalhieight;
//        outRect.left = 5;
////        outRect.right = 5;
////        outRect.top = 5;
    }
}
