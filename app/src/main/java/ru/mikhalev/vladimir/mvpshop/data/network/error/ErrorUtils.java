package ru.mikhalev.vladimir.mvpshop.data.network.error;

import retrofit2.Response;

/**
 * Developer Vladimir Mikhalev 15.12.2016
 */

public class ErrorUtils {
    public static ApiError parseError(Response<?> response) {
        ApiError error = new ApiError();

        // TODO: implement
//        try {
//            error = (ApiError) DataManager.getInstance()
//                    .getRetrofit().responseBodyConverter(ApiError.class, ApiError.class.getAnnotations())
//                    .convert(response.errorBody());
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ApiError();
//        }

        return error;
    }
}
