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

    @Inject
    AccountScreen.AccountPresenter mPresenter;
    private ScreenAccountBinding mBinding;
    private AccountScreen mScreen;
    private AddressListAdapter mAdapter;

    public AccountView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            DaggerService.<AccountScreen.Component>getDaggerComponent(getContext()).inject(this);
            mScreen = Flow.getKey(this);
        }
    }

    public AddressListAdapter getAdapter() {
        return mAdapter;
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
        mBinding.getViewModel().setOrderNotification(isCheked);
        mPresenter.switchNotification();
    }

    @Override
    public void switchPromo(boolean isCheked) {
        mBinding.getViewModel().setPromoNotification(isCheked);
        mPresenter.switchNotification();
    }

    @Override
    public void changeAvatar() {
        if (mBinding.getViewState() == STATE.EDIT) {
            mPresenter.changeAvatar();
        }
    }

    //endregion

    private void showViewFromState() {
        mBinding.setViewState(mScreen.getViewState());
    }

    public void initView() {
        initAddressList();
        initSwipe();
    }

    public void initAddressList() {
        mAdapter = new AddressListAdapter();
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        mBinding.addressList.setLayoutManager(lm);
        mBinding.addressList.setAdapter(mAdapter);
    }


    private void initSwipe() {
        ItemSwipeCallback swipeCallback = new ItemSwipeCallback(getContext(), 0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT) {
                    showRemoveAddressDialog(position);
                } else {
                    showEditAddressDialog(position);
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeCallback);
        itemTouchHelper.attachToRecyclerView(mBinding.addressList);
    }

    private void showRemoveAddressDialog(int position) {
        new AlertDialog.Builder(getContext())
                .setTitle("Удалить адрес")
                .setMessage("Вы уверены что хотите удалить данный адрес?")
                .setPositiveButton("Удалить", (dialog, i) -> mPresenter.removeAddress(position))
                .setNegativeButton("Отмена", (dialog, i) -> dialog.cancel())
                .setOnCancelListener(dialog -> mAdapter.notifyDataSetChanged())
                .show();
    }

    private void showEditAddressDialog(int position) {
        new AlertDialog.Builder(getContext())
                .setTitle("Перейти к редактированию адреса")
                .setMessage("Вы уверены что хотите редактировать данный адрес?")
                .setPositiveButton("Редактировать", (dialog, i) -> mPresenter.editAddress(position))
                .setNegativeButton("Отмена", (dialog, i) -> dialog.cancel())
                .setOnCancelListener(dialog -> mAdapter.notifyDataSetChanged())
                .show();
    }

    //region ==================== IAccountView ========================

    public void setViewModel(AccountDto viewModel) {
        mBinding.setViewModel(viewModel);
    }

    @Override
    public AccountDto getViewModel() {
        return mBinding.getViewModel();
    }

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
    public boolean viewOnBackPressed() {
        if (mScreen.getViewState() == STATE.EDIT) {
            changeState();
            return true;
        }
        return false;
    }

    //endregion
}
