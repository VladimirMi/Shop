package ru.mikhalev.vladimir.mvpshop.data.network.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import ru.mikhalev.vladimir.mvpshop.data.storage.CommentRealm;

/**
 * Developer Vladimir Mikhalev, 06.01.2017.
 */
public class CommentRes {
    @SerializedName("_id")
    private String id;
    private String avatar;
    private String userName;
    @SerializedName("raiting")
    private float rating;
    private Date commentDate;
    private String comment;
    private boolean active;

    public CommentRes(CommentRealm commentRealm) {
        this.avatar = commentRealm.getAvatar();
        this.userName = commentRealm.getUserName();
        this.rating = commentRealm.getRating();
        this.commentDate = commentRealm.getCommentDate();
        this.comment = commentRealm.getComment();
    }

    public String getId() {
        return id;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getUserName() {
        return userName;
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

    public boolean isActive() {
        return active;
    }
}
