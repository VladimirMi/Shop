package ru.mikhalev.vladimir.mvpshop.features.details;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.mikhalev.vladimir.mvpshop.R;

/**
 * Developer Vladimir Mikhalev, 06.01.2017.
 */
public class DetailsAdapter extends PagerAdapter {


    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int layout = 0;
        switch (position) {
            case 0:
                // TODO: 06.01.2017 create screen with scope
                layout = R.layout.screen_description;
                break;
            case 1:
                layout = R.layout.screen_comments;
                break;
        }

        View newView = LayoutInflater.from(container.getContext()).inflate(layout, container, false);
        container.addView(newView);
        return newView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "Описание";
                break;
            case 1:
                title = "Комментарии";
                break;
        }
        return title;
    }
}
