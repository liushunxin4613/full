package com.ylink.fullgoal.cr.surface;

import com.leo.core.iapi.inter.IBolAction;
import com.leo.core.iapi.main.IOnCom;
import com.leo.core.util.JavaTypeUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.cr.core.AddController;
import com.ylink.fullgoal.fg.ImageFg;
import com.ylink.fullgoal.vo.ImageVo;
import java.util.List;

import static com.leo.core.util.TextUtils.check;
import static com.leo.core.util.TextUtils.getMoneyString;
import static com.ylink.fullgoal.config.ComConfig.UPDATE;
import static com.ylink.fullgoal.config.ComConfig.UPDATE_MONEY;

/**
 * 图像集合控制器
 */
public class ImageListController<T extends ImageListController> extends AddController<T, ImageVo> {

    private double sum;
    private IOnCom onCom;

    public void setOnCom(IOnCom onCom) {
        this.onCom = onCom;
    }

    @Override
    public List<ImageVo> getData() {
        return super.getData();
    }

    @Override
    public T initDB(ImageVo imageVo) {
        return super.initDB(imageVo);
    }

    @Override
    public T remove(ImageVo imageVo, IOnCom action) {
        return super.remove(imageVo, action);
    }

    @Override
    protected ImageVo onAddDB(ImageVo imageVo) {
        if (!TextUtils.isEmpty(imageVo.getImageURL())) {
            imageVo.setImageURL(imageVo.getImageURL().replaceAll("\\\\", "/"));
        }
        return super.onAddDB(imageVo);
    }

    @Override
    protected void notifyDataChanged() {
        super.notifyDataChanged();
        executeNon(onCom, api -> api.onCom(0, UPDATE_MONEY, getMoneyString(sum())));
    }

    public void remove(String path){
        if(check(path)){
            executeBol(getData(), item -> {
                if(TextUtils.equals(item.getPath(), path)){
                    getData().remove(item);
                    return true;
                }
                return false;
            });
        }
    }

    public void updateMoney(ImageVo imageVo) {
        executeNon(imageVo, vo -> executeBol(getData(), item -> {
            if (TextUtils.equals(item.getImageID(), vo.getImageID())) {
                item.setAmount(imageVo.getAmount());
                notifyDataChanged();
                return true;
            }
            return false;
        }));
    }

    private double sum() {
        sum = 0;
        execute(getData(), item -> sum += JavaTypeUtil.getdouble(item.getAmount(), 0));
        return sum;
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

    public void onError(String msg){
        if(!TextUtils.isEmpty(msg)){
            execute(getData(), obj -> {
                if(TextUtils.equals(obj.getPhoto(), msg)){
                    obj.onError(true);
                }
            });
        }
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

}