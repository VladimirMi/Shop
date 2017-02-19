package ru.mikhalev.vladimir.mvpshop.data.jobs;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import ru.mikhalev.vladimir.mvpshop.data.managers.DataManager;
import ru.mikhalev.vladimir.mvpshop.utils.AppConfig;
import timber.log.Timber;

/**
 * Developer Vladimir Mikhalev, 07.02.2017.
 */

public class UploadAvatarJob extends Job {
    private final String mImageUri;

    public UploadAvatarJob(String imageUri) {
        super(new Params(JobPriority.HIGH)
                .requireNetwork()
                .persist());
        mImageUri = imageUri;
    }

    @Override
    public void onAdded() {
        Timber.e("onAdded: ");
    }

    @Override
    public void onRun() throws Throwable {
        Timber.e("onRun: ");
        File file = new File(Uri.parse(mImageUri).getPath());
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part bodyPart = MultipartBody.Part.createFormData("avatar", file.getName(), body);
        DataManager.getInstance().uploadAvatar(bodyPart)
                .subscribe(avatarUrlRes -> {
                    Timber.e("onRun: avatar uploaded");
                });
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {
        Timber.e("onCancel: ");
    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        return RetryConstraint.createExponentialBackoff(runCount, AppConfig.INITIAL_BACK_OFF_IN_MS);
    }
}
