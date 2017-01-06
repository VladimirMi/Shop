package ru.mikhalev.vladimir.mvpshop.data.network.models;

import java.util.Date;

/**
 * Developer Vladimir Mikhalev, 06.01.2017.
 */
public class CommentRes {
    private String id;
    private String avatar;
    private String userName;
    private float rating;
    private Date commentDate;
    private String comment;
    private boolean active;

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
