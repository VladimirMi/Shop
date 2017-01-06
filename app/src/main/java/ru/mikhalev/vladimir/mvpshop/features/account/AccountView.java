package ru.mikhalev.vladimir.mvpshop.features.account;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;

import ru.mikhalev.vladimir.mvpshop.core.BaseView;
import ru.mikhalev.vladimir.mvpshop.core.BaseViewModel;
import ru.mikhalev.vladimir.mvpshop.databinding.ScreenAccountBinding;
import ru.mikhalev.vladimir.mvpshop.di.DaggerService;

/**
 * Developer Vladimir Mikhalev 29.11.2016
 */

public class AccountView extends BaseView<AccountScreen.AccountPresenter> implements IAccountView, IAccountActions {

    private ScreenAccountBinding mBinding;
    private AccountViewModel mViewModel;
    private AddressListAdapter mAdapter;

    public AccountView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<AccountScreen.Component>getDaggerComponent(getContext()).inject(this);
    }

    @Override
    protected void initBinding() {
        mBinding = ScreenAccountBinding.bind(this);
    }

    public AddressListAdapter getAdapter() {
        return mAdapter;
    }


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
    public void switchOrder(boolean isChecked) {
        mViewModel.setOrderNotification(isChecked);
        mPresenter.switchNotification();
    }

    @Override
    public void switchPromo(boolean isChecked) {
        mViewModel.setPromoNotification(isChecked);
        mPresenter.switchNotification();
    }

    @Override
    public void changeAvatar() {
        if (mViewModel.getViewState() == AccountViewModel.STATE.EDIT) {
            mPresenter.changeAvatar();
        }
    }

    //endregion


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


    @Override
    public void setViewModel(BaseViewModel viewModel) {
        mViewModel = (AccountViewModel) viewModel;
        mBinding.setViewModel(mViewModel);
        mBinding.setActionsHandler(this);
    }

    @Override
    public void initView() {
        initAddressList();
        initSwipe();
    }

    @Override
    public void changeState() {
        if (mViewModel.getViewState() == AccountViewModel.STATE.EDIT) {
            mViewModel.setViewState(AccountViewModel.STATE.PREVIEW);
        } else {
            mViewModel.setViewState(AccountViewModel.STATE.EDIT);
        }
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
        if (mViewModel.getViewState() == AccountViewModel.STATE.EDIT) {
            changeState();
            return true;
        }
        return false;
    }

    public AccountViewModel getViewModel() {
        return mViewModel;
    }

    //endregion
}
