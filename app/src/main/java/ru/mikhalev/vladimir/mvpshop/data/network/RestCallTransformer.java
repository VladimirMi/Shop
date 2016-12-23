package ru.mikhalev.vladimir.mvpshop.data.network;

import retrofit2.Response;
import ru.mikhalev.vladimir.mvpshop.data.managers.DataManager;
import ru.mikhalev.vladimir.mvpshop.data.network.error.ErrorUtils;
import ru.mikhalev.vladimir.mvpshop.data.network.error.NetworkAvailableError;
import ru.mikhalev.vladimir.mvpshop.utils.ConstantManager;
import ru.mikhalev.vladimir.mvpshop.utils.NetworkStatusChecker;
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
