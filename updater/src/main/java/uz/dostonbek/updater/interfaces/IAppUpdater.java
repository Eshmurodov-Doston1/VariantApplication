package uz.dostonbek.updater.interfaces;

import android.content.DialogInterface;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import uz.dostonbek.updater.AppUpdater;
import uz.dostonbek.updater.enums.AppUpdaterError;
import uz.dostonbek.updater.enums.Display;
import uz.dostonbek.updater.enums.Duration;
import uz.dostonbek.updater.enums.UpdateFrom;
import uz.dostonbek.updater.objects.Update;


public interface IAppUpdater {

    AppUpdater setDisplay(Display display);


    AppUpdater setUpdateFrom(UpdateFrom updateFrom);

    AppUpdater setDuration(Duration duration);

    AppUpdater setGitHubUserAndRepo(@NonNull String user, @NonNull String repo);


    AppUpdater setUpdateXML(@NonNull String xmlUrl);



    AppUpdater setUpdateJSON(@NonNull String jsonUrl);


    AppUpdater showEvery(Integer times);

    AppUpdater showAppUpdated(Boolean res);

    AppUpdater setDialogTitleWhenUpdateAvailable(@NonNull String title);

    AppUpdater setDialogTitleWhenUpdateAvailable(@StringRes int textResource);


    AppUpdater setTitleOnUpdateAvailable(@NonNull String title);

    /**
     * Set a custom title for the dialog when an update is available.
     *
     * @param textResource resource from the strings xml file for the dialog
     * @return this
     */
    AppUpdater setTitleOnUpdateAvailable(@StringRes int textResource);

    /**
     * Set a custom description for the dialog when an update is available.
     *
     * @param description for the dialog
     * @return this
     * @deprecated use {@link #setContentOnUpdateAvailable(String)} instead
     */
    AppUpdater setDialogDescriptionWhenUpdateAvailable(@NonNull String description);

    /**
     * Set a custom description for the dialog when an update is available.
     *
     * @param textResource resource from the strings xml file for the dialog
     * @return this
     * @deprecated use {@link #setContentOnUpdateAvailable(int)} instead
     */
    AppUpdater setDialogDescriptionWhenUpdateAvailable(@StringRes int textResource);

    /**
     * Set a custom description for the dialog when an update is available.
     *
     * @param description for the dialog
     * @return this
     */
    AppUpdater setContentOnUpdateAvailable(@NonNull String description);

    /**
     * Set a custom description for the dialog when an update is available.
     *
     * @param textResource resource from the strings xml file for the dialog
     * @return this
     */
    AppUpdater setContentOnUpdateAvailable(@StringRes int textResource);

    /**
     * Set a custom title for the dialog when no update is available.
     *
     * @param title for the dialog
     * @return this
     * @deprecated use {@link #setTitleOnUpdateNotAvailable(String)} instead
     */
    AppUpdater setDialogTitleWhenUpdateNotAvailable(@NonNull String title);

    /**
     * Set a custom title for the dialog when no update is available.
     *
     * @param textResource resource from the strings xml file for the dialog
     * @return this
     * @deprecated use {@link #setTitleOnUpdateNotAvailable(int)} instead
     */
    AppUpdater setDialogTitleWhenUpdateNotAvailable(@StringRes int textResource);

    /**
     * Set a custom title for the dialog when no update is available.
     *
     * @param title for the dialog
     * @return this
     */
    AppUpdater setTitleOnUpdateNotAvailable(@NonNull String title);

    /**
     * Set a custom title for the dialog when no update is available.
     *
     * @param textResource resource from the strings xml file for the dialog
     * @return this
     */
    AppUpdater setTitleOnUpdateNotAvailable(@StringRes int textResource);

    /**
     * Set a custom description for the dialog when no update is available.
     *
     * @param description for the dialog
     * @return this
     * @deprecated use {@link #setContentOnUpdateNotAvailable(String)} instead
     */
    AppUpdater setDialogDescriptionWhenUpdateNotAvailable(@NonNull String description);

    /**
     * Set a custom description for the dialog when no update is available.
     *
     * @param textResource resource from the strings xml file for the dialog
     * @return this
     * @deprecated use {@link #setContentOnUpdateNotAvailable(int)} instead
     */
    AppUpdater setDialogDescriptionWhenUpdateNotAvailable(@StringRes int textResource);

    /**
     * Set a custom description for the dialog when no update is available.
     *
     * @param description for the dialog
     * @return this
     */
    AppUpdater setContentOnUpdateNotAvailable(@NonNull String description);

    /**
     * Set a custom description for the dialog when no update is available.
     *
     * @param textResource resource from the strings xml file for the dialog
     * @return this
     */
    AppUpdater setContentOnUpdateNotAvailable(@StringRes int textResource);

    /**
     * Set a custom "Update" button text when a new update is available.
     *
     * @param text for the update button
     * @return this
     * @deprecated use {@link #setButtonUpdate(String)} instead
     */
    AppUpdater setDialogButtonUpdate(@NonNull String text);

    /**
     * Set a custom "Update" button text when a new update is available.
     *
     * @param textResource resource from the strings xml file for the update button
     * @return this
     * @deprecated use {@link #setButtonUpdate(int)} instead
     */
    AppUpdater setDialogButtonUpdate(@StringRes int textResource);

    /**
     * Set a custom "Update" button text when a new update is available.
     *
     * @param text for the update button
     * @return this
     */
    AppUpdater setButtonUpdate(@NonNull String text);

    /**
     * Set a custom "Update" button text when a new update is available.
     *
     * @param textResource resource from the strings xml file for the update button
     * @return this
     */
    AppUpdater setButtonUpdate(@StringRes int textResource);

    /**
     * Set a custom "Dismiss" button text when a new update is available.
     *
     * @param text for the dismiss button
     * @return this
     * @deprecated use {@link #setButtonDismiss(String)} instead
     */
    AppUpdater setDialogButtonDismiss(@NonNull String text);

    /**
     * Set a custom "Dismiss" button text when a new update is available.
     *
     * @param textResource resource from the strings xml file for the dismiss button
     * @return this
     * @deprecated  use {@link #setButtonDismiss(int)} instead
     */
    AppUpdater setDialogButtonDismiss(@StringRes int textResource);

    /**
     * Set a custom "Dismiss" button text when a new update is available.
     *
     * @param text for the dismiss button
     * @return this
     */
    AppUpdater setButtonDismiss(@NonNull String text);

    /**
     * Set a custom "Dismiss" button text when a new update is available.
     *
     * @param textResource resource from the strings xml file for the dismiss button
     * @return this
     */
    AppUpdater setButtonDismiss(@StringRes int textResource);

    /**
     * Set a custom "Don't show again" button text when a new update is available.
     *
     * @param text for the disable button
     * @return this
     * @deprecated use {@link #setButtonDoNotShowAgain(String)} instead
     */
    AppUpdater setDialogButtonDoNotShowAgain(@NonNull String text);

    /**
     * Set a custom "Don't show again" button text when a new update is available.
     *
     * @param textResource resource from the strings xml file for the disable button
     * @return this
     * @deprecated use {@link #setButtonDoNotShowAgain(int)} instead
     */
    AppUpdater setDialogButtonDoNotShowAgain(@StringRes int textResource);

    /**
     * Set a custom "Don't show again" button text when a new update is available.
     *
     * @param text for the disable button
     * @return this
     */
    AppUpdater setButtonDoNotShowAgain(@NonNull String text);

    /**
     * Set a custom "Don't show again" button text when a new update is available.
     *
     * @param textResource resource from the strings xml file for the disable button
     * @return this
     */
    AppUpdater setButtonDoNotShowAgain(@StringRes int textResource);


    AppUpdater setButtonUpdateClickListener(DialogInterface.OnClickListener clickListener);

    AppUpdater setButtonDismissClickListener(DialogInterface.OnClickListener clickListener);

    AppUpdater setButtonDoNotShowAgainClickListener(DialogInterface.OnClickListener clickListener);

    AppUpdater setIcon(@DrawableRes int iconRes);

    /**
     * Make update dialog non-cancelable, and
     * force user to make update
     *  @param isCancelable true to force user to make update, false otherwise
     *  @return this
     */
    AppUpdater setCancelable(Boolean isCancelable);

    /**
     * Execute AppUpdater in background.
     *
     * @return this
     * @deprecated use {@link #start()} instead
     */
    AppUpdater init();

    /**
     * Execute AppUpdater in background.
     */
    void start();

    /**
     * Stops the execution of AppUpdater.
     */
    void stop();

    /**
     * Dismisses the alert dialog or the snackbar.
     */
    void dismiss();

    interface LibraryListener {
        void onSuccess(Update update);

        void onFailed(AppUpdaterError error);
    }
}