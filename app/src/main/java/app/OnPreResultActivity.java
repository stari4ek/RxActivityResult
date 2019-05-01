package app;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import app.multi_start.FirstActivity;
import io.reactivex.Observable;
import io.victoralbertos.app.R;
import rx_activity_result2.RxActivityResult;

public class OnPreResultActivity extends AppCompatActivity {
    public static final String EXTRA_PRE = "EXTRA_PRE";
    private TextView preResult;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.on_pre_result);

        View startPreForResult = findViewById(R.id.start_pre_for_result);
        preResult = (TextView) findViewById(R.id.pre_result);
        result = (TextView) findViewById(R.id.result);

        startPreForResult.setOnClickListener(v ->
            RxActivityResult
                .on(this)
                .startIntent(
                    new Intent(this, FirstActivity.class),
                    (resultCode, requestCode, data) ->
                        Observable
                            .just(Ignore.Get)
                            .map(_I -> data.putExtra(EXTRA_PRE, "Do whatever you want with the data, but not with the UI")))
                .subscribe(result -> {
                    result.targetUI()
                        .preResult.setText(result.data().getStringExtra(EXTRA_PRE));
                    result.targetUI()
                        .result.setText(result.data().getStringExtra(FirstActivity.EXTRA));
                })
        );
    }

    /**
     * A dummy value to be ignored when dealing with RxJava 2 which does not return anything useful.
     */
    public enum Ignore {
        Get
    }
}
