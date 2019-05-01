package app;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import app.multi_start.MultiStartActivity;
import io.victoralbertos.app.R;


public class StartActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        findViewById(R.id.bt_activity).setOnClickListener(view -> {
            startActivity(new Intent(StartActivity.this, SampleActivity.class));
        });

        findViewById(R.id.bt_fragment).setOnClickListener(view -> {
            startActivity(new Intent(StartActivity.this, HostActivitySampleFragment.class));
        });

        findViewById(R.id.bt_fragment_nested).setOnClickListener(view -> {
            startActivity(new Intent(StartActivity.this, HostActivitySampleFragmentNested.class));
        });

        findViewById(R.id.bt_multi_start).setOnClickListener(view -> {
            startActivity(new Intent(StartActivity.this, MultiStartActivity.class));
        });

        findViewById(R.id.bt_pre_result).setOnClickListener(view -> {
            startActivity(new Intent(StartActivity.this, OnPreResultActivity.class));
        });
    }
}
