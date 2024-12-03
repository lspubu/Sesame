package io.github.lazyimmortal.sesame.entity;

import java.util.ArrayList;
import java.util.List;

public class CustomOption extends IdAndName {

    public CustomOption(String i, String n) {
        id = i;
        name = n;
    }

    public static List<CustomOption> getEcoLifeOptions() {
        List<CustomOption> list = new ArrayList<>();
        list.add(new CustomOption("tick", "绿色行动打卡"));
        list.add(new CustomOption("dish", "光盘行动打卡"));
        return list;
    }

    public static List<CustomOption> getUseAccelerateToolOptions() {
        List<CustomOption> list = new ArrayList<>();
        list.add(new CustomOption("useAccelerateToolContinue", "连续使用"));
        list.add(new CustomOption("useAccelerateToolWhenMaxEmotion", "仅在满状态时使用"));
        return list;
    }

    public static List<CustomOption> getAntFarmFamilyOptions() {
        List<CustomOption> list = new ArrayList<>();
        list.add(new CustomOption("familySign", "每日签到"));
        list.add(new CustomOption("familyFeed", "帮喂成员"));
        list.add(new CustomOption("familyEatTogether", "美食请客"));
        list.add(new CustomOption("familyAwardList", "领取奖励"));
        return list;
    }

    public static List<CustomOption> getAntInsuranceOptions() {
        List<CustomOption> list = new ArrayList<>();
        list.add(new CustomOption("beanSignIn", "安心豆签到"));
        list.add(new CustomOption("beanExchangeGoldenTicket", "安心豆兑换黄金票"));
        list.add(new CustomOption("beanExchangeBubbleBoost", "安心豆兑换时光加速器"));
        list.add(new CustomOption("gainSumInsured", "保障金领取"));
        return list;
    }
}
