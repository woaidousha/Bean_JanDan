package org.bean.jandan.common;

/**
 * Created by liuyulong@yixin.im on 2015/4/27.
 */
public interface C {

    interface URL {
        /*无聊图*/
        String PIC = "http://jandan.net/?oxwlxojflwblxbsapi=jandan.get_pic_comments";
        String OOXX = "http://jandan.net/?oxwlxojflwblxbsapi=jandan.get_ooxx_comments";

        int FIRST_PAGE = 1;
        String PARAM_PAGE = "page";
    }

    interface UNIT {
        int MB = 1024 * 1024;
    }

    interface DISK_CACHE {
        String CACHE_PATH = "image_cache";
        int MAX_CACHE_SIZE = 300 * UNIT.MB;
        int MAX_CACHE_SIZE_ON_LOW_DISK_SPACE = 100 * UNIT.MB;
        int MAX_CACHE_SIZE_ON_VERY_LOW_DISK_SPACE = 50 * UNIT.MB;
    }

    enum Directory {

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
