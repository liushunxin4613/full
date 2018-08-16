package com.ylink.fullgoal.json;

import java.util.List;

public class CoreJson {

    private List<InitBean> init;

    public List<InitBean> getInit() {
        return init;
    }

    public void setInit(List<InitBean> init) {
        this.init = init;
    }

    public static class InitBean {
        /**
         * name : report
         * fileName : report.json
         */

        private String name;
        private String fileName;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }
    }

}