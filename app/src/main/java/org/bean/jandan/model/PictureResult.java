package org.bean.jandan.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuyulong@yixin.im on 2015/4/28.
 */
public class PictureResult extends CommentsResult<Picture, SinglePicture> {

    @Override
    public List<SinglePicture> transfor() {
        ArrayList<SinglePicture> pictures = new ArrayList<>();
        for (Picture picture : getComments()) {
            pictures.addAll(picture.toSinglePictures());
        }
        return pictures;
    }
}
