package com.example.dongvatquanhta;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Locale;

public class Kiemtra extends AppCompatActivity {
    DatabaseHandler db = new DatabaseHandler(this);
    ArrayList<cauhoi> listCauHoi;
    private TextView tvCauHoi;
    ImageButton DaA, DaB, DaC, DaD, imgdoc, imgtiengkeu, imgtrangchu;
    int DapanDung, i;
    MediaPlayer mediaPlayer, md;
    TextToSpeech textToSpeech;
    Dialog dialog;
    int luotchoi = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kiemtrabe);
        anhxa();

        Doc();
        event();
        Loadcauhoi();
        ViewCauhoi(i++);
        if (i == 9) {
            KetThuc();
        }
    }

    public void anhxa() {
        DaA = (ImageButton) findViewById(R.id.dapanA);
        DaB = (ImageButton) findViewById(R.id.dapanB);
        DaC = (ImageButton) findViewById(R.id.dapanC);
        DaD = (ImageButton) findViewById(R.id.dapanD);
        tvCauHoi = (TextView) findViewById(R.id.tvCauhoi);
        imgdoc = findViewById(R.id.imgdoc);
        imgtiengkeu = findViewById(R.id.imgtiengkeu);
        imgtrangchu = findViewById(R.id.imgtrangchu);
    }

    public void amthanh(int nhac) {
        mediaPlayer = MediaPlayer.create(this, nhac);
        mediaPlayer.start();
    }

    public void phatnhac() {
        if (md != null) {
            md.release();
        }
        int resId = getResources().getIdentifier(listCauHoi.get(i - 1).getTiengkeu(), "raw", getPackageName());
        md = MediaPlayer.create(Kiemtra.this, resId);
    }

    public void Doc() {
       textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
           @Override
           public void onInit(int status) {
               if(status!=TextToSpeech.ERROR){
                   textToSpeech.setLanguage(Locale.ROOT);
               }else {
                   Toast.makeText(Kiemtra.this,"Errol", Toast.LENGTH_LONG).show();
               }
               if (tvCauHoi != null){
                   textToSpeech.speak(tvCauHoi.getText().toString(),TextToSpeech.QUEUE_FLUSH,null);
               }
           }
       });
    }

    public void imgtrai(ImageButton btn) {
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left);
        btn.startAnimation(animation);
    }

    public void animationNhay(ImageButton btn) {
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.nhay);
        btn.startAnimation(animation);
    }

    public void imgphai(ImageButton btn) {
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right);
        btn.startAnimation(animation);
    }

    private void ViewCauhoi(int i) {
        Doc();
        tvCauHoi.setText(listCauHoi.get(i).getLoihoi());
        Bitmap a = BitmapFactory.decodeByteArray(listCauHoi.get(i).getDapan1(), 0, listCauHoi.get(i).getDapan1().length);
        DaA.setImageBitmap(a);
        DaA.setEnabled(true);
        imgtrai(DaA);
        Bitmap b = BitmapFactory.decodeByteArray(listCauHoi.get(i).getDapan2(), 0, listCauHoi.get(i).getDapan2().length);
        DaB.setImageBitmap(b);
        DaB.setEnabled(true);
        imgphai(DaB);
        Bitmap c = BitmapFactory.decodeByteArray(listCauHoi.get(i).getDapan3(), 0, listCauHoi.get(i).getDapan3().length);
        DaC.setImageBitmap(c);
        DaC.setEnabled(true);
        imgtrai(DaC);
        Bitmap d = BitmapFactory.decodeByteArray(listCauHoi.get(i).getDapan4(), 0, listCauHoi.get(i).getDapan4().length);
        DaD.setImageBitmap(d);
        DaD.setEnabled(true);
        imgphai(DaD);
        switch (listCauHoi.get(i).getDapandung()) {
            case 1:
                DapanDung = R.id.dapanA;
                break;
            case 2:
                DapanDung = R.id.dapanB;
                break;
            case 3:
                DapanDung = R.id.dapanC;
                break;
            case 4:
                DapanDung = R.id.dapanD;
                break;
        }
    }

    public void chon(int v, ImageButton img) {
        if (v != DapanDung) {
            if (luotchoi > 0) {
                luotchoi = luotchoi - 1;
            } else if (luotchoi == 0) {
                KetThuc();
            }
            amthanh(R.raw.sai);
        } else {
            if (i < 9) {
                animationNhay(img);
                amthanh(R.raw.dungroi);
                Handler handler = new Handler();
                handler.postDelayed(() -> ViewCauhoi(i++), 1500);
            }
            if (i == 9) {
                Tongket();
            }
        }
    }

    private void Tongket() {
        dialog = new Dialog(this, android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.tongket, null);
        TextView tvFinish = view.findViewById(R.id.tvketthuc);
        amthanh(R.raw.hoanthanh);
        Button btnFinish = (Button) view.findViewById(R.id.btnFinish);
        btnFinish.setOnClickListener(v -> {
            onBackPressed();
            dialog.dismiss();
        });
        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.show();
    }

    private void KetThuc() {
        dialog = new Dialog(this, android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.ketthuc, null);
        TextView tvFinish =  view.findViewById(R.id.tvkt);
        amthanh(R.raw.ketthuc);
        Button btnFinish = (Button) view.findViewById(R.id.btnFinish);
        btnFinish.setOnClickListener(v -> {
            onBackPressed();
            dialog.dismiss();
        });
        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.show();
    }

    private void Loadcauhoi() {
        db.copyDB2SDCard();
        cauhoi ch;
        listCauHoi = new ArrayList<cauhoi>();
        String sql = "SELECT * FROM cauhoi ORDER BY random() LIMIT 10";
        Cursor c = db.getCursor(sql);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            ch = new cauhoi();
            ch.id = c.getInt(0);
            ch.loihoi = c.getString(1);
            ch.dapan1 = c.getBlob(2);
            ch.dapan2 = c.getBlob(3);
            ch.dapan3 = c.getBlob(4);
            ch.dapan4 = c.getBlob(5);
            ch.dapandung = c.getInt(6);
            ch.tiengkeu = c.getString(7);
            listCauHoi.add(ch);
            c.moveToNext();
        }
    }

    public void event() {
        imgdoc.setOnClickListener(v -> {
            if (!listCauHoi.get(i - 1).getTiengkeu().equals("")) {
                phatnhac();
                if (!md.isPlaying()) {
                    Doc();
                }
                if (md.isPlaying()) {
                    md.pause();
                    Doc();
                }
            }
            if (listCauHoi.get(i - 1).getTiengkeu().equals("")) {
                Doc();
            }
        });

        imgtrangchu.setOnClickListener(v -> {
            Intent it = new Intent(Kiemtra.this, MainActivity.class);
            startActivity(it);
        });

        imgtiengkeu.setOnClickListener(v -> {
            if (!listCauHoi.get(i - 1).getTiengkeu().equals("")) {
                imgtiengkeu.setEnabled(true);
                phatnhac();
                md.start();
            }
            if (listCauHoi.get(i - 1).getTiengkeu().equals("")) {
                imgtiengkeu.setEnabled(false);
            }
        });
    }

    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.dapanA:
                if (!listCauHoi.get(i - 1).getTiengkeu().equals("")) {
                    phatnhac();
                    if (md.isPlaying()) {
                        md.pause();
                    } else {
                        chon(R.id.dapanA, DaA);
                    }
                }
                if (listCauHoi.get(i - 1).getTiengkeu().equals("")) {
                    chon(R.id.dapanA, DaA);
                }
                break;
            case R.id.dapanB:
                if (!listCauHoi.get(i - 1).getTiengkeu().equals("")) {
                    phatnhac();
                    if (md.isPlaying()) {
                        md.pause();
                    } else {
                        chon(R.id.dapanB, DaB);
                    }
                }
                if (listCauHoi.get(i - 1).getTiengkeu().equals("")) {
                    chon(R.id.dapanB, DaB);
                }
                break;
            case R.id.dapanC:
                if (!listCauHoi.get(i - 1).getTiengkeu().equals("")) {
                    phatnhac();
                    if (md.isPlaying()) {
                        md.pause();
                    } else {
                        chon(R.id.dapanC, DaC);
                    }
                }
                if (listCauHoi.get(i - 1).getTiengkeu().equals("")) {
                    chon(R.id.dapanC, DaC);
                }
                break;
            case R.id.dapanD:
                if (!listCauHoi.get(i - 1).getTiengkeu().equals("")) {
                    phatnhac();
                    if (md.isPlaying()) {
                        md.pause();
                    } else {
                        chon(R.id.dapanD, DaD);
                    }
                }
                if (listCauHoi.get(i - 1).getTiengkeu().equals("")) {
                    chon(R.id.dapanD, DaD);
                }
                break;
        }
    }
}