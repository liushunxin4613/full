package com.ylink.fullgoal.api.surface;

import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class TestControllerApi<T extends TestControllerApi, C> extends SurfaceControllerApi<T, C> {

    public TestControllerApi(C controller) {
        super(controller);
    }

    @Override
    public void initView() {
        super.initView();


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