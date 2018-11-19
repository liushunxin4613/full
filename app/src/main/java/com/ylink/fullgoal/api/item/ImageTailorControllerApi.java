package com.ylink.fullgoal.api.item;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leo.core.factory.ImageFactory;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.api.surface.Camera2ControllerApi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

import java.io.FileOutputStream;

import butterknife.Bind;
import uk.co.senab.photoview.PhotoView;

import static com.ylink.fullgoal.config.Config.FILE_PATH;
import static com.ylink.fullgoal.config.Config.IMAGE_TYPE;

public class ImageTailorControllerApi<C> extends SurfaceControllerApi<ImageTailorControllerApi, C> {

    @Bind(R.id.photo_iv)
    PhotoView photoIv;
    @Bind(R.id.to_left_tv)
    ImageView toLeftTv;
    @Bind(R.id.to_right_tv)
    ImageView toRightTv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.detail_tv)
    TextView detailTv;

    private String photo;
    private String imageType;

    public ImageTailorControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_image_tailor;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void initView() {
        super.initView();
        executeBundle(bundle -> {
            photo = bundle.getString(FILE_PATH);
            imageType = bundle.getString(IMAGE_TYPE);
        });
        setVisibility(TextUtils.isEmpty(photo) ?
                View.INVISIBLE : View.VISIBLE, photoIv)
                .setImage(photoIv, photo, ImageFactory.getInstance().getRotate(photo),
                        (path, iv, rotate, success) -> ImageFactory.getInstance().save(path, rotate))
                .setOnClickListener(toLeftTv, view -> setImage(photoIv, photo,
                        ImageFactory.getInstance().getRotate(photo, -90),
                        (path, iv, rotate, success) -> ImageFactory.getInstance().save(path, rotate)))
                .setOnClickListener(toRightTv, view -> setImage(photoIv, photo,
                        ImageFactory.getInstance().getRotate(photo, 90),
                        (path, iv, rotate, success) -> ImageFactory.getInstance().save(path, rotate)))
                .setOnClickListener(nameTv, v -> getActivity().finish())
                .setOnClickListener(detailTv, v -> {
                    //第一次上线需求变更
                    float rotate = ImageFactory.getInstance().getRotate(photo);
                    Bitmap bitmap = BitmapFactory.decodeFile(photo);
                    Matrix matrix = new Matrix();
                    matrix.postRotate(rotate);
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),
                            matrix, true);
                    saveBitmap(bitmap, photo);
                    ee(String.format("图片处理完毕: %s, 旋转角度: %.2f", photo, rotate));

                    activityLifecycleApi().finishActivity(map(m -> m
                            .put(IMAGE_TYPE, imageType)
                            .put(FILE_PATH, photo)), getClass(), Camera2ControllerApi.class);
                });
    }

    private void saveBitmap(Bitmap bitmap, String filePath){
        if(TextUtils.check(bitmap, filePath)){
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(filePath);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                // 如果图片还没有回收，强制回收
                if (!bitmap.isRecycled()) {
                    bitmap.recycle();
                    System.gc();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}