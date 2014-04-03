package com.blogspot.vsvydenko.jsonparseapp.ui.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.vsvydenko.jsonparseapp.R;
import com.blogspot.vsvydenko.jsonparseapp.helpers.RestClient;
import com.blogspot.vsvydenko.jsonparseapp.helpers.Utils;

import java.util.ArrayList;

/**
 * Created by vsvydenko on 03.04.14.
 */
public class CityListFragment extends Fragment {

    ArrayAdapter<String> adapter = null;
    ArrayList<String> citiesList = new ArrayList<String>();
    View returnView;

    public CityListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        returnView = inflater.inflate(R.layout.fragment_main, container, false);
        return returnView;
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getCitiesList(1);
        initializeContent();
    }

    private void initializeContent() {
        View.OnClickListener click = new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_send:
                        EditText mEditText = (EditText)returnView.findViewById(R.id.edit_text);
                        int id = TextUtils.isEmpty(mEditText.getText().toString()) ? 1 :
                                Integer.parseInt(mEditText.getText().toString());
                        hideKeyboard();
                        if (id > 99) {
                            Toast.makeText(getActivity(), "id should have 1..99 value",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            getCitiesList(id);
                        }
                        break;
                }
            }
        };

        ((EditText)returnView.findViewById(R.id.edit_text)).setOnEditorActionListener(
                new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    returnView.findViewById(R.id.btn_send).performClick();
                    return true;
                }
                return false;
            }
        });

        returnView.findViewById(R.id.btn_send).setOnClickListener(click);
    }

    private void getCitiesList(final int id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String list = RestClient.getCityList(id);
                citiesList.clear();
                citiesList.addAll(Utils.parse(list));

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        populateList();
                    }
                });

            }
        }).start();
    }

    private void populateList() {
        if (adapter == null) {
            adapter =  new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, citiesList);
            ((ListView) returnView.findViewById(R.id.cities_list)).setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }

    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(((EditText)returnView.findViewById(R.id.edit_text)).
                getWindowToken(), 0);
    }
}
