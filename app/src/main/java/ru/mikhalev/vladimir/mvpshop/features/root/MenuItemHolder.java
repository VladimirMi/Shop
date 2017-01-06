package ru.mikhalev.vladimir.mvpshop.features.root;

import android.view.MenuItem;

/**
 * Developer Vladimir Mikhalev, 05.01.2017.
 */
public class MenuItemHolder {
    private final String itemTitle;
    private final int iconResId;
    private final MenuItem.OnMenuItemClickListener listener;

    public MenuItemHolder(String itemTitle, int iconResId, MenuItem.OnMenuItemClickListener listener) {
        this.itemTitle = itemTitle;
        this.iconResId = iconResId;
        this.listener = listener;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public int getIconResId() {
        return iconResId;
    }

    public MenuItem.OnMenuItemClickListener getListener() {
        return listener;
    }
}
