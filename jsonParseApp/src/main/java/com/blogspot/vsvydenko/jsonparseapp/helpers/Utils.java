package com.blogspot.vsvydenko.jsonparseapp.helpers;

import android.content.Context;
import android.text.TextUtils;

import com.blogspot.vsvydenko.jsonparseapp.R;

import java.util.ArrayList;

/**
 * Created by vsvydenko on 03.04.14.
 */
public class Utils {

    public static ArrayList<String> parse(Context mContext, String response) {
        ArrayList<String> citiesList = new ArrayList<String>();

        if (TextUtils.isEmpty(response) || response.equals("null")) {
            citiesList.add(mContext.getResources().getString(R.string.no_items));
        } else {
            String[] citiesArray = response.split(",");
            for(String item : citiesArray) {
                citiesList.add(item.split(":")[0].split("\"")[1]);
            }
        }

        return citiesList;
    }
}
