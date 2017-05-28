package com.example.tools.traceur;

import com.tspoon.traceur.Traceur;

/**
 * We are using an interface to initialize Traceur that gets implemented both in the debug folder and in the release folder. In the debug
 * folder it starts up Traceur, while in the release folder it does nothing. This way we don't need to keep the "compile" dependency on
 * Traceur, but we can replace it with a "debugCompile" dependency.
 */
public class TraceurToolImpl implements TraceurTool {

    @Override
    public void init() {
        Traceur.enableLogging();
    }

}
