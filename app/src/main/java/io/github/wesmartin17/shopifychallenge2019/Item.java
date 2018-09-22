package io.github.wesmartin17.shopifychallenge2019;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.Pair;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by WM on 2017-12-27.
 */

public class Item {

    private String title;
    private Pair<String,Integer>[] inventory;
    private int totalInventory;
    private String[] tags;
    private String imgURL;

    public Item(String title, Pair<String, Integer>[] inventory, int totalInventory, String[] tags, String imgURL) {
        this.title = title;
        this.inventory = inventory;
        this.totalInventory = totalInventory;
        this.tags = tags;
        this.imgURL = imgURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Pair<String, Integer>[] getInventory() {
        return inventory;
    }

    public void setInventory(Pair<String, Integer>[] inventory) {
        this.inventory = inventory;
    }

    public int getTotalInventory() {
        return totalInventory;
    }

    public void setTotalInventory(int totalInventory) {
        this.totalInventory = totalInventory;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
