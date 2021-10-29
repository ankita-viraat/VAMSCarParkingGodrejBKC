package info.vams.zktecoedu.reception.Util;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatSpinner;

/**
 * Created by vishal on 08-Apr-19.
 */

public class BuildingSpinner extends AppCompatSpinner {

    public BuildingSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
    }

    public BuildingSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BuildingSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BuildingSpinner(Context context, int mode) {
        super(context, mode);
    }

    public BuildingSpinner(Context context) {
        super(context);
    }


        /**
         * An interface which a client of this Spinner could use to receive
         * open/closed events for this Spinner.
         */
    public interface OnSpinnerEventsListener {

        /**
         * Callback triggered when the spinner was opened.
         */
        void onSpinnerOpened(AppCompatSpinner spinner);

        /**
         * Callback triggered when the spinner was closed.
         */
        void onSpinnerClosed(AppCompatSpinner spinner);

    }

    private OnSpinnerEventsListener mListener;
    private boolean mOpenInitiated = false;

    // implement the Spinner constructors that you need

    @Override
    public boolean performClick() {
        // register that the Spinner was opened so we have a status
        // indicator for when the container holding this Spinner may lose focus
        mOpenInitiated = true;
        if (mListener != null) {
            mListener.onSpinnerOpened(this);
        }
        return super.performClick();
    }

    @Override
    public void onWindowFocusChanged (boolean hasFocus) {
        if (hasBeenOpened() && hasFocus) {
            performClosedEvent();
        }
    }

    /**
     * Register the listener which will listen for events.
     */
    public void setSpinnerEventsListener(
            OnSpinnerEventsListener onSpinnerEventsListener) {
        mListener = onSpinnerEventsListener;
    }

    /**
     * Propagate the closed Spinner event to the listener from outside if needed.
     */
    public void performClosedEvent() {
        mOpenInitiated = false;
        if (mListener != null) {
            mListener.onSpinnerClosed(this);
        }
    }

    /**
     * A boolean flag indicating that the Spinner triggered an open event.
     *
     * @return true for opened Spinner
     */
    public boolean hasBeenOpened() {
        return mOpenInitiated;
    }


}
