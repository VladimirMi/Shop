package ru.mikhalev.vladimir.mvpauth.ui.views;

import android.content.Context;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.mikhalev.vladimir.mvpauth.R;
import ru.mikhalev.vladimir.mvpauth.data.managers.DataManager;
import ru.mikhalev.vladimir.mvpauth.utils.ConstantManager;


public class AuthPanel extends LinearLayout {

    private int mCustomState = ConstantManager.IDLE_STATE;

    @BindView(R.id.auth_card) CardView mAuthCard;
    @BindView(R.id.login_email_et) EditText mEmailEt;
    @BindView(R.id.login_password_et) EditText mPasswordEt;
    @BindView(R.id.show_catalog_btn) Button mShowCatalogBtn;
    @BindView(R.id.login_btn) Button mLoginBtn;

    private Animation mInAnimation = AnimationUtils.loadAnimation(DataManager.getInstance().getAppContext(),
            R.anim.card_in_animation);
    private Animation mOutAnimation = AnimationUtils.loadAnimation(DataManager.getInstance().getAppContext(),
            R.anim.card_out_animation);

    public AuthPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // TODO: 20-Oct-16 validate and save state for email and password

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        showViewFromState();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.state = mCustomState;
        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(state);
        setCustomState(savedState.state);
    }

    public void setCustomState(int customState) {
        mCustomState = customState;
        showViewFromState();
    }

    private void showViewFromState() {
        if (mCustomState == ConstantManager.LOGIN_STATE) {
            showLoginState();
        } else {
            showIdleState();
        }
    }

    private void showLoginState() {
        mAuthCard.setVisibility(VISIBLE);
        mShowCatalogBtn.setVisibility(GONE);
        mAuthCard.startAnimation(mInAnimation);
    }

    private void showIdleState() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAuthCard.setVisibility(GONE);
                mShowCatalogBtn.setVisibility(VISIBLE);
            }
        }, 500);

        mAuthCard.startAnimation(mOutAnimation);
    }

    public String getUserEmail() {
        return String.valueOf(mEmailEt.getText());
    }

    public String getUserPassword() {
        return String.valueOf(mPasswordEt.getText());
    }

    public boolean isIdle() {
        return mCustomState == ConstantManager.IDLE_STATE;
    }


    static class SavedState extends BaseSavedState {

        private int state;

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public SavedState(Parcel source) {
            super(source);
            state = source.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(state);
        }
    }
}
