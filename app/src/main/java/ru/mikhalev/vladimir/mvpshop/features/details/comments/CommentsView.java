package ru.mikhalev.vladimir.mvpshop.features.details.comments;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import io.realm.RealmList;
import ru.mikhalev.vladimir.mvpshop.R;
import ru.mikhalev.vladimir.mvpshop.core.BaseView;
import ru.mikhalev.vladimir.mvpshop.data.storage.CommentRealm;
import ru.mikhalev.vladimir.mvpshop.di.DaggerService;

/**
 * Developer Vladimir Mikhalev 08.01.2017
 */
public class CommentsView extends BaseView<CommentsScreen.CommentsPresenter> {

    private CommentsAdapter mAdapter;

    public CommentsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<CommentsScreen.Component>getDaggerComponent(context).inject(this);
    }

    @Override
    protected void initView() {
        initCommentsList();
    }

    private void initCommentsList() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.comments);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        mAdapter = new CommentsAdapter(getContext(), null, false);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(mAdapter);
    }

    public void updateComments(RealmList<CommentRealm> commentRealms) {
        mAdapter.updateData(commentRealms);
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }
}
