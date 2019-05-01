package app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import io.victoralbertos.app.R;
import rx_activity_result2.RxActivityResult;

/**
 * Created by victor on 06/05/16.
 */

public class SampleParentFragmentNested extends Fragment {

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sample_parent_fragment_nested, container, false);
        return view;
    }

    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.fl_fragment_container, new SampleFragmentNested())
                .commit();
    }

    public static class SampleFragmentNested extends Fragment {
        @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.sample_layout, container, false);
            return view;
        }

        @Override public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            getView().findViewById(R.id.bt_camera).setOnClickListener(view -> camera());
        }

        private void camera() {
            Intent takePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            RxActivityResult.on(this).startIntent(takePhoto)
                    .subscribe(result -> {
                        Intent data = result.data();
                        int resultCode = result.resultCode();

                        if (resultCode == Activity.RESULT_OK) {
                            result.targetUI().showImage(data);
                        } else {
                            result.targetUI().printUserCanceled();
                        }
                    });
        }

        private void showImage(Intent data) {
            Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
            ((ImageView) getView().findViewById(R.id.iv_thumbnail)).setImageBitmap(imageBitmap);
        }

        private void printUserCanceled() {
            Toast.makeText(getActivity(), getString(R.string.user_canceled_action), Toast.LENGTH_LONG).show();
        }
    }
}


