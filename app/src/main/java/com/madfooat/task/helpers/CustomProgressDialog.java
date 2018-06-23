package com.madfooat.task.helpers;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.madfooat.task.R;

/**
 * Created by MaysAtari on 6/22/2018.
 */

public class CustomProgressDialog {

    private Dialog alertDialog;
    private Context context;

    public CustomProgressDialog(Context context) {
        alertDialog = new Dialog(context);
        this.context = context;
    }


    public void showCustomDialog() {
        if(!((Activity) context).isFinishing()) {
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertDialog.setContentView(R.layout.dialog_progress);

            if (alertDialog.getWindow() != null)
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            alertDialog.setCancelable(false);


            alertDialog.show();
        }
    }

    public void hideCustomDialog() {
        if (((Activity) context).isDestroyed())
            return;

        if (alertDialog != null && alertDialog.isShowing())
            alertDialog.hide();
    }

    public void dismissCustomDialog() {
        if (((Activity) context).isDestroyed())
            return;

        if (alertDialog != null && alertDialog.isShowing())
            alertDialog.dismiss();
    }
}
