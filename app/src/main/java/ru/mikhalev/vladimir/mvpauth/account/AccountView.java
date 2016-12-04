package ru.mikhalev.vladimir.mvpauth.account;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Inject;

import flow.Flow;
import ru.mikhalev.vladimir.mvpauth.core.di.DaggerService;
import ru.mikhalev.vladimir.mvpauth.databinding.ScreenAccountBinding;

/**
 * Developer Vladimir Mikhalev 29.11.2016
 */

public class AccountView extends CoordinatorLayout implements IAccountView, IAccountActions {
    private static final String TAG = "AccountView";

    @IntDef({STATE.PREVIEW, STATE.EDIT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface STATE {
        int PREVIEW = 0;
        int EDIT = 1;
    }

    @Inject AccountScreen.AccountPresenter mPresenter;
    private ScreenAccountBinding mBinding;
    private AccountScreen mScreen;

    public AccountView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            DaggerService.<AccountScreen.Component>getDaggerComponent(getContext()).inject(this);
            mScreen = Flow.getKey(this);
        }
    }

    //region =============== Lifecycle ==============

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()) {
            mBinding = ScreenAccountBinding.bind(this);
            mBinding.setActionsHandler(this);
            showViewFromState();
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            mPresenter.takeView(this);
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (!isInEditMode()) {
            mPresenter.dropView(this);
        }
    }
    //endregion

    //region =============== Events ==============

    @Override
    public void switchViewState() {
        mPresenter.switchViewState();
    }

    @Override
    public void clickOnAddAddress() {
        mPresenter.clickOnAddAddress();
    }

    @Override
    public void switchOrder(boolean isCheked) {
        mPresenter.switchOrder(isCheked);
    }

    @Override
    public void switchPromo(boolean isCheked) {
        mPresenter.switchPromo(isCheked);
    }

    @Override
    public void changeAvatar() {
        mPresenter.changeAvatar();
    }

    //endregion

    private void showViewFromState() {
        mBinding.setViewState(mScreen.getViewState());
    }

    public void initView(AccountViewModel viewModel) {
        mBinding.setViewModel(viewModel);
        initAddressList(viewModel);
        initSwipe();
    }

    private void initAddressList(AccountViewModel viewModel) {
        AddressListAdapter adapter = new AddressListAdapter(viewModel.getAddresses());
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        mBinding.addressList.setLayoutManager(lm);
        mBinding.addressList.setAdapter(adapter);
    }


    private void initSwipe() {
        ItemSwipeCallback swipeCallback = new ItemSwipeCallback(getContext(), 0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT) {
                    mPresenter.removeAddress(position); // TODO: 29.11.2016 get address object
                } else {
                    mPresenter.editAddress(position); // TODO: 29.11.2016
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeCallback);
        itemTouchHelper.attachToRecyclerView(mBinding.addressList);
    }

    //region ==================== IAccountView ========================

    @Override
    public void changeState() {
        if (mScreen.getViewState() == STATE.EDIT) {
            mScreen.setViewState(STATE.PREVIEW);
        } else {
            mScreen.setViewState(STATE.EDIT);
        }
        showViewFromState();
    }

    @Override
    public void showPhotoSourceDialog() {
        // FIXME: 01.12.2016 refactoring
        String[] source = {"Загрузить из галереи", "Сделать фото", "Отмена"};
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Установить фото");
        alertDialog.setItems(source, (dialog, i) -> {
            switch (i) {
                case 0:
                    mPresenter.chooseGallery();
                    break;
                case 1:
                    mPresenter.chooseCamera();
                    break;
                case 2:
                    dialog.cancel();
                    break;
            }
        });
        alertDialog.show();
    }

    @Override
    public String getUserName() {
        return String.valueOf(mBinding.fullname.getText());
    }

    @Override
    public String getUserPhone() {
        return String.valueOf(mBinding.phone.getText());
    }

    @Override
    public boolean viewOnBackPressed() {
        if (mScreen.getViewState() == STATE.EDIT) {
            changeState();
            return true;
        }
        return false;
    }

    //endregion
}
