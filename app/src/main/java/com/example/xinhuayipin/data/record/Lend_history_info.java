package com.example.xinhuayipin.data.record;

/**
 * @Author skygge.
 * @Date on 2019-09-10.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public class Lend_history_info {

        private String coverage;
        private String title;
        private String creator;
        private long lend_time;
        private long return_time;
        public void setCoverage(String coverage) {
            this.coverage = coverage;
        }
        public String getCoverage() {
            return coverage;
        }

        public void setTitle(String title) {
            this.title = title;
        }
        public String getTitle() {
            return title;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }
        public String getCreator() {
            return creator;
        }

        public void setLend_time(long lend_time) {
            this.lend_time = lend_time;
        }
        public long getLend_time() {
            return lend_time;
        }

        public void setReturn_time(long return_time) {
            this.return_time = return_time;
        }
        public long getReturn_time() {
            return return_time;
        }

    }
