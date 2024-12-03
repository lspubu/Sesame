package io.github.lazyimmortal.sesame.model.testRpc;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Objects;

import io.github.lazyimmortal.sesame.data.TokenConfig;
import io.github.lazyimmortal.sesame.hook.Toast;
import io.github.lazyimmortal.sesame.model.task.antSports.AntSportsRpcCall;
import io.github.lazyimmortal.sesame.util.*;
import io.github.lazyimmortal.sesame.util.idMap.UserIdMap;

public class TestRpc {
    private static final String TAG = "TestRpc";

    public static void start(String broadcastFun, String broadcastData, String testType) {
        new Thread() {
            String broadcastFun;
            String broadcastData;
            String testType;

            public Thread setData(String fun, String data, String type) {
                broadcastFun = fun;
                broadcastData = data;
                testType = type;
                return this;
            }

            @Override
            public void run() {
                try {
                    Class<?> clazz = Class.forName("io.github.lazyimmortal.sesame.model.testRpc.TestRpcAlpha");
                    clazz.getMethod("handleTestRpc", String.class, String.class, String.class).invoke(null, testType, broadcastFun, broadcastData);
                } catch (Exception ignored) {
                }
                if (Objects.equals("getTreeItems", testType)) {
                    getTreeItems();
                }
                if (Objects.equals("getNewTreeItems", testType)) {
                    getNewTreeItems();
                }
                if (Objects.equals("queryAreaTrees", testType)) {
                    queryAreaTrees();
                }
                if (Objects.equals("getUnlockTreeItems", testType)) {
                    getUnlockTreeItems();
                }
                if (Objects.equals("setCustomWalkPathId", testType)) {
                    setCustomWalkPathId(broadcastData);
                }
                if (Objects.equals("addCustomWalkPathIdQueue", testType)) {
                    addCustomWalkPathIdQueue(broadcastData);
                }
                if (Objects.equals("clearCustomWalkPathIdQueue", testType)) {
                    if (TokenConfig.clearCustomWalkPathIdQueue()) {
                        Toast.show("清除待行走路线成功");
                    }
                }
            }
        }.setData(broadcastFun, broadcastData, testType).start();
    }

    private static void getNewTreeItems() {
        try {
            String s = TestRpcCall.queryTreeItemsForExchange("COMING");
            JSONObject jo = new JSONObject(s);
            if ("SUCCESS".equals(jo.getString("resultCode"))) {
                JSONArray ja = jo.getJSONArray("treeItems");
                if (ja.length() == 0) {
                    Log.forest("新树上苗🌱[当前没有新树上苗信息!]");
                    return;
                }
                for (int i = 0; i < ja.length(); i++) {
                    jo = ja.getJSONObject(i);
                    if (!jo.has("projectType"))
                        continue;
                    if (!"TREE".equals(jo.getString("projectType")))
                        continue;
                    if (!"COMING".equals(jo.getString("applyAction")))
                        continue;
                    String projectId = jo.getString("itemId");
                    queryTreeForExchange(projectId);
                }
            } else {
                Log.i(TAG, jo.getString("resultDesc"));
            }
        } catch (Throwable t) {
            Log.i(TAG, "getTreeItems err:");
            Log.printStackTrace(TAG, t);
        }
    }

    private static void queryTreeForExchange(String projectId) {
        try {
            String s = TestRpcCall.queryTreeForExchange(projectId);
            JSONObject jo = new JSONObject(s);
            if ("SUCCESS".equals(jo.getString("resultCode"))) {
                JSONObject exchangeableTree = jo.getJSONObject("exchangeableTree");
                int currentBudget = exchangeableTree.getInt("currentBudget");
                String region = exchangeableTree.getString("region");
                String treeName = exchangeableTree.getString("treeName");
                String tips = "不可合种";
                if (exchangeableTree.optBoolean("canCoexchange", false)) {
                    tips = "可以合种-合种类型："
                            + exchangeableTree.getJSONObject("extendInfo").getString("cooperate_template_id_list");
                }
                Log.forest("新树上苗🌱[" + region + "-" + treeName + "]#" + currentBudget + "株-" + tips);
            } else {
                Log.record(jo.getString("resultDesc") + s);
            }
        } catch (Throwable t) {
            Log.i(TAG, "queryTreeForExchange err:");
            Log.printStackTrace(TAG, t);
        }
    }

    private static void getTreeItems() {
        try {
            String s = TestRpcCall.queryTreeItemsForExchange("AVAILABLE,ENERGY_LACK");
            JSONObject jo = new JSONObject(s);
            if ("SUCCESS".equals(jo.getString("resultCode"))) {
                JSONArray ja = jo.getJSONArray("treeItems");
                for (int i = 0; i < ja.length(); i++) {
                    jo = ja.getJSONObject(i);
                    if (!jo.has("projectType"))
                        continue;
                    String projectId = jo.getString("itemId");
                    String itemName = jo.getString("itemName");
                    getTreeCurrentBudget(projectId, itemName);
                    TimeUtil.sleep(100);
                }
            } else {
                Log.i(TAG, jo.getString("resultDesc"));
            }
        } catch (Throwable t) {
            Log.i(TAG, "getTreeItems err:");
            Log.printStackTrace(TAG, t);
        }
    }

    private static void getTreeCurrentBudget(String projectId, String treeName) {
        try {
            String s = TestRpcCall.queryTreeForExchange(projectId);
            JSONObject jo = new JSONObject(s);
            if ("SUCCESS".equals(jo.getString("resultCode"))) {
                JSONObject exchangeableTree = jo.getJSONObject("exchangeableTree");
                int currentBudget = exchangeableTree.getInt("currentBudget");
                String region = exchangeableTree.getString("region");
                Log.forest("树苗查询🌱[" + region + "-" + treeName + "]#剩余:" + currentBudget);
            } else {
                Log.record(jo.getString("resultDesc") + s);
            }
        } catch (Throwable t) {
            Log.i(TAG, "queryTreeForExchange err:");
            Log.printStackTrace(TAG, t);
        }
    }

    private static void queryAreaTrees() {
        try {
            String s = TestRpcCall.queryAreaTrees();
            JSONObject jo = new JSONObject(s);
            if ("SUCCESS".equals(jo.getString("resultCode"))) {
                JSONObject areaTrees = jo.getJSONObject("areaTrees");
                JSONObject regionConfig = jo.getJSONObject("regionConfig");
                Iterator<String> regionKeys = regionConfig.keys();
                while (regionKeys.hasNext()) {
                    String regionKey = regionKeys.next();
                    if (!areaTrees.has(regionKey)) {
                        JSONObject region = regionConfig.getJSONObject(regionKey);
                        String regionName = region.optString("regionName");
                        Log.forest("未解锁地区🗺️[" + regionName + "]");
                    }
                }
            } else {
                Log.i(TAG, jo.getString("resultDesc"));
            }
        } catch (Throwable t) {
            Log.i(TAG, "queryAreaTrees err:");
            Log.printStackTrace(TAG, t);
        }
    }

    private static void getUnlockTreeItems() {
        try {
            String s = TestRpcCall.queryTreeItemsForExchange("");
            JSONObject jo = new JSONObject(s);
            if ("SUCCESS".equals(jo.getString("resultCode"))) {
                JSONArray ja = jo.getJSONArray("treeItems");
                for (int i = 0; i < ja.length(); i++) {
                    jo = ja.getJSONObject(i);
                    if (!jo.has("projectType"))
                        continue;
                    int certCountForAlias = jo.optInt("certCountForAlias", -1);
                    if (certCountForAlias == 0) {
                        String itemName = jo.optString("itemName");
                        String region = jo.optString("region");
                        String organization = jo.optString("organization");
                        Log.forest("未解锁项目🐘[" + region + "-" + itemName + "]#" + organization);
                    }
                }
            } else {
                Log.i(TAG, jo.getString("resultDesc"));
            }
        } catch (Throwable t) {
            Log.i(TAG, "getUnlockTreeItems err:");
            Log.printStackTrace(TAG, t);
        }
    }

    private static void setCustomWalkPathId(String pathId) {
        String userId = UserIdMap.getCurrentUid();
        if (StringUtil.isEmpty(userId)) {
            Toast.show("设置自定义路线失败:找不到用户信息");
            return;
        }
        String pathName = "自定义路线关闭";
        if (!StringUtil.isEmpty(pathId)) {
            pathName = AntSportsRpcCall.queryPathName(pathId);
            if (pathName == null) {
                Toast.show("设置自定义路线失败:找不到路线信息");
                return;
            }
        }
        String userMaskName = UserIdMap.getCurrentMaskName();
        if (TokenConfig.setCustomWalkPathId(userId, pathId)) {
            Toast.show("设置自定义路线成功:" + pathName + "-->" + userMaskName);
        }
    }

    private static void addCustomWalkPathIdQueue(String pathId) {
        if (!StringUtil.isEmpty(pathId)) {
            String pathName = AntSportsRpcCall.queryPathName(pathId);
            if (pathName == null) {
                Toast.show("添加待行走路线失败:找不到路线信息");
                return;
            }
            if (TokenConfig.addCustomWalkPathIdQueue(pathId)) {
                Toast.show("添加待行走路线成功:" + pathName);
            }
        }
    }
}
