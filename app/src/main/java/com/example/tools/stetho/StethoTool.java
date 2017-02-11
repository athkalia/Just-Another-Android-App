package com.example.tools.stetho;

/**
 * We are using an interface to initialize Stetho that gets implemented both in the debug folder and in the release folder. In the debug
 * folder it starts up Stetho, while in the release folder it does nothing. This way we don't need to keep the "compile" dependency on
 * Stetho, but we can replace it with a "debugCompile" dependency.
 */
public interface StethoTool {

    void init();

}
