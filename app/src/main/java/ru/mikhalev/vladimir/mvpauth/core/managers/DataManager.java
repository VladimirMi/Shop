package ru.mikhalev.vladimir.mvpauth.core.managers;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.mikhalev.vladimir.mvpauth.catalog.ProductDto;
import ru.mikhalev.vladimir.mvpauth.core.App;
import ru.mikhalev.vladimir.mvpauth.core.di.DaggerService;
import ru.mikhalev.vladimir.mvpauth.core.di.components.DataManagerComponent;
import ru.mikhalev.vladimir.mvpauth.core.di.modules.LocaleModule;
import ru.mikhalev.vladimir.mvpauth.core.di.modules.NetworkModule;
import ru.mikhalev.vladimir.mvpauth.core.network.api.RestService;

public class DataManager {
    @Inject
    PreferencesManager mPreferencesManager;
    @Inject
    RestService mRestService;


    private List<ProductDto> mMockProductList = new ArrayList<>();

    public DataManager() {
        DaggerService.getComponent(DataManagerComponent.class,
                App.getAppComponent(),
                new LocaleModule(),
                new NetworkModule()).inject(this);
        generateMockData();
    }

    public PreferencesManager getPreferencesManager() {
        return mPreferencesManager;
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
        mMockProductList.add(new ProductDto(1, "LG MS-2595GIS", "http://mdata.yandex.net/i?path=b1017020219_img_id151065237584951211.jpeg&size=9", "Отдельно стоящая микроволновая печь", 12299, 1));
        mMockProductList.add(new ProductDto(2, "Microsoft Xbox One 500 ГБ", "http://mdata.yandex.net/i?path=b0315200439_img_id8312843205844878861.jpeg&size=9", "Стационарная игровая приставка", 19989, 1));
        mMockProductList.add(new ProductDto(3, "Samsung UE32J4000AU", "http://mdata.yandex.net/i?path=b0528113945_img_id383163117745284423.jpeg&size=9", "ЖК-телевизор, 720p HD", 17989, 1));
        mMockProductList.add(new ProductDto(4, "Palit GeForce GTX 1060 1506Mhz PCI-E 3.0 6144Mb 8000Mhz 192 bit DVI HDMI HDCP", "http://mdata.yandex.net/i?path=b0720170635_img_id894639973318463136.jpeg&size=9", "Видеокарта NVIDIA GeForce GTX 1060", 20990, 1));
        mMockProductList.add(new ProductDto(5, "Xiaomi Redmi 3", "http://mdata.yandex.net/i?path=b0201213211_img_id8890851960327700873.jpeg", "Смартфон, Android 5.1", 13559, 1));
        mMockProductList.add(new ProductDto(6, "HGST HUC156060CSS204", "http://mdata.yandex.net/i?path=b1221154653_img_id8182254918959210372.jpeg&size=9", "Жесткий диск для сервера", 25980, 1));
        mMockProductList.add(new ProductDto(7, "Smart Balance Wheel SUV 10", "http://mdata.yandex.net/i?path=b0721165228_img_id1146336079498735060.jpeg&size=9", "Гироскутер", 12400, 1));
        mMockProductList.add(new ProductDto(8, "Makita HR2450", "http://mdata.yandex.net/i?path=b1221162808_img_id7519836334746001621.jpeg", "Перфоратор мощностью 780 Вт", 7206, 1));
        mMockProductList.add(new ProductDto(9, "Casio GWG-1000-1A3", "http://mdata.yandex.net/i?path=b0908180932_img_id3349079021650035138.jpeg", "Кварцевые наручные часы", 57490, 1));
        mMockProductList.add(new ProductDto(10, "Рекурсивный арбалет Man Kung MK-80 A4AL Оса", "http://cdn.e96.ru/assets/images/catalog/bows_crossbows_slingshots/arbalety/589396/man-kung-mk-80-a4al-osa_2092141.jpg", "Арбалет-пистолет \\\"Оса\\\" MK-80 A4AL (алюминий, черный, с рычагом)", 2390, 1));

    }
}
