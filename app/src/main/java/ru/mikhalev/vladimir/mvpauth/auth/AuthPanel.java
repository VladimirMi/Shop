package ru.mikhalev.vladimir.mvpauth.auth;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;

import ru.mikhalev.vladimir.mvpauth.R;


public class AuthPanel extends LinearLayout {

    private static final String TAG = "AuthPanel";
    public static int LOGIN_STATE = 0;
    public static int IDLE_STATE = 1;
    private int mCustomState = IDLE_STATE;

    private CardView mAuthCard;
    private Button mShowCatalogBtn;

//    private Animation mCardInAnimation = AnimationUtils.loadAnimation(DataManager.getInstance().getAppContext(),
//            R.anim.card_in_animation);
//    private Animation mCardOutAnimation = AnimationUtils.loadAnimation(DataManager.getInstance().getAppContext(),
//            R.anim.card_out_animation);

    public AuthPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mAuthCard = (CardView) findViewById(R.id.auth_card);
        mShowCatalogBtn = (Button) findViewById(R.id.show_catalog_btn);
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
        if (mCustomState == LOGIN_STATE) {
            showLoginState();
        } else {
            showIdleState();
        }
    }

    private void showAnimationFromState() {
        if (mCustomState == LOGIN_STATE) {
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
//        mAuthCard.startAnimation(mCardInAnimation);
    }

    private void showIdleStateAnimation() {
//        mAuthCard.startAnimation(mCardOutAnimation);
    }

    public boolean isIdle() {
        return mCustomState == IDLE_STATE;
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
