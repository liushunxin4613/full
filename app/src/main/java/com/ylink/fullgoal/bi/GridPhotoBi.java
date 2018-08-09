package com.ylink.fullgoal.bi;

import android.support.annotation.NonNull;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.leo.core.factory.ImageFactory;
import com.leo.core.queue.QueueFactory;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.GridPhotoBean;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceBi;
import com.ylink.fullgoal.vo.ImageVo;

import butterknife.Bind;

public class GridPhotoBi extends SurfaceBi<GridPhotoBi, GridPhotoBean> {

    @Bind(R.id.icon_iv)
    ImageView iconIv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.progress_bar)
    ContentLoadingProgressBar progressBar;
    @Bind(R.id.error_iv)
    ImageView errorIv;

    @Override
    public Integer getDefLayoutResId() {
        return R.layout.l_grid_photo;
    }

    @Override
    public void updateBind(@NonNull SurfaceControllerApi api, @NonNull GridPhotoBean bean) {
        super.updateBind(api, bean);
        api.setImage(iconIv, bean.getRes(), ImageFactory.getInstance().getRotate(bean.getRes()))
                .execute(() -> {
                    if (bean.getObj() instanceof ImageVo) {
                        ImageVo vo = (ImageVo) bean.getObj();
                        api.setText(nameTv, String.format("金额: %s", TextUtils.isEmpty(vo.getAmount())
                                ? "0" : vo.getAmount()));
                        onError(vo.isError());
                        vo.setProgressListener(this::onLoading).setErrorAction(this::onError);
                    }
                })
                .setLayoutParams(iconIv, new FrameLayout.LayoutParams(-1, bean.getUnit()))
                .setVisibility(nameTv, bean.isVisible() ? View.VISIBLE : View.GONE)
                .setOnClickListener(bean.getOnClickListener())
                .setOnLongClickListener(bean.getOnLongClickListener());
    }

    private void onLoading(long progress, long total) {
        QueueFactory.getInstance().uiEnqueue(() -> {
            api().ii("percentage", TextUtils.getPercentage(progress, total));
            progressBar.setProgress((int) (progress * 100 / total));
            if (progress >= total) {
                progressBar.hide();
            } else {
                progressBar.show();
            }
        });
    }

    private void onError(boolean error) {
        if (error) {
            api().setVisibility(progressBar, View.GONE);
            api().setVisibility(errorIv, View.VISIBLE);
        } else {
            api().setVisibility(errorIv, View.GONE);
        }
    }

}