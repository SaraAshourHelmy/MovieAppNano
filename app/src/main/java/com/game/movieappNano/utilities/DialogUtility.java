package com.game.movieappNano.utilities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.game.movieappNano.R;

/**
 * Created by Sara on 6/3/2017.
 */

public class DialogUtility {

    static ProgressDialog progressDialog;
    static Dialog dialog;

    public static void showProgressDialog(Context context) {
        if (progressDialog != null)
            progressDialog = null;

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getString(R.string.wait));
        progressDialog.show();
    }

    public static void dismissProgressDialog() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }

    public static void showMessageDialog(Context context, String msg) {

        if (dialog != null)
            dialog = null;

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_layout);

        TextView tv_msg = (TextView) dialog.findViewById(R.id.tv_dialog_msg);
        Button btn_ok = (Button) dialog.findViewById(R.id.btn_dialog_ok);

        tv_msg.setText(msg);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
