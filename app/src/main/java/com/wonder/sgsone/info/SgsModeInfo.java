package com.wonder.sgsone.info;

/**
 * Created by u6031313 on 2/26/2016.
 */
public class SgsModeInfo {
    public static final int CLASS_GUO_ZHAN = 7;
    public static final int CLASS_Identity = 1;
    public static final int CLASS_Match = 5;
    public static final int CLASS_Novice = 4;
    public static final int CLASS_READSGS = -1;
    public static final int CLASS_Tournament = 2;
    public static final int MODE_1V1BZ = 9;
    public static final int MODE_1V1KS = 11;
    public static final int MODE_1V1Teach = 55;
    public static final int MODE_1V1_NEW = 30;
    public static final int MODE_1V3HLG = 17;
    public static final int MODE_3V3BZ = 8;
    public static final int MODE_3V3KS = 10;
    public static final int MODE_COUNTRYWAR = 28;
    public static final int MODE_EightBZ = 1;
    public static final int MODE_EightJZ = 15;
    public static final int MODE_EightSN = 2;
    public static final int MODE_FiveBZ = 5;
    public static final int MODE_FiveJZ = 16;
    public static final int MODE_FiveNew = 54;
    public static final int MODE_HAPPY_1V1_MOBILE = 52;
    public static final int MODE_HAPPY_2V2 = 33;
    public static final int MODE_HAPPY_3V3 = 32;
    public static final int MODE_MATCH = 50;
    public static final int MODE_NEW3V3 = 23;
    public static final int MODE_Radar = 51;
    public static final int MODE_ReadSDA = -2;
    public static final int MODE_ReadSGS = -3;
    public static final int MODE_SHENHUAZAILIN = 22;
    public static final int MODE_SWORD = 39;
    public static final int MODE_TEACH = 29;
    public static final int MODE_Teach = 53;
    public static final int MODE_ZHUOGUI = 38;
    public static final int SECTION_Advance = 5;
    public static final int SECTION_Extreme = 6;
    public static final int SECTION_Free = 3;
    public static final int SECTION_Junior = 2;
    public static final int SECTION_Medium = 4;
    public int nModel;
    public static String getModelName(int paramInt)
    {
        switch (paramInt)
        {
            case 1:
                return "八人标准场";
            case 2:
                return "八人双内场";
            case 5:
                return "五人标准场";
            case 8:
                return "3V3标准场";
            case 9:
                return "1V1标准场";
            case 10:
                return "3V3快速场";
            case 15:
                return "八人军争场";
            case 16:
                return "五人军争场";
            case 17:
                return "1v3虎牢关";
            case 99:
                return "新手教学";
            case 11:
                return "1V1快速场";
            case 23:
                return "新3V3场";
            case 30:
                return "新1v1";
            case 28:
                return "国战";
            case 38:
                return "捉鬼模式";
            case 32:
                return "欢乐3V3";
            case 33:
                return "欢乐成双";
            case 53:
                return "教学场";
            case 54:
                return "五人训练场";
            case 55:
                return "新手上路";
            default: return "守卫剑阁";
        }
    }
    public static String getSectionName(int paramInt)
    {
        switch (paramInt)
        {
            case 2:
                return "测试";
            case 3:
                return "自由";
            case 4:
                return "中级";
            case 5:
                return "高级";
            default:
                return "至尊";
        }

    }
}
