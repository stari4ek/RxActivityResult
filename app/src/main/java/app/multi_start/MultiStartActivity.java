package app.multi_start;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import io.victoralbertos.app.R;
import rx_activity_result2.RxActivityResult;

public class MultiStartActivity extends AppCompatActivity {
    private TextView firstResult;
    private TextView secondResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multi_start);

        View startTwoForResult = findViewById(R.id.start_two_for_result);
        firstResult = (TextView) findViewById(R.id.first_result);
        secondResult = (TextView) findViewById(R.id.second_result);

        startTwoForResult.setOnClickListener(v ->
            RxActivityResult.on(this)
                .startIntent(new Intent(this, FirstActivity.class))
                .flatMap(result -> {
                    result.targetUI()
                        .firstResult.setText(result.data().getStringExtra(FirstActivity.EXTRA));
                    return RxActivityResult.on(this)
                        .startIntent(new Intent(this, SecondActivity.class));
                })
                .subscribe(result -> {
                    result.targetUI()
                        .secondResult.setText(result.data().getStringExtra(SecondActivity.EXTRA));
                })
        );
    }

}
