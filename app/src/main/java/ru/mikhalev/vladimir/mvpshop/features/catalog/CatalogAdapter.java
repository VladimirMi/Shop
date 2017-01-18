package ru.mikhalev.vladimir.mvpshop.features.catalog;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;
import mortar.MortarScope;
import ru.mikhalev.vladimir.mvpshop.R;
import ru.mikhalev.vladimir.mvpshop.data.storage.ProductRealm;
import timber.log.Timber;

/**
 * Developer Vladimir Mikhalev, 29.10.2016.
 */

public class CatalogAdapter extends PagerAdapter {
    private List<ProductRealm> mProductRealmList = new ArrayList<>();

    public CatalogAdapter() {
    }

    @Override
    public int getCount() {
        return mProductRealmList.size();
    }

    public void updateData(RealmResults<ProductRealm> productRealms) {
        mProductRealmList = productRealms;
        notifyDataSetChanged();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ProductRealm productRealm = mProductRealmList.get(position);
        Context productContext = CatalogScreen.Factory.createProductContext(productRealm, container.getContext());
        View newView = LayoutInflater.from(productContext).inflate(R.layout.screen_product, container, false);
        container.addView(newView);
        return newView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        MortarScope screenScope = MortarScope.getScope(((View) object).getContext());
        screenScope.destroy();
        container.removeView((View) object);
        Timber.e("destroyItem");
    }


}
