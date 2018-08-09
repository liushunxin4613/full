package com.leo.core.factory;

import com.leo.core.iapi.api.IDataApi;
import com.leo.core.util.TextUtils;

public class ImageFactory {

    private static ImageFactory instance;
    public static ImageFactory getInstance() {
        if (instance == null) {
            synchronized (ImageFactory.class) {
                if (instance == null) {
                    instance = new ImageFactory();
                }
            }
        }
        return instance;
    }

    private String getName(Object res){
        if(res instanceof String){
            return (String) res;
        } else if(res != null){
            return String.valueOf(res);
        }
        return null;
    }

    private float getRotate(float rotate, float offset){
        rotate = (rotate + offset) % 360;
        return rotate >= 0 ? rotate : rotate + 360;
    }

    public float getRotate(Object res){
        return getRotate(res, 0);
    }

    public float getRotate(Object res, float offset){
        Float f = null;
        String name = getName(res);
        if(TextUtils.check(name)){
            IDataApi dataApi = DataFactory.getApi();
            if(dataApi != null){
                f = (Float) dataApi.getBean(name, Float.class);
            }
        }
        return getRotate(f == null ? 0 : f, offset);
    }

    public void save(Object res, float rotate){
        String name = getName(res);
        if(TextUtils.check(name)){
            IDataApi dataApi = DataFactory.getApi();
            if(dataApi != null){
                dataApi.saveData(name, rotate);
            }
        }
    }

}
