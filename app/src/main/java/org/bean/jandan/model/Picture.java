package org.bean.jandan.model;

import java.util.ArrayList;

/**
 * Created by liuyulong@yixin.im on 2015/4/27.
 */
public class Picture {

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
    private String[] pics;

    public String getComment_karma() {
        return comment_karma;
    }

    public void setComment_karma(String comment_karma) {
        this.comment_karma = comment_karma;
    }

    public String getComment_ID() {
        return comment_ID;
    }

    public void setComment_ID(String comment_ID) {
        this.comment_ID = comment_ID;
    }

    public String getComment_post_ID() {
        return comment_post_ID;
    }

    public void setComment_post_ID(String comment_post_ID) {
        this.comment_post_ID = comment_post_ID;
    }

    public String getComment_author() {
        return comment_author;
    }

    public void setComment_author(String comment_author) {
        this.comment_author = comment_author;
    }

    public String getComment_author_email() {
        return comment_author_email;
    }

    public void setComment_author_email(String comment_author_email) {
        this.comment_author_email = comment_author_email;
    }

    public String getComment_author_url() {
        return comment_author_url;
    }

    public void setComment_author_url(String comment_author_url) {
        this.comment_author_url = comment_author_url;
    }

    public String getComment_author_IP() {
        return comment_author_IP;
    }

    public void setComment_author_IP(String comment_author_IP) {
        this.comment_author_IP = comment_author_IP;
    }

    public String getComment_date() {
        return comment_date;
    }

    public void setComment_date(String comment_date) {
        this.comment_date = comment_date;
    }

    public String getComment_date_gmt() {
        return comment_date_gmt;
    }

    public void setComment_date_gmt(String comment_date_gmt) {
        this.comment_date_gmt = comment_date_gmt;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }

    public String getComment_approved() {
        return comment_approved;
    }

    public void setComment_approved(String comment_approved) {
        this.comment_approved = comment_approved;
    }

    public String getComment_agent() {
        return comment_agent;
    }

    public void setComment_agent(String comment_agent) {
        this.comment_agent = comment_agent;
    }

    public String getComment_type() {
        return comment_type;
    }

    public void setComment_type(String comment_type) {
        this.comment_type = comment_type;
    }

    public String getComment_parent() {
        return comment_parent;
    }

    public void setComment_parent(String comment_parent) {
        this.comment_parent = comment_parent;
    }

    public String getComment_subscribe() {
        return comment_subscribe;
    }

    public void setComment_subscribe(String comment_subscribe) {
        this.comment_subscribe = comment_subscribe;
    }

    public String getComment_reply_ID() {
        return comment_reply_ID;
    }

    public void setComment_reply_ID(String comment_reply_ID) {
        this.comment_reply_ID = comment_reply_ID;
    }

    public String getVote_positive() {
        return vote_positive;
    }

    public void setVote_positive(String vote_positive) {
        this.vote_positive = vote_positive;
    }

    public String getVote_negative() {
        return vote_negative;
    }

    public void setVote_negative(String vote_negative) {
        this.vote_negative = vote_negative;
    }

    public String getText_content() {
        return text_content;
    }

    public void setText_content(String text_content) {
        this.text_content = text_content;
    }

    public String[] getPics() {
        return pics;
    }

    public void setPics(String[] pics) {
        this.pics = pics;
    }

    public ArrayList<SinglePicture> toSinglePictures() {
        ArrayList<SinglePicture> pictures = new ArrayList<>();
        for (String pic : pics) {
            SinglePicture singlePicture = new SinglePicture(comment_ID, comment_post_ID, comment_author,
                    comment_author_email, comment_author_url, comment_author_IP, comment_date, comment_date_gmt, comment_content, comment_karma, comment_approved, comment_agent, comment_type, comment_parent, comment_subscribe, comment_reply_ID, vote_positive, vote_negative, text_content, pic);
            pictures.add(singlePicture);
        }
        return pictures;
    }
}
