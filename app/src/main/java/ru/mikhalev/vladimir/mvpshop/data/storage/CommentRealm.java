package ru.mikhalev.vladimir.mvpshop.data.storage;

import com.google.common.base.Objects;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import ru.mikhalev.vladimir.mvpshop.data.network.models.CommentRes;

/**
 * Developer Vladimir Mikhalev, 06.01.2017.
 */

public class CommentRealm extends RealmObject {

    @PrimaryKey
    private String id;
    private String userName;
    private String avatar;
    private float rating;
    private Date commentDate;
    private String comment;

    public CommentRealm() {
    }

    public CommentRealm(CommentRes commentRes) {
        this.id = commentRes.getId();
        this.userName = commentRes.getUserName();
        this.avatar = commentRes.getAvatar();
        this.rating = commentRes.getRating();
        this.commentDate = commentRes.getCommentDate();
        this.comment = commentRes.getComment();
    }

    public CommentRealm(float rating, String comment, AccountRealm accountRealm) {
        this.id = String.valueOf(this.hashCode());
        this.userName = accountRealm.getFullName();
        this.avatar = accountRealm.getAvatar();
        this.rating = rating;
        this.commentDate = new Date();
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public float getRating() {
        return rating;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (avatar != null ? avatar.hashCode() : 0);
        result = 31 * result + (rating != +0.0f ? Float.floatToIntBits(rating) : 0);
        result = 31 * result + (commentDate != null ? commentDate.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }
}
