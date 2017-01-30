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
import android.view.View;

import com.duckduckgo.mobile.android.activity.DuckDuckGo;

/**
 * Created by fgei on 1/26/17.
 */
@TargetApi(Build.VERSION_CODES.M)
public class AssistantSession extends VoiceInteractionSession {

    public static final String EXTRA_SEARCH = "extra_search";

    private String search;

    public AssistantSession(Context context) {
        super(context);
    }

    @Override
    public void onHandleAssist(Bundle data, AssistStructure structure, AssistContent content) {
        super.onHandleAssist(data, structure, content);
        Log.e("assist_test", "on handle assist");
        CharSequence text = null;
        for(int i=0; i<structure.getWindowNodeCount(); i++) {
            AssistStructure.WindowNode node = structure.getWindowNodeAt(i);
            AssistStructure.ViewNode viewNode = node.getRootViewNode();
            printViewNode(viewNode);
        }
        Intent intent = new Intent(getContext(), DuckDuckGo.class);
        intent.setAction(Intent.ACTION_ASSIST);
        getContext().startActivity(intent);
    }

    private void printViewNode(AssistStructure.ViewNode viewNode) {
        Log.e("assist_test", "text: "+viewNode.getText()+" class name"+viewNode.getClassName()+" extras!=null: "+(viewNode.getExtras()!=null));
        for(int i=0; i<viewNode.getChildCount(); i++) {
            AssistStructure.ViewNode child = viewNode.getChildAt(i);
            printViewNode(child);
        }
    }
}
