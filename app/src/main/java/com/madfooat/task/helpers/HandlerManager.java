package com.madfooat.task.helpers;

import android.os.Handler;
import android.os.Message;

/**
 * Created by MaysAtari on 6/22/2018.
 */

public class HandlerManager {

    private Handler handler;


    public HandlerManager(OnResponseListener listener) {
        handler=new ResponseHandler(listener);
    }

    public Handler getHandler() {
        return handler;
    }


    private static class ResponseHandler extends Handler {
        private final OnResponseListener listener;

        private ResponseHandler(OnResponseListener listener) {
            this.listener = listener;
        }

        @Override
        public void handleMessage(Message msg) {
            if (listener != null) {
                listener.onResponse(msg);
            }
        }
    }

    public interface OnResponseListener{
        void onResponse(Message msg);
    }

}
