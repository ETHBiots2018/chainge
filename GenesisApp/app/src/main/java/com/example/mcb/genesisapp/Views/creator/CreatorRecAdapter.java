package com.example.mcb.genesisapp.Views.creator;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mcb.genesisapp.R;

import java.util.ArrayList;
import java.util.List;

import Features.IFeature;
import Features.properties.AbstractProperty;
import Features.properties.IProperty;
import Features.properties.basic.ABasicProperty;
import Features.properties.basic.NameProperty;
import Repository.IRepository;
import Token.IToken;

/**
 * Created by mcb on 10.02.18.
 */

public class CreatorRecAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public final static int NOTHING =-1;
    public final static int STANDARD_HOLDER=-2;
    public final static int ADD_BUTTON=-3;

    public final static int DEFAULT=0;
    public final static int BASIC_NAME_PROPERTY =1;
    public final static int BASIC_SYMBOL_PROPERTY =2;
    public final static int BASIC_DECIMALS_PROPERTY =3;
    public final static int BASIC_GENERAL_SUPPLY_PROPERTY =4;
    public final static int BASIC_GENESIS_SUPPLY_PROPERTY =5;




    List<IFeature> choosableFeatures; // features the user can choose from besides basic Properties
    List<IFeature> chosenFeatures;

    IRepository repository;

    public List<CreatorAdapterListener> listeners;

    public interface CreatorAdapterListener{

        public void tokenCreated(IToken token);


    }


    public CreatorRecAdapter(IRepository repository, List<IFeature> basicProperties,
                             List<IFeature> choosableFeatures) {
        this.choosableFeatures = choosableFeatures;
        this.chosenFeatures = new ArrayList<>(basicProperties);
        this.repository = repository;
    }

    int getFeatureNumber(IFeature feature){
        switch(feature.getFeatureType()) {
            case PROPERTY:
                if(feature instanceof ABasicProperty){
                    ABasicProperty property = (ABasicProperty) feature;
                    switch (property.getBasicPropertyType()){
                        case NAME:
                            return BASIC_NAME_PROPERTY;
                        case SYMBOL:
                            return BASIC_SYMBOL_PROPERTY;

                        case DECIMALS:
                            return BASIC_DECIMALS_PROPERTY;

                        case GENERAL_SUPPLY:
                            return BASIC_GENERAL_SUPPLY_PROPERTY;

                        case GENESIS_SUPPLY:
                            return BASIC_GENESIS_SUPPLY_PROPERTY;

                        default:
                            return DEFAULT;
                    }
                }
                break;
            case UNDERLYING:
                return DEFAULT;
            case OPERATION:
                return DEFAULT;
            default:
                return DEFAULT;
        }
        return DEFAULT;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==chosenFeatures.size()){
            return ADD_BUTTON;
        }
       IFeature feature = chosenFeatures.get(position);

       int featureNumber = getFeatureNumber(feature);

       if(featureNumber>0 && featureNumber<6){
           return STANDARD_HOLDER;
       }else{
           return NOTHING;
       }



    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType==ADD_BUTTON){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.creator_adapter_add_button,
                    parent, false);
            return new PlusButtonViewHolder(v);

        }

        if(viewType==STANDARD_HOLDER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.creator_adapter_standard_text_element,
                    parent, false);
            StandardTextViewHolder holder = new StandardTextViewHolder(v);
            //set listener on holder this
            return holder;

        }else{
            return null;
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder!=null){

            if(holder instanceof StandardTextViewHolder){
                //for all cases dirty hack for now
               // IFeature feature = chosenFeatures.get(position);
                int featureNumber= getFeatureNumber(chosenFeatures.get(position));
                if(featureNumber==1||featureNumber==2)
                    ((StandardTextViewHolder) holder).update(chosenFeatures.get(position),false);
                else
                    ((StandardTextViewHolder) holder).update(chosenFeatures.get(position),true);
            }

        }
    }

    @Override
    public int getItemCount() {
        return chosenFeatures.size()+1;
    }


    public class PlusButtonViewHolder extends RecyclerView.ViewHolder{

        ImageButton button;
        public PlusButtonViewHolder(final View itemView) {
            super(itemView);
            this.button = itemView.findViewById(R.id.creator_add_image_button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(itemView.getContext(),"Coming soon: add other features",
                            Toast.LENGTH_LONG).show();
                }
            });

        }

    }

    public class StandardTextViewHolder extends RecyclerView.ViewHolder{

        TextView description;
        EditText input;
        Button save;


        public StandardTextViewHolder(View itemView) {
            super(itemView);
            this.description = itemView.findViewById(R.id.creator_standard_text_description);
            this.input = itemView.findViewById(R.id.creator_standard_text_edit_text);
            this.save = itemView.findViewById(R.id.creator_standard_text_save_button);
        }

        public void update(final IFeature feature, boolean numberInput){
            if(numberInput) {
                input.setInputType(4096);
            }
            else {
                input.setInputType(1);
            }

            switch(feature.getFeatureType()){
                case PROPERTY:
                    switch (getFeatureNumber(feature)) {
                        case BASIC_NAME_PROPERTY:
                            this.description.setText("TokenName");
                            if (feature.isSet())
                                input.setText((String) ((AbstractProperty) feature).getProperty());
                            else
                                input.setText("");
                            save.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if(!input.getText().toString().equals(""))
                                    ((AbstractProperty) feature).setProperty(input.getText().toString());
                                }
                            });

                            break;
                        case BASIC_SYMBOL_PROPERTY:
                            this.description.setText("Symbol \n (Abbrev.)");
                            if (feature.isSet())
                                input.setText((String) ((AbstractProperty) feature).getProperty());
                            else
                                input.setText("");

                            save.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if(!input.getText().toString().equals(""))
                                    ((AbstractProperty) feature).setProperty(input.getText().toString());
                                }
                            });

                            break;
                        case BASIC_GENERAL_SUPPLY_PROPERTY:
                            this.description.setText("Maximum \n supply \n (neg int for uncapped)");
                            if (feature.isSet())
                                input.setText((Integer) ((AbstractProperty) feature).getProperty());
                            else
                                input.setText("");
                            save.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if(!input.getText().toString().equals(""))
                                        ((AbstractProperty) feature).setProperty(Integer.parseInt(input.getText().toString()));
                                }
                            });

                            break;
                        case BASIC_GENESIS_SUPPLY_PROPERTY:
                            this.description.setText("Genesis Supply ");
                            if (feature.isSet())
                                input.setText((Integer) ((AbstractProperty) feature).getProperty());
                            else
                                input.setText("");
                            save.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if(!input.getText().toString().equals(""))
                                    ((AbstractProperty) feature).setProperty(Integer.parseInt(input.getText().toString()));
                                }
                            });

                            break;
                        case BASIC_DECIMALS_PROPERTY:
                            this.description.setText("Decimals");
                            if (feature.isSet())
                                input.setText((Integer) ((AbstractProperty) feature).getProperty());
                            else
                                input.setText("");

                            save.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if(!input.getText().toString().equals(""))
                                    ((AbstractProperty) feature).setProperty(Integer.parseInt(input.getText().toString()));
                                }
                            });

                            break;
                        default:
                            this.description.setText(feature.toString());
                    }
                    break;
                default:
                    this.description.setText(feature.toString());
            }
        }
    }

    public List<IFeature> getChosenFeatures() {
        return new ArrayList<>(chosenFeatures);
    }

    Object nullHandling(Object o){
        if(o==null)
            return "";
        else
            return o;
    }

    public void updateDataList(List<IFeature> basicProperties){
        if(basicProperties == null){
            basicProperties = new ArrayList<>();
        }
        if(this.chosenFeatures != null){
            chosenFeatures.clear();
            notifyDataSetChanged();
            chosenFeatures.addAll(basicProperties);

        }else{
            this.chosenFeatures = new ArrayList<>(basicProperties);
        }
        notifyDataSetChanged();
    }

}
