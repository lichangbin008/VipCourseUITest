package com.lcb.study.vipcourseuitest.recyclerview;

import android.view.View;

/**
 * 自定义ViewHolder
 */
public class ViewHolder {

    //itemView根布局
    public View itemView;

    int mItemViewType = -1;

    public ViewHolder(View itemView) {
        this.itemView = itemView;
    }

    public int getmItemViewType() {
        return mItemViewType;
    }

    public void setmItemViewType(int mItemViewType) {
        this.mItemViewType = mItemViewType;
    }
}
