package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class AdapterMask extends BaseAdapter {

    private Context mContext;
    List<Mask> maskList;

    public AdapterMask(Context mContext, List<Mask> maskList) {
        this.mContext= mContext;
        this.maskList=maskList;
    }

    @Override
    public int getCount() {
        return maskList.size();
    }

    @Override
    public Object getItem(int i) {
        return maskList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return maskList.get(i).getID();
    }

    private Bitmap getUserImage(String encodedImg)
    {

        if(encodedImg!=null&& !encodedImg.equals("null")) {
            byte[] bytes = Base64.decode(encodedImg, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        else
        {
            return  BitmapFactory.decodeResource(mContext.getResources(),R.drawable.ic_launcher_playstore);

        }
    }
    @Override
    public View getView(int i, View view, ViewGroup ViewGroup) {
        View v= View.inflate(mContext,R.layout.activity_adapter_mask, null);
        Mask mask= maskList.get(i);

        TextView power = v.findViewById(R.id.Power_mask);
        TextView name = v.findViewById(R.id.Name_mask);
        ImageView imageView = v.findViewById(R.id.Image_mask);

        power.setText((mask.getPower()));
        name.setText(mask.getName());
        imageView.setImageBitmap(getUserImage(mask.getImage()));

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,Update_Delete.class);
                intent.putExtra("Autoes",mask);
                mContext.startActivity(intent);

            }
        });
        return v;
    }
}