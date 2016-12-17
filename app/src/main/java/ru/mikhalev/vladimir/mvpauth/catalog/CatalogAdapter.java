package ru.mikhalev.vladimir.mvpauth.catalog;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import mortar.MortarScope;
import ru.mikhalev.vladimir.mvpauth.R;
import timber.log.Timber;

/**
 * Developer Vladimir Mikhalev, 29.10.2016.
 */

public class CatalogAdapter extends PagerAdapter {
    private List<ProductDto> mProductList = new ArrayList<>();

    public CatalogAdapter() {

    }

    @Override
    public int getCount() {
        return mProductList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    public void addItem(ProductDto productDto) {
        mProductList.add(productDto);
        notifyDataSetChanged();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ProductDto productDto = mProductList.get(position);
        Context productContext = CatalogScreen.Factory.createProductContext(productDto, container.getContext());
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
