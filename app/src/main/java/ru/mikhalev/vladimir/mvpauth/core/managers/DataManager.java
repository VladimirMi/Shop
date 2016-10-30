package ru.mikhalev.vladimir.mvpauth.core.managers;


import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ru.mikhalev.vladimir.mvpauth.catalog.product.ProductDto;
import ru.mikhalev.vladimir.mvpauth.core.MvpApplication;

public class DataManager {
    private static DataManager ourInstance = new DataManager();
    private PreferencesManager mPreferencesManager;
    private Context mAppContext;
    private List<ProductDto> mMockProductList = new ArrayList<>();

    private DataManager() {
        mPreferencesManager = new PreferencesManager();
        mAppContext = MvpApplication.getAppContext();
        generateMockData();
    }

    public static DataManager getInstance() {
        return ourInstance;
    }

    public PreferencesManager getPreferencesManager() {
        return mPreferencesManager;
    }

    public Context getAppContext() {
        return mAppContext;
    }


    public boolean isAuthUser() {
//        return !mPreferencesManager.getAuthToken().equals(ConstantManager.INVALID_TOKEN);
        return false;
    }

    public void loginUser(String email, String password) {
        // TODO: 10/22/16 implement auth
    }

    public ProductDto getProductById(int productId) {
        // TODO: 27-Oct-16 temp sample mock data fix me (maybe load from db)
        return mMockProductList.get(productId-1);
    }

    public void updateProduct(ProductDto product) {
        // TODO: 27-Oct-16 update product count or status (something in product) save in DB
    }

    public List<ProductDto> getProductList() {
        // TODO: 27-Oct-16 load product list from anywhere
        return mMockProductList;
    }

    private void generateMockData() {
        mMockProductList.add(new ProductDto(1, "test 1", "imageUrl", "description 1, description 1,, description 1, description 1, description 1, description 1, description 1, description 1, description 1, description 1, description 1, description 1, description 1", 100, 1));
        mMockProductList.add(new ProductDto(2, "test 2", "imageUrl", "description 1, description 1, description 1, description 1, description 1", 200, 1));
        mMockProductList.add(new ProductDto(3, "test 3", "imageUrl", "description 1, description 1, description 1, description 1, description 1", 300, 1));
        mMockProductList.add(new ProductDto(4, "test 4", "imageUrl", "description 1, description 1, description 1, description 1, description 1", 400, 1));
        mMockProductList.add(new ProductDto(5, "test 5", "imageUrl", "description 1, description 1, description 1, description 1, description 1", 500, 1));
        mMockProductList.add(new ProductDto(6, "test 6", "imageUrl", "description 1, description 1, description 1, description 1, description 1", 600, 1));
        mMockProductList.add(new ProductDto(7, "test 7", "imageUrl", "description 1, description 1, description 1, description 1, description 1", 700, 1));
        mMockProductList.add(new ProductDto(8, "test 8", "imageUrl", "description 1, description 1, description 1, description 1, description 1", 800, 1));
        mMockProductList.add(new ProductDto(9, "test 9", "imageUrl", "description 1, description 1, description 1, description 1, description 1", 900, 1));
        mMockProductList.add(new ProductDto(10, "test 10", "imageUrl", "description 1, description 1, description 1, description 1, description 1", 1000, 1));
        mMockProductList.add(new ProductDto(11, "test 11", "imageUrl", "description 1, description 1, description 1, description 1, description 1", 1100, 1));

    }
}
