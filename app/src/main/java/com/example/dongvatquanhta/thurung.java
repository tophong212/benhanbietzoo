package com.example.dongvatquanhta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class thurung extends AppCompatActivity {
    DatabaseHandler db = new DatabaseHandler(this);
    ArrayList<Dongvat> arrayList = null;
    ArrayAdapter<Dongvat> adap = null;
    Dongvat ob = new Dongvat();

    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thurung);

        anhxa();
        Loadcauhoi();
        db.copyDB2SDCard();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(thurung.this, chitietDV.class);
                it.putExtra("iddv", arrayList.get(position).getId());
                startActivity(it);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    public void anhxa() {
        lv = (ListView) findViewById(R.id.lvthurung);
    }

    private void Loadcauhoi() {
        arrayList = new ArrayList<Dongvat>();
        Dongvat dv;
        Cursor c = db.getCursor("select * from DongVat where chude ='thurung'");
        c.moveToFirst();
        while (!c.isAfterLast()) {
            dv = new Dongvat();
            dv.id = c.getInt(0);
            dv.ten = c.getString(1);
            dv.anh = c.getBlob(2);
            arrayList.add(dv);
            c.moveToNext();
        }
        adap = new DongvatAdapter(this, R.layout.itemdv, arrayList);
        lv.setAdapter(adap);
        adap.notifyDataSetChanged();
        registerForContextMenu(lv);
        c.close();
    }
}