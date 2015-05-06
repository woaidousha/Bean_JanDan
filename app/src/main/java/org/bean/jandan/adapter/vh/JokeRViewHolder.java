package org.bean.jandan.adapter.vh;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import org.bean.jandan.R;
import org.bean.jandan.model.Comment;

/**
 * Created by liuyulong@yixin.im on 2015/4/30.
 */
public class JokeRViewHolder extends RViewHolder {

    private TextView mAuthor;
    private TextView mDate;
    private TextView mTextContent;

    public JokeRViewHolder(View mView) {
        super(mView);
    }

    @Override
    public void inflate(View view) {
        mAuthor = (TextView) view.findViewById(R.id.author);
        mDate = (TextView) view.findViewById(R.id.date);
        mTextContent = (TextView) view.findViewById(R.id.text_content);
    }

    public void refresh(Object item) {
        Comment comment = (Comment) item;
        mAuthor.setText(comment.getComment_author());
        mDate.setText(comment.getComment_date());
        String commentContent = comment.getText_content();
        mTextContent.setVisibility(TextUtils.isEmpty(commentContent) ? View.GONE : View.VISIBLE);
        mTextContent.setText(commentContent);
    }

}
