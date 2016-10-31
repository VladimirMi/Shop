package ru.mikhalev.vladimir.mvpauth.catalog;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import ru.mikhalev.vladimir.mvpauth.catalog.product.ProductDto;
import ru.mikhalev.vladimir.mvpauth.catalog.product.ProductFragment;

/**
 * Developer Vladimir Mikhalev, 29.10.2016.
 */

public class CatalogAdapter extends FragmentPagerAdapter {
    private List<ProductDto> mProductList = new ArrayList<>();

    public CatalogAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ProductFragment.newInstance(mProductList.get(position));
    }

    @Override
    public int getCount() {
        return mProductList.size();
    }

    public void addItem(ProductDto product) {
        mProductList.add(product);
        notifyDataSetChanged();
    }
}