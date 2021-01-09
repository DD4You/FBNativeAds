package in.dd4you.fbnativeads;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.ads.AdError;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdsManager;

import java.util.ArrayList;
import java.util.List;

import in.dd4you.fbnativeads.databinding.NativeAdLayoutBinding;
import in.dd4you.fbnativeads.databinding.RowItemBinding;

/**
 * Created by Vinay Singh (https://dd4you.in) on 08/01/2021
 * Copyright (c) 2018-2021. All rights reserved.
 * Project Name:- FBNativeAds
 * Package Name:- in.dd4you.fbnativeads
 */
public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Object> objectList;
    private NativeAdsManager nativeAdsManager;
    private List<NativeAd> nativeAds = new ArrayList<>();

    private static int ITEM_TYPE = 0;
    private static int ADS_TYPE = 1;

    private int AD_POSITION = 1;
    private int AD_POSITION_EVERY_COUNT = 5;

    public MainAdapter(Context context, List<Object> objectList, int ListSize) {
        this.context = context;
        this.objectList = objectList;
        int no_of_ad_req = ListSize / (AD_POSITION_EVERY_COUNT - 1);
        nativeAdsManager = new NativeAdsManager(context, "YOUR_PLACEMENT_ID", no_of_ad_req);

    }

    public void initNativeAds() {
        Log.d("TEST_APP", "Size1: " + objectList.size());
        nativeAdsManager.setListener(new NativeAdsManager.Listener() {
            @Override
            public void onAdsLoaded() {
                int count = nativeAdsManager.getUniqueNativeAdCount();
                for (int i = 0; i < count; i++) {
                    NativeAd ad = nativeAdsManager.nextNativeAd();
                    addNativeAd(i, ad);
                }
            }

            @Override
            public void onAdError(AdError adError) {
                Log.d("TEST_APP", "" + adError.getErrorMessage());
            }
        });
        nativeAdsManager.loadAds();
    }

    public void addNativeAd(int i, NativeAd ad) {
        if (ad == null) {
            return;
        }
        if (this.nativeAds.size() > i && this.nativeAds.get(i) != null) {
            this.nativeAds.get(i).unregisterView();
            this.objectList.remove(AD_POSITION + (i * AD_POSITION_EVERY_COUNT));
            this.nativeAds = null;
            this.notifyDataSetChanged();
        }
        this.nativeAds.add(i, ad);

        if (objectList.size() > (AD_POSITION + (i * AD_POSITION_EVERY_COUNT))) {
            objectList.add(AD_POSITION + (i * AD_POSITION_EVERY_COUNT), ad);
            notifyItemInserted(AD_POSITION + (i * AD_POSITION_EVERY_COUNT));
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("TEST_APP", "Size2: " + objectList.size());
        if (viewType == ADS_TYPE) {
            return new NativeViewHolder(LayoutInflater.from(context).inflate(R.layout.native_ad_layout, parent, false));
        } else {
            return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.row_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d("TEST_APP", "Sizeb: " + objectList.size());
        if (holder instanceof MyViewHolder) {
            MainModel mainModel = (MainModel) objectList.get(position);
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder.binding.textView.setText(mainModel.title);
            myViewHolder.binding.textView2.setText(mainModel.subtitle);
        } else {
            NativeAd nativeAd = (NativeAd) objectList.get(position);
            NativeViewHolder nativeViewHolder = (NativeViewHolder) holder;

            nativeViewHolder.binding.nativeAdTitle.setText(nativeAd.getAdvertiserName());
            nativeViewHolder.binding.nativeAdBody.setText(nativeAd.getAdBodyText());
            nativeViewHolder.binding.nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
            nativeViewHolder.binding.nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
            nativeViewHolder.binding.nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
            nativeViewHolder.binding.nativeAdSponsoredLabel.setText(nativeAd.getSponsoredTranslation());

            List<View> clickableViews = new ArrayList<>();
            clickableViews.add(nativeViewHolder.binding.nativeAdTitle);
            clickableViews.add(nativeViewHolder.binding.nativeAdCallToAction);

            // Register the Title and CTA button to listen for clicks.
            nativeAd.registerViewForInteraction(nativeViewHolder.binding.getRoot(),
                    nativeViewHolder.binding.nativeAdMedia,
                    nativeViewHolder.binding.nativeAdIcon, clickableViews);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (objectList.get(position) instanceof NativeAd) {
            return ADS_TYPE;
        } else {
            return ITEM_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        Log.d("TEST_APP", "Sizeb: " + objectList.size());
        return objectList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        RowItemBinding binding;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RowItemBinding.bind(itemView);
        }
    }

    static class NativeViewHolder extends RecyclerView.ViewHolder {
        NativeAdLayoutBinding binding;

        public NativeViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = NativeAdLayoutBinding.bind(itemView);
        }
    }
}
