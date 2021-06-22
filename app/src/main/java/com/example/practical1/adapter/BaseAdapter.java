package com.example.practical1.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practical1.BR;
import com.example.practical1.R;
import com.example.practical1.databinding.ItemButtonBinding;
import com.example.practical1.listener.ItemClickListener;
import com.example.practical1.model.GridModel;

import java.util.ArrayList;

public class BaseAdapter extends RecyclerView.Adapter<BaseAdapter.MyViewHolder> {

    private final Context mContext;
    private final ArrayList<?> items;
    private final int layoutId;
    private ItemClickListener listener;

    public BaseAdapter(Context mContext, ArrayList<?> items, int layoutId) {
        this.mContext = mContext;
        this.items = items;
        this.layoutId = layoutId;
    }

    public BaseAdapter(Context mContext, ArrayList<?> items, int layoutId, ItemClickListener listener) {
        this.mContext = mContext;
        this.items = items;
        this.layoutId = layoutId;
        this.listener = listener;
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

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final ViewDataBinding binding;

        public MyViewHolder(@NonNull ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void setBinding(Object object) {

            if (listener == null)
                listener = (ItemClickListener) mContext;

            binding.setVariable(BR.data, object);

            if (binding instanceof ItemButtonBinding) {

                GridModel gridModel = (GridModel) object;

                switch (gridModel.getColor()) {

                    case Red:
                        ((ItemButtonBinding) binding).btn.setBackgroundTintList(ColorStateList.valueOf(
                                ((ItemButtonBinding) binding).btn.getContext().getResources().getColor(R.color.red)));
                        break;

                    case Blue:
                        ((ItemButtonBinding) binding).btn.setBackgroundTintList(ColorStateList.valueOf(
                                ((ItemButtonBinding) binding).btn.getContext().getResources().getColor(R.color.blue)));
                        break;

                    case White:
                        ((ItemButtonBinding) binding).btn.setBackgroundTintList(ColorStateList.valueOf(
                                ((ItemButtonBinding) binding).btn.getContext().getResources().getColor(R.color.white)));
                        break;
                }

                ((ItemButtonBinding) binding).btn.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onClick(View v) {
                        if (gridModel.isClickable()) {
                            listener.onItemClick(getLayoutPosition(), gridModel);
                        }
                    }
                });
            }
        }
    }
}
