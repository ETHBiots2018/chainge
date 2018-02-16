package com.example.mcb.genesisapp.Views.creator;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.mcb.genesisapp.R;
import com.example.mcb.genesisapp.State.StateCallback;

import java.util.ArrayList;
import java.util.List;

import Features.IFeature;
import Features.properties.basic.DecimalsProperty;
import Features.properties.basic.GeneralSupplyProperty;
import Features.properties.basic.GenesisSupplyProperty;
import Features.properties.basic.NameProperty;
import Features.properties.basic.SymbolProperty;
import Repository.IRepository;

/**
 * Fragment holding the logic for creating a new Token
 */
public class CreatorFragment extends Fragment implements StateCallback.StateListener{


    IRepository repository;
    protected CreatorRecAdapter recAdapter;
    StateCallback stateActivity;
    protected RecyclerView recyclerView;

    protected Button saveToken;

    public CreatorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment CreatorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreatorFragment newInstance() {
        CreatorFragment fragment = new CreatorFragment();
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_creator, container, false);

        /*
        Initialise Recyclerview
         */
        recyclerView =  view.findViewById(R.id.creator_fragment_recyclerview);
       // recyclerView.setHasFixedSize(true); // for speedup
        this.recAdapter = new CreatorRecAdapter(this.repository,createNewBasicProperties(),
                new ArrayList<IFeature>()); // adapter creating the views dipslayed in recyclerview
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(this.recAdapter);

        /*
            Initialise Save Token Button
         */
        this.saveToken = view.findViewById(R.id.creator_fragment_save_token_button);
        saveToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<IFeature> features = recAdapter.getChosenFeatures();

                boolean allSpecified = true;
                for(IFeature feature:features){
                    allSpecified = allSpecified && feature.isSet();
                }
                if(allSpecified){
                    repository.crateNewToken(features);
                    Toast.makeText(getContext(),"New Token Created",Toast.LENGTH_LONG).show();
                    recAdapter.updateDataList(createNewBasicProperties());
                    stateActivity.dataBaseModified();
                }else{
                    Toast.makeText(getContext(),"Not all Features are specified, \n please save them first" +
                            "",Toast.LENGTH_LONG).show();
                }

            }
        });


        return view;

    }


    protected List<IFeature> createNewBasicProperties(){
        List<IFeature> basicProperties = new ArrayList<>();

        basicProperties.add(new NameProperty());
        basicProperties.add(new SymbolProperty());
        basicProperties.add(new GeneralSupplyProperty());
        basicProperties.add(new DecimalsProperty());
        basicProperties.add(new GenesisSupplyProperty());
        return basicProperties;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            stateActivity = (StateCallback) getActivity();
            stateActivity.registerStateListener(this);
            this.repository = stateActivity.getRepository();

        } catch (ClassCastException e) {
            throw new ClassCastException(this.getClass().getName()
                    + " must implement StateCallback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        stateActivity.unRegisterStateListener(this);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void dataBaseModified(String type_of_object, String subTypeObject) {

    }
}
