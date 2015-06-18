package org.bean.jandan.adapter.vh;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import org.bean.jandan.R;
import org.bean.jandan.common.util.ClipboardUtils;
import org.bean.jandan.model.Comment;

/**
 * Created by liuyulong@yixin.im on 2015/4/30.
 */
public class JokeRViewHolder extends RViewHolder<Comment> implements View.OnClickListener {

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

    @Override
    public void refresh(Comment item) {
        super.refresh(item);
        mAuthor.setText(item.getComment_author());
        mDate.setText(item.getComment_date());
        String commentContent = item.getText_content();
        mTextContent.setVisibility(TextUtils.isEmpty(commentContent) ? View.GONE : View.VISIBLE);
        mTextContent.setText(commentContent);
        mTextContent.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mTextContent) {
            ClipboardUtils.copyToClipboard(mContext, mTextContent.getText().toString());
        }
    }
}
