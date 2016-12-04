package ru.mikhalev.vladimir.mvpauth.account;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import ru.mikhalev.vladimir.mvpauth.address.AddressViewModel;
import ru.mikhalev.vladimir.mvpauth.databinding.ItemAddressBinding;

/**
 * Developer Vladimir Mikhalev, 04.12.2016.
 */

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.ViewHolder> {

    private final ArrayList<AddressViewModel> mAddresses;

    public AddressListAdapter(ArrayList<AddressViewModel> addresses) {
        mAddresses = addresses;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemAddressBinding binding = ItemAddressBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mAddresses.get(position));
    }

    @Override
    public int getItemCount() {
        return mAddresses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemAddressBinding mBinding;

        public ViewHolder(ItemAddressBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(AddressViewModel viewModel) {
            mBinding.setViewModel(viewModel);
        }
    }
}