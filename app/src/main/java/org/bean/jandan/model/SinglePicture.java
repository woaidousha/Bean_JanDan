package org.bean.jandan.model;

import android.net.Uri;
import android.text.TextUtils;

import org.bean.jandan.common.util.StringUtil;

/**
 * Created by liuyulong@yixin.im on 2015/4/29.
 */
public class SinglePicture {
    private String comment_ID;
    private String comment_post_ID;
    private String comment_author;
    private String comment_author_email;
    private String comment_author_url;
    private String comment_author_IP;
    private String comment_date;
    private String comment_date_gmt;
    private String comment_content;
    private String comment_karma;
    private String comment_approved;
    private String comment_agent;
    private String comment_type;
    private String comment_parent;
    private String comment_subscribe;
    private String comment_reply_ID;
    private String vote_positive;
    private String vote_negative;
    private String text_content;
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

    public String getComment_ID() {
        return comment_ID;
    }

    public String getComment_post_ID() {
        return comment_post_ID;
    }

    public String getComment_author() {
        return comment_author;
    }

    public String getComment_author_email() {
        return comment_author_email;
    }

    public String getComment_author_url() {
        return comment_author_url;
    }

    public String getComment_author_IP() {
        return comment_author_IP;
    }

    public String getComment_date() {
        return comment_date;
    }

    public String getComment_date_gmt() {
        return comment_date_gmt;
    }

    public String getComment_content() {
        return comment_content;
    }

    public String getComment_karma() {
        return comment_karma;
    }

    public String getComment_approved() {
        return comment_approved;
    }

    public String getComment_agent() {
        return comment_agent;
    }

    public String getComment_type() {
        return comment_type;
    }

    public String getComment_parent() {
        return comment_parent;
    }

    public String getComment_subscribe() {
        return comment_subscribe;
    }

    public String getComment_reply_ID() {
        return comment_reply_ID;
    }

    public String getVote_positive() {
        return vote_positive;
    }

    public String getVote_negative() {
        return vote_negative;
    }

    public String getText_content() {
        return StringUtil.replaceBlank(text_content);
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
