package caixin.android.com.entity;

public class DigResponse {


    public static final String ADD_DIG = "add";
    public static final String DEL_DIG = "del";


    /**
     * act : add
     */

    private String act;

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }
}
