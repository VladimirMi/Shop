package ru.mikhalev.vladimir.mvpshop.features.details.comments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

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

    public void showAddCommentDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = LayoutInflater.from(getContext());
        @SuppressLint("InflateParams") View dialogView = inflater.inflate(R.layout.dialog_comment, null);

        AppCompatRatingBar ratingBar = (AppCompatRatingBar) findViewById(R.id.comment_rating);
        TextInputEditText editText = (TextInputEditText) findViewById(R.id.comment_field);

        alertDialog.setTitle(R.string.comments_dialog_title)
                .setView(dialogView)
                .setPositiveButton("Оставить отзыв", (dialog, which) -> {
                    mPresenter.saveComment(ratingBar.getRating(), editText.getText().toString());
                })
                .setNegativeButton("Отмена", null)
                .show();
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }
}
