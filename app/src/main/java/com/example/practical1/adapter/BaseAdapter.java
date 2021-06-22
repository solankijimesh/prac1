package com.example.practical1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practical1.BR;

import java.util.ArrayList;

public class BaseAdapter extends RecyclerView.Adapter<BaseAdapter.MyViewHolder> {

    private final Context mContext;
    private final ArrayList<?> items;
    private final int layoutId;

    public BaseAdapter(Context mContext, ArrayList<?> items, int layoutId) {
        this.mContext = mContext;
        this.items = items;
        this.layoutId = layoutId;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), layoutId, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseAdapter.MyViewHolder holder, int position) {
        holder.setBinding(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final ViewDataBinding binding;

        public MyViewHolder(@NonNull ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void setBinding(Object object) {
            binding.setVariable(BR.data, object);
        }
    }
}
