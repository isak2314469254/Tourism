package com.trytry.lasttry.utils;


import com.trytry.lasttry.pojo.Diary;

import java.util.ArrayList;
import java.util.List;

public class OrderByUtil {

    // 快速排序，根据 avgRating 降序排序
    public static void quickSort_rating(List<Diary> diaries, int left, int right) {
        if (left >= right) return;

        int i = left;
        int j = right;
        Diary pivot = diaries.get(left);

        while (i < j) {
            // 从右往左找比 pivot 小的
            while (i < j && diaries.get(j).getAvgRating() <= pivot.getAvgRating()) j--;
            if (i < j) diaries.set(i, diaries.get(j));

            // 从左往右找比 pivot 大的
            while (i < j && diaries.get(i).getAvgRating() >= pivot.getAvgRating()) i++;
            if (i < j) diaries.set(j, diaries.get(i));
        }
        diaries.set(i, pivot);

        quickSort_rating(diaries, left, i - 1);
        quickSort_rating(diaries, i + 1, right);
    }


    // 快速排序，根据 views 降序排序
    public static void quickSort_views(List<Diary> diaries, int left, int right) {
        if (left >= right) return;

        int i = left;
        int j = right;
        Diary pivot = diaries.get(left);

        while (i < j) {
            // 从右往左找比 pivot 小的
            while (i < j && diaries.get(j).getViews() <= pivot.getViews()) j--;
            if (i < j) diaries.set(i, diaries.get(j));

            // 从左往右找比 pivot 大的
            while (i < j && diaries.get(i).getViews() >= pivot.getViews()) i++;
            if (i < j) diaries.set(j, diaries.get(i));
        }
        diaries.set(i, pivot);

        quickSort_views(diaries, left, i - 1);
        quickSort_views(diaries, i + 1, right);
    }

    // 分页方法
    public static List<Diary> paginate(List<Diary> diaries, int page, int pageSize) {
        int fromIndex = page * pageSize;
        if (fromIndex >= diaries.size()) return new ArrayList<>();
        int toIndex = Math.min(fromIndex + pageSize, diaries.size());
        return diaries.subList(fromIndex, toIndex);
    }

}
