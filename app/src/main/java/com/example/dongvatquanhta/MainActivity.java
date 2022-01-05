package com.example.dongvatquanhta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phatnhac();
        ImageButton imgdongvat = (ImageButton) findViewById(R.id.imgdongvat);
        ImageButton imgkiemtra = (ImageButton) findViewById(R.id.imgkiemtra);

        imgdongvat.setOnClickListener(this);
        imgkiemtra.setOnClickListener(this);
    }

    private void amnhac(int nhac) {
        mediaPlayer = MediaPlayer.create(this, nhac);
        mediaPlayer.start();
    }

    public void phatnhac() {
        amnhac(R.raw.nhac_nen);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.imgdongvat:
                i = new Intent(this, Chude.class);
                startActivity(i);
                mediaPlayer.stop();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.imgkiemtra:
                i = new Intent(this, Kiemtra.class);
                startActivity(i);
                mediaPlayer.stop();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
        }
    }
}