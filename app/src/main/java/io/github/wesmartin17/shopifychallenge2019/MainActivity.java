package io.github.wesmartin17.shopifychallenge2019;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.xml.datatype.Duration;

public class MainActivity extends AppCompatActivity implements TagListFragment.OnFragmentInteractionListener, ProductListFragment.OnFragmentInteractionListener{

    private static FragmentManager mFragmentManager;
    private static FragmentTransaction mFragmentTransition;
    private static Item[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragmentManager = getSupportFragmentManager();

        RestManager.getInstance(getApplicationContext()).getJSONObjectFromRest("https://shopicruit.myshopify.com/admin/products.json?page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6", new ServerCallback() {
            @Override
            public void onSuccess(Object response) {

            }

            @Override
            public void onSuccess(JSONObject response) {
                try {
                    JSONArray products = response.getJSONArray("products");
                    items = new Item[products.length()];
                    Set<String> allTags = new HashSet<>();

                    for(int i = 0; i < products.length(); i++) {
                        JSONObject product = products.getJSONObject(i);

                        JSONArray variants = product.getJSONArray("variants");
                        Pair<String, Integer>[] inventoryList = new Pair[variants.length()];
                        int totalInventory = 0;
                        for(int j = 0; j < variants.length(); j++) {
                            String variantTitle = variants.getJSONObject(j).getString("title");
                            int variantInventory = variants.getJSONObject(j).getInt("inventory_quantity");
                            totalInventory += variantInventory;
                            inventoryList[j] = new Pair<>(variantTitle,variantInventory);
                        }

                        String[] productTags = product.getString("tags").split(", ");
                        allTags.addAll(Arrays.asList(productTags));

                        String imgURL = product.getJSONObject("image").getString("src");

                        String title = product.getString("title");

                        items[i] = new Item(title,inventoryList,totalInventory,productTags,imgURL);
                    }

                    ArrayList<String> allTagsList = new ArrayList<>();
                    allTagsList.addAll(allTags);
                    Collections.sort(allTagsList);

                    findViewById(R.id.progressBar).setVisibility(View.GONE);

                    mFragmentTransition = mFragmentManager.beginTransaction();
                    mFragmentTransition.add(R.id.mainFrame, TagListFragment.newInstance("asdf","asdf",allTagsList.toArray(new String[0]))).commit();

                } catch (JSONException e) {
                    Toast.makeText(getBaseContext(),"Could not connect", Toast.LENGTH_LONG);
                    e.printStackTrace();
                }
            }
        });
    }

    public static void loadProductList(String tag) {
        ArrayList<Item> filteredItemList = new ArrayList<>();
        for(Item i : items) {
            for(String s: i.getTags()) {
                if (s.equalsIgnoreCase(tag)) {
                    filteredItemList.add(i);
                    break;
                }
            }
        }
        mFragmentTransition = mFragmentManager.beginTransaction();
        mFragmentTransition.setCustomAnimations(R.anim.fade_in,R.anim.fade_out, R.anim.fade_back_out, R.anim.fade_back_in);
        mFragmentTransition.replace(R.id.mainFrame, ProductListFragment.newInstance(filteredItemList.toArray(new Item[0]),tag)).addToBackStack("main").setCustomAnimations(R.anim.fade_in,R.anim.fade_out).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
