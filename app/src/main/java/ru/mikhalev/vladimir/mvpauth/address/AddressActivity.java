package ru.mikhalev.vladimir.mvpauth.address;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import ru.mikhalev.vladimir.mvpauth.R;
import ru.mikhalev.vladimir.mvpauth.core.layers.view.BaseActivity;
import ru.mikhalev.vladimir.mvpauth.databinding.ActivityAddressBinding;

public class AddressActivity extends BaseActivity {

    private ActivityAddressBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding(DataBindingUtil.setContentView(this, R.layout.activity_address));
        mBinding = (ActivityAddressBinding) getBinding();

        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(mBinding.toolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
