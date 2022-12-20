package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Add extends AppCompatActivity {

    private EditText PowerAdd, NameAdd;
    private Button Add_button;
    private ImageView Image_add;
    String Img="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        PowerAdd=findViewById(R.id.Power_add);
        NameAdd = findViewById(R.id.Name_add);
        Image_add= findViewById(R.id.image_add);
    }


    public  void Add(View v)
    {
        if (NameAdd.getText().length()==0 || PowerAdd.getText().length()==0  )
        {
            Toast.makeText(Add.this, "Не заполненны обязательные поля", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            if (Img=="")
            {
                Img=null;
                postData(NameAdd.getText().toString(),PowerAdd.getText().toString(),Img);
            }
            else
            {
                postData(NameAdd.getText().toString(),PowerAdd.getText().toString(),Img);
            }
            new CountDownTimer(1000, 1000) {
                public void onFinish() {
                    Next();
                }

                public void onTick(long millisUntilFinished) {

                }
            }.start();

        }

    }

    private void Next() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onClickChooseImage(View view)
   {
       getImage();
   }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && data!=null && data.getData()!=null)
        {
            if(resultCode == RESULT_OK)
            {
                Log.d("MyLog","Image URI : "+data.getData());
                Image_add.setImageURI(data.getData());
                Bitmap bitmap = ((BitmapDrawable)Image_add.getDrawable()).getBitmap();
                encodeImage(bitmap);
            }
        }
    }
    private  String encodeImage(Bitmap bitmap)
    {
        int prevW= 150;
        int prevH= bitmap.getHeight()* prevW / bitmap.getWidth();
       Bitmap b= Bitmap.createScaledBitmap(bitmap, prevW, prevH, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
                Img= Base64.getEncoder().encodeToString(bytes);
            return  Img;
        }
           return "";
    }
    private void getImage()
    {
        Intent intentchoose= new Intent();
        intentchoose.setType("image/*");
        intentchoose.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentchoose,1);
    }
    private void postData(String Name, String Power, String Image)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ngknn.ru:5001/NGKNN/ВласоваАС/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAdd retrofitAdd = retrofit.create(RetrofitAdd.class);
        DataModal modal = new DataModal(Name, Power, Image);
        Call<DataModal> call = retrofitAdd.createPost(modal);
        call.enqueue(new Callback<DataModal>() {
            @Override
            public void onResponse(Call<DataModal> call, Response<DataModal> response) {
                Toast.makeText(Add.this, "Данные добавлены", Toast.LENGTH_SHORT).show();
                NameAdd.setText("");
                PowerAdd.setText("");
                DataModal responseFromApi= response.body();

            }

            @Override
            public void onFailure(Call<DataModal> call, Throwable t) {

            }
        });
    }
}