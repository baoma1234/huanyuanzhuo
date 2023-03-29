package caixin.android.com.widget;


import java.util.Comparator;

import caixin.android.com.entity.LiuHeIndexItem;


public class PinyinComparator implements Comparator<LiuHeIndexItem> {

    public int compare(LiuHeIndexItem o1, LiuHeIndexItem o2) {
        if (o1.getKey().equals("@")
                || o2.getKey().equals("#")) {
            return 1;
        } else if (o1.getKey().equals("#")
                || o2.getKey().equals("@")) {
            return -1;
        }else if (o1.getKey().equals("热门推荐") || o2.getKey().equals("热门推荐")){
            return 1;
        }else {
            return o1.getKey().compareTo(o2.getKey());
        }
    }

}
