package com.primecloud.huafenghuang.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.primecloud.huafenghuang.ui.home.findfragment.bean.FindBean;

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;
    private int spacing;
    private boolean includeEdge;

    private boolean flag = false;
    private int maxPosition;

    public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position

        RecyclerView.Adapter adapter = parent.getAdapter();
        int itemViewType = adapter.getItemViewType(position);

        int column = position % spanCount; // item column

        if (includeEdge) {

            if(itemViewType == FindBean.ITEM_TYPE || itemViewType == FindBean.ITEM_HEAD){

                if(position > 1){
                    maxPosition = position;
                }
            }else{
                if(maxPosition != 0 && position > maxPosition && maxPosition % 2 == 0){
                    outRect.left = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)
                    outRect.right = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                }else{
                    outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                    outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)
                }
//                if(position > 6){
//                    outRect.left = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)
//                    outRect.right = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
//                }else{
//                    outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
//                    outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)
//                }

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom

            }
        } else {
            outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
            outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = spacing; // item top
            }
        }
    }
}

