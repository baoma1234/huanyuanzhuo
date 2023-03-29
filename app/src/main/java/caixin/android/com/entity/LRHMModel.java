package caixin.android.com.entity;

import java.io.Serializable;
import java.util.List;

public class LRHMModel implements Serializable{
    private int nums;

    private List<HotdataBean> hotdata;
    private List<ColdHotDataBean> cold_hot_data;

    public List<HotdataBean> getHotdata() {
        return hotdata;
    }

    public void setHotdata(List<HotdataBean> hotdata) {
        this.hotdata = hotdata;
    }

    public List<ColdHotDataBean> getCold_hot_data() {
        return cold_hot_data;
    }
    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }

    public void setCold_hot_data(List<ColdHotDataBean> cold_hot_data) {
        this.cold_hot_data = cold_hot_data;
    }

    public static class HotdataBean implements Serializable{
        /**
         * id : 8534
         * year : 2019
         * number : 064
         * open_date : 2019-06-09
         * num1 : 24
         * num2 : 03
         * num3 : 27
         * num4 : 49
         * num5 : 28
         * num6 : 8
         * nums : 44
         */

        private int id;
        private int year;
        private String number;
        private String open_date;
        private List<String> numbers;

        public List<String> getNumbers() {
            return numbers;
        }

        public void setNumbers(List<String> numbers) {
            this.numbers = numbers;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getOpen_date() {
            return open_date;
        }

        public void setOpen_date(String open_date) {
            this.open_date = open_date;
        }

    }

    public static class ColdHotDataBean implements Serializable {
        /**
         * num : 21
         * count : 13
         */

        private String num;
        private int count;

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
