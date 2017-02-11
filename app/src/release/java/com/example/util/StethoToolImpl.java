package com.example.util;

import android.content.Context;
import com.example.tools.stetho.StethoTool;

/**
 * We are using an interface to initialize Stetho that gets implemented both in the debug folder and in the release folder. In the debug
 * folder it starts up Stetho, while in the release folder it does nothing. This way we don't need to keep the "compile" dependency on
 * Stetho, but we can replace it with a "debugCompile" dependency.
 */
public class StethoToolImpl implements StethoTool {

    @SuppressWarnings("PMD.UnusedFormalParameter")
    public StethoToolImpl(Context context) {
        // Constructor needs to be the same as the debug build that initializes stetho.
    }

    @Override
    public void init() {
        // Do nothing for release builds.
    }

}
