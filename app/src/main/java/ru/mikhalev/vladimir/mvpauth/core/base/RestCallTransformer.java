package ru.mikhalev.vladimir.mvpauth.core.base;

import retrofit2.Response;
import ru.mikhalev.vladimir.mvpauth.data.managers.DataManager;
import ru.mikhalev.vladimir.mvpauth.data.network.error.ErrorUtils;
import ru.mikhalev.vladimir.mvpauth.data.network.error.NetworkAvailableError;
import ru.mikhalev.vladimir.mvpauth.utils.ConstantManager;
import ru.mikhalev.vladimir.mvpauth.utils.NetworkStatusChecker;
import rx.Observable;

/**
 * Developer Vladimir Mikhalev 15.12.2016
 */

public class RestCallTransformer<T> implements Observable.Transformer<Response<T>, T> {
    @Override
    public Observable<T> call(Observable<Response<T>> responseObservable) {

        return NetworkStatusChecker.isNetworkAvailable()
                .flatMap(available -> available ? responseObservable : Observable.error(new NetworkAvailableError()))
                .flatMap(tResponse -> {
                    switch (tResponse.code()) {
                        case 200:
                            String lastModified = tResponse.headers().get(ConstantManager.LAST_MODIFIED_HEADER);
                            if (lastModified != null) {
                                DataManager.getInstance().getPreferencesManager().saveLastProductUpdate(lastModified);
                            }
                            return Observable.just(tResponse.body());
                        case 304:
                            return Observable.empty();
                        default:
                            return Observable.error(ErrorUtils.parseError(tResponse));
                    }
                });
    }
}
