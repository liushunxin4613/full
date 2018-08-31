package com.ylink.fullgoal.api.surface;

import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class TestControllerApi<T extends TestControllerApi, C> extends SurfaceControllerApi<T, C> {

    public TestControllerApi(C controller) {
        super(controller);
    }

    /*@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return createAssetsJsonView("view/test_apply.json", container);
    }*/

}