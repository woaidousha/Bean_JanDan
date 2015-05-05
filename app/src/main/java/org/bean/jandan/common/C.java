package org.bean.jandan.common;

/**
 * Created by liuyulong@yixin.im on 2015/4/27.
 */
public interface C {

    public static interface URL {
        /*无聊图*/
        public static final String PIC = "http://jandan.net/?oxwlxojflwblxbsapi=jandan.get_pic_comments";
        public static final String OOXX = "http://jandan.net/?oxwlxojflwblxbsapi=jandan.get_ooxx_comments";

        public static final int FIRST_PAGE = 1;
        public static final String PARAM_PAGE = "page";
    }

    public static interface UNIT {
        public static final int MB = 1024 * 1024;
    }

    public static interface DISK_CACHE {
        public static final String CACHE_PATH = "image_cache";
        public static final int MAX_CACHE_SIZE = 300 * UNIT.MB;
        public static final int MAX_CACHE_SIZE_ON_LOW_DISK_SPACE = 100 * UNIT.MB;
        public static final int MAX_CACHE_SIZE_ON_VERY_LOW_DISK_SPACE = 50 * UNIT.MB;
    }

    public static enum Directory {

        SHARE("share", false);

        public static final String APP_DIRECTORY_NAME = "BeanJD";

        String path;
        boolean storageByMD5;

        Directory(String path, boolean storageByMD5) {
            this.path = path;
            this.storageByMD5 = storageByMD5;
        }

        public String getStoragePath() {
            return "/" + path + "/";
        }

        public boolean isStorageByMD5() {
            return storageByMD5;
        }
    }
}
