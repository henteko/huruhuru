package com.henteko07.huruhuru;

import android.app.Activity;
import android.content.Context;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.PushService;

/**
 * Created by pepe_air on 2014/08/13.
 */
public class ParseUtil {
    private static final String PARSE_ENABLE_CHANNEL = "enable_channel";
    private static final String PARSE_NOT_ENABLE_CHANNEL = "not_enable_channel";

    private static Context mContext;
    private static Activity mActivity;

    ParseUtil(Context context, Activity activity) {
        this.mContext = context;
        this.mActivity = activity;
        Parse.initialize(mContext, BuildConfig.PARSE_APPLICATION_ID, BuildConfig.PARSE_CLIENT_KEY);
    }

    public void updateChannel(Boolean status) {
        String parseChannel = (status) ? PARSE_ENABLE_CHANNEL : PARSE_NOT_ENABLE_CHANNEL;
        String parseUnChannel = (status) ? PARSE_NOT_ENABLE_CHANNEL : PARSE_ENABLE_CHANNEL;
        PushService.subscribe(mContext, parseChannel, mActivity.getClass());
        PushService.unsubscribe(mContext, parseUnChannel);
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
