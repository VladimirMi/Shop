package ru.mikhalev.vladimir.mvpshop.data.dto;

/**
 * Developer Vladimir Mikhalev 06.12.2016
 */

public class PermissionResultDto {
    private int requestCode;
    private boolean granted;


    public PermissionResultDto(int requestCode, boolean granted) {
        this.requestCode = requestCode;
        this.granted = granted;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public boolean isGranted() {
        return granted;
    }
}
