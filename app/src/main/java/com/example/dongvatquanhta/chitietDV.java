package com.example.dongvatquanhta;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import java.util.Locale;

public class chitietDV extends AppCompatActivity {
    DatabaseHandler db = new DatabaseHandler(this);
    TextView tendv;
    ImageView anh;
    ImageButton tieng, docten, trangchu;
    int id;
    TextToSpeech textToSpeech;
    MediaPlayer md;
    Dongvat dv;
    Drawable bt_thuong, bt_Khong;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chitietdongvat);
        anhxa();
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status!=TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.ROOT);
                }
            }
        });
        layanh();
        Event();
        db.copyDB2SDCard();
        laydl();
    }

    public void anhxa() {
        tendv = findViewById(R.id.tvten);
        anh = findViewById(R.id.anhct);
        tieng = findViewById(R.id.nghetiengkeu);
        docten = findViewById(R.id.ngheten);
        trangchu = findViewById(R.id.trangchu);
    }

    private void layanh() {
        try {
            bt_thuong = AppCompatResources.getDrawable(this, R.drawable.loa);
            bt_Khong = AppCompatResources.getDrawable(this, R.drawable.tatam);
        } catch (Exception e) {
            Log.e("Error", "Exception loading drawable");
        }
    }

    private void laydl() {
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("iddv");
        Cursor c = db.getCursor("SELECT * FROM dongvat WHERE id ='" + id + "'");
        c.moveToFirst();
        dv = new Dongvat();
        dv.setId(c.getInt(0));
        dv.setTen(c.getString(1));
        dv.setAnh(c.getBlob(2));
        dv.setTiengkeu(c.getString(3));
        dv.setChude(c.getString(4));

        tendv.setText("" + dv.getTen());
        Bitmap a = BitmapFactory.decodeByteArray(dv.getAnh(), 0, dv.getAnh().length);
        anh.setImageBitmap(a);
    }

    private void Event() {
        docten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!dv.getTiengkeu().equals("")) {
                    phattieng();
                    if (!md.isPlaying()) {
                        Doc();
                    }
                    if (md.isPlaying()) {
                        md.pause();
                        Doc();
                    }
                }
                if (dv.getTiengkeu().equals("")) {
                    Doc();
                }
            }
        });

        tieng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                laydl();
                if (!dv.getTiengkeu().equals("")) {
                    phattieng();
                    md.start();
                }
                if (dv.getTiengkeu().equals("")) {
                    tieng.setEnabled(false);
                }
            }
        });

        trangchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(chitietDV.this, MainActivity.class);
                startActivity(it);
                md.pause();
            }
        });
    }

    public void phattieng() {
        if (md != null) {
            md.release();
        }
        int resId = getResources().getIdentifier(dv.getTiengkeu(), "raw", getPackageName());
        md = MediaPlayer.create(chitietDV.this, resId);
    }

    public void Doc() {
        textToSpeech.speak(tendv.getText().toString(), textToSpeech.QUEUE_FLUSH, null);
    }
}
