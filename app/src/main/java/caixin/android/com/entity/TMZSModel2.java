package caixin.android.com.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TMZSModel2 {


    private List<FiveBean> five;
    private List<AnimalBean> animal;
    private List<WeiBean> wei;
    private List<TouBean> tou;
    private List<MenBean> men;
    private List<DuanBean> duan;
    private List<HeBean> he;
    private List<TouSingleDoubleBean> tou_single_double;
    private List<ColorSingleDoubleBean> color_single_double;
    private List<BoBean> bo;
    private List<JiaShouBean> jia_shou;
    private List<BigSmallBean> big_small;
    private List<SmallSingleDoubleBean> small_single_double;
    private List<HeBigSmallBean> he_big_small;
    private List<SingleDoubleBean> single_double;
    private List<BigSingleDoubleBean> big_single_double;
    private List<HeSingleDoubleBean> he_single_double;

    public List<FiveBean> getFive() {
        return five;
    }

    public void setFive(List<FiveBean> five) {
        this.five = five;
    }

    public List<AnimalBean> getAnimal() {
        return animal;
    }

    public void setAnimal(List<AnimalBean> animal) {
        this.animal = animal;
    }

    public List<WeiBean> getWei() {
        return wei;
    }

    public void setWei(List<WeiBean> wei) {
        this.wei = wei;
    }

    public List<TouBean> getTou() {
        return tou;
    }

    public void setTou(List<TouBean> tou) {
        this.tou = tou;
    }

    public List<MenBean> getMen() {
        return men;
    }

    public void setMen(List<MenBean> men) {
        this.men = men;
    }

    public List<DuanBean> getDuan() {
        return duan;
    }

    public void setDuan(List<DuanBean> duan) {
        this.duan = duan;
    }

    public List<HeBean> getHe() {
        return he;
    }

    public void setHe(List<HeBean> he) {
        this.he = he;
    }

    public List<TouSingleDoubleBean> getTou_single_double() {
        return tou_single_double;
    }

    public void setTou_single_double(List<TouSingleDoubleBean> tou_single_double) {
        this.tou_single_double = tou_single_double;
    }

    public List<ColorSingleDoubleBean> getColor_single_double() {
        return color_single_double;
    }

    public void setColor_single_double(List<ColorSingleDoubleBean> color_single_double) {
        this.color_single_double = color_single_double;
    }

    public List<BoBean> getBo() {
        return bo;
    }

    public void setBo(List<BoBean> bo) {
        this.bo = bo;
    }

    public List<JiaShouBean> getJia_shou() {
        return jia_shou;
    }

    public void setJia_shou(List<JiaShouBean> jia_shou) {
        this.jia_shou = jia_shou;
    }

    public List<BigSmallBean> getBig_small() {
        return big_small;
    }

    public void setBig_small(List<BigSmallBean> big_small) {
        this.big_small = big_small;
    }

    public List<SmallSingleDoubleBean> getSmall_single_double() {
        return small_single_double;
    }

    public void setSmall_single_double(List<SmallSingleDoubleBean> small_single_double) {
        this.small_single_double = small_single_double;
    }

    public List<HeBigSmallBean> getHe_big_small() {
        return he_big_small;
    }

    public void setHe_big_small(List<HeBigSmallBean> he_big_small) {
        this.he_big_small = he_big_small;
    }

    public List<SingleDoubleBean> getSingle_double() {
        return single_double;
    }

    public void setSingle_double(List<SingleDoubleBean> single_double) {
        this.single_double = single_double;
    }

    public List<BigSingleDoubleBean> getBig_single_double() {
        return big_single_double;
    }

    public void setBig_single_double(List<BigSingleDoubleBean> big_single_double) {
        this.big_single_double = big_single_double;
    }

    public List<HeSingleDoubleBean> getHe_single_double() {
        return he_single_double;
    }

    public void setHe_single_double(List<HeSingleDoubleBean> he_single_double) {
        this.he_single_double = he_single_double;
    }

    public static class FiveBean {
        /**
         * gold : 5,6,19,20,27,28,35,36,49
         * timber : 1,2,9,10,17,18,31,32,39,40,47,48
         * water : 7,8,15,16,23,24,37,38,45,46
         * fires : 3,4,11,12,25,26,33,34,41,42
         * soil : 13,14,21,22,29,30,43,44
         */

        private String gold;
        private String timber;
        private String water;
        private String fires;
        private String soil;

        public String getGold() {
            return gold;
        }

        public void setGold(String gold) {
            this.gold = gold;
        }

        public String getTimber() {
            return timber;
        }

        public void setTimber(String timber) {
            this.timber = timber;
        }

        public String getWater() {
            return water;
        }

        public void setWater(String water) {
            this.water = water;
        }

        public String getFires() {
            return fires;
        }

        public void setFires(String fires) {
            this.fires = fires;
        }

        public String getSoil() {
            return soil;
        }

        public void setSoil(String soil) {
            this.soil = soil;
        }
    }

    public static class AnimalBean {
        /**
         * dog : 2,14,26,38
         * chicken : 3,15,27,39
         * monkey : 4,16,28,40
         * sheep : 5,17,29,41
         * horse : 6,18,30,42
         * snake : 7,19,31,43
         * loong : 8,20,32,44
         * rabbit : 9,21,33,45
         * tiger : 10,22,34,46
         * cattle : 11,23,35,47
         * rat : 12,24,36,48
         * pig : 1,13,25,37,49
         */

        private String dog;
        private String chicken;
        private String monkey;
        private String sheep;
        private String horse;
        private String snake;
        private String loong;
        private String rabbit;
        private String tiger;
        private String cattle;
        private String rat;
        private String pig;

        public String getDog() {
            return dog;
        }

        public void setDog(String dog) {
            this.dog = dog;
        }

        public String getChicken() {
            return chicken;
        }

        public void setChicken(String chicken) {
            this.chicken = chicken;
        }

        public String getMonkey() {
            return monkey;
        }

        public void setMonkey(String monkey) {
            this.monkey = monkey;
        }

        public String getSheep() {
            return sheep;
        }

        public void setSheep(String sheep) {
            this.sheep = sheep;
        }

        public String getHorse() {
            return horse;
        }

        public void setHorse(String horse) {
            this.horse = horse;
        }

        public String getSnake() {
            return snake;
        }

        public void setSnake(String snake) {
            this.snake = snake;
        }

        public String getLoong() {
            return loong;
        }

        public void setLoong(String loong) {
            this.loong = loong;
        }

        public String getRabbit() {
            return rabbit;
        }

        public void setRabbit(String rabbit) {
            this.rabbit = rabbit;
        }

        public String getTiger() {
            return tiger;
        }

        public void setTiger(String tiger) {
            this.tiger = tiger;
        }

        public String getCattle() {
            return cattle;
        }

        public void setCattle(String cattle) {
            this.cattle = cattle;
        }

        public String getRat() {
            return rat;
        }

        public void setRat(String rat) {
            this.rat = rat;
        }

        public String getPig() {
            return pig;
        }

        public void setPig(String pig) {
            this.pig = pig;
        }
    }

    public static class WeiBean {
        /**
         * zero_w : 10,20,30,40
         * one_w : 1,11,21,31,41
         * two_w : 2,12,22,32,42
         * three_w : 3,13,23,33,43
         * four_w : 4,14,24,34,44
         * five_w : 5,15,25,35,45
         * six_w : 6,16,26,36,46
         * seven_w : 7,17,27,37,47
         * eight_w : 8,18,28,38,48
         * nine_w : 9,19,29,39,49
         * big_w : 5,6,7,8,9,15,16,17,18,19,25,26,27,28,29,35,36,37,38,39,45,46,47,48,49
         * small_w : 1,2,3,4,10,11,12,13,14,20,21,22,23,24,30,31,32,33,34,40,41,42,43,44
         */

        private String zero_w;
        private String one_w;
        private String two_w;
        private String three_w;
        private String four_w;
        private String five_w;
        private String six_w;
        private String seven_w;
        private String eight_w;
        private String nine_w;
        private String big_w;
        private String small_w;

        public String getZero_w() {
            return zero_w;
        }

        public void setZero_w(String zero_w) {
            this.zero_w = zero_w;
        }

        public String getOne_w() {
            return one_w;
        }

        public void setOne_w(String one_w) {
            this.one_w = one_w;
        }

        public String getTwo_w() {
            return two_w;
        }

        public void setTwo_w(String two_w) {
            this.two_w = two_w;
        }

        public String getThree_w() {
            return three_w;
        }

        public void setThree_w(String three_w) {
            this.three_w = three_w;
        }

        public String getFour_w() {
            return four_w;
        }

        public void setFour_w(String four_w) {
            this.four_w = four_w;
        }

        public String getFive_w() {
            return five_w;
        }

        public void setFive_w(String five_w) {
            this.five_w = five_w;
        }

        public String getSix_w() {
            return six_w;
        }

        public void setSix_w(String six_w) {
            this.six_w = six_w;
        }

        public String getSeven_w() {
            return seven_w;
        }

        public void setSeven_w(String seven_w) {
            this.seven_w = seven_w;
        }

        public String getEight_w() {
            return eight_w;
        }

        public void setEight_w(String eight_w) {
            this.eight_w = eight_w;
        }

        public String getNine_w() {
            return nine_w;
        }

        public void setNine_w(String nine_w) {
            this.nine_w = nine_w;
        }

        public String getBig_w() {
            return big_w;
        }

        public void setBig_w(String big_w) {
            this.big_w = big_w;
        }

        public String getSmall_w() {
            return small_w;
        }

        public void setSmall_w(String small_w) {
            this.small_w = small_w;
        }
    }

    public static class TouBean {
        /**
         * zero_t : 1,2,3,4,5,6,7,8,9
         * one_t : 10,11,12,13,14,15,16,17,18,19
         * two_t : 20,21,22,23,24,25,26,27,28,29
         * three_t : 30,31,32,33,34,35,36,37,38,39
         * four_t : 40,41,42,43,44,45,46,47,48,49
         */

        private String zero_t;
        private String one_t;
        private String two_t;
        private String three_t;
        private String four_t;

        public String getZero_t() {
            return zero_t;
        }

        public void setZero_t(String zero_t) {
            this.zero_t = zero_t;
        }

        public String getOne_t() {
            return one_t;
        }

        public void setOne_t(String one_t) {
            this.one_t = one_t;
        }

        public String getTwo_t() {
            return two_t;
        }

        public void setTwo_t(String two_t) {
            this.two_t = two_t;
        }

        public String getThree_t() {
            return three_t;
        }

        public void setThree_t(String three_t) {
            this.three_t = three_t;
        }

        public String getFour_t() {
            return four_t;
        }

        public void setFour_t(String four_t) {
            this.four_t = four_t;
        }
    }

    public static class MenBean {
        /**
         * one_m : 1,2,3,4,5,6,7,8,9
         * two_m : 10,11,12,13,14,15,16,17,18
         * three_m : 19,20,21,22,23,24,25,26,27
         * four_m : 28,29,30,31,32,33,34,35,36,37
         * five_m : 38,39,40,41,42,43,44,45,46,47,48,49
         */

        private String one_m;
        private String two_m;
        private String three_m;
        private String four_m;
        private String five_m;

        public String getOne_m() {
            return one_m;
        }

        public void setOne_m(String one_m) {
            this.one_m = one_m;
        }

        public String getTwo_m() {
            return two_m;
        }

        public void setTwo_m(String two_m) {
            this.two_m = two_m;
        }

        public String getThree_m() {
            return three_m;
        }

        public void setThree_m(String three_m) {
            this.three_m = three_m;
        }

        public String getFour_m() {
            return four_m;
        }

        public void setFour_m(String four_m) {
            this.four_m = four_m;
        }

        public String getFive_m() {
            return five_m;
        }

        public void setFive_m(String five_m) {
            this.five_m = five_m;
        }
    }

    public static class DuanBean {
        /**
         * one_d : 1,2,3,4,5,6,7
         * two_d : 8,9,10,11,12,13,14
         * three_d : 15,16,17,18,19,20,21
         * four_d : 22,23,24,25,26,27,28
         * five_d : 29,30,31,32,33,34,35
         * six_d : 36,37,38,39,40,41,42
         * seven_d : 43,44,45,46,47,48,49
         */

        private String one_d;
        private String two_d;
        private String three_d;
        private String four_d;
        private String five_d;
        private String six_d;
        private String seven_d;

        public String getOne_d() {
            return one_d;
        }

        public void setOne_d(String one_d) {
            this.one_d = one_d;
        }

        public String getTwo_d() {
            return two_d;
        }

        public void setTwo_d(String two_d) {
            this.two_d = two_d;
        }

        public String getThree_d() {
            return three_d;
        }

        public void setThree_d(String three_d) {
            this.three_d = three_d;
        }

        public String getFour_d() {
            return four_d;
        }

        public void setFour_d(String four_d) {
            this.four_d = four_d;
        }

        public String getFive_d() {
            return five_d;
        }

        public void setFive_d(String five_d) {
            this.five_d = five_d;
        }

        public String getSix_d() {
            return six_d;
        }

        public void setSix_d(String six_d) {
            this.six_d = six_d;
        }

        public String getSeven_d() {
            return seven_d;
        }

        public void setSeven_d(String seven_d) {
            this.seven_d = seven_d;
        }
    }

    public static class HeBean {
        /**
         * one_h : 1,10
         * two_h : 2,11,20
         * three_h : 3,12,21,30
         * four_h : 4,13,22,31,40
         * five_h : 5,14,23,32,41
         * six_h : 6,15,24,33,42
         * seven_h : 7,16,25,34,43
         * eight_h : 8,17,26,35,44
         * nine_h : 9,18,27,36,45
         * ten_h : 19,28,37,46
         * eleven_h : 29,38,47
         * twelve_h : 39,48
         * thirteen_h : 49
         */

        private String one_h;
        private String two_h;
        private String three_h;
        private String four_h;
        private String five_h;
        private String six_h;
        private String seven_h;
        private String eight_h;
        private String nine_h;
        private String ten_h;
        private String eleven_h;
        private String twelve_h;
        private String thirteen_h;

        public String getOne_h() {
            return one_h;
        }

        public void setOne_h(String one_h) {
            this.one_h = one_h;
        }

        public String getTwo_h() {
            return two_h;
        }

        public void setTwo_h(String two_h) {
            this.two_h = two_h;
        }

        public String getThree_h() {
            return three_h;
        }

        public void setThree_h(String three_h) {
            this.three_h = three_h;
        }

        public String getFour_h() {
            return four_h;
        }

        public void setFour_h(String four_h) {
            this.four_h = four_h;
        }

        public String getFive_h() {
            return five_h;
        }

        public void setFive_h(String five_h) {
            this.five_h = five_h;
        }

        public String getSix_h() {
            return six_h;
        }

        public void setSix_h(String six_h) {
            this.six_h = six_h;
        }

        public String getSeven_h() {
            return seven_h;
        }

        public void setSeven_h(String seven_h) {
            this.seven_h = seven_h;
        }

        public String getEight_h() {
            return eight_h;
        }

        public void setEight_h(String eight_h) {
            this.eight_h = eight_h;
        }

        public String getNine_h() {
            return nine_h;
        }

        public void setNine_h(String nine_h) {
            this.nine_h = nine_h;
        }

        public String getTen_h() {
            return ten_h;
        }

        public void setTen_h(String ten_h) {
            this.ten_h = ten_h;
        }

        public String getEleven_h() {
            return eleven_h;
        }

        public void setEleven_h(String eleven_h) {
            this.eleven_h = eleven_h;
        }

        public String getTwelve_h() {
            return twelve_h;
        }

        public void setTwelve_h(String twelve_h) {
            this.twelve_h = twelve_h;
        }

        public String getThirteen_h() {
            return thirteen_h;
        }

        public void setThirteen_h(String thirteen_h) {
            this.thirteen_h = thirteen_h;
        }
    }

    public static class TouSingleDoubleBean {
        /**
         * zero_tou_single : 1,3,5,7,9
         * one_tou_single : 11,13,15,17,19
         * two_tou_single : 21,23,25,27,29
         * three_tou_single : 31,33,35,37,39
         * four_tou_single : 41,43,45,47,49
         * zero_tou_double : 2,4,6,8
         * one_tou_double : 10,12,14,16,18
         * two_tou_double : 20,22,24,26,28
         * three_tou_double : 30,32,34,36,38
         * four_tou_double : 40,42,44,46,48
         */

        private String zero_tou_single;
        private String one_tou_single;
        private String two_tou_single;
        private String three_tou_single;
        private String four_tou_single;
        private String zero_tou_double;
        private String one_tou_double;
        private String two_tou_double;
        private String three_tou_double;
        private String four_tou_double;

        public String getZero_tou_single() {
            return zero_tou_single;
        }

        public void setZero_tou_single(String zero_tou_single) {
            this.zero_tou_single = zero_tou_single;
        }

        public String getOne_tou_single() {
            return one_tou_single;
        }

        public void setOne_tou_single(String one_tou_single) {
            this.one_tou_single = one_tou_single;
        }

        public String getTwo_tou_single() {
            return two_tou_single;
        }

        public void setTwo_tou_single(String two_tou_single) {
            this.two_tou_single = two_tou_single;
        }

        public String getThree_tou_single() {
            return three_tou_single;
        }

        public void setThree_tou_single(String three_tou_single) {
            this.three_tou_single = three_tou_single;
        }

        public String getFour_tou_single() {
            return four_tou_single;
        }

        public void setFour_tou_single(String four_tou_single) {
            this.four_tou_single = four_tou_single;
        }

        public String getZero_tou_double() {
            return zero_tou_double;
        }

        public void setZero_tou_double(String zero_tou_double) {
            this.zero_tou_double = zero_tou_double;
        }

        public String getOne_tou_double() {
            return one_tou_double;
        }

        public void setOne_tou_double(String one_tou_double) {
            this.one_tou_double = one_tou_double;
        }

        public String getTwo_tou_double() {
            return two_tou_double;
        }

        public void setTwo_tou_double(String two_tou_double) {
            this.two_tou_double = two_tou_double;
        }

        public String getThree_tou_double() {
            return three_tou_double;
        }

        public void setThree_tou_double(String three_tou_double) {
            this.three_tou_double = three_tou_double;
        }

        public String getFour_tou_double() {
            return four_tou_double;
        }

        public void setFour_tou_double(String four_tou_double) {
            this.four_tou_double = four_tou_double;
        }
    }

    public static class ColorSingleDoubleBean {
        /**
         * red_single : 1,7,13,19,23,29,35,45
         * red_double : 2,8,12,18,24,30,34,40,46
         * blue_single : 3,9,15,25,31,37,41,47
         * blue_double : 4,10,14,20,26,36,42,48
         * green_single : 5,11,17,21,27,33,39,43,49
         * green_double : 6,16,22,28,32,38,44
         */

        private String red_single;
        private String red_double;
        private String blue_single;
        private String blue_double;
        private String green_single;
        private String green_double;

        public String getRed_single() {
            return red_single;
        }

        public void setRed_single(String red_single) {
            this.red_single = red_single;
        }

        public String getRed_double() {
            return red_double;
        }

        public void setRed_double(String red_double) {
            this.red_double = red_double;
        }

        public String getBlue_single() {
            return blue_single;
        }

        public void setBlue_single(String blue_single) {
            this.blue_single = blue_single;
        }

        public String getBlue_double() {
            return blue_double;
        }

        public void setBlue_double(String blue_double) {
            this.blue_double = blue_double;
        }

        public String getGreen_single() {
            return green_single;
        }

        public void setGreen_single(String green_single) {
            this.green_single = green_single;
        }

        public String getGreen_double() {
            return green_double;
        }

        public void setGreen_double(String green_double) {
            this.green_double = green_double;
        }
    }

    public static class BoBean {
        /**
         * red_bo : 1,2,7,8,12,13,18,19,23,24,29,30,34,35,40,45,46
         * blue_bo : 3,4,9,10,14,15,20,25,26,31,36,37,41,42,47,48
         * green_bo : 5,6,11,16,17,21,22,27,28,32,33,38,39,43,44,49
         */

        private String red_bo;
        private String blue_bo;
        private String green_bo;

        public String getRed_bo() {
            return red_bo;
        }

        public void setRed_bo(String red_bo) {
            this.red_bo = red_bo;
        }

        public String getBlue_bo() {
            return blue_bo;
        }

        public void setBlue_bo(String blue_bo) {
            this.blue_bo = blue_bo;
        }

        public String getGreen_bo() {
            return green_bo;
        }

        public void setGreen_bo(String green_bo) {
            this.green_bo = green_bo;
        }
    }

    public static class JiaShouBean {
        /**
         * jiaqin : 1,2,3,5,6,11,13,14,15,17,18,23,25,26,27,29,30,35,37,38,39,41,42,47,49
         * yeshou : 4,7,8,9,10,12,16,19,20,21,22,24,28,31,32,33,34,36,40,43,44,45,46,48
         */

        private String jiaqin;
        private String yeshou;

        public String getJiaqin() {
            return jiaqin;
        }

        public void setJiaqin(String jiaqin) {
            this.jiaqin = jiaqin;
        }

        public String getYeshou() {
            return yeshou;
        }

        public void setYeshou(String yeshou) {
            this.yeshou = yeshou;
        }
    }

    public static class BigSmallBean {
        /**
         * big : 25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49
         * small : 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24
         */

        private String big;
        private String small;

        public String getBig() {
            return big;
        }

        public void setBig(String big) {
            this.big = big;
        }

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }
    }

    public static class SmallSingleDoubleBean {
        /**
         * small_single : 1,3,5,7,9,11,13,15,17,19,21,23
         * small_double : 2,4,6,8,10,12,14,16,18,20,22,24
         */

        private String small_single;
        private String small_double;

        public String getSmall_single() {
            return small_single;
        }

        public void setSmall_single(String small_single) {
            this.small_single = small_single;
        }

        public String getSmall_double() {
            return small_double;
        }

        public void setSmall_double(String small_double) {
            this.small_double = small_double;
        }
    }

    public static class HeBigSmallBean {
        /**
         * he_big : 7,8,9,16,17,18,19,25,26,27,28,29,34,35,36,37,38,39,43,44,45,46,47,48,49
         * he_small : 1,2,3,4,5,6,10,11,12,13,14,15,20,21,22,23,24,30,31,32,33,40,41,42
         */

        private String he_big;
        private String he_small;

        public String getHe_big() {
            return he_big;
        }

        public void setHe_big(String he_big) {
            this.he_big = he_big;
        }

        public String getHe_small() {
            return he_small;
        }

        public void setHe_small(String he_small) {
            this.he_small = he_small;
        }
    }

    public static class SingleDoubleBean {
        /**
         * single : 1,3,5,7,9,11,13,15,17,19,21,23,25,27,29,31,33,35,37,39,41,43,45,47,49
         * double : 2,4,6,8,10,12,14,16,18,20,22,24,26,28,30,32,34,36,38,40,42,44,46,48
         */

        private String single;
        @SerializedName("double")
        private String doubleX;

        public String getSingle() {
            return single;
        }

        public void setSingle(String single) {
            this.single = single;
        }

        public String getDoubleX() {
            return doubleX;
        }

        public void setDoubleX(String doubleX) {
            this.doubleX = doubleX;
        }
    }

    public static class BigSingleDoubleBean {
        /**
         * big_single : 25,27,29,31,33,35,37,39,41,43,45,47,49
         * big_double : 26,28,30,32,34,36,38,40,42,44,46,48
         */

        private String big_single;
        private String big_double;

        public String getBig_single() {
            return big_single;
        }

        public void setBig_single(String big_single) {
            this.big_single = big_single;
        }

        public String getBig_double() {
            return big_double;
        }

        public void setBig_double(String big_double) {
            this.big_double = big_double;
        }
    }

    public static class HeSingleDoubleBean {
        /**
         * he_single : 1,3,5,7,9,10,12,14,16,18,21,23,25,27,29,30,32,34,36,38,41,43,45,47,49
         * he_double : 2,4,6,8,11,13,15,17,19,20,22,24,26,28,31,33,35,37,39,40,42,44,46,48
         */

        private String he_single;
        private String he_double;

        public String getHe_single() {
            return he_single;
        }

        public void setHe_single(String he_single) {
            this.he_single = he_single;
        }

        public String getHe_double() {
            return he_double;
        }

        public void setHe_double(String he_double) {
            this.he_double = he_double;
        }
    }
}
