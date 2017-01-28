package ru.mikhalev.vladimir.mvpshop.features.details;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mortar.MortarScope;
import ru.mikhalev.vladimir.mvpshop.R;
import ru.mikhalev.vladimir.mvpshop.core.BaseScreen;
import ru.mikhalev.vladimir.mvpshop.features.details.comments.CommentsScreen;
import ru.mikhalev.vladimir.mvpshop.features.details.description.DescriptionScreen;
import ru.mikhalev.vladimir.mvpshop.mortar.ScreenScoper;

/**
 * Developer Vladimir Mikhalev, 06.01.2017.
 */
public class DetailsAdapter extends PagerAdapter {


    private final String mProductId;
    private final String[] mTabsTitles;

    public DetailsAdapter(Context context, String id) {
        mProductId = id;
        mTabsTitles = context.getResources().getStringArray(R.array.details_tabs);
    }

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
        BaseScreen screen;
        if (position == 0) {
            screen = new DescriptionScreen(mProductId);
        } else {
            screen = new CommentsScreen(mProductId);
        }

        MortarScope scope = ScreenScoper.createScreenScopeFromContext(container.getContext(), screen, null);
        Context screenContext = scope.createContext(container.getContext());

        View newView = LayoutInflater.from(screenContext).inflate(screen.getLayoutResId(), container, false);
        container.addView(newView);
        return newView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabsTitles[position];
    }
}
