package ru.mikhalev.vladimir.mvpshop.features.details.comments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import ru.mikhalev.vladimir.mvpshop.data.storage.CommentRealm;
import ru.mikhalev.vladimir.mvpshop.databinding.ItemCommentBinding;

/**
 * Developer Vladimir Mikhalev, 14.01.2017.
 */

public class CommentsAdapter extends RealmRecyclerViewAdapter<CommentRealm, CommentsAdapter.ViewHolder> {

    public CommentsAdapter(@NonNull Context context, @Nullable OrderedRealmCollection<CommentRealm> data, boolean autoUpdate) {
        super(context, data, autoUpdate);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemCommentBinding binding = ItemCommentBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemCommentBinding mBinding;

        public ViewHolder(ItemCommentBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(CommentRealm commentRealm) {
            // TODO: 14.01.2017 set info
            mBinding.setViewModel(new CommentViewModel(commentRealm));
        }
    }
}
