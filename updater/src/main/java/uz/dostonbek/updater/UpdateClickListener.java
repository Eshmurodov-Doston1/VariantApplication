package uz.dostonbek.updater;

import android.content.Context;
import android.content.DialogInterface;


import java.net.URL;

import uz.dostonbek.updater.enums.UpdateFrom;


public class UpdateClickListener implements DialogInterface.OnClickListener {

    private final Context context;
    private final UpdateFrom updateFrom;
    private final URL apk;

    public UpdateClickListener(final Context context, final UpdateFrom updateFrom, final URL apk) {
        this.context = context;
        this.updateFrom = updateFrom;
        this.apk = apk;
    }

    @Override
    public void onClick(final DialogInterface dialog, final int which) {
       UtilsLibrary.goToUpdate(context, updateFrom, apk);
    }
}