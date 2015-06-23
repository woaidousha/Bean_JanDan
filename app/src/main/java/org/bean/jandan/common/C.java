package org.bean.jandan.common;

/**
 * Created by liuyulong@yixin.im on 2015/4/27.
 */
public interface C {

    interface URL {

        interface PIC{
            /*无聊图*/
            String WLT = "http://jandan.net/?oxwlxojflwblxbsapi=jandan.get_pic_comments";
            /*妹子图*/
            String OOXX = "http://jandan.net/?oxwlxojflwblxbsapi=jandan.get_ooxx_comments";

            //评论列表
            public static final String URL_COMMENT_LIST = "http://jandan.duoshuo.com/api/threads/listPosts" +
                    ".json?thread_key=comment-";
            //发表评论
            public static final String URL_PUSH_COMMENT = "http://jandan.duoshuo.com/api/posts/create.json";
        }

        String URL_COMMENT_COUNTS = "http://jandan.duoshuo.com/api/threads/counts.json?threads=";
        /*评论列表*/
        String COMMENTS = "http://jandan.net/?oxwlxojflwblxbsapi=get_post&include=comments&id=";

        /*发表评论*/
        String PUSH_COMMENT = "http://jandan.net/?oxwlxojflwblxbsapi=respond.submit_comment";

        /*新鲜事列表*/
        String POSTS = "http://jandan.net/?oxwlxojflwblxbsapi=get_recent_posts&include=url,date,tags,author," +
                "title,comment_count,custom_fields&custom_fields=thumb_c,views&dev=1";

        /*新鲜事详情*/
        String FRESH_NEWS_DETAIL = "http://i.jandan.net/?oxwlxojflwblxbsapi=get_post&include=content&id=";

        //评论列表
        public static final String URL_COMMENTS = "http://jandan.net/?oxwlxojflwblxbsapi=get_post&include=comments&id=";
        //对新鲜事发表评论
        public static final String URL_PUSH_COMMENT ="http://jandan.net/?oxwlxojflwblxbsapi=respond.submit_comment";

        /*段子*/
        String DUANZI = "http://jandan.net/?oxwlxojflwblxbsapi=jandan.get_duan_comments";

        /*视频*/
        String VIDEOS = "http://jandan.net/?oxwlxojflwblxbsapi=jandan.get_video_comments";

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

        SHARE("share", false),
        LOG("log", false);

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

    interface Extra {
        String TAG_SINGLE_PICTURE = "single_picture";
        String TAG_COMMENT_ID = "comment_id";
        String KEY_URL = "URL";
        String KEY_TITLE = "TITLE";
    }
}
