package caixin.android.com.entity;

public class HelperLRHMRequest {
    private int nums;//统计期数nums 值为（50、100、150、200） 如未提供本参数或者不再范围内默认为50
    private String sortType;//排序类型 hot热号、cold冷号排序 值为（hot、cold） 如未提供本参数或者不再范围内默认为hot
    private int switchtype;

    public int getSwitchtype() {
        return switchtype;
    }

    public void setSwitchtype(int switchtype) {
        this.switchtype = switchtype;
    }

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }
}
