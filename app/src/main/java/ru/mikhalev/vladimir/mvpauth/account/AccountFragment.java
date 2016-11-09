package ru.mikhalev.vladimir.mvpauth.account;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.mikhalev.vladimir.mvpauth.R;
import ru.mikhalev.vladimir.mvpauth.databinding.FragmentAccountBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {
    public static final String TAG = "AccountFragment";
    private FragmentAccountBinding mBinding;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false);
        return mBinding.getRoot();
    }

}
