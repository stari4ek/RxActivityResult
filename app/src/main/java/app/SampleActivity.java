package app;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import io.reactivex.disposables.Disposable;
import io.victoralbertos.app.R;
import rx_activity_result2.RxActivityResult;

public class SampleActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_layout);

        findViewById(R.id.bt_camera).setOnClickListener(view -> camera());
        findViewById(R.id.bt_intent_sender).setOnClickListener(v -> intentSender());
    }

    private void camera() {
        Intent takePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        @SuppressWarnings("unused") Disposable ignored = RxActivityResult
            .on(this)
            .startIntent(takePhoto)
            .subscribe(result -> {
                Intent data = result.data();
                int resultCode = result.resultCode();

                if (resultCode == RESULT_OK) {
                    result.targetUI().showImage(data);
                } else {
                    result.targetUI().printUserCanceled();
                }
            }, Throwable::printStackTrace);
    }

    private void showImage(Intent data) {
        Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
        ((ImageView) findViewById(R.id.iv_thumbnail)).setImageBitmap(imageBitmap);
    }

    private void intentSender() {
        Intent intent  = new Intent("sample.intentsender.intent.AN_INTENT");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, 0);

        @SuppressWarnings("unused") Disposable ignored = RxActivityResult
            .on(this)
            .startIntentSender(pendingIntent.getIntentSender(), new Intent(), 0, 0, 0)
            .subscribe(result -> {
                if (result.resultCode() != RESULT_OK) {
                    result.targetUI().printUserCanceled();
                }
            });
    }

    private void printUserCanceled() {
        Toast.makeText(this, getString(R.string.user_canceled_action), Toast.LENGTH_LONG).show();
    }
}
