package rx_activity_result2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;


class ActivitiesLifecycleCallbacks {
    private final Application application;
    private final Subject<Wrap> subject = BehaviorSubject.create();

    private Application.ActivityLifecycleCallbacks activityLifecycleCallbacks;

    /**
     * Kind of Optional. We need it cause we'd like to emit "nulls" to stream
     */
    static final class Wrap {
        @SuppressLint("StaticFieldLeak")
        private static final Wrap EMPTY = new Wrap(null);

        @Nullable Activity activity;

        static Wrap of(@NonNull Activity activity) { return new Wrap(activity); }
        static Wrap empty() { return EMPTY; }

        boolean isEmpty() { return activity == null; }

        private Wrap(@Nullable Activity activity) {this.activity = activity;}
    }

    ActivitiesLifecycleCallbacks(Application application) {
        this.application = application;
        registerActivityLifeCycle();
    }

    private void registerActivityLifeCycle() {
        if (activityLifecycleCallbacks != null) application.unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks);

        activityLifecycleCallbacks = new Application.ActivityLifecycleCallbacks() {
            @Override public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                subject.onNext(Wrap.of(activity));
            }

            @Override public void onActivityStarted(Activity activity) {}

            @Override public void onActivityResumed(Activity activity) {
                subject.onNext(Wrap.of(activity));

            }

            @Override public void onActivityPaused(Activity activity) {
                subject.onNext(Wrap.empty());
            }

            @Override public void onActivityStopped(Activity activity) {}

            @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}

            @Override public void onActivityDestroyed(Activity activity) {}
        };

        application.registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
    }

    Observable<Activity> observeActivities() {
        return subject
            .distinctUntilChanged()
            .filter((wrapped) -> !wrapped.isEmpty())
            .map((wrapped) -> wrapped.activity);
    }

    /**
     * Emits just one time a valid reference to the current activity
     * @return the current activity
     */
    Single<Activity> waitForActivity() {
        return observeActivities()
            .firstOrError();
    }
}
