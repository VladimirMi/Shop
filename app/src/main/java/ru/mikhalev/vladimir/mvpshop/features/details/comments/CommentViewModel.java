package ru.mikhalev.vladimir.mvpshop.features.details.comments;

import android.databinding.ObservableField;
import android.databinding.ObservableFloat;

import ru.mikhalev.vladimir.mvpshop.core.BaseViewModel;
import ru.mikhalev.vladimir.mvpshop.data.storage.Comment;

/**
 * Developer Vladimir Mikhalev, 14.01.2017.
 */

public class CommentViewModel extends BaseViewModel {

    public final ObservableField<String> avatar = new ObservableField<>();
    public final ObservableField<String> userName = new ObservableField<>();
    public final ObservableField<String> comment = new ObservableField<>();
    public final ObservableField<String> date = new ObservableField<>();
    public final ObservableFloat rating = new ObservableFloat();


    public CommentViewModel(Comment comment) {
        avatar.set(comment.getAvatar());
        userName.set(comment.getUserName());
        this.comment.set(comment.getComment());
        rating.set(comment.getRating());
    }
}
