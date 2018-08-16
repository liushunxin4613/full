package com.ylink.fullgoal.json;

import java.util.List;

public class ReportJson {

    /**
     * filter : {"one":["权益专户投资部","权益投资部","固定收益研究部","固定收益投资部","固定收益专户投资部","养老金投资部","量化投资部","海外权益投资部"],"all":["权益研究部"]}
     */

    private FilterBean filter;

    public FilterBean getFilter() {
        return filter;
    }

    public void setFilter(FilterBean filter) {
        this.filter = filter;
    }

    public static class FilterBean {
        private List<String> one;
        private List<String> all;

        public List<String> getOne() {
            return one;
        }

        public void setOne(List<String> one) {
            this.one = one;
        }

        public List<String> getAll() {
            return all;
        }

        public void setAll(List<String> all) {
            this.all = all;
        }
    }

}