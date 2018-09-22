package io.github.wesmartin17.shopifychallenge2019;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by WM on 2017-12-26.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    private Item[] items;

    public ItemAdapter(Item[] items){
        this.items = items;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemCard = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_list_item,parent,false);

        return new ItemViewHolder(itemCard);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        final Item item = items[position];
        holder.updateUI(item);
    }

    @Override
    public int getItemCount() {
        return items.length;
    }
}
