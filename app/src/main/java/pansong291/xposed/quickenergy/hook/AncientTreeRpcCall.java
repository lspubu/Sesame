package pansong291.xposed.quickenergy.hook;

public class AncientTreeRpcCall {
        private static final String VERSION = "20230522";

        public static String homePage(String selectCityCode) {
                return ApplicationHook.request("alipay.greenmatrix.rpc.h5.ancienttree.homePage",
                                "[{\"cityCode\":\"330100\",\"selectCityCode\":\"" + selectCityCode
                                                + "\",\"source\":\"antforesthome\"}]");
        }

        public static String queryTreeItemsForExchange(String cityCode) {
                return ApplicationHook.request("alipay.antforest.forest.h5.queryTreeItemsForExchange",
                                "[{\"cityCode\":\"" + cityCode
                                                + "\",\"itemTypes\":\"\",\"source\":\"chInfo_ch_appcenter__chsub_9patch\",\"version\":\""
                                                + VERSION + "\"}]");
        }

        public static String districtDetail(String districtCode) {
                return ApplicationHook.request("alipay.greenmatrix.rpc.h5.ancienttree.districtDetail",
                                "[{\"districtCode\":\"" + districtCode + "\",\"source\":\"antforesthome\"}]");
        }

        public static String projectDetail(String ancientTreeProjectId, String cityCode) {
                return ApplicationHook.request("alipay.greenmatrix.rpc.h5.ancienttree.projectDetail",
                                "[{\"ancientTreeProjectId\":\"" + ancientTreeProjectId
                                                + "\",\"channel\":\"ONLINE\",\"cityCode\":\"" + cityCode
                                                + "\",\"source\":\"ancientreethome\"}]");
        }

        public static String protect(String activityId, String ancientTreeProjectId, String cityCode) {
                return ApplicationHook.request("alipay.greenmatrix.rpc.h5.ancienttree.protect",
                                "[{\"ancientTreeActivityId\":\"" + activityId + "\",\"ancientTreeProjectId\":\""
                                                + ancientTreeProjectId + "\",\"cityCode\":\"" + cityCode
                                                + "\",\"source\":\"ancientreethome\"}]");
        }
}
