package org.bean.jandan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.bean.jandan.R;
import org.bean.jandan.common.C;
import org.bean.jandan.fragment.CommentsFragment;

/**
 * Created by liuyulong@yixin.im on 2015/5/14.
 */
public class CommentsActivity extends BaseColorActivity {

    public static void start(Context context, String commentId) {
        Intent intent = new Intent(context, CommentsActivity.class);
        intent.putExtra(C.Extra.TAG_COMMENT_ID, commentId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (!intent.hasExtra(C.Extra.TAG_COMMENT_ID)) {
            finish();
            return;
        }
        Bundle argument = new Bundle();
        argument.putString(C.Extra.TAG_COMMENT_ID, intent.getStringExtra(C.Extra.TAG_COMMENT_ID));
        CommentsFragment fragment = new CommentsFragment();
        fragment.setArguments(argument);
        getSupportFragmentManager().beginTransaction()
                                   .add(R.id.container, fragment)
                                   .commit();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.comments_activity_layout;
    }
}
