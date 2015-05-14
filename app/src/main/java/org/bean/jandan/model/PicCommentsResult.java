package org.bean.jandan.model;

import org.bean.jandan.common.util.DebugLog;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by liuyulong@yixin.im on 2015/5/14.
 */
public class PicCommentsResult extends Result<PicComment> {

    private List<PicComment> mResults;

    @Override
    public List<PicComment> getResults() {
        return mResults;
    }

    public static PicCommentsResult fromJson(String json) {
        PicCommentsResult result = new PicCommentsResult();
        try {
            JSONObject jo = new JSONObject(json);
            JSONObject parentPosts = jo.getJSONObject(Tag.TAG_PARENTPOSTS);
            List<PicComment> comments = new ArrayList<>();
            Iterator keyIter = parentPosts.keys();
            while (keyIter.hasNext()) {
                try {
                    String key = (String)keyIter.next();
                    JSONObject post = parentPosts.getJSONObject(key);
                    JSONObject authorJo = post.getJSONObject(Tag.TAG_AUTHOR);
                    PicComment picComment = new PicComment();
                    PicComment.Author author = picComment.new Author();
                    picComment.author = author;
                    picComment.message = post.getString(Tag.TAG_MESSAGE);
                    picComment.created_at = post.getString(Tag.TAG_CREATE_AT);
                    author.user_id = authorJo.getString(Tag.TAG_AUTHOR_USER_ID);
                    author.name = authorJo.getString(Tag.TAG_AUTHOR_NAME);
                    author.url = authorJo.getString(Tag.TAG_AUTHOR_URL);
                    author.avatar_url = authorJo.getString(Tag.TAG_AUTHOR_AVATAR_URL);
                    comments.add(picComment);
                    DebugLog.e("commentsize :" + comments.size());
                } catch (Exception e) {
                    e.printStackTrace();
                    DebugLog.e(e.getMessage());
                }
            }
            result.mResults = comments;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    interface Tag {
        String TAG_PARENTPOSTS = "parentPosts";
        String TAG_MESSAGE = "message";
        String TAG_CREATE_AT = "created_at";

        String TAG_AUTHOR = "author";
        String TAG_AUTHOR_USER_ID = "user_id";
        String TAG_AUTHOR_NAME = "name";
        String TAG_AUTHOR_URL = "url";
        String TAG_AUTHOR_AVATAR_URL = "avatar_url";
    }


}
