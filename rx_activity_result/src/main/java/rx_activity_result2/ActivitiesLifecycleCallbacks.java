package rx_activity_result2;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.Nullable;

import io.reactivex.Observable;
import io.reactivex.Single;
import java.util.concurrent.TimeUnit;

class ActivitiesLifecycleCallbacks {
    final Application application;
    volatile Activity liveActivityOrNull;
    Application.ActivityLifecycleCallbacks activityLifecycleCallbacks;

    private final Application application;
    private volatile Activity liveActivityOrNull;
    private Application.ActivityLifecycleCallbacks activityLifecycleCallbacks;

    ActivitiesLifecycleCallbacks(Application application) {
        this.application = application;
        registerActivityLifeCycle();
    }

    private void registerActivityLifeCycle() {
        if (activityLifecycleCallbacks != null) application.unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks);

        activityLifecycleCallbacks = new Application.ActivityLifecycleCallbacks() {
            @Override public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                liveActivityOrNull = activity;
            }

            @Override public void onActivityStarted(Activity activity) {}

            @Override public void onActivityResumed(Activity activity) {
                liveActivityOrNull = activity;
            }

            @Override public void onActivityPaused(Activity activity) {
                liveActivityOrNull = null;
            }

            @Override public void onActivityStopped(Activity activity) {}

            @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}

            @Override public void onActivityDestroyed(Activity activity) {}
        };

        application.registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
    }

    @Nullable Activity getLiveActivity() {
        return liveActivityOrNull;
    }

    /**
     * Emits just one time a valid reference to the current activity
     * @return the current activity
     */
    Single<Activity> getOLiveActivity() {
        return Observable.interval(50, 50, TimeUnit.MILLISECONDS)
            .flatMap(ignored -> {
                if (liveActivityOrNull == null) return Observable.empty();
                return Observable.just(liveActivityOrNull);
            }).firstOrError();
    }

}
