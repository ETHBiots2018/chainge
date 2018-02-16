package com.example.mcb.genesisapp.Views;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mcb.genesisapp.R;
import com.example.mcb.genesisapp.State.StateCallback;

/**
 * Fragment holding the Obtainer view
 */
public class ObtainerFragment extends Fragment implements StateCallback.StateListener {
    private StateCallback stateActivity;
    public Action action;
    private View localView;

    public ObtainerFragment() {
        // Required empty public constructor
    }

    public static ObtainerFragment newInstance(){
        return new ObtainerFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // custom
        // create action object
        this.action = new Action(stateActivity);
        View view = inflater.inflate(R.layout.fragment_obtainer, container, false);
        this.localView = view;
        // Set balance
        TextView tb = view.findViewById(R.id.trust_balance);
        tb.setText("Trust Balance: "+action.repo.readUserTrust(2));
        TextView eb = view.findViewById(R.id.evaluation_balance);
        eb.setText("Evaluation Balance: "+action.repo.readUserEval(2));
        TextView ebw = view.findViewById(R.id.evaluation_balance_web);
        ebw.setText("Evaluation Balance: "+action.repo.readWebsiteEval(0));

        Button button = view.findViewById(R.id.creator_fragment_get_balance_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // REFRESH
                TextView tb = localView.findViewById(R.id.trust_balance);
                tb.setText("Trust Balance: " + action.repo.readUserTrust(2));
                TextView eb = localView.findViewById(R.id.evaluation_balance);
                eb.setText("Evaluation Balance: "+action.repo.readUserEval(2));
                TextView ebw = localView.findViewById(R.id.evaluation_balance_web);
                ebw.setText("Evaluation Balance: "+action.repo.readWebsiteEval(0));

            }
        });


        // Inflate the layout for this fragment
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
