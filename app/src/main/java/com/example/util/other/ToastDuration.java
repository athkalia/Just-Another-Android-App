package com.example.util.other;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;

@IntDef({LENGTH_SHORT, LENGTH_LONG})
@Retention(RetentionPolicy.SOURCE)
public @interface ToastDuration {

}
