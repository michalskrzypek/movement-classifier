package pl.skrzypekmichal.movementclassifier;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_UNKNOWN = 0;
    private static final int REQUEST_CODE_CLASSIFY = 1;
    private static final int REQUEST_CODE_COLLECT = 2;

    private Button btnClassifier;
    private Button btnCollectData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initializeViews();

    }

    private void initializeViews() {
        btnClassifier = findViewById(R.id.btn_classifier);
        btnClassifier.setOnClickListener(this);

        btnCollectData = findViewById(R.id.btn_collect_data);
        btnCollectData.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i = null;
        int requestCode = REQUEST_CODE_UNKNOWN;
        if (v == btnClassifier) {
            i = new Intent(this, ClassifierActivity.class);
            requestCode = REQUEST_CODE_CLASSIFY;
        } else if (v == btnCollectData) {
            i = new Intent(this, ClassifierActivity.class);
            requestCode = REQUEST_CODE_COLLECT;
        }
        startActivityForResult(i, requestCode);
    }
}
