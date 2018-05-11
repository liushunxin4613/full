package com.ylink.fullgoal.api.surface;

import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.util.DisneyUtil;
import com.leo.core.util.ResUtil;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.GridBean;
import com.ylink.fullgoal.bean.GridPhotoBean;
import com.ylink.fullgoal.bean.TvH2Bean;
import com.ylink.fullgoal.bean.TvH2MoreBean;
import com.ylink.fullgoal.bean.TvHEt3Bean;
import com.ylink.fullgoal.bean.TvHEtIconMoreBean;
import com.ylink.fullgoal.bean.TvV2DialogBean;
import com.ylink.fullgoal.controllerApi.surface.RecycleBarControllerApi;

/**
 * 一般费用报销
 */
public class CommonControllerApi<T extends CommonControllerApi, C> extends RecycleBarControllerApi<T, C> {

    public CommonControllerApi(C controller) {
        super(controller);
    }

    @Override
    public IControllerApi createControllerApi(ViewGroup container, int resId) {
        switch (resId) {
            case GridBean.LAYOUT_RES_ID:
                return new GridItemControllerApi(null);
        }
        return super.createControllerApi(container, resId);
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("一般费用报销").setRightTv("提交", v -> {
            show("提交");
        });
        clear().addVgBean(new TvH2Bean("张三", "计划财务部"),
                new TvHEtIconMoreBean(R.mipmap.test_icon_user, "报销人", null, "请输入报销人", (bean, view) -> {
                    show(bean.getName() + ", " + bean.getText());
                }), new TvH2MoreBean("预算归属部门", "请选择预算归属部门", (bean, view) -> {
                    show(bean.getName());
                }), new TvH2MoreBean("项目", "请选择项目", (bean, view) -> {
                    show(bean.getName());
                }), new TvH2MoreBean("合同付款申请单", "请选择合同付款申请单", (bean, view) -> {
                    show(bean.getName());
                }), new TvH2MoreBean("招待申请单", "请选择招待申请单", (bean, view) -> {
                    show(bean.getName());
                }), new TvHEt3Bean("事由", null, "请输入事由"))
                .addGridBean(new GridPhotoBean(R.mipmap.test_photo, this::onGridPhotoClick, this::onGridPhotoLongClick),
                        new GridPhotoBean(R.mipmap.test_photo, this::onGridPhotoClick, this::onGridPhotoLongClick),
                        new GridPhotoBean(R.mipmap.test_photo, this::onGridPhotoClick, this::onGridPhotoLongClick),
                        new GridPhotoBean(R.mipmap.posting_add, this::onGridPhotoClick, this::onGridPhotoLongClick))
                .notifyDataSetChanged();
    }

    private boolean onGridPhotoClick(GridPhotoBean bean, View view) {
        show("图片");
        return false;
    }

    private boolean onGridPhotoLongClick(GridPhotoBean bean, View view) {
        TvV2DialogBean db = new TvV2DialogBean("重新上传", "删除", (item, v) -> {
            show(item.getName());
        }, (item, v) -> {
            show(item.getDetail());
        });
        ItemControllerApi api = getDialogControllerApi(ItemControllerApi.class, db.getApiType());
        api.dialogShow().onBindViewHolder(db, 0);
        Window window = api.getDialog().getWindow();
        if(window != null){
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = DisneyUtil.getScreenDisplay().getX() - ResUtil.getDimenInt(R.dimen.x120);
            window.setAttributes(lp);
        }
        return false;
    }

}
