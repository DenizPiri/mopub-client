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

import com.revmob.RevMob;
import com.revmob.ads.banner.Banner;

public class RevMobAdapter extends BaseAdapter {

    private RevMob revmob;
    private Banner banner;

    @Override
    public void loadAd() {
        if (isInvalidated()) return;

		revmob = RevMob.start(mMoPubView.getActivity(), "504cf4a136296b0c00000064");
        banner = revmob.createBanner(mMoPubView.getActivity());

		mMoPubView.removeAllViews();
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
			FrameLayout.LayoutParams.FILL_PARENT, 
			FrameLayout.LayoutParams.FILL_PARENT);
		layoutParams.gravity = Gravity.CENTER;
		mMoPubView.addView(banner, layoutParams);

		mMoPubView.nativeAdLoaded();
		mMoPubView.trackNativeImpression();
        Log.d("MoPub", "Revmob loading..."); 
	}

    @Override
    public void invalidate() {
        if (banner != null) {
            mMoPubView.removeView(banner);
        }
        super.invalidate();
    }
}
