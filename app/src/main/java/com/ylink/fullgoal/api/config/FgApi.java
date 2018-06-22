package com.ylink.fullgoal.api.config;

import com.leo.core.api.main.CoreControllerApi;
import com.leo.core.api.main.HasCoreControllerApi;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.util.Base64Util;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.vo.ImageVo;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.ylink.fullgoal.config.UrlConfig.FG_ROOT_URL;
import static com.ylink.fullgoal.config.UrlConfig.FULL_DIMENSION_LIST;
import static com.ylink.fullgoal.config.UrlConfig.FULL_IMAGE_UPLOAD;
import static com.ylink.fullgoal.config.UrlConfig.FULL_REIMBURSE_QUERY;
import static com.ylink.fullgoal.config.UrlConfig.FULL_REIMBURSE_SUBMIT;

public class FgApi<T extends FgApi> extends HasCoreControllerApi<T> {

    private final static String ROOT_URL = FG_ROOT_URL;
    private final static String DEBUG_URL = "http://192.168.8.108:8088/ssca/";

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
        api().post(ROOT_URL, "Reimburse_ment.action");
    }

    /**
     * 获取部门信息
     */
    public void queryDepartmentData() {
        api().post(ROOT_URL, "Budget_department.action");
    }

    /**
     * 获取项目信息
     */
    public void queryProjectData(String departmentCode) {
        api().post(ROOT_URL, "Project_list.action", get(map
                -> map.put("departmentCode", departmentCode)));
    }

    /**
     * 获取合同申请单
     */
    public void queryContractPaymentData() {
        api().post(ROOT_URL, "Contract_payment.action");
    }

    /**
     * 获取招待申请单
     */
    public void queryProcessData() {
        api().post(ROOT_URL, "Process_query.action");
    }

    /**
     * 获取费用指标
     */
    public void queryCostIndexData() {
        api().post(ROOT_URL, "Cost_index.action");
    }

    /**
     * 获取出差申请单
     */
    public void queryTravelFormData() {
        api().post(ROOT_URL, "Travel_form.action");
    }

    /**
     * 获取投研报告
     */
    public void queryResearchReportData() {
        api().post(ROOT_URL, "Research_report.action");
    }

    /**
     * 获取携程机票
     */
    public void queryCtripTicketsData() {
        api().post(ROOT_URL, "Ctrip_tickets.action");
    }

    /**
     * 报销确认请求数据
     *
     * @param serialNo 报销批次号
     */
    public void queryMessageBack(String serialNo) {
        api().post(ROOT_URL, FULL_REIMBURSE_QUERY, get(map -> map.put("serialNo", serialNo)));
    }

    /**
     * 报销确认请求数据
     */
    public void queryBank() {
        api().post(ROOT_URL, "Bank_mess.action");
    }

    /**
     * 获取报销列表
     */
    public void queryApplicationForm(Map<String, Object> map, String msg) {
        if(!TextUtils.isEmpty(map)){
            api().post(ROOT_URL, "Application_form.action", get(mp -> mp.putAll(map)), msg);
        }
    }

    /**
     * 分摊维度列表
     */
    public void queryDimensionList(String costIndex) {
        api().post(ROOT_URL, FULL_DIMENSION_LIST, get(map -> map.put("costIndex", costIndex)));
    }

    /**
     * 分摊维度信息列表
     */
    public void queryDimensionInformation(String dimensionCode) {
        api().post(ROOT_URL, "Dimension_information.action", get(map -> map.put("dimensionCode", dimensionCode)));
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
    public void imageUpload(String first, int photoType, String serialNo, File file, String tag) {
        if (file != null) {
            String base64 = Base64Util.getFileToBase64(file);
            String invoiceUse = ImageVo.getValue(photoType);
            final String fst = (TextUtils.equals("1", first) &&
                    !TextUtils.isEmpty(serialNo)) ? "6" : first;
            api().post(ROOT_URL, FULL_IMAGE_UPLOAD, get(map -> {
                map.put("first", fst);
                map.put("agent", api().getUId());
                map.put("stream", base64);
                map.put("serialNo", serialNo);
                map.put("fileName", file.getName());
                map.put("status", "上传");
                map.put("invoiceUse", invoiceUse);
                map.put("invoiceAmount", "");
            }), photoType, tag);
        }
    }

    /**
     * 删除图片
     *
     * @param serialNo 报销序列号
     * @param imageId  图片id
     */
    public void imageDelete(String serialNo, String imageId, String invoiceAmount, int type, String tag) {
        api().post(ROOT_URL, FULL_IMAGE_UPLOAD, get(map -> {
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
        api().post(DEBUG_URL, FULL_IMAGE_UPLOAD, get(map -> {
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
            ee("map", map);
            api().post(ROOT_URL, FULL_REIMBURSE_SUBMIT, get(mp -> mp.putAll(map)));
        }
    }

    //私有的

    private IObjAction<Map<String, String>> get(IObjAction<Map<String, Object>> action) {
        Map<String, Object> mp;
        executeNon(mp = new HashMap<>(), action);
        if (!TextUtils.isEmpty(mp)) {
            return map -> map.put("REQINFO", api().encode(mp));
        }
        return null;
    }

}
