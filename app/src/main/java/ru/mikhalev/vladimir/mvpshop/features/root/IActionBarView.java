package ru.mikhalev.vladimir.mvpshop.features.root;

import android.support.v4.view.ViewPager;

import java.util.List;

/**
 * Developer Vladimir Mikhalev, 05.01.2017.
 */

public interface IActionBarView {
    void setToolbarVisible(boolean isVisible);

    void setTitle(String title);

    void setBackArrow(boolean enabled);

    void setMenuItems(List<MenuItemHolder> items);

    void setTabLayout(ViewPager pager);

    void removeTabLayout();
}
