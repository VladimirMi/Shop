package ru.mikhalev.vladimir.mvpauth.catalog;

import java.util.List;

import ru.mikhalev.vladimir.mvpauth.core.layers.model.BaseModel;
import ru.mikhalev.vladimir.mvpauth.data.dto.ProductLocalInfo;
import ru.mikhalev.vladimir.mvpauth.data.dto.ProductRes;
import rx.Observable;

/**
 * Developer Vladimir Mikhalev, 29.10.2016.
 */

public class CatalogModel extends BaseModel {

    public boolean isUserAuth() {
//        return mDataManager.getAuthTokenPref();
        return false;
    }

    public void updateProduct(ProductRes productRes) {
        mDataManager.updateProduct(productRes);
    }

    public Observable<ProductDto> getProductFromPosition(int position) {
        return mDataManager.getProductFromPosition(position);
    }

    public Observable<ProductDto> getProductsObs() {
        Observable<ProductDto> disk = fromDisk();
        Observable<ProductRes> network = fromNetwork();
        Observable<ProductLocalInfo> local = network.flatMap(productRes -> mDataManager.getProductLocalInfoObs(productRes));

        Observable<ProductDto> productFromNetwork = Observable.zip(network, local, ProductDto::new);
        return Observable.merge(disk, productFromNetwork)
                .compose(mAsyncTransformer.transform());
    }

    //    @RxLogObservable
    public Observable<ProductRes> fromNetwork() {
        return mDataManager.getProductsFromNetwork();
    }

    // TODO: 17.12.2016 move to datamanager
//    @RxLogObservable
    public Observable<ProductDto> fromDisk() {
        return Observable.defer(() -> {
            List<ProductDto> diskData = mDataManager.getProductsFromDB();
            return diskData == null ?
                    Observable.empty() :
                    Observable.from(diskData);
        });
    }
}
