package com.example.dongvatquanhta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Chude extends AppCompatActivity {
    ImageButton imgthunuoi, imgthurung, imgduoinuoc, imgcontrung, imglongvu, imgdongco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chude);
        anhxa();
    }

    public void anhxa() {
        imgthunuoi = (ImageButton) findViewById(R.id.imgthunuoi);
        imgthurung = (ImageButton) findViewById(R.id.imgthurung);
        imgdongco = (ImageButton) findViewById(R.id.imgdongco);
        imgduoinuoc = (ImageButton) findViewById(R.id.imgduoinuoc);
        imgcontrung = (ImageButton) findViewById(R.id.imgkhac);
        imglongvu = (ImageButton) findViewById(R.id.imglongvu);
    }

    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.imgthunuoi:
                i = new Intent(this, thunuoi.class);
                startActivity(i);
                break;
            case R.id.imgthurung:
                i = new Intent(this, thurung.class);
                startActivity(i);
                break;
            case R.id.imgdongco:
                i = new Intent(this, dongco.class);
                startActivity(i);
                break;
            case R.id.imglongvu:
                i = new Intent(this, longvu.class);
                startActivity(i);
                break;
            case R.id.imgkhac:
                i = new Intent(this, loaikhac.class);
                startActivity(i);
                break;
            case R.id.imgduoinuoc:
                i = new Intent(this, duoinuoc.class);
                startActivity(i);
                break;
        }
    }
}