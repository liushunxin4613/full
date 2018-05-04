package com.leo.core.iapi;

/**
 * 尺寸转换接口
 *
 * @param <T> 本身泛型
 * @param <I> 输入数据泛型
 * @param <P> 输入参数泛型
 * @param <R> 输出数据泛型
 */
public interface IDisplayApi<T extends IDisplayApi, I, P, R> extends IBindContextApi<T> {

    /**
     * 获取系统宽度和高度
     *
     * @return 二位数据
     */
    ScreenDisplay getWindowScreenDisplay();

    /**
     * 转换数据
     *
     * @param in    输入数据
     * @param param 输入参数
     * @return 输出数据
     */
    R convert(I in, P param);


    /**
     * 二维数据类
     */
    class ScreenDisplay {
        private int x;
        private int y;

        public ScreenDisplay(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }

}
