package ru.mikhalev.vladimir.mvpauth.account;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.mikhalev.vladimir.mvpauth.R;
import ru.mikhalev.vladimir.mvpauth.address.AddressActivity;
import ru.mikhalev.vladimir.mvpauth.databinding.ActivityHomeBinding;
import ru.mikhalev.vladimir.mvpauth.databinding.FragmentAccountBinding;
import ru.mikhalev.vladimir.mvpauth.home.HomeActivity;

public class AccountFragment extends Fragment implements AppBarLayout.OnOffsetChangedListener, View.OnClickListener {
    public static final String TAG = "AccountFragment";
    private int mMaxScrollSize;
    private FragmentAccountBinding mBinding;

    //region ============================== Life cycle ==============================

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentAccountBinding.inflate(inflater, container, false);

        initToolbar();
        initAppBar();

        mBinding.addAddressButton.setOnClickListener(this);

        return mBinding.getRoot();
    }

    //endregion

    private void initToolbar() {
        ActivityHomeBinding binding = (ActivityHomeBinding) getHomeActivity().getBinding();
        binding.setToolbarTitle(getString(R.string.nav_profile));
        binding.setLogoVisible(false);
    }

    private void initAppBar() {
        mBinding.appbarAccount.addOnOffsetChangedListener(this);
        mMaxScrollSize = mBinding.appbarAccount.getTotalScrollRange();
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (mMaxScrollSize == 0) {
            mMaxScrollSize = appBarLayout.getTotalScrollRange();
        }

        float percentage = 1f - (float) (Math.abs(i)) / mMaxScrollSize;

        mBinding.avatar.animate()
                .scaleY(percentage)
                .scaleX(percentage)
                .setDuration(0)
                .start();
    }

    private HomeActivity getHomeActivity() {
        if (!(getActivity() instanceof HomeActivity)) {
            throw new IllegalStateException("Parent activity should be HomeActivity");
        }
        return (HomeActivity) getActivity();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_address_button:
                openAddressScreen();
                break;
        }
    }

    private void openAddressScreen() {
        startActivity(new Intent(getHomeActivity(), AddressActivity.class));
    }
}
