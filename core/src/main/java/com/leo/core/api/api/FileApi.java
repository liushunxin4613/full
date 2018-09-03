package com.leo.core.api.api;

import android.content.Context;

import com.leo.core.api.main.HasControllerApi;
import com.leo.core.iapi.api.IFileApi;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.util.RunUtil;
import com.leo.core.util.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class FileApi extends HasControllerApi implements IFileApi {

    private final static List<String> ASSETS = new ArrayList<>();

    public static List<String> getAssetsData() {
        return ASSETS;
    }

    public static boolean assetsContains(String assets) {
        return TextUtils.check(assets) && ASSETS.contains(assets);
    }

    public static void assetsAdd(String assets) {
        if (TextUtils.check(assets) && !ASSETS.contains(assets)) {
            ASSETS.add(assets);
        }
    }

    public static List<String> getAssetsData(String dir, String suffix) {
        if (TextUtils.check(dir)) {
            String d = dir.endsWith("/") ? dir : dir + "/";
            String s = TextUtils.isEmpty(suffix) || (suffix.contains(".") && !suffix.startsWith("."))
                    ? null : (suffix.startsWith(".") ? suffix : "." + suffix);
            List<String> data = new ArrayList<>();
            RunUtil.execute(ASSETS, obj -> {
                if (obj.startsWith(d) && (!TextUtils.check(s) ||
                        (TextUtils.check(s) && obj.endsWith(s)))) {
                    data.add(obj);
                }
            });
            return data;
        }
        return null;
    }

    public static void assetsClear() {
        FileApi.ASSETS.clear();
    }

    public FileApi(IControllerApi controllerApi) {
        super(controllerApi);
    }

    public static String getAssetsString(Context context, String assets) {
        if (TextUtils.check(context) && assetsContains(assets)) {
            try {
                InputStreamReader is = new InputStreamReader(
                        context.getAssets().open(assets), "UTF-8");
                BufferedReader bf = new BufferedReader(is);
                String line;
                StringBuilder builder = new StringBuilder();
                while ((line = bf.readLine()) != null) {
                    builder.append(line);
                }
                is.close();
                bf.close();
                return builder.toString();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public String getAssetsString(String assets) {
        return getAssetsString(controllerApi().getContext(), assets);
    }

}