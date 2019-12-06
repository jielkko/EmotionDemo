package com.hjl.emotiondemo.common;

import android.content.Context;

public class ContextUtil {
    private static Context context;

    public static void init(Context ctx) {
        context = ctx;
    }

    public static Context getContext() {
        return context;
    }
}
