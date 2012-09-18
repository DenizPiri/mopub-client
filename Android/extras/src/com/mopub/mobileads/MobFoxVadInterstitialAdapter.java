/*
 * Copyright (c) 2011, MoPub Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'MoPub Inc.' nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.mopub.mobileads;

import android.location.Location;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.mobfox.video.sdk.MobFoxAdListener;
import com.mobfox.video.sdk.MobFoxAdManager;
import com.mobfox.video.sdk.RequestException;
import com.mobfox.video.sdk.RichMediaAdData;

public class MobFoxVadInterstitialAdapter extends BaseInterstitialAdapter implements MobFoxAdListener {

    private MobFoxAdManager mInterstitialAd;
    
    @Override
    public void init(MoPubInterstitial interstitial, String jsonParams) {
        super.init(interstitial, jsonParams);
        
        Log.e("ngine-mopub", "MOBFOX VAD INTERSTITIAL ADAPTER !!!");
        
        // The following parameters are required. Fail if they aren't set. 
        JSONObject object; 
        String pubId;
        try { 
            object = (JSONObject) new JSONTokener(mJsonParams).nextValue(); 
			mInterstitialAd = new MobFoxAdManager(mInterstitial.getActivity(), object.getString("site_id"), object.optString("location", "0") == "1", object.optString("predownload", "1") == "1");
			mInterstitialAd.setListener(this);
            Log.e("ngine-mopub-MOBFOX", object.toString());
        } catch (Exception exc) {
            if (mAdapterListener != null) mAdapterListener.onNativeInterstitialFailed(this);
            Log.e("ngine-mopub-MOBFOX-exc", exc.toString());
            return;
        }
    }
    
    
    ////////////////////// MOBFOX HANDLERS ///////////////////////////
    @Override
	public void noAdFound() {
		if (isInvalidated()) return;
        Log.d("MoPub", "MobFox VAD noAdFound.");
        if (mAdapterListener != null) mAdapterListener.onNativeInterstitialFailed(this);
	}
	
	@Override
	public void adShown(RichMediaAdData arg0, boolean arg1) {
		Log.d("MoPub", "MobFox VAD adShown.");
	}
	
	@Override
	public void adLoadSucceeded(RichMediaAdData arg0) {
		if (isInvalidated()) return;
        
        Log.d("MoPub", "MobFox VAD adLoadSucceeded.");
        if (mAdapterListener != null) mAdapterListener.onNativeInterstitialLoaded(this);
	}
	
	@Override
	public void adLoadFailed(RequestException arg0) {
		if (isInvalidated()) return;
        Log.d("MoPub", "MobFox VAD adLoadFailed.");
        if (mAdapterListener != null) mAdapterListener.onNativeInterstitialFailed(this);
	}
	
	@Override
	public void adClosed(RichMediaAdData arg0, boolean arg1) {
		Log.d("MoPub", "MobFox VAD adClosed.");
	}
    ///////////////////////// MOBFOX HANDLERS /////////////////////


    @Override
    public void loadInterstitial() {
        if (isInvalidated()) return;
        Log.d("MoPub", "MobFox VAD requestAd.");
        try
        {
			if (mInterstitialAd != null)
				mInterstitialAd.requestAd();
		} catch(Exception e) { }
    }

    @Override
    public void showInterstitial() {
        if (isInvalidated()) return;
        Log.d("MoPub", "MobFox VAD showAd.");
        try
        {
			if (mInterstitialAd != null)
				mInterstitialAd.showAd();
		} catch(Exception e) { }
    }
}
