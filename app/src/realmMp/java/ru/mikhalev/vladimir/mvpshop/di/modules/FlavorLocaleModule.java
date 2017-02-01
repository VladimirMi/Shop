package ru.mikhalev.vladimir.mvpshop.di.modules;

import android.content.Context;

import com.facebook.stetho.Stetho;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.ObjectServerError;
import io.realm.Realm;
import io.realm.SyncConfiguration;
import io.realm.SyncCredentials;
import io.realm.SyncUser;
import ru.mikhalev.vladimir.mvpshop.data.managers.RealmManager;
import ru.mikhalev.vladimir.mvpshop.utils.AppConfig;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Developer Vladimir Mikhalev, 06.11.2016.
 */

@Module
public class FlavorLocaleModule {

    @Provides
    @Singleton
    RealmManager provideRealmManager(Context context) {
        Timber.tag("realm_mp");
        Timber.e("provideRealmManager: ");
        Stetho.initializeWithDefaults(context);

        /*Observable.fromCallable(() -> SyncCredentials.usernamePassword(AppConfig.REALM_USER,
                AppConfig.REALM_PASSWORD, false))
                .map(syncCredentials -> SyncUser.login(syncCredentials, AppConfig.REALM_AUTH_URL));*/

        Observable.create(new Observable.OnSubscribe<SyncUser>() {
            @Override
            public void call(Subscriber<? super SyncUser> subscriber) {
                SyncCredentials syncCredentials = SyncCredentials.usernamePassword(AppConfig.REALM_USER,
                        AppConfig.REALM_PASSWORD, false);

                if (!subscriber.isUnsubscribed()) {
                    try {
                        subscriber.onNext(SyncUser.login(syncCredentials, AppConfig.REALM_AUTH_URL));
                        subscriber.onCompleted();
                    } catch (ObjectServerError objectServerError) {
                        subscriber.onError(objectServerError);
                    }
                }

                /*if (!subscriber.isUnsubscribed()) {
                    SyncUser.loginAsync(syncCredentials, AppConfig.REALM_AUTH_URL, new SyncUser.Callback() {
                        @Override
                        public void onSuccess(SyncUser user) {
                            subscriber.onNext(user);
                            subscriber.onCompleted();
                        }

                        @Override
                        public void onError(ObjectServerError error) {
                            subscriber.onError(error);
                        }
                    });
                }*/
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(syncUser -> {
            SyncConfiguration syncConfig = new SyncConfiguration.Builder(syncUser, AppConfig.REALM_DB_URL).build();
            Realm.setDefaultConfiguration(syncConfig);
        }, Throwable::printStackTrace);

        return new RealmManager();
    }
}

