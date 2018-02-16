package com.example.mcb.genesisapp.Layers.base;


import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;


import com.eftimoff.viewpagertransformers.BaseTransformer;
import com.eftimoff.viewpagertransformers.DepthPageTransformer;
import com.example.mcb.genesisapp.R;
import com.example.mcb.genesisapp.State.StateActivity;
import com.example.mcb.genesisapp.State.StateCallback;
import com.example.mcb.genesisapp.Views.creator.CreatorFragment;
import com.example.mcb.genesisapp.Views.FProjects;
import com.example.mcb.genesisapp.Views.wallet.FWallet;
import com.example.mcb.genesisapp.Views.ObtainerFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Base Layer containing the viewpager containing the Creator, Wallet, Obtainer and Projects Fragment
 * A simple {@link Fragment} subclass.
 */
public class FBaseList extends Fragment implements StateActivity.StateListener {


    ViewPager viewPager;
    private StateCallback stateActivity;

    public FBaseList() {
        // Required empty public constructor
    }

    public static FBaseList newInstance() {
        FBaseList fragment = new FBaseList();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      //  stateActivity.setFamVisibility();
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_base_list_new, container, false);
        viewPager = (ViewPager) rootView.findViewById(R.id.fragment_base_list_viewpager);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        Drawable background = viewPager.getBackground();
        if(background!=null)
        background.setAlpha(20);

        List<Fragment> fragmentList = new ArrayList<>();

        fragmentList.add(CreatorFragment.newInstance());
        fragmentList.add(FWallet.newInstance());
        fragmentList.add(ObtainerFragment.newInstance());
        fragmentList.add(FProjects.newInstance());
//        fragmentList.add(ContactListFragment.newInstance());
//        fragmentList.add(WorkViewPagerFragmentNewNew.newInstance(false));

        BaseListViewPagerAdapter viewPagerAdapter = new BaseListViewPagerAdapter
                (getChildFragmentManager(),fragmentList);
        viewPager.setOffscreenPageLimit(3);

        viewPager.setAdapter(viewPagerAdapter);

        viewPager.setPageTransformer(true, new DepthPageTransformer()
        );

        for(Fragment fragment: fragmentList){
            fragment.setUserVisibleHint(true);
        }


        viewPager.setCurrentItem(1);



        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 2){
                 //   stateActivity.hideAddFab();
                } else {
                //    stateActivity.showAddFab();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        SmartTabLayout viewPagerTab = (SmartTabLayout) rootView.findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);

        return rootView;
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


        if(viewPager.getCurrentItem() == 1) {
            //if current Item equals the ContactList
            //do nothing and let stateactivity decide what to do
            return false;
        }
        //swtich to the contact List
        viewPager.setCurrentItem(1);
        return true;
    }


    @Override
    public void dataBaseModified(String type_of_object, String subTypeObject) {


    }




    public static class ZoomOutSlideTransformer extends BaseTransformer {
        private static final float MIN_SCALE = 0.75F;
        public ZoomOutSlideTransformer() {
        }
        protected void onTransform(View view, float position) {
            if(position >= -1.0F || position <= 1.0F) {
                float height = (float)view.getHeight();
                float scaleFactor = Math.max(MIN_SCALE, 1.0F - Math.abs(position));
                float vertMargin = height * (1.0F - scaleFactor) / 2.0F;
                float horzMargin = (float)view.getWidth() * (1.0F - scaleFactor) / 2.0F;
                view.setPivotY(0.5F * height);
                if(position < 0.0F) {
                    view.setTranslationX(horzMargin - vertMargin / 2.0F);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2.0F);
                }
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);
            }
        }
    }
}
