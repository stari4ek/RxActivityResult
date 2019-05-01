package rx_activity_result2;

import android.content.Intent;
import androidx.annotation.Nullable;

import java.io.Serializable;

interface OnResult extends Serializable {
    void response(int requestCode, int resultCode, @Nullable Intent data);
    void error(Throwable throwable);
}
