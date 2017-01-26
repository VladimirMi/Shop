package ru.mikhalev.vladimir.mvpshop.utils;

import io.realm.Realm;

/**
 * Developer Vladimir Mikhalev, 25.01.2017.
 */

public class RealmUtils {

    public static void executeTransactionAsync(Realm.Transaction transaction) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(transaction);
        realm.close();
    }

}
