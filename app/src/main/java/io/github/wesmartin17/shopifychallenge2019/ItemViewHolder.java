package io.github.wesmartin17.shopifychallenge2019;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

/**
 * Created by WM on 2017-12-26.
 */


public class ItemViewHolder extends RecyclerView.ViewHolder {

    private ImageView imageView;
    private TextView titleText;
    private TextView inventoryText;
    private TextView showAllInventory;
    private Resources res;

    public ItemViewHolder(View itemView) {
        super(itemView);
        imageView = (ImageView)itemView.findViewById(R.id.imagePreview);
        titleText = (TextView)itemView.findViewById(R.id.txtTitle);
        inventoryText = (TextView)itemView.findViewById(R.id.txtInventorySummary);
        showAllInventory = (TextView)itemView.findViewById(R.id.txtInventoryAll);
        showAllInventory.setOnClickListener(new View.OnClickListener() {
            boolean expanded = false;

            @Override
            public void onClick(View v) {
                if(!expanded) {
                    showAllInventory.setText("▲");

                    inventoryText.setMaxLines(Integer.MAX_VALUE);
                    expanded = true;
                }
                else{
                    showAllInventory.setText("▼");

                    inventoryText.setMaxLines(3);
                    expanded = false;
                }
            }
        });
        res = itemView.getResources();
    }

    public void updateUI(Item item){
        titleText.setText(item.getTitle());
        inventoryText.setText(res.getString(R.string.inventory_total,item.getTotalInventory()));
        Picasso.get().load(item.getImgURL()).into(imageView);
        for(Pair<String, Integer> inventory: item.getInventory()){
            String newInventoryTextString = inventoryText.getText()+"\n"+res.getString(R.string.inventory_item,inventory.first,inventory.second.toString());
            inventoryText.setText(newInventoryTextString);

            inventoryText.post(new Runnable() {
                @Override
                public void run() {
                    int lineCnt = inventoryText.getLineCount();
                    if(lineCnt > 3){
                        showAllInventory.setVisibility(View.VISIBLE);
                        inventoryText.setMaxLines(3);
                    }
                }
            });
        }

    }
}
