package com.leo.core.api;

import com.leo.core.api.main.HasControllerApi;
import com.leo.core.iapi.IFileApi;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.util.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class FileApi extends HasControllerApi implements IFileApi {

    public FileApi(IControllerApi controllerApi) {
        super(controllerApi);
    }

    @Override
    public String getAssetsString(String file) {
        if (!TextUtils.isEmpty(file) && file.contains(".")) {
            try {
                InputStreamReader is = new InputStreamReader(
                        controllerApi().getContext().getAssets().open(file), "UTF-8");
                BufferedReader bf = new BufferedReader(
                        is);
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

}
