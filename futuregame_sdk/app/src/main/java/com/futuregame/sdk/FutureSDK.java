package com.futuregame.sdk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.futuregame.sdk.model.FutureEntity;

public class FutureSDK {

    public interface LoginListener {
        void onComplete(int Code, String retMsg, FutureEntity futureEntity);
    }

    public static void init(Activity activity) {
        Singleton.init(activity);
    }

    public static Bundle getMetaData() {
        return Singleton.getMetaData();
    }

    public static void showFloatingView() {
        Singleton.showFloatingView();
    }

    public static void doLogin(Activity activity, LoginListener loginListener) {
        Singleton.doLogin(activity, loginListener);
    }

    public static void logout() {
        Singleton.logout();
    }

    public static void onNewIntent(Activity activity, Intent intent) {
    }

    public static void onStart(Activity activity) {

    }

    public static void onResume(Activity activity) {

    }

    public static void onPause(Activity activity) {

    }

    public static void onStop(Activity activity) {

    }

    public static void onRestart(Activity activity) {

    }

    public static void onDestroy(Activity activity) {

    }

    public static void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {

    }

}
