package ch.heigvd.iict.sym.sym_labo4;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;

public class WearSynchronizedActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{

    private static final String TAG = WearSynchronizedActivity.class.getSimpleName();

    private SeekBar redSlider = null;
    private SeekBar greenSlider = null;
    private SeekBar blueSlider = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wearsynchronized);

        redSlider = findViewById(R.id.red_seekBar);
        greenSlider = findViewById(R.id.green_seekBar);
        blueSlider = findViewById(R.id.blue_seekBar);

        redSlider.setOnSeekBarChangeListener(this);
        greenSlider.setOnSeekBarChangeListener(this);
        blueSlider.setOnSeekBarChangeListener(this);


        updateBackgroundColor();
        /* A IMPLEMENTER */

    }

    /* A IMPLEMENTER */

    /*
     *  Code utilitaire fourni
     */

    /**
     * Method used to update the background color of the activity
     *
     */
    private void updateBackgroundColor() {

        int r = redSlider.getProgress();
        int g = greenSlider.getProgress();
        int b = blueSlider.getProgress();

        View rootView = findViewById(android.R.id.content);
        rootView.setBackgroundColor(Color.argb(255, r, g, b));
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        updateBackgroundColor();
    }
}
