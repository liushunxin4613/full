package com.ylink.fullgoal.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.util.DisplayMetrics;
import android.view.Surface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("MismatchedQueryAndUpdateOfStringBuilder")
public class CameraUtil {

    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    private static CameraUtil instance;
    private CameraAscendSizeComparator ascendSizeComparator = new CameraAscendSizeComparator();

    public static void init(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        if (dm.widthPixels < dm.heightPixels) {
            SCREEN_WIDTH = dm.widthPixels;
            SCREEN_HEIGHT = dm.heightPixels;
        } else {
            SCREEN_WIDTH = dm.heightPixels;
            SCREEN_HEIGHT = dm.widthPixels;
        }
    }

    public static CameraUtil getInstance() {
        if(instance == null){
            synchronized (CameraUtil.class){
                if(instance == null){
                    instance = new CameraUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 保证预览方向正确
     */
    public void setCameraDisplayOrientation(Activity activity, int cameraId, Camera camera) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }
        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }

    public Bitmap setTakePicktrueOrientation(int id, Bitmap bitmap) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(id, info);
        bitmap = rotaingImageView(id, info.orientation, bitmap);
        return bitmap;
    }

    /**
     * 把相机拍照返回照片转正
     *
     * @param angle 旋转角度
     * @return bitmap 图片
     */
    private Bitmap rotaingImageView(int id, int angle, Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        //加入翻转 把相机拍照返回照片转正
        if (id == 1) {
            matrix.postScale(-1, 1);
        }
        // 创建新的图片
        return Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /**
     * 获取所有支持的预览尺寸
     */
    public Size getPropPreviewSize(List<Size> list, int minWidth) {
        Collections.sort(list, ascendSizeComparator);

        int i = 0;
        for (Size s : list) {
            if ((s.width >= minWidth)) {
                break;
            }
            i++;
        }
        if (i == list.size()) {
            i = 0;//如果没找到，就选最小的size
        }
        return list.get(i);
    }

    /**
     * 获取所有支持的返回图片尺寸
     */
    public Size getPropPictureSize(List<Size> list, int minWidth) {
        Collections.sort(list, ascendSizeComparator);
        int i = 0;
        for (Size s : list) {
            if ((s.width >= minWidth)) {
                break;
            }
            i++;
        }
        if (i == list.size()) {
            i = 0;//如果没找到，就选最小的size
        }
        return list.get(i);
    }

    public boolean equalRate(Size s, float rate) {
        float r = (float) (s.width) / (float) (s.height);
        return Math.abs(r - rate) <= 0.03;
    }

    //降序
    public class CameraDropSizeComparator implements Comparator<Size> {
        public int compare(Size lhs, Size rhs) {
            return Integer.compare(rhs.width, lhs.width);
        }
    }

    //升序
    public class CameraAscendSizeComparator implements Comparator<Size> {
        public int compare(Size lhs, Size rhs) {
            return Integer.compare(lhs.width, rhs.width);
        }
    }

    /**
     * 打印支持的previewSizes
     */
    public void printSupportPreviewSize(Camera.Parameters params) {
        List<Size> previewSizes = params.getSupportedPreviewSizes();
        for (int i = 0; i < previewSizes.size(); i++) {
            Size size = previewSizes.get(i);
        }
    }

    /**
     * 打印支持的pictureSizes
     */
    public void printSupportPictureSize(Camera.Parameters params) {
        List<Size> pictureSizes = params.getSupportedPictureSizes();
        for (int i = 0; i < pictureSizes.size(); i++) {
            Size size = pictureSizes.get(i);
        }
    }

    /**
     * 打印支持的聚焦模式
     */
    public void printSupportFocusMode(Camera.Parameters params) {
        List<String> focusModes = params.getSupportedFocusModes();
        for (String mode : focusModes) {
        }
    }

    /**
     * 打开闪关灯
     */
    public void turnLightOn(Camera mCamera) {
        if (mCamera == null) {
            return;
        }
        Camera.Parameters parameters = mCamera.getParameters();
        if (parameters == null) {
            return;
        }
        List<String> flashModes = parameters.getSupportedFlashModes();
        // Check if camera flash exists
        if (flashModes == null) {
            // Use the screen as a flashlight (next best thing)
            return;
        }
        String flashMode = parameters.getFlashMode();
        if (!Camera.Parameters.FLASH_MODE_ON.equals(flashMode)) {
            // Turn on the flash
            if (flashModes.contains(Camera.Parameters.FLASH_MODE_TORCH)) {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                mCamera.setParameters(parameters);
            } else {
            }
        }
    }


    /**
     * 自动模式闪光灯
     */
    public void turnLightAuto(Camera mCamera) {
        if (mCamera == null) {
            return;
        }
        Camera.Parameters parameters = mCamera.getParameters();
        if (parameters == null) {
            return;
        }
        List<String> flashModes = parameters.getSupportedFlashModes();
        if (flashModes == null) {
            return;
        }
        String flashMode = parameters.getFlashMode();
        if (!Camera.Parameters.FLASH_MODE_AUTO.equals(flashMode)) {
            // Turn on the flash
            if (flashModes.contains(Camera.Parameters.FLASH_MODE_TORCH)) {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                mCamera.setParameters(parameters);
            } else {
            }
        }
    }


    /**
     * 最小预览界面的分辨率
     */
    private static final int MIN_PREVIEW_PIXELS = 480 * 320;
    /**
     * 最大宽高比差
     */
    private static final double MAX_ASPECT_DISTORTION = 0.15;


    /**
     * 关闭闪光灯
     */
    public void turnLightOff(Camera mCamera) {
        if (mCamera == null) {
            return;
        }
        Camera.Parameters parameters = mCamera.getParameters();
        if (parameters == null) {
            return;
        }
        List<String> flashModes = parameters.getSupportedFlashModes();
        String flashMode = parameters.getFlashMode();
        // Check if camera flash exists
        if (flashModes == null) {
            return;
        }
        if (!Camera.Parameters.FLASH_MODE_OFF.equals(flashMode)) {
            // Turn off the flash
            if (flashModes.contains(Camera.Parameters.FLASH_MODE_TORCH)) {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                mCamera.setParameters(parameters);
            } else {
            }
        }
    }

    public static Size findBestPreviewResolution(Camera mCamera) {
        Camera.Parameters cameraParameters = mCamera.getParameters();
        Size defaultPreviewResolution = cameraParameters.getPreviewSize();

        List<Size> rawSupportedSizes = cameraParameters.getSupportedPreviewSizes();
        if (rawSupportedSizes == null) {
            return defaultPreviewResolution;
        }
        // 按照分辨率从大到小排序
        List<Size> supportedPreviewResolutions = new ArrayList<>(rawSupportedSizes);
        Collections.sort(supportedPreviewResolutions, (a, b) -> {
            int aPixels = a.height * a.width;
            int bPixels = b.height * b.width;
            return Integer.compare(bPixels, aPixels);
        });
        StringBuilder previewResolutionSb = new StringBuilder();
        for (Size supportedPreviewResolution : supportedPreviewResolutions) {
            previewResolutionSb.append(supportedPreviewResolution.width).append('x')
                    .append(supportedPreviewResolution.height).append(' ');
        }
        // 移除不符合条件的分辨率
        double screenAspectRatio = (double) (SCREEN_WIDTH / SCREEN_HEIGHT);
        Iterator<Size> it = supportedPreviewResolutions.iterator();
        while (it.hasNext()) {
            Size supportedPreviewResolution = it.next();
            int width = supportedPreviewResolution.width;
            int height = supportedPreviewResolution.height;
            // 移除低于下限的分辨率，尽可能取高分辨率
            if (width * height < MIN_PREVIEW_PIXELS) {
                it.remove();
                continue;
            }
            // 在camera分辨率与屏幕分辨率宽高比不相等的情况下，找出差距最小的一组分辨率
            // 由于camera的分辨率是width>height，我们设置的portrait模式中，width<height
            // 因此这里要先交换然preview宽高比后在比较
            boolean isCandidatePortrait = width > height;
            int maybeFlippedWidth = isCandidatePortrait ? height : width;
            int maybeFlippedHeight = isCandidatePortrait ? width : height;
            double aspectRatio = (double) maybeFlippedWidth / (double) maybeFlippedHeight;
            double distortion = Math.abs(aspectRatio - screenAspectRatio);
            // 找到与屏幕分辨率完全匹配的预览界面分辨率直接返回
            if (maybeFlippedWidth == SCREEN_WIDTH
                    && maybeFlippedHeight == SCREEN_HEIGHT) {
                return supportedPreviewResolution;
            }
            if (distortion > MAX_ASPECT_DISTORTION) {
                it.remove();
            }
        }
        // 如果没有找到合适的，并且还有候选的像素，则设置其中最大比例的，对于配置比较低的机器不太合适
        if (!supportedPreviewResolutions.isEmpty()) {
            return supportedPreviewResolutions.get(0);
        }
        // 没有找到合适的，就返回默认的
        return defaultPreviewResolution;
    }
}
