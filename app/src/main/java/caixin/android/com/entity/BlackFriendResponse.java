package caixin.android.com.entity;

public class BlackFriendResponse {

    public static final String DELETE_BLACK_FRINED = "del";
    public static final String ADD_BLACK_FRINED = "add";

    /**
     * act : del
     */

    private String act;

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }
}
