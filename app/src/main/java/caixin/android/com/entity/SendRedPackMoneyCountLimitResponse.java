package caixin.android.com.entity;

/**
 * ===================================
 * Author: Eric
 * Date: 2020/10/19 15:09
 * Description:
 * ===================================
 */
public class SendRedPackMoneyCountLimitResponse {

    /**
     * max : 30
     * small : 0.01
     * number : 100
     */

    private int max;
    private double small;
    private int number;

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public double getSmall() {
        return small;
    }

    public void setSmall(double small) {
        this.small = small;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
