package cn.com.fullgoal.pt0001.test;

import android.os.Bundle;

import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.controllerApi.func.MainControllerApi;

public class TestV2ControllerApi<T extends TestV2ControllerApi, C> extends MainControllerApi<T, C> {

    public TestV2ControllerApi(C controller) {
        super(controller);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        Process process = new Process();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            process.pushFooter((map, pro) -> {
                int key = TextUtils.get(map, int.class, "key");
                switch (key) {
                    case 0:
                        dialog("测试[天] " + finalI, "确定", "取消", (bean, v, dialog) -> {
                            dialog.dismiss();
                            pro.next();
                        }, (bean, v, dialog) -> {
                            dialog.dismiss();
                            pro.put("key", 1);
                            pro.next();
                        });
                        return;
                    case 1:
                        dialog("测试[地] " + finalI, "确定", "取消", (bean, v, dialog) -> {
                            dialog.dismiss();
                            pro.next();
                        }, (bean, v, dialog) -> {
                            dialog.dismiss();
                            pro.put("key", 2);
                            pro.next();
                        });
                        return;
                    case 2:
                        dialog("测试[玄] " + finalI, "确定", "取消", (bean, v, dialog) -> {
                            dialog.dismiss();
                            pro.next();
                        }, (bean, v, dialog) -> {
                            dialog.dismiss();
                            pro.put("key", 3);
                            pro.next();
                        });
                        return;
                    case 3:
                        dialog("测试[黄] " + finalI, "确定", "取消", (bean, v, dialog) -> {
                            dialog.dismiss();
                            pro.next();
                        }, (bean, v, dialog) -> {
                            dialog.dismiss();
                            pro.complete();
                        });
                        return;
                }
                pro.next();
            });
        }
        process.addCompleteAction(map -> ee("action 结束", TextUtils.get(map, int.class, "key")));
        process.next();
        ee("结束");
    }

}