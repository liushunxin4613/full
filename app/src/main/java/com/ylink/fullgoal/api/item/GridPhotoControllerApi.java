package com.ylink.fullgoal.api.item;

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
import com.ylink.fullgoal.controllerApi.core.NormControllerApi;
import com.ylink.fullgoal.norm.GridPhotoNorm;
import com.ylink.fullgoal.vo.ImageVo;

import butterknife.Bind;

public class GridPhotoControllerApi<C> extends NormControllerApi<GridPhotoControllerApi, C, GridPhotoNorm>{

    @Bind(R.id.icon_iv)
    ImageView iconIv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.progress_bar)
    ContentLoadingProgressBar progressBar;
    @Bind(R.id.error_iv)
    ImageView errorIv;

    public GridPhotoControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_grid_photo;
    }

    @Override
    protected void onSafeNorm(@NonNull GridPhotoNorm norm, int position) {
        setImage(iconIv, norm.getRes(), ImageFactory.getInstance().getRotate(norm.getRes()))
                .execute(() -> {
                    if (norm.getObj() instanceof ImageVo) {
                        ImageVo vo = (ImageVo) norm.getObj();
                        setText(nameTv, String.format("金额: %s", TextUtils.isEmpty(vo.getAmount())
                                ? "0" : vo.getAmount()));
                        onError(vo.isError());
                        vo.setProgressListener(this::onLoading).setErrorAction(this::onError);
                    }
                })
                .setLayoutParams(iconIv, new FrameLayout.LayoutParams(-1, norm.getUnit()))
                .setVisibility(nameTv, norm.isVisible() ? View.VISIBLE : View.GONE)
                .setOnClickListener(norm.getOnClickListener())
                .setOnLongClickListener(norm.getOnLongClickListener());
    }

    private void onLoading(long progress, long total) {
        QueueFactory.getInstance().uiEnqueue(() -> {
            ii("percentage", TextUtils.getPercentage(progress, total));
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
            setVisibility(progressBar, View.GONE);
            setVisibility(errorIv, View.VISIBLE);
        } else {
            setVisibility(errorIv, View.GONE);
        }
    }

}