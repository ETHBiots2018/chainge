package com.example.mcb.genesisapp.Views;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.example.mcb.genesisapp.R;
import com.example.mcb.genesisapp.Repository.SQLite.BasicSQLiteRepo;
import com.example.mcb.genesisapp.State.StateCallback;

import java.util.List;

import Features.IFeature;

/**
 * Fragment holding the Projects view.
 */
public class FProjects extends Fragment implements StateCallback.StateListener{

    private StateCallback stateActivity;
    public Action action;

    public FProjects() {
    }

    public static FProjects newInstance(){
        return new FProjects();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // custom
        // create action object
        this.action = new Action(stateActivity);

        action.repo.addUser(0,1000, 1);
        action.repo.addUser(1, 10000, 2);
        action.repo.addUser(2, 500, 1);
        action.repo.addWebsites(0, "sbb.ch", 100);


        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_fprojects, container, false);

        Button button = view.findViewById(R.id.creator_fragment_open_browser_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*
                    Simulate Webservice
                 */
                // use for presentation purpose only
                // first call: positive rating, second call: negative rating
                if(action.repo.readWebsiteEval(0) < 105){
                    action.rateWebsite(2, 0, 1);
                } else {
                    action.rateWebsite(2, 0, 0);
                }

                // Open modified Website for demo
                stateActivity.openWebLinkInBrowser("https://biotschainge.github.io/");
            }
        });


        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            stateActivity = (StateCallback) getActivity();
            stateActivity.registerStateListener(this);


        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement StateCallback");
        }
        try {
            //    ((StateActivity) getActivity()).registerStateListener(this);

        } catch (ClassCastException e) {
            throw new ClassCastException(
                    "FBaseList must implement StateListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            stateActivity.unRegisterStateListener(this);

            //  ((StateActivity) getActivity()).unRegisterStateListener(this);

        } catch (ClassCastException e) {
            throw new ClassCastException(
                    "\"FBaseList must implement StateListener");
        }

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void dataBaseModified(String type_of_object, String subTypeObject) {

    }
}

