package ru.mikhalev.vladimir.mvpshop.data.network.api;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import ru.mikhalev.vladimir.mvpshop.data.network.models.ProductRes;
import ru.mikhalev.vladimir.mvpshop.utils.ConstantManager;
import rx.Observable;

/**
 * Developer Vladimir Mikhalev 05.11.2016
 */

public interface RestService {
    @GET("products")
    Observable<Response<List<ProductRes>>> getProductResponse(@Header(ConstantManager.IF_MODIFIED_SINCE_HEADER) String lastEntityUpdate);
}
