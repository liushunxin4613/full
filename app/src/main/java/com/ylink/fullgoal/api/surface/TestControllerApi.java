package com.ylink.fullgoal.api.surface;

import com.leo.core.util.FileUtil;
import com.ylink.fullgoal.config.JsonHelper;
import com.ylink.fullgoal.config.Node;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

import java.io.File;

public class TestControllerApi<T extends TestControllerApi, C> extends SurfaceControllerApi<T, C> {

    public TestControllerApi(C controller) {
        super(controller);
    }

    @Override
    public void initView() {
        super.initView();
//        getRootDir("config")
        File configDir = getRootDir("config");
        if (configDir.exists() && configDir.isDirectory()) {
            for (File file : configDir.listFiles()) {
                ee("file", file.getPath());
            }
        }
        /*String config = FileUtil.readFile(new File(configDir, "config.json"));
        JsonHelper.newBuilder()
                .setRoot(new Node("view"),
                        new Node("data", "name", "l_view.xml"),
                        new Node("data", "code", "1"))
                .addChild(String.class, key -> {
                    ee("key", key);
                }, new Node("key", "vId", "tv1"))
                .execute(config);*/

    }

    /*@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        XmlResourceParser parser = null;
        try {
            parser = getContext().getResources().getAssets()
                    .openXmlResourceParser("assets/l_view.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (parser != null) {
            setRootView(inflater.inflate(parser, container));
        }
        return getRootView();
    }*/

}