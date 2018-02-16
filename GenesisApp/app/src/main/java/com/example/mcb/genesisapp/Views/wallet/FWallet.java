package com.example.mcb.genesisapp.Views.wallet;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mcb.genesisapp.R;
import com.example.mcb.genesisapp.State.StateActivity;
import com.example.mcb.genesisapp.State.StateCallback;
import com.example.mcb.genesisapp.Views.creator.CreatorRecAdapter;

import java.util.ArrayList;

import Features.IFeature;
import Repository.IRepository;
import Token.IToken;

/**
 * Fragment holding the wallet.
 */
public class FWallet extends Fragment implements StateCallback.StateListener {

    protected StateCallback stateActivity;
    protected RecyclerView recyclerView;
    protected WalletAdapter recAdapter;
    protected IRepository<IToken> repository;

    public FWallet() {
        // Required empty public constructor
    }

    public static FWallet newInstance(){
        return new FWallet();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fwallet, container, false);

        /*
        Initialise Recyclerview
         */
        recyclerView =  view.findViewById(R.id.fragment_wallet_recycler_view);
        // recyclerView.setHasFixedSize(true);




        this.recAdapter = new WalletAdapter(new ArrayList<IToken>(repository.getAllTokens()));

        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

        recyclerView.setAdapter(this.recAdapter);
        return view;
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
        this.recAdapter.updateDataList(new ArrayList<IToken>(this.repository.getAllTokens()));
    }
}
