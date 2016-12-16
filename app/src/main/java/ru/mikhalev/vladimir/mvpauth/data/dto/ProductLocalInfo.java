package ru.mikhalev.vladimir.mvpauth.data.dto;

/**
 * Developer Vladimir Mikhalev 15.12.2016
 */

public class ProductLocalInfo {
    private int remoteId;
    private boolean favorite;
    private int count;

    public ProductLocalInfo(int remoteId, boolean favorite, int count) {
        this.remoteId = remoteId;
        this.favorite = favorite;
        this.count = count;
    }

    public int getRemoteId() {
        return remoteId;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public int getCount() {
        return count;
    }

    public void addCount() {
        count++;
    }

    public void deleteCount() {
        count--;
    }
}
