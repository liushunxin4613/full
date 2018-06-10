package com.ylink.fullgoal.api.config;

import com.leo.core.api.main.CoreControllerApi;
import com.leo.core.bean.Uri;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.inter.IProgressListener;
import com.leo.core.net.UrlApi;
import com.leo.core.util.Base64Util;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.vo.ImageVo;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.leo.core.util.TextUtils.check;
import static com.ylink.fullgoal.config.UrlConfig.FG_ROOT_URL;
import static com.ylink.fullgoal.config.UrlConfig.FULL_IMAGE_UPLOAD;
import static com.ylink.fullgoal.config.UrlConfig.FULL_REIMBURSE_SUBMIT;
import static com.ylink.fullgoal.config.UrlConfig.PATH_QUERY_APPLICATION_FORM_DATA;
import static com.ylink.fullgoal.config.UrlConfig.PATH_QUERY_BANK_DATA;
import static com.ylink.fullgoal.config.UrlConfig.PATH_QUERY_CONTRACT_PAYMENT_DATA;
import static com.ylink.fullgoal.config.UrlConfig.PATH_QUERY_COST_INDEX_DATA;
import static com.ylink.fullgoal.config.UrlConfig.PATH_QUERY_CTRIP_TICKETS_DATA;
import static com.ylink.fullgoal.config.UrlConfig.PATH_QUERY_DEPARTMENT_DATA;
import static com.ylink.fullgoal.config.UrlConfig.PATH_QUERY_DIMENSION_DATA;
import static com.ylink.fullgoal.config.UrlConfig.PATH_QUERY_DIMENSION_INFORMATION_DATA;
import static com.ylink.fullgoal.config.UrlConfig.PATH_QUERY_MESSAGE_BACK_DATA;
import static com.ylink.fullgoal.config.UrlConfig.PATH_QUERY_PROCESS_DATA;
import static com.ylink.fullgoal.config.UrlConfig.PATH_QUERY_PROJECT_DATA;
import static com.ylink.fullgoal.config.UrlConfig.PATH_QUERY_RESEARCH_REPORT_DATA;
import static com.ylink.fullgoal.config.UrlConfig.PATH_QUERY_TRAVEL_FORM_DATA;
import static com.ylink.fullgoal.config.UrlConfig.PATH_QUERY_USER_DATA;

public class FgApi<T extends FgApi> extends UrlApi<T> {

    private final static boolean SERVER = true;
    private final static String ROOT_URL = FG_ROOT_URL;
    private final static String DEBUG_URL = SERVER
            ? "http://111.231.231.226/app/fullApp/"
            : "http://192.168.8.102/app/fullApp/";

    public FgApi(CoreControllerApi controllerApi) {
        super(controllerApi);
    }

    private CoreControllerApi api() {
        return controllerApi();
    }

    /**
     * 获取员工信息
     */
    public void queryUserData() {
        post(ROOT_URL, PATH_QUERY_USER_DATA);
    }

    /**
     * 获取部门信息
     */
    public void queryDepartmentData() {
        post(ROOT_URL, PATH_QUERY_DEPARTMENT_DATA);
    }

    /**
     * 获取项目信息
     */
    public void queryProjectData(String departmentCode) {
        post(ROOT_URL, PATH_QUERY_PROJECT_DATA, g(map ->
                map.put("departmentCode", departmentCode)));
    }

    /**
     * 获取合同申请单
     */
    public void queryContractPaymentData() {
        post(ROOT_URL, PATH_QUERY_CONTRACT_PAYMENT_DATA);
    }

    /**
     * 获取招待申请单
     */
    public void queryProcessData() {
        post(ROOT_URL, PATH_QUERY_PROCESS_DATA);
    }

    /**
     * 获取费用指标
     */
    public void queryCostIndexData() {
        post(ROOT_URL, PATH_QUERY_COST_INDEX_DATA);
    }

    /**
     * 获取出差申请单
     */
    public void queryTravelFormData() {
        post(ROOT_URL, PATH_QUERY_TRAVEL_FORM_DATA);
    }

    /**
     * 获取投研报告
     */
    public void queryResearchReportData() {
        post(ROOT_URL, PATH_QUERY_RESEARCH_REPORT_DATA);
    }

    /**
     * 获取携程机票
     */
    public void queryCtripTicketsData() {
        post(ROOT_URL, PATH_QUERY_CTRIP_TICKETS_DATA);
    }

    /**
     * 报销确认请求数据
     *
     * @param serialNo 报销批次号
     */
    public void queryMessageBack(String serialNo) {
        post(ROOT_URL, PATH_QUERY_MESSAGE_BACK_DATA, g(map -> map.put("serialNo", serialNo)));
    }

    /**
     * 银行卡请求数据
     */
    public void queryBank() {
        post(ROOT_URL, PATH_QUERY_BANK_DATA);
    }

    /**
     * 获取报销列表
     */
    public void queryApplicationForm(Map<String, Object> map, String msg) {
        if (!TextUtils.isEmpty(map)) {
            post(ROOT_URL, PATH_QUERY_APPLICATION_FORM_DATA, g(mp -> mp.putAll(map)), msg);
        }
    }

    /**
     * 分摊维度列表
     */
    public void queryDimensionList(String costIndex) {
        post(ROOT_URL, PATH_QUERY_DIMENSION_DATA, g(map -> map.put("costIndex", costIndex)));
    }

    /**
     * 分摊维度信息列表
     */
    public void queryDimensionInformation(String dimensionCode) {
        post(ROOT_URL, PATH_QUERY_DIMENSION_INFORMATION_DATA, g(map -> map.put("dimensionCode",
                dimensionCode)));
    }

    /**
     * 上传图片
     *
     * @param first     是否第一次上传:1.首次;2.确认;3.修改;
     * @param file      文件
     * @param serialNo  报销序列号
     * @param photoType 图片类型
     * @param tag       标志:文件地址;
     */
    public void imageUpload(String first, int photoType, String serialNo, File file, String tag,
                            IProgressListener listener) {
        if (file != null) {
            String base64 = Base64Util.getFileToBase64(file);
            String invoiceUse = ImageVo.getValue(photoType);
            final String fst = (TextUtils.equals("1", first) &&
                    !TextUtils.isEmpty(serialNo)) ? "6" : first;
            bodyPost(ROOT_URL, FULL_IMAGE_UPLOAD, g(map -> {
                map.put("first", fst);
                map.put("agent", api().getUId());
                map.put("stream", base64);
                map.put("serialNo", serialNo);
                map.put("fileName", file.getName());
                map.put("status", "上传");
                map.put("invoiceUse", invoiceUse);
                map.put("invoiceAmount", "");
            }), photoType, tag, listener);
        }
    }

    /**
     * 删除图片
     *
     * @param serialNo 报销序列号
     * @param imageId  图片id
     */
    public void imageDelete(String serialNo, String imageId, String invoiceAmount, int type, String tag) {
        post(ROOT_URL, FULL_IMAGE_UPLOAD, g(map -> {
            map.put("agent", api().getUId());
            map.put("serialNo", serialNo);
            map.put("status", "删除");
            map.put("imageId", imageId);
            map.put("invoiceAmount", invoiceAmount == null ? "" : invoiceAmount);
        }), type, tag);
    }

    /**
     * 修改图片金额
     *
     * @param serialNo 报销序列号
     * @param imageId  图片id
     * @param amount   修改金额
     */
    public void imageUpdateAmount(String serialNo, String imageId, String amount) {
        post(ROOT_URL, FULL_IMAGE_UPLOAD, g(map -> {
            map.put("agent", api().getUId());
            map.put("serialNo", serialNo);
            map.put("status", "修改金额");
            map.put("imageId", imageId);
            map.put("invoiceAmount", amount);
        }));
    }

    /**
     * 提交报销、修改、确认任务
     */
    public void submitReimburse(Map<String, Object> map) {
        if (!TextUtils.isEmpty(map)) {
            post(ROOT_URL, FULL_REIMBURSE_SUBMIT, g(mp -> mp.putAll(map)));
        }
    }

    /**
     * SSO 模拟登入
     */
    public void SSO(String tid) {
        if (!TextUtils.isEmpty(tid)) {
            get("http://192.168.40.87:8080/", "sso-server/validateTGT", map -> {
                map.put("ticketGrantingTicketId", tid);
                map.put("type", "validateTGT");
            });
        }
    }

    // >>> ****************************** 2018-07-05 11:38 ****************************** >>>

    /**
     * 银行卡请求数据
     */
    public void queryBankV1() {
        post(ROOT_URL, PATH_QUERY_BANK_DATA, g(map -> {
            map.put("agent", api().getUId());
            map.put("agentName", api().getUserName());
            map.put("type", "1");
        }));
    }

    /**
     * 银行卡请求数据
     */
    public void submitBankV1(String cardNo, String openBank) {
        post(ROOT_URL, PATH_QUERY_BANK_DATA, g(map -> {
            map.put("agent", api().getUId());
            map.put("agentName", api().getUserName());
            map.put("type", "2");
            map.put("cardNo", cardNo);
            map.put("openBank", openBank);
        }));
    }

    /**
     * 申请单查询
     */
    public void queryApply(String costCode) {
        if (check(costCode)) {
            post(DEBUG_URL, "ApplyCompensation", map -> {
                map.put("agent", api().getUId());
                map.put("departmentCode", api().getDepartmentCode());
                map.put("costCode", costCode);
            });
        }
    }

    /**
     * 申请单查询
     */
    public void queryApplyContent(String applyCode) {
        if (check(applyCode)) {
            post(DEBUG_URL, "ApplyContentCompensation", map -> map.put("applyCode", applyCode));
        }
    }

    // >>> ****************************** 2018-06-09 19:25 ****************************** >>>

    public void download(String url) {
        Uri uri = new Uri(url);
        if (uri.check()) {
            get(uri.getStart(), uri.getContent() + uri.getEnd(), uri.getEnd());
        }
    }

    public void queryConfig(){
        post("http://111.231.231.226/", "app/fullApp/queryConfig", g(map -> map.put("channel", "android")));
    }

    //私有的

    private IObjAction<Map<String, Object>> g(IObjAction<Map<String, Object>> action) {
        Map<String, Object> mp;
        executeNon(mp = new HashMap<>(), action);
        if (!TextUtils.isEmpty(mp)) {
            Map<String, Object> m = TextUtils.copy(mp, true, "stream");
            api().ii(m);
            return map -> map.put("REQINFO", api().encode(mp));
        }
        return null;
    }

}
