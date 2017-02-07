package ru.mikhalev.vladimir.mvpshop.data.jobs;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;

import io.realm.Realm;
import ru.mikhalev.vladimir.mvpshop.data.managers.DataManager;
import ru.mikhalev.vladimir.mvpshop.data.network.models.CommentRes;
import ru.mikhalev.vladimir.mvpshop.data.storage.CommentRealm;
import ru.mikhalev.vladimir.mvpshop.data.storage.ProductRealm;
import ru.mikhalev.vladimir.mvpshop.utils.AppConfig;
import timber.log.Timber;

/**
 * Developer Vladimir Mikhalev, 07.02.2017.
 */

public class SendMessageJob extends Job {

    private final CommentRealm mCommentRealm;
    private final String mProductId;

    public SendMessageJob(String productId, CommentRealm commentRealm) {
        super(new Params(JobPriority.MID)
                .requireNetwork()
                .persist()
                .groupBy("Comments"));
        mProductId = productId;
        mCommentRealm = commentRealm;
    }

    @Override
    public void onAdded() {
        Timber.e("onAdded: ");
    }

    @Override
    public void onRun() throws Throwable {
        Timber.e("onRun: ");
        CommentRes comment = new CommentRes(mCommentRealm);
        DataManager.getInstance().sendComment(mProductId, comment)
                .subscribe(commentRes -> {
                    Realm realm = Realm.getDefaultInstance();

                    CommentRealm serverComment = new CommentRealm(commentRes);
                    CommentRealm localComment = realm.where(CommentRealm.class)
                            .equalTo("id", mCommentRealm.getId())
                            .findFirst();
                    ProductRealm productRealm = realm.where(ProductRealm.class)
                            .equalTo("id", mProductId)
                            .findFirst();

                    realm.executeTransaction(realm1 -> {
                        localComment.deleteFromRealm();
                        productRealm.getCommentRealms().add(serverComment);
                    });
                    realm.close();
                });
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {
        Timber.e("onCancel: ");
        // do nothing
    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        Timber.e("shouldReRunOnThrowable: ");
        return RetryConstraint.createExponentialBackoff(runCount, AppConfig.INITIAL_BACK_OFF_IN_MS);
    }
}
