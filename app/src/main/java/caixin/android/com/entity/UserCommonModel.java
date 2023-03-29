package caixin.android.com.entity;

public class UserCommonModel {
    private int fs_num;
    private int dt_num;
    private int new_fs;

    public int getNew_fs() {
        return new_fs;
    }

    public void setNew_fs(int new_fs) {
        this.new_fs = new_fs;
    }

    public int getFs_num() {
        return fs_num;
    }

    public void setFs_num(int fs_num) {
        this.fs_num = fs_num;
    }

    public int getDt_num() {
        return dt_num;
    }

    public void setDt_num(int dt_num) {
        this.dt_num = dt_num;
    }

    @Override
    public String toString() {
        return "UserCommonModel{" +
                "fs_num=" + fs_num +
                ", dt_num=" + dt_num +
                ", new_fs=" + new_fs +
                '}';
    }
}
