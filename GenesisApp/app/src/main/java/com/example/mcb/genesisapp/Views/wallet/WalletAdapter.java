package com.example.mcb.genesisapp.Views.wallet;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mcb.genesisapp.R;

import java.util.ArrayList;
import java.util.List;

import Features.IFeature;
import Features.properties.AbstractProperty;
import Token.IToken;

/**
 * Created by mcb on 11.02.18.
 */

public class WalletAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{



    List<IToken> dataList;

    public WalletAdapter(List<IToken> dataList) {
        this();
        if(dataList!=null)
            this.dataList = dataList;
    }

    public WalletAdapter() {
        this.dataList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallet_adapter_standard_list_element,
                parent, false);
        return new StandardTextViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((StandardTextViewHolder)holder).update(this.dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return this.dataList.size();
    }
    public void updateDataList(List<IToken> thingList){
        if(thingList == null){
            thingList = new ArrayList<>();
        }
        if(this.dataList != null){
            dataList.clear();
            notifyDataSetChanged();
            dataList.addAll(thingList);

        }else{
            this.dataList = thingList;
        }
        notifyDataSetChanged();
    }

    public class StandardTextViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView symbol;
        TextView decimals;
        TextView premined;
        TextView capped;




        public StandardTextViewHolder(View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.wallet_standard_text_name);
            this.symbol = itemView.findViewById(R.id.wallet_standard_text_symbol);
            this.decimals = itemView.findViewById(R.id.wallet_standard_text_decimals);
            this.capped = itemView.findViewById(R.id.wallet_standard_text_capped);
            this.premined = itemView.findViewById(R.id.wallet_standard_text_pre_mined);

        }

        public void update(final IToken token){
            name.setText("Name: \n"+ token.getName());
            symbol.setText("Symbol: \n" + token.getSymbol());
            decimals.setText(" Decimals: \n" +token.getDecimals()  );
            if(token.preMined()){
                premined.setText("PreMined with: \n"+token.preMinedAmount() );
            }else
                premined.setText("not Pre-Mined");
            if(token.isCapped())
                capped.setText("capped at: \n" +token.cappedAmount());
            else
                capped.setText("not capped");

        }
    }
}
