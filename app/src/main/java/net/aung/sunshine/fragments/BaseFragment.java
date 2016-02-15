package net.aung.sunshine.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import de.greenrobot.event.EventBus;

/**
 * Created by aung on 12/10/15.
 */
public class BaseFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            readArguments(bundle);
        }

        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        EventBus eventBus = EventBus.getDefault();
        eventBus.unregister(this);
    }

    //Overwrite this if your fragment is expecting arguments being set from static builder method.
    protected void readArguments(Bundle bundle) {

    }

    //This method is for those child fragment which doesn't need to use EventBus.
    public void onEventMainThread(Object event) {

    }
}
