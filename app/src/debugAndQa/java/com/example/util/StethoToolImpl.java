package com.example.util;

import android.content.Context;
import com.example.tools.stetho.StethoTool;
import com.facebook.stetho.Stetho;

/**
 * We are using an interface to initialize Stetho that gets implemented both in the debug folder and in the release folder. In the debug
 * folder it starts up Stetho, while in the release folder it does nothing. This way we don't need to keep the "compile" dependency on
 * Stetho, but we can replace it with a "debugCompile" dependency.
 */
public class StethoToolImpl implements StethoTool {

    private final Context context;

    public StethoToolImpl(Context context) {

        this.context = context;
    }

    @Override
    public void init() {

        Stetho.initializeWithDefaults(context);
    }

}
