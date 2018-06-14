package com.ylink.fullgoal.cr.surface;

import com.leo.core.iapi.inter.IBolAction;
import com.leo.core.iapi.main.IOnCom;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.cr.core.AddController;
import com.ylink.fullgoal.fg.ImageFg;
import com.ylink.fullgoal.vo.ImageVo;

import java.util.List;

import static com.ylink.fullgoal.config.ComConfig.SHOW;
import static com.ylink.fullgoal.config.ComConfig.UPDATE;

/**
 * 图像集合控制器
 */
public class ImageListController<T extends ImageListController> extends AddController<T, ImageVo> {

    @Override
    public T initDB(ImageVo imageVo) {
        return super.initDB(imageVo);
    }

    @Override
    public T remove(ImageVo imageVo, IOnCom action) {
        return super.remove(imageVo, action);
    }

    /**
     * 处理返回消息
     *
     * @param fg fg
     * @return 本身
     */
    public T initImageFg(String msg, ImageFg fg, IOnCom action) {
        if (!TextUtils.isEmpty(msg) && fg != null) {
            if (fg.isUpload()) {//上传
                executeBol(getData(), item -> {
                    if (TextUtils.equals(msg, item.getPhoto())) {
                        item.setImageID(fg.getImageId());
                        item.setSerialNo(fg.getSerialNo());
                        if (action != null) {
                            action.onCom(0, SHOW, "图片上传成功");
                        }
                        return true;
                    }
                    return false;
                });
            } else if (fg.isDelete()) {//删除
                executeBol(getData(), item -> {
                    if (TextUtils.equals(msg, item.getImageID())) {
                        getData().remove(item);
                        if (action != null) {
                            action.onCom(0, UPDATE, null);
                        }
                        return true;
                    }
                    return false;
                });
            }
        }
        return getThis();
    }

    @Override
    protected ImageVo getFilterDB(String key, ImageVo imageFg) {
        if (imageFg != null && TextUtils.equals(key, imageFg.getInvoiceUse())) {
            return imageFg;
        }
        return null;
    }

    @Override
    public ImageVo getDB() {
        return super.getDB();
    }

    public List<ImageVo> getFilterDBData(int i) {
        return getFilterDBData(ImageVo.getValue(i));
    }

    @Override
    public ImageVo getFilterDB(IBolAction<ImageVo>... args) {
        return super.getFilterDB(args);
    }

    @Override
    protected Class<ImageVo> getClz() {
        return ImageVo.class;
    }

}
