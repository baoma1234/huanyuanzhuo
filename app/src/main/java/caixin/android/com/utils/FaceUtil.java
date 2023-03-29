package caixin.android.com.utils;


import com.caixin.huanyu.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by cxf on 2018/7/11.
 */

public class FaceUtil {

    private static final Map<String, Integer> FACE_MAP;

    private static final List<String> FACE_LIST;

    private static String[] emojis = new String[]{"\uD83D\uDC2D", "\uD83D\uDC2E", "\uD83D\uDC2F", "\uD83D\uDC30",
            "\uD83D\uDC09", "\uD83D\uDC0D", "\uD83D\uDC34", "\uD83D\uDC11", "\uD83D\uDC35",
            "\uD83D\uDC14", "\uD83D\uDC36", "\uD83D\uDC37", "\uD83D\uDE01", "\uD83D\uDE02",
            "\uD83D\uDE03", "\uD83D\uDE04", "\uD83D\uDC7F", "\uD83D\uDE09", "\uD83D\uDE0A",
            "☺", "\uD83D\uDE0C", "\uD83D\uDE0D", "\uD83D\uDE0F", "\uD83D\uDE12",
            "\uD83D\uDE13", "\uD83D\uDE14", "\uD83D\uDE16", "\uD83D\uDE18", "\uD83D\uDE1A",
            "\uD83D\uDE1C", "\uD83D\uDE1D", "\uD83D\uDE1E", "\uD83D\uDE20", "\uD83D\uDE21",
            "\uD83D\uDE22", "\uD83D\uDE23", "\uD83D\uDE25", "\uD83D\uDE28", "\uD83D\uDE2A",
            "\uD83D\uDE2D", "\uD83D\uDE30", "\uD83D\uDE31", "\uD83D\uDE32", "\uD83D\uDE33",
            "\uD83D\uDE37", "\uD83D\uDE43", "\uD83D\uDE0B", "\uD83D\uDE17", "\uD83D\uDE1B",
            "\uD83E\uDD11", "\uD83E\uDD13", "\uD83D\uDE0E", "\uD83E\uDD17", "\uD83D\uDE44",
            "\uD83E\uDD14", "\uD83D\uDE29", "\uD83D\uDE24", "\uD83E\uDD10", "\uD83E\uDD12",
            "\uD83D\uDE34", "\uD83D\uDC6F", "\uD83D\uDC76", "\uD83D\uDC66", "\uD83D\uDC67",
            "\uD83D\uDC68", "\uD83D\uDC69", "\uD83D\uDC6B", "\uD83D\uDC71", "\uD83D\uDC72",
            "\uD83D\uDC73", "\uD83D\uDC74", "\uD83D\uDC75", "\uD83D\uDC6E", "\uD83D\uDC77",
            "\uD83D\uDC78", "\uD83D\uDC82", "\uD83D\uDC7C", "\uD83C\uDF85", "\uD83D\uDC7B",
            "\uD83D\uDCA9", "\uD83D\uDC80", "\uD83D\uDC7D", "\uD83D\uDC7E", "\uD83D\uDC81",
            "\uD83D\uDE45", "\uD83D\uDE46", "\uD83D\uDC86", "\uD83D\uDC87", "\uD83D\uDE4B",
            "\uD83D\uDE47", "\uD83D\uDC91", "\uD83D\uDC8F", "\uD83D\uDE4C", "\uD83D\uDC4F",
            "\uD83D\uDC42", "\uD83D\uDC40", "\uD83D\uDC43", "\uD83D\uDC44", "\uD83D\uDC8B",
            "\uD83D\uDC85", "\uD83D\uDC4B", "\uD83D\uDC4D", "\uD83D\uDC4E", "\uD83D\uDC46",
            "\uD83D\uDC47", "\uD83D\uDC48", "\uD83D\uDC49", "\uD83D\uDC4C", "✌",
            "\uD83D\uDC4A", "✊", "\uD83D\uDCAA", "\uD83D\uDC50", "\uD83D\uDE4F"
    };

    static {
        FACE_MAP = new LinkedHashMap<>();
        FACE_MAP.put("[鼠]", R.mipmap.mouse_1f401);
        FACE_MAP.put("[牛]", R.mipmap.ox_1f402);
        FACE_MAP.put("[虎]", R.mipmap.tiger_1f405);
        FACE_MAP.put("[兔]", R.mipmap.rabbit_1f407);
        FACE_MAP.put("[龙]", R.mipmap.dragon_1f409);
        FACE_MAP.put("[蛇]", R.mipmap.snake_1f40d);
        FACE_MAP.put("[马]", R.mipmap.horse_1f40e);
        FACE_MAP.put("[羊]", R.mipmap.goat_1f410);
        FACE_MAP.put("[猴]", R.mipmap.monkey_1f412);
        FACE_MAP.put("[鸡]", R.mipmap.chicken_1f414);
        FACE_MAP.put("[狗]", R.mipmap.dog_1f415);
        FACE_MAP.put("[猪]", R.mipmap.pig_1f416);
        FACE_MAP.put("[中]", R.mipmap.mahjong_tile_red_dragon_1f004);
        FACE_MAP.put("[双心]", R.mipmap.revolving_hearts_1f49e);
        FACE_MAP.put("[100]", R.mipmap.hundred_points_symbol_1f4af);
        FACE_MAP.put("[骰子]", R.mipmap.game_die_1f3b2);
        FACE_MAP.put("[对号]", R.mipmap.heavy_check_mark_2714);
        FACE_MAP.put("[红叉]", R.mipmap.cross_mark_274c);
        FACE_MAP.put("[问号]", R.mipmap.black_question_mark_ornament_2753);
        FACE_MAP.put("[感叹号]", R.mipmap.heavy_exclamation_mark_symbol_2757);
        FACE_MAP.put("[啤酒]", R.mipmap.clinking_beer_mugs_1f37b);
        FACE_MAP.put("[邮件]", R.mipmap.love_letter_1f48c);
        FACE_MAP.put("[钻石]", R.mipmap.gem_stone_1f48e);
        FACE_MAP.put("[钱袋]", R.mipmap.money_bag_1f4b0);
        FACE_MAP.put("[钱]", R.mipmap.money_with_wings_1f4b8);
        FACE_MAP.put("[呵呵]", R.mipmap.tt_e0);
        FACE_MAP.put("[嘻嘻]", R.mipmap.tt_e1);
        FACE_MAP.put("[哈哈]", R.mipmap.tt_e2);
        FACE_MAP.put("[晕]", R.mipmap.tt_e3);
        FACE_MAP.put("[泪]", R.mipmap.tt_e4);
        FACE_MAP.put("[馋嘴]", R.mipmap.tt_e5);
        FACE_MAP.put("[抓狂]", R.mipmap.tt_e6);
        FACE_MAP.put("[哼]", R.mipmap.tt_e7);
        FACE_MAP.put("[可爱]", R.mipmap.tt_e8);
        FACE_MAP.put("[怒]", R.mipmap.tt_e9);
        FACE_MAP.put("[困]", R.mipmap.tt_e10);
        FACE_MAP.put("[汗]", R.mipmap.tt_e11);
        FACE_MAP.put("[睡觉]", R.mipmap.tt_e12);
        FACE_MAP.put("[偷笑]", R.mipmap.tt_e13);
        FACE_MAP.put("[酷]", R.mipmap.tt_e14);
        FACE_MAP.put("[吃惊]", R.mipmap.tt_e15);
        FACE_MAP.put("[闭嘴]", R.mipmap.tt_e16);
        FACE_MAP.put("[花心]", R.mipmap.tt_e17);
        FACE_MAP.put("[失望]", R.mipmap.tt_e18);
        FACE_MAP.put("[生病]", R.mipmap.tt_e19);
        FACE_MAP.put("[亲亲]", R.mipmap.tt_e20);
        FACE_MAP.put("[右哼哼]", R.mipmap.tt_e21);
        FACE_MAP.put("[嘘]", R.mipmap.tt_e22);
        FACE_MAP.put("[挤眼]", R.mipmap.tt_e23);
        FACE_MAP.put("[酷]", R.mipmap.tt_e24);
        FACE_MAP.put("[感冒]", R.mipmap.tt_e25);
        FACE_MAP.put("[做鬼脸]", R.mipmap.tt_e26);
        FACE_MAP.put("[阴险]", R.mipmap.tt_e27);
        FACE_MAP.put("[热吻]", R.mipmap.tt_e28);
        FACE_MAP.put("[心]", R.mipmap.tt_e29);
        FACE_MAP.put("[OK]", R.mipmap.tt_e30);
        FACE_MAP.put("[不要]", R.mipmap.tt_e31);
        FACE_MAP.put("[弱]", R.mipmap.tt_e32);
        FACE_MAP.put("[棒]", R.mipmap.tt_e33);
        FACE_MAP.put("[拳头]", R.mipmap.tt_e34);
        FACE_MAP.put("[耶]", R.mipmap.tt_e35);
        FACE_MAP.put("[0]", R.mipmap.tt_e36);
        FACE_MAP.put("[1]", R.mipmap.tt_e37);
        FACE_MAP.put("[2]", R.mipmap.tt_e38);
        FACE_MAP.put("[3]", R.mipmap.tt_e39);
        FACE_MAP.put("[4]", R.mipmap.tt_e40);
        FACE_MAP.put("[5]", R.mipmap.tt_e41);
        FACE_MAP.put("[6]", R.mipmap.tt_e42);
        FACE_MAP.put("[7]", R.mipmap.tt_e43);
        FACE_MAP.put("[8]", R.mipmap.tt_e44);
        FACE_MAP.put("[9]", R.mipmap.tt_e45);
        FACE_LIST = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : FACE_MAP.entrySet()) {
            FACE_LIST.add(entry.getKey());
        }

        FACE_LIST.clear();
        for (int i = 0; i < emojis.length; i++) {
            FACE_LIST.add(emojis[i]);
        }
    }

    public static List<String> getFaceList() {
        return FACE_LIST;
    }

    public static Integer getFaceImageRes(String key) {
        return FACE_MAP.get(key);
    }
}
