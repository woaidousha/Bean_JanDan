package org.bean.jandan.model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by liuyulong@yixin.im on 2015/4/27.
 */
public class Picture extends Comment {

    private String[] pics;

    public String[] getPics() {
        return pics;
    }

    public void setPics(String[] pics) {
        this.pics = pics;
    }

    public ArrayList<SinglePicture> toSinglePictures() {
        ArrayList<SinglePicture> pictures = new ArrayList<>();
        if (pics == null) {
            return pictures;
        }
        for (String pic : pics) {
            SinglePicture singlePicture = new SinglePicture(comment_ID, comment_post_ID, comment_author,
                    comment_author_email, comment_author_url, comment_author_IP, comment_date, comment_date_gmt, comment_content, comment_karma, comment_approved, comment_agent, comment_type, comment_parent, comment_subscribe, comment_reply_ID, vote_positive, vote_negative, text_content, pic);
            singlePicture.setBaseKey(getBaseKey());
            pictures.add(singlePicture);
        }
        return pictures;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "pics=" + Arrays.toString(pics) +
                "} " + super.toString();
    }
}
