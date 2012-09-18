/*
 * Copyright (c) 2010, MoPub Inc.
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

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.location.Location;
import android.util.Log;
import android.view.Gravity;
import android.widget.FrameLayout;

import de.madvertise.android.sdk.MadvertiseView;

public class MadvertiseAdapter extends BaseAdapter implements MadvertiseView.MadvertiseViewCallbackListener {
    
    private de.madvertise.android.sdk.MadvertiseView mAdView;

    @Override
    public void loadAd() {
        if (isInvalidated()) return;

        mAdView = new de.madvertise.android.sdk.MadvertiseView(mMoPubView.getActivity());
        mAdView.setMadvertiseViewCallbackListener(this);

        Log.d("MoPub", "Madvertise loading..."); 
	}

    @Override
    public void invalidate() {
        if (mAdView != null) {
            mMoPubView.removeView(mAdView);
        }
        super.invalidate();
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	* Notifies the listener on success or failure
	*
	* @param succeed true, if an ad could be loaded, else false
	* @param madView specified view
	*/
	@Override
	public void onLoaded(final boolean succeed, final MadvertiseView madView)
	{
		if (isInvalidated()) return;
        
        if (succeed)
        {
			Log.d("MoPub", "Madvertise load succeeded. Showing ad..."); 
			mMoPubView.removeAllViews();
			FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
					FrameLayout.LayoutParams.FILL_PARENT, 
					FrameLayout.LayoutParams.FILL_PARENT);
			layoutParams.gravity = Gravity.CENTER;
			mMoPubView.addView(mAdView, layoutParams);

			mMoPubView.nativeAdLoaded();
			mMoPubView.trackNativeImpression();
		}
		else
		{
			Log.d("MoPub", "Madvertise failed, onLoaded. Trying another"); 
			mMoPubView.loadFailUrl();
		}
	}

	/**
	* Notifies the listener when exceptions are thrown
	*
	* @param the thrown exception
	*/
	@Override
	public void onError(final Exception exception)
	{
		if (isInvalidated()) return;

		Log.d("MoPub", "Madvertise failed, exception. Trying another"); 
		mMoPubView.loadFailUrl();
	};

	/**
	* Notifies the listener when an illegal HTTP status code was received.
	* This method is not called when the status code is okay (200)
	*
	* @param statusCode the HTTP status code
	* @param message a message with a reason of the problem
	*/
	@Override
	public void onIllegalHttpStatusCode(final int statusCode, final String message)
	{
		if (isInvalidated()) return;

		Log.d("MoPub", "Madvertise failed, http status code. Trying another"); 
		mMoPubView.loadFailUrl();
	}

	/**
	* Notifies the listener when an ad is clicked
	*/
	@Override
	public void onAdClicked()
	{
		if (isInvalidated()) return;
        
        Log.d("MoPub", "Madvertise clicked"); 
        mMoPubView.registerClick();
	}

	/**
	* Notifies the listener when a rich media ad is expanded so that the
	* application can be paused.
	*/
	@Override
	public void onApplicationPause()
	{
	}

	/**
	* Notifies the listener when a rich media ad is closed so that the
	* application can resume.
	*/
	@Override
	public void onApplicationResume()
	{
	}
}
