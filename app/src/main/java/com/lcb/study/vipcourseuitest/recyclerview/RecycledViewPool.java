package com.lcb.study.vipcourseuitest.recyclerview;

import android.util.SparseArray;

import java.util.ArrayList;

/**
 * 回收池
 */
public class RecycledViewPool {
    //二维的list集合

    //ScrapData
    static class ScrapData {
        final ArrayList<ViewHolder> mScrapHeap = new ArrayList<>();
    }

    SparseArray<ScrapData> mScrap = new SparseArray<>();

    //get方法
    public ViewHolder getRecycledView(int type) {
        ScrapData scrapData = mScrap.get(type);
        if (scrapData != null && !scrapData.mScrapHeap.isEmpty()) {
            //有值
            final ArrayList<ViewHolder> scrapHeap = scrapData.mScrapHeap;
            for (int i = scrapHeap.size() - 1; i >= 0; i--) {
                return scrapHeap.remove(i);
            }
        }
        return null;
    }

    //put方法
    public void putRecycledView(ViewHolder scrap){
        final int viewType=scrap.getmItemViewType();
        final ArrayList<ViewHolder> scrapHeap=getScrapDataForType(viewType).mScrapHeap;
        scrapHeap.add(scrap);
    }

    //recyclerview源码copy
    private ScrapData getScrapDataForType(int viewType) {
        ScrapData scrapData = mScrap.get(viewType);
        if (scrapData == null) {
            scrapData = new ScrapData();
            mScrap.put(viewType, scrapData);
        }
        return scrapData;
    }
}
