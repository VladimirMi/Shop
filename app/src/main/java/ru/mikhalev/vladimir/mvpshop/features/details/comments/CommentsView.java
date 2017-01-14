package ru.mikhalev.vladimir.mvpshop.features.details.comments;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

import io.realm.RealmList;
import ru.mikhalev.vladimir.mvpshop.core.BaseView;
import ru.mikhalev.vladimir.mvpshop.data.storage.Comment;
import ru.mikhalev.vladimir.mvpshop.databinding.ScreenCommentsBinding;
import ru.mikhalev.vladimir.mvpshop.di.DaggerService;

/**
 * Developer Vladimir Mikhalev 08.01.2017
 */
public class CommentsView extends BaseView<CommentsScreen.CommentsPresenter> {

    private ScreenCommentsBinding mBinding;

    public CommentsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<CommentsScreen.Component>getDaggerComponent(context).inject(this);
    }

    @Override
    protected void initView() {
        mBinding = ScreenCommentsBinding.bind(this);
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    public void initComments(RealmList<Comment> comments) {
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        CommentsAdapter adapter = new CommentsAdapter(getContext(), comments, true);
        mBinding.comments.setLayoutManager(lm);
        mBinding.comments.setAdapter(adapter);
    }
}
