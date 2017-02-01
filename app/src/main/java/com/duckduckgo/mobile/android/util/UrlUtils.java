package com.duckduckgo.mobile.android.util;

import android.net.Uri;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by fgei on 2/1/17.
 */

public class UrlUtils {

    public static final String SEARCH_URL = "https://www.duckduckgo.com/?ko=-1&q=";
    public static final String SEARCH_URL_JAVASCRIPT_DISABLED = "https://duckduckgo.com/html/?q=";

    public static final String AUTO_COMPLETE_URL = "https://duckduckgo.com/ac/?q=";
    public static final String SEARCH_URL_ONION = "http://3g2upl4pq6kufc4m.onion/?ko=-1&q=";

    public static String getSearchUrlForTerm(String term) {
        return getSearchUrlForterm(PreferencesManager.getEnableJavascript() ? SEARCH_URL : SEARCH_URL_JAVASCRIPT_DISABLED, term);
    }

    public static String getSearchUrlForterm(String baseUrl, String term) {
        StringBuilder urlBuilder = new StringBuilder(baseUrl);
        try {
            urlBuilder.append(URLEncoder.encode(term, "UTF-8"));
            if(DDGControlVar.regionString.equals("wt-wt")) {
                urlBuilder.append("&kl=")
                        .append(URLEncoder.encode(DDGControlVar.regionString, "UTF-8"));
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        return urlBuilder.toString();
    }

    public static boolean isSerpUrl(String url) {
        return url.contains("duckduckgo.com");
    }

    /**
     * Checks to see if URL is DuckDuckGo SERP
     * Returns the query if it's a SERP, otherwise null
     *
     * @param url
     * @return
     */
    public static String getQueryIfSerp(String url) {
        if(!isSerpUrl(url)) {
            return null;
        }

        Uri uri = Uri.parse(url);
        String query = uri.getQueryParameter("q");
        if(query != null)
            return query;

        String lastPath = uri.getLastPathSegment();
        if(lastPath == null)
            return null;

        if(!lastPath.contains(".html")) {
            return lastPath.replace("_", " ");
        }

        return null;
    }

    public static String getUrlToDisplay(String url) {
        if(url==null || url.length()==0) {
            return "";
        }
        if (url.startsWith("https://")) {
            url = url.replace("https://", "");
        } else if (url.startsWith("http://")) {
            url = url.replace("http://", "");
        }
        if (url.startsWith("www.")) {
            url = url.replace("www.", "");
        }
        return url;
    }
}
