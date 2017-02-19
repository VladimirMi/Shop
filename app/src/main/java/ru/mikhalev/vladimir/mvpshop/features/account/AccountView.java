package ru.mikhalev.vladimir.mvpshop.features.account;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;

import java.util.UUID;

import ru.mikhalev.vladimir.mvpshop.core.BaseView;
import ru.mikhalev.vladimir.mvpshop.data.storage.AccountRealm;
import ru.mikhalev.vladimir.mvpshop.data.storage.AddressRealm;
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
    protected void initView() {
        mBinding = ScreenAccountBinding.bind(this);
        mBinding.setActionsHandler(this);
        initAddressList();
        initSwipe();
    }


    //region =============== Events ==============

    @Override
    public void switchViewState() {
        mViewModel.changeState();
        if (mViewModel.getViewState() == AccountViewModel.STATE.EDIT) {
            mPresenter.saveAccount(mViewModel);
        }
    }

    @Override
    public void clickOnAddAddress() {
        String id = UUID.randomUUID().toString();
        AddressRealm addressRealm = new AddressRealm(id);
        showAddress(addressRealm);
    }

    @Override
    public void changeAvatar() {
        if (mViewModel.getViewState() == AccountViewModel.STATE.EDIT) {
            showPhotoSourceDialog();
        }
    }

    //endregion


    public void initAddressList() {
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        mAdapter = new AddressListAdapter(getContext(), null, false);
        mBinding.addressList.setLayoutManager(lm);
        mBinding.addressList.setAdapter(mAdapter);
    }


    private void initSwipe() {
        ItemSwipeCallback swipeCallback = new ItemSwipeCallback(getContext(), 0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                AddressRealm addressRealm = mAdapter.getData().get(position);

                if (direction == ItemTouchHelper.LEFT) {
                    showRemoveAddressDialog(addressRealm);
                } else {
                    showEditAddressDialog(addressRealm);
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeCallback);
        itemTouchHelper.attachToRecyclerView(mBinding.addressList);
    }

    private void showRemoveAddressDialog(AddressRealm addressRealm) {
        // TODO: 26.01.2017 check notifydatasetchanged
        new AlertDialog.Builder(getContext())
                .setTitle("Удалить адрес")
                .setMessage("Вы уверены что хотите удалить данный адрес?")
                .setPositiveButton("Удалить", (dialog, i) -> mViewModel.removeAddress(addressRealm))
                .setNegativeButton("Отмена", (dialog, i) -> dialog.cancel())
                .setOnCancelListener(dialog -> mAdapter.notifyDataSetChanged())
                .show();
    }

    private void showEditAddressDialog(AddressRealm addressRealm) {
        new AlertDialog.Builder(getContext())
                .setTitle("Перейти к редактированию адреса")
                .setMessage("Вы уверены что хотите редактировать данный адрес?")
                .setPositiveButton("Редактировать", (dialog, i) -> showAddress(addressRealm))
                .setNegativeButton("Отмена", (dialog, i) -> dialog.cancel())
                .setOnCancelListener(dialog -> mAdapter.notifyDataSetChanged())
                .show();
    }

    //region ==================== IAccountView ========================


    @Override
    public void showAddress(AddressRealm addressRealm) {
        // TODO: 10.01.2017 open with addressRealm
    }

    @Override
    public void setViewModel(AccountRealm accountRealm) {
        if (mViewModel == null) {
            mViewModel = new AccountViewModel(accountRealm);
            mBinding.setViewModel(mViewModel);
        } else {
            mViewModel.update(accountRealm);
        }
        mAdapter.updateData(accountRealm.getAddressRealms());
    }

    public void showPhotoSourceDialog() {
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
            switchViewState();
            return true;
        }
        return false;
    }

    //endregion
}
