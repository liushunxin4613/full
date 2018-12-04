package com.ylink.fullgoal.api.surface;

import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.leo.core.iapi.inter.IAction;
import com.leo.core.rect.Rec;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.util.CameraUtil;
import com.ylink.fullgoal.view.MaskView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import static com.ylink.fullgoal.config.Config.IMAGE_TYPE;

public class Camera2ControllerApi<T extends Camera2ControllerApi, C> extends SurfaceControllerApi<T, C> {

    @Bind(R.id.my_surfaceView)
    SurfaceView mySurfaceView;
    @Bind(R.id.openLight)
    ImageButton openLight;
    @Bind(R.id.cameraSwitch)
    ImageButton cameraSwitch;
    @Bind(R.id.topRly)
    RelativeLayout topRly;
    @Bind(R.id.takePhoto)
    Button takePhoto;
    @Bind(R.id.cancel_bt)
    Button cancelBt;
    @Bind(R.id.mask_view)
    MaskView maskView;
    @Bind(R.id.boottomRly)
    RelativeLayout boottomRly;

    private float dist;
    private Camera mCamera;
    private SurfaceHolder holder;
    private float pointX, pointY;
    private int cameraPosition = 0;//0代表前置摄像头,1代表后置摄像头,默认打开前置摄像头
    private static final int FOCUS = 1;// 聚焦
    private static final int ZOOM = 2;// 缩放
    private int mode;//0是聚焦 1是放大
    private int curZoomValue = 0;//放大缩小
    private Camera.Parameters parameters;
    private boolean safeToTakePicture = true;
    private String imageType;
    private int previewSizeWidth;
    private int previewSizeHeight;

    public Camera2ControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.c_test_camera;
    }

    @Override
    public void initView() {
        super.initView();
        executeBundle(bundle -> imageType = bundle.getString(IMAGE_TYPE));
        initSurfaceView();
        initOnClickListener();
    }

    private void initOnClickListener() {
        setOnClickListener(takePhoto, v -> op(() -> {
            if (safeToTakePicture && mCamera != null) {
                mCamera.takePicture(null, null, mJpeg);
                safeToTakePicture = false;
            }
        })).setOnClickListener(openLight, v -> turnLight(mCamera)).setOnClickListener(cameraSwitch, v -> {
            releaseCamera();
            cameraPosition = (cameraPosition + 1) % Camera.getNumberOfCameras();
            mCamera = getCamera(cameraPosition);
            if (holder != null) {
                startPreview(mCamera, holder);
            }
        }).setOnClickListener(cancelBt, v -> getActivity().finish());
    }

    @SuppressLint("CheckResult")
    private void op(IAction action) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            final RxPermissions rxPermissions = new RxPermissions(getFragmentActivity());
            rxPermissions.requestEachCombined(Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(permission -> {
                        if (permission.granted) {
                            if (action != null) {
                                action.execute();
                            }
                        } else {
                            show("请开启拍照权限");
                        }
                    });
        } else {
            if (action != null) {
                action.execute();
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initSurfaceView() {
        holder = mySurfaceView.getHolder();
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                mCamera.stopPreview();
                startPreview(mCamera, holder);
                autoFocus();
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                startPreview(mCamera, holder);
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                releaseCamera();
            }
        }); // 回调接口
        mySurfaceView.setOnTouchListener((v, event) -> {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                // 主点按下
                case MotionEvent.ACTION_DOWN:
                    pointX = event.getX();
                    pointY = event.getY();
                    mode = FOCUS;
                    break;
                // 副点按下
                case MotionEvent.ACTION_POINTER_DOWN:
                    dist = spacing(event);
                    // 如果连续两点距离大于10，则判定为多点模式
                    if (spacing(event) > 10f) {
                        mode = ZOOM;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
                    mode = FOCUS;
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (mode == FOCUS) {
                    } else if (mode == ZOOM) {
                        float newDist = spacing(event);
                        if (newDist > 10f) {
                            float tScale = (newDist - dist) / dist;
                            if (tScale < 0) {
                                tScale = tScale * 10;
                            }
                            addZoomIn((int) tScale);
                        }
                    }
                    break;
            }
            return false;
        });
        mySurfaceView.setOnClickListener(v -> {
            try {
                pointFocus((int) pointX, (int) pointY);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void addZoomIn(int delta) {
        try {
            Camera.Parameters params = mCamera.getParameters();
            if (!params.isZoomSupported()) {
                return;
            }
            curZoomValue += delta;
            if (curZoomValue < 0) {
                curZoomValue = 0;
            } else if (curZoomValue > params.getMaxZoom()) {
                curZoomValue = params.getMaxZoom();
            }
            if (!params.isSmoothZoomSupported()) {
                params.setZoom(curZoomValue);
                mCamera.setParameters(params);
            } else {
                mCamera.startSmoothZoom(curZoomValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //定点对焦的代码
    @SuppressLint("ObsoleteSdkInt")
    private void pointFocus(int x, int y) {
        mCamera.cancelAutoFocus();
        parameters = mCamera.getParameters();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            showPoint(x, y);
        }
        mCamera.setParameters(parameters);
        autoFocus();
    }

    /**
     * 两点的距离
     */
    private float spacing(MotionEvent event) {
        if (event == null) {
            return 0;
        }
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    private void showPoint(int x, int y) {
        if (parameters.getMaxNumMeteringAreas() > 0) {
            List<Camera.Area> areas = new ArrayList<>();
            //xy变换了
            int rectY = -x * 2000 / CameraUtil.SCREEN_WIDTH + 1000;
            int rectX = y * 2000 / CameraUtil.SCREEN_HEIGHT - 1000;
            int left = rectX < -900 ? -1000 : rectX - 100;
            int top = rectY < -900 ? -1000 : rectY - 100;
            int right = rectX > 900 ? 1000 : rectX + 100;
            int bottom = rectY > 900 ? 1000 : rectY + 100;
            Rect area1 = new Rect(left, top, right, bottom);
            areas.add(new Camera.Area(area1, 800));
            parameters.setMeteringAreas(areas);
        }
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
    }

    //实现自动对焦
    private void autoFocus() {
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (mCamera == null) {
                    return;
                }
                mCamera.autoFocus((success, camera) -> {
                    if (success) {
                        setupCamera(camera);//实现相机的参数初始化
                    }
                });
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mCamera == null) {
            mCamera = getCamera(cameraPosition);
            if (holder != null) {
                startPreview(mCamera, holder);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        releaseCamera();
    }

    /**
     * 闪光灯开关   开->关->自动
     */
    private void turnLight(Camera mCamera) {
        if (mCamera == null || mCamera.getParameters() == null
                || mCamera.getParameters().getSupportedFlashModes() == null) {
            return;
        }
        Camera.Parameters parameters = mCamera.getParameters();
        String flashMode = mCamera.getParameters().getFlashMode();
        List<String> supportedModes = mCamera.getParameters().getSupportedFlashModes();
        if (Camera.Parameters.FLASH_MODE_OFF.equals(flashMode)
                && supportedModes.contains(Camera.Parameters.FLASH_MODE_ON)) {//关闭状态
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
            mCamera.setParameters(parameters);
            openLight.setImageResource(R.mipmap.camera_flash_on);
        } else if (Camera.Parameters.FLASH_MODE_ON.equals(flashMode)) {//开启状态
            if (supportedModes.contains(Camera.Parameters.FLASH_MODE_AUTO)) {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
                openLight.setImageResource(R.mipmap.camera_flash_auto);
                mCamera.setParameters(parameters);
            } else if (supportedModes.contains(Camera.Parameters.FLASH_MODE_OFF)) {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                openLight.setImageResource(R.mipmap.camera_flash_off);
                mCamera.setParameters(parameters);
            }
        } else if (Camera.Parameters.FLASH_MODE_AUTO.equals(flashMode)
                && supportedModes.contains(Camera.Parameters.FLASH_MODE_OFF)) {
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            mCamera.setParameters(parameters);
            openLight.setImageResource(R.mipmap.camera_flash_off);
        }
    }

    /**
     * 获取Camera实例
     */
    private Camera getCamera(int id) {
        try {
            return Camera.open(id);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * 预览相机
     */
    private void startPreview(Camera camera, SurfaceHolder holder) {
        try {
            setupCamera(camera);
            camera.setPreviewDisplay(holder);
            //亲测的一个方法 基本覆盖所有手机 将预览矫正
            CameraUtil.getInstance().setCameraDisplayOrientation(getActivity(), cameraPosition, camera);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放相机资源
     */
    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    /**
     * 设置
     */
    private void setupCamera(Camera camera) {
        Camera.Parameters parameters = camera.getParameters();
        List<String> focusModes = parameters.getSupportedFocusModes();
        if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            // Autofocus mode is supported 自动对焦
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        }
        Camera.Size previewSize = CameraUtil.findBestPreviewResolution(camera);
        previewSizeWidth = previewSize.width;
        previewSizeHeight = previewSize.height;
        parameters.setPreviewSize(previewSizeWidth, previewSizeHeight);
        Camera.Size pictrueSize = CameraUtil.getInstance().getPropPictureSize(
                parameters.getSupportedPictureSizes(), 1000);
        parameters.setPictureSize(pictrueSize.width, pictrueSize.height);
        try {
            camera.setParameters(parameters);
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        int picWidth = CameraUtil.SCREEN_WIDTH;
        int picHeight = CameraUtil.SCREEN_WIDTH * previewSizeWidth / previewSizeHeight;
        mySurfaceView.setLayoutParams(new FrameLayout.LayoutParams(picWidth, picHeight));
        maskView.initCenterRect(picWidth, picHeight);
    }

    //将bitmap保存在本地，然后通知图库更新
    private File saveImageToGallery(Bitmap bmp) {
        // 首先保存图片
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(getRootDir(), fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            show("保存图片失败");
        }
        return null;
    }

    private Bitmap jz(Bitmap bitmap) {
        if (bitmap != null && previewSizeWidth > 0 && previewSizeHeight > 0
                && bitmap.getHeight() > 0 && bitmap.getWidth() > 0) {
            Matrix matrix = new Matrix();
            if(bitmap.getWidth() > bitmap.getHeight()){
                matrix.preRotate(90);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                        bitmap.getHeight(), matrix, true);
            }
            int pmw = previewSizeWidth > previewSizeHeight ? previewSizeHeight : previewSizeWidth;
            int pwh = previewSizeWidth > previewSizeHeight ? previewSizeWidth : previewSizeHeight;
            double sa = (double) pmw / pwh;
            double sb = (double) bitmap.getHeight() / bitmap.getWidth();
            if (sa > sb) {
                double ds = (double) bitmap.getWidth() * pwh / pmw;
                double offset = (bitmap.getHeight() - ds) / 2;
                return Bitmap.createBitmap(bitmap, 0, (int) offset, bitmap.getWidth(), (int) ds);
            } else if (sa < sb) {
                double ds = (double) bitmap.getHeight() * pmw / pwh;
                double offset = (bitmap.getWidth() - ds) / 2;
                return Bitmap.createBitmap(bitmap, (int) offset, 0, (int) ds, bitmap.getHeight());
            }
        }
        return bitmap;
    }

    //图像数据处理完成后的回调函数
    private Camera.PictureCallback mJpeg = (data, camera) -> new Thread(() -> {
        try {
            //将照片改为竖直方向
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            bitmap = jz(bitmap);
            Rec rec = maskView.newRec(bitmap.getWidth(), bitmap.getHeight());
            bitmap = Bitmap.createBitmap(bitmap, rec.getX(), rec.getY(), rec.getW(),
                    rec.getH(), new Matrix(), true);
            File file = saveImageToGallery(bitmap);
            if (file != null) {
                runOnUiThread(() -> routeApi().tailor(imageType, file.getPath()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        safeToTakePicture = true;
    }).start();

}
