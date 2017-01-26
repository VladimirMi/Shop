package ru.mikhalev.vladimir.mvpshop.features.account;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import ru.mikhalev.vladimir.mvpshop.R;
import ru.mikhalev.vladimir.mvpshop.data.storage.AddressRealm;

/**
 * Developer Vladimir Mikhalev, 04.12.2016.
 */

public class AddressListAdapter extends RealmRecyclerViewAdapter<AddressRealm, AddressListAdapter.ViewHolder> {

    public AddressListAdapter(@NonNull Context context, @Nullable OrderedRealmCollection<AddressRealm> data, boolean autoUpdate) {
        super(context, data, autoUpdate);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_address, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mAddress.setText(getItem(position).toString());
        holder.mComment.setText(getItem(position).getComment());
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mAddress;
        private TextView mComment;

        public ViewHolder(View view) {
            super(view);
            mAddress = (TextView) view.findViewById(R.id.address);
            mComment = (TextView) view.findViewById(R.id.comment);
        }
    }
}
