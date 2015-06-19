package org.bean.jandan.model;

import org.bean.jandan.common.cache.Cacheable;
import org.bean.jandan.common.util.StringUtil;

/**
 * Created by liuyulong@yixin.im on 2015/5/6.
 */
public class Post implements Cacheable<String> {

    String id;
    String url;
    String title;
    String date;
    Tags[] tags;
    Author author;
    int comment_count;
    CustomFields custom_fields;

    private String baseKey;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return StringUtil.replaceBlank(title);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Tags[] getTags() {
        return tags;
    }

    public void setTags(Tags[] tags) {
        this.tags = tags;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public CustomFields getCustom_fields() {
        return custom_fields;
    }

    public void setCustom_fields(CustomFields custom_fields) {
        this.custom_fields = custom_fields;
    }

    @Override
    public void setBaseKey(String key) {
        this.baseKey = key;
    }

    @Override
    public String getBaseKey() {
        return baseKey;
    }

    class Tags {
        String id;
        String slug;
        String title;
        String description;
        int post_count;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getPost_count() {
            return post_count;
        }

        public void setPost_count(int post_count) {
            this.post_count = post_count;
        }
    }

    public class Author {
        String id;
        String slug;
        String name;
        String nickname;
        String url;
        String description;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public class CustomFields {
        String[] thumb_c;
        String[] views;

        public String[] getThumb_c() {
            return thumb_c;
        }

        public void setThumb_c(String[] thumb_c) {
            this.thumb_c = thumb_c;
        }

        public String[] getViews() {
            return views;
        }

        public void setViews(String[] views) {
            this.views = views;
        }

        public String getFirstThumb() {
            return thumb_c == null || thumb_c.length == 0 ? "" : thumb_c[0];
        }
    }
}
