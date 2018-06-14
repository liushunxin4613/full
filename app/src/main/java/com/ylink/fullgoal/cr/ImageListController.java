package com.ylink.fullgoal.cr;

import com.leo.core.iapi.inter.IBolAction;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.cr.core.AddController;
import com.ylink.fullgoal.fg.MImageFg;

import java.util.List;

/**
 * 图像集合控制器
 */
public class ImageListController<T extends ImageListController> extends AddController<T, MImageFg> {

    private final static String FILTER_YB = "一般";
    private final static String FILTER_JTF = "交通费";
    private final static String FILTER_ZSF = "住宿费";
    private final static String FILTER_CCJPF = "车船机票费";

    @Override
    public T initDB(MImageFg imageFg) {
        return super.initDB(imageFg);
    }

    @Override
    protected MImageFg getFilterDB(String key, MImageFg imageFg) {
        if (imageFg != null && TextUtils.equals(key, imageFg.getInvoiceUse())) {
            return imageFg;
        }
        return null;
    }

    @Override
    public MImageFg getDB() {
        return super.getDB();
    }

    /**
     * 一般票据组
     */
    public List<MImageFg> getYbData() {
        return getFilterDBData(FILTER_YB);
    }

    /**
     * 交通费票据组
     */
    public List<MImageFg> getJtfData() {
        return getFilterDBData(FILTER_JTF);
    }

    /**
     * 住宿费票据组
     */
    public List<MImageFg> getZsfData() {
        return getFilterDBData(FILTER_ZSF);
    }

    /**
     * 车船机票费票据组
     */
    public List<MImageFg> getCcjpfData() {
        return getFilterDBData(FILTER_CCJPF);
    }

    @Override
    public MImageFg getFilterDB(IBolAction<MImageFg>... args) {
        return super.getFilterDB(args);
    }

    @Override
    protected Class<MImageFg> getClz() {
        return MImageFg.class;
    }

    @Override
    protected String getDefUBKey() {
        return null;
    }

    @Override
    protected <UB> UB getDefUB() {
        return null;
    }

}
