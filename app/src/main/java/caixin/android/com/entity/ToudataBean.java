package caixin.android.com.entity;

public class ToudataBean {
    /**
     * id : 1
     * name : 狗
     * p_num : 16
     */

    private int id;
    private String name;
    private int p_num;
    private boolean isSelected;
    private String flag;
    private int sxnum;


    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int getSxnum() {
        return sxnum;
    }

    public void setSxnum(int sxnum) {
        this.sxnum = sxnum;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getP_num() {
        return p_num;
    }

    public void setP_num(int p_num) {
        this.p_num = p_num;
    }
}
