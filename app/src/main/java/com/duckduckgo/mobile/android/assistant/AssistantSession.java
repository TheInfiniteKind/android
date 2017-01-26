package com.duckduckgo.mobile.android.assistant;

import android.annotation.TargetApi;
import android.app.assist.AssistContent;
import android.app.assist.AssistStructure;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.service.voice.VoiceInteractionSession;
import android.util.Log;

import com.duckduckgo.mobile.android.activity.DuckDuckGo;

/**
 * Created by fgei on 1/26/17.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class AssistantSession extends VoiceInteractionSession {
    public AssistantSession(Context context) {
        super(context);
    }

    @Override
    public void onHandleAssist(Bundle data, AssistStructure structure, AssistContent content) {
        super.onHandleAssist(data, structure, content);
        Log.e("assist_test", "on handle assist");

    }
}
