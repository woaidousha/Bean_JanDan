package org.bean.jandan.model;

import android.net.Uri;
import android.text.TextUtils;

/**
 * Created by liuyulong@yixin.im on 2015/4/29.
 */
public class SinglePicture extends Comment {

    private String pic;

    public SinglePicture(String comment_ID, String comment_post_ID, String comment_author, String comment_author_email, String comment_author_url, String comment_author_IP, String comment_date, String comment_date_gmt, String comment_content, String comment_karma, String comment_approved, String comment_agent, String comment_type, String comment_parent, String comment_subscribe, String comment_reply_ID, String vote_positive, String vote_negative, String text_content, String pic) {
        this.comment_ID = comment_ID;
        this.comment_post_ID = comment_post_ID;
        this.comment_author = comment_author;
        this.comment_author_email = comment_author_email;
        this.comment_author_url = comment_author_url;
        this.comment_author_IP = comment_author_IP;
        this.comment_date = comment_date;
        this.comment_date_gmt = comment_date_gmt;
        this.comment_content = comment_content;
        this.comment_karma = comment_karma;
        this.comment_approved = comment_approved;
        this.comment_agent = comment_agent;
        this.comment_type = comment_type;
        this.comment_parent = comment_parent;
        this.comment_subscribe = comment_subscribe;
        this.comment_reply_ID = comment_reply_ID;
        this.vote_positive = vote_positive;
        this.vote_negative = vote_negative;
        this.text_content = text_content;
        this.pic = pic;
    }

    public String getPic() {
        return pic;
    }

    public Uri getPicUri() {
        return Uri.parse(getPic());
    }

    public boolean isGif() {
        if (TextUtils.isEmpty(pic)) {
            return false;
        }
        return pic.toLowerCase().endsWith("gif");
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        return TextUtils.equals(((SinglePicture) o).getPic(), getPic());
    }

    @Override
    public String toString() {
        return "SinglePicture{" +
                "comment_ID='" + comment_ID + '\'' +
                ", comment_post_ID='" + comment_post_ID + '\'' +
                ", comment_author='" + comment_author + '\'' +
                ", comment_author_email='" + comment_author_email + '\'' +
                ", comment_author_url='" + comment_author_url + '\'' +
                ", comment_author_IP='" + comment_author_IP + '\'' +
                ", comment_date='" + comment_date + '\'' +
                ", comment_date_gmt='" + comment_date_gmt + '\'' +
                ", comment_content='" + comment_content + '\'' +
                ", comment_karma='" + comment_karma + '\'' +
                ", comment_approved='" + comment_approved + '\'' +
                ", comment_agent='" + comment_agent + '\'' +
                ", comment_type='" + comment_type + '\'' +
                ", comment_parent='" + comment_parent + '\'' +
                ", comment_subscribe='" + comment_subscribe + '\'' +
                ", comment_reply_ID='" + comment_reply_ID + '\'' +
                ", vote_positive='" + vote_positive + '\'' +
                ", vote_negative='" + vote_negative + '\'' +
                ", text_content='" + text_content + '\'' +
                ", pic='" + pic + '\'' +
                '}';
    }
}
