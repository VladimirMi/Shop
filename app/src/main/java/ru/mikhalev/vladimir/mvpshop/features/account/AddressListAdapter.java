package ru.mikhalev.vladimir.mvpshop.features.account;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.mikhalev.vladimir.mvpshop.data.dto.AccountAddressDto;
import ru.mikhalev.vladimir.mvpshop.databinding.ItemAddressBinding;
import ru.mikhalev.vladimir.mvpshop.features.address.AddressViewModel;

/**
 * Developer Vladimir Mikhalev, 04.12.2016.
 */

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.ViewHolder> {

    private List<AddressViewModel> mAddresses = new ArrayList<>();

    public AddressListAdapter() {

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

    // TODO: 28.12.2016 optimize notify
    public void addItem(AccountAddressDto address) {
        mAddresses.add(new AddressViewModel(address));
        notifyDataSetChanged();
    }

    public void reloadAdapter() {
        mAddresses.clear();
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemAddressBinding mBinding;

        public ViewHolder(ItemAddressBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(AddressViewModel addressViewModel) {
            mBinding.setViewModel(addressViewModel);
        }
    }
}
