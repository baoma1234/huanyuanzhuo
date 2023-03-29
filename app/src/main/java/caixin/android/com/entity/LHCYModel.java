package caixin.android.com.entity;

import java.io.Serializable;
import java.util.List;

public class LHCYModel implements Serializable {
    private List<ItemModel> sx;
    private List<ItemModel> bo;

    public List<ItemModel> getSx() {
        return sx;
    }

    public void setSx(List<ItemModel> sx) {
        this.sx = sx;
    }

    public List<ItemModel> getBo() {
        return bo;
    }

    public void setBo(List<ItemModel> bo) {
        this.bo = bo;
    }

    public static class ItemModel implements Serializable{
        /**
         * name : 鼠
         * number : ["12","24","36","48"]
         */

        private String name;
        private List<String> number;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getNumber() {
            return number;
        }

        public void setNumber(List<String> number) {
            this.number = number;
        }
    }

}

