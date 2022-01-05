package com.example.dongvatquanhta;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;


public class DongvatAdapter extends ArrayAdapter<Dongvat> {
    ArrayList<Dongvat> arr = new ArrayList<>();
    Activity context;
    int layout;

    public DongvatAdapter(Context context, int resource, ArrayList<Dongvat> obj) {
        super(context, resource, obj);
        this.context = (Activity) context;
        arr = obj;
        layout = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(layout, null);
        Bitmap b= BitmapFactory.decodeByteArray(arr.get(position).getAnh(),0,arr.get(position).getAnh().length);

        ImageView img =(ImageView)view.findViewById(R.id.itemanh);

        Dongvat ob = arr.get(position);
        img.setImageBitmap(b);
        Animation a= AnimationUtils.loadAnimation(context,R.anim.zoom_in);
        view.startAnimation(a);

        return view;
    }

    @Override
    public Dongvat getItem(int position) {
        return super.getItem(position);
    }
}
