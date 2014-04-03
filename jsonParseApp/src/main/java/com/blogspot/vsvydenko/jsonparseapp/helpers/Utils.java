package com.blogspot.vsvydenko.jsonparseapp.helpers;

import java.util.ArrayList;

/**
 * Created by vsvydenko on 03.04.14.
 */
public class Utils {

    public static ArrayList<String> parse(String response) {
        ArrayList<String> citiesList = new ArrayList<String>();

        String[] citiesArray = response.split(",");
        for(String item : citiesArray) {
            citiesList.add(item.split(":")[0]);
        }

        return citiesList;
    }
}
