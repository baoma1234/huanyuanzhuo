package caixin.android.com.widget.picker;

import java.util.List;

/**
 * Created by xxx on 2018/7/19.
 * 选择年份的Adapter
 */

public class WheelChooseYearAdapter implements WheelAdapter {
    private List<String> list;

    public void setData(List<String> mList) {
        this.list = mList;
    }

    @Override
    public int getItemsCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int index) {
        return list == null ? "" : list.get(index) + "年";
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }
}
