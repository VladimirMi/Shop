package ru.mikhalev.vladimir.mvpauth.auth;

import android.content.Context;
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
import ru.mikhalev.vladimir.mvpauth.core.managers.DataManager;
import ru.mikhalev.vladimir.mvpauth.core.utils.ConstantManager;
import ru.mikhalev.vladimir.mvpauth.core.utils.MyTextWatcher;


public class AuthPanel extends LinearLayout {

    private static final String TAG = ConstantManager.TAG_PREFIX + "AuthPanel";
    private int mCustomState = ConstantManager.IDLE_STATE;
    private AuthPresenter mPresenter = AuthPresenter.getInstance();

    @BindView(R.id.auth_card) CardView mAuthCard;
    @BindView(R.id.login_email_et) EditText mEmailEt;
    @BindView(R.id.login_password_et) EditText mPasswordEt;
    @BindView(R.id.show_catalog_btn) Button mShowCatalogBtn;

    @BindView(R.id.login_btn) Button mLoginBtn;
    private Animation mCardInAnimation = AnimationUtils.loadAnimation(DataManager.getInstance().getAppContext(),
            R.anim.card_in_animation);
    private Animation mCardOutAnimation = AnimationUtils.loadAnimation(DataManager.getInstance().getAppContext(),
            R.anim.card_out_animation);

    public AuthPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        mPasswordEt.addTextChangedListener(new MyTextWatcher(mPasswordEt));
        mEmailEt.addTextChangedListener(new MyTextWatcher(mEmailEt));

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
        mCustomState = savedState.state;
        showViewFromState();
    }

    public void setCustomState(int customState) {
        mCustomState = customState;
        showAnimationFromState();
        showViewFromState();
    }

    private void showViewFromState() {
        if (mCustomState == ConstantManager.LOGIN_STATE) {
            showLoginState();
        } else {
            showIdleState();
        }
    }

    private void showAnimationFromState() {
        if (mCustomState == ConstantManager.LOGIN_STATE) {
            showLoginStateAnimation();
        } else {
            showIdleStateAnimation();
        }
    }

    private void showLoginState() {
        mAuthCard.setVisibility(VISIBLE);
        mShowCatalogBtn.setVisibility(GONE);

    }

    private void showIdleState() {
        mShowCatalogBtn.setVisibility(VISIBLE);
        mAuthCard.setVisibility(GONE);
    }

    private void showLoginStateAnimation() {
        mAuthCard.startAnimation(mCardInAnimation);
    }

    private void showIdleStateAnimation() {
        mAuthCard.startAnimation(mCardOutAnimation);
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
