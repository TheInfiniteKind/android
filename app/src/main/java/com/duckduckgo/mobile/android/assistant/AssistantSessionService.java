package com.duckduckgo.mobile.android.assistant;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.service.voice.VoiceInteractionSession;
import android.service.voice.VoiceInteractionSessionService;

/**
 * Created by fgei on 1/26/17.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class AssistantSessionService extends VoiceInteractionSessionService {
    @Override
    public VoiceInteractionSession onNewSession(Bundle args) {
        return new AssistantSession(this);
    }
}
