package uz.dostonbek.updater;

import android.content.Context;
import android.content.DialogInterface;

public class DisableClickListener implements DialogInterface.OnClickListener {

    private final LibraryPreferences libraryPreferences;

    public DisableClickListener(final Context context) {
        libraryPreferences = new LibraryPreferences(context);
    }

    @Override
    public void onClick(final DialogInterface dialog, final int which) {
        libraryPreferences.setAppUpdaterShow(false);
    }
}
