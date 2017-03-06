package ru.mikhalev.vladimir.mvpshop.data.network.api;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import ru.mikhalev.vladimir.mvpshop.data.network.models.AvatarUrlRes;
import ru.mikhalev.vladimir.mvpshop.data.network.models.CommentRes;
import ru.mikhalev.vladimir.mvpshop.data.network.models.LoginReq;
import ru.mikhalev.vladimir.mvpshop.data.network.models.LoginRes;
import ru.mikhalev.vladimir.mvpshop.data.network.models.ProductRes;
import ru.mikhalev.vladimir.mvpshop.utils.ConstantManager;
import rx.Observable;

/**
 * Developer Vladimir Mikhalev 05.11.2016
 */

public interface RestService {
    @GET("products")
    Observable<Response<List<ProductRes>>> getProductResponse(@Header(ConstantManager.IF_MODIFIED_SINCE_HEADER) String lastEntityUpdate);

    @POST("products/{productId}/comments")
    Observable<CommentRes> sendComment(@Path("productId") String productId, @Body CommentRes commentRes);

    @Multipart
    @POST("avatar")
    Observable<AvatarUrlRes> uploadUserAvatar(@Part MultipartBody.Part file);

    @POST("login")
    Observable<LoginRes> loginUser(@Body LoginReq loginReq);
}
