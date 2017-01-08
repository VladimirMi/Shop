package ru.mikhalev.vladimir.mvpshop.features.details.comments;

import android.content.Context;
import android.util.AttributeSet;

import ru.mikhalev.vladimir.mvpshop.core.BaseView;
import ru.mikhalev.vladimir.mvpshop.core.BaseViewModel;
import ru.mikhalev.vladimir.mvpshop.di.DaggerService;

/**
 * Developer Vladimir Mikhalev 08.01.2017
 */
public class CommentsView extends BaseView<CommentsScreen.CommentsPresenter> {

    public CommentsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<CommentsScreen.Component>getDaggerComponent(context).inject(this);
    }

    @Override
    protected void initBinding() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void setViewModel(BaseViewModel viewModel) {

    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }
}
