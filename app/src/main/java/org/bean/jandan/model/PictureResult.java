package org.bean.jandan.model;

import java.util.ArrayList;

/**
 * Created by liuyulong@yixin.im on 2015/4/28.
 */
public class PictureResult extends PageResult {
    private ArrayList<Picture> comments;

    public ArrayList<Picture> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Picture> comments) {
        this.comments = comments;
    }

    public ArrayList<SinglePicture> getPictures() {
        ArrayList<SinglePicture> pictures = new ArrayList<>();
        for (Picture picture : comments) {
            pictures.addAll(picture.toSinglePictures());
        }
        return pictures;
    }
}
