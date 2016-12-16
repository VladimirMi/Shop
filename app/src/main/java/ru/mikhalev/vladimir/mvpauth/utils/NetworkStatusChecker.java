package ru.mikhalev.vladimir.mvpauth.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import ru.mikhalev.vladimir.mvpauth.core.App;
import rx.Observable;

public class NetworkStatusChecker {

    public static Observable<Boolean> isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) App.getAppComponent().getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean available = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return Observable.just(available);
    }
}
