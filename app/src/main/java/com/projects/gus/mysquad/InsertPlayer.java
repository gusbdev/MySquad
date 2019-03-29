package com.projects.gus.mysquad;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class InsertPlayer extends Activity {

    private EditText edtName;
    private EditText edtDataNas;
    private RadioGroup rgPd;
    private RadioButton rbPd;
    private Spinner spnPosicao;
    private EditText edtCarac;
    private int selectId;
    private String peDominante, idTime;
    private ImageView img;
    private Bitmap imageBitmap,resized,imgProfile = null;
    private Button btnSave, btnTakePhoto,btnLoadPhoto;
    static final int REQUEST_IMAGE_CAPTURE = 2;
    static final int RESULT_LOAD_IMAGE = 1;
    private byte[] imgByte;


    public void onCreate(Bundle savedStateBundle) {
        super.onCreate(savedStateBundle);
        setContentView(R.layout.activity_insert_jogador);
        idTime = this.getIntent().getStringExtra("idTime");
        btnSave = findViewById(R.id.btnSave);
        btnLoadPhoto = findViewById(R.id.btnLoad);
        btnTakePhoto = findViewById(R.id.btnTake);
        img = findViewById(R.id.imageView);
        img.setImageResource(R.drawable.img_profile);
        imgProfile = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.img_profile);
        edtName = findViewById(R.id.edtName);
        edtDataNas = findViewById(R.id.edtDataNas);

        spnPosicao = findViewById(R.id.spnPD);
        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(this, R.array.posicao, android.R.layout.simple_spinner_item);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPosicao.setAdapter(adapterSpinner);
        edtCarac = findViewById(R.id.edtCarac);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBController dbController = new DBController(getBaseContext());
                try{
                    imgByte = getBitmapAsByteArray(imageBitmap);
                    String nome = edtName.getText().toString();
                    String dataNascimento = edtDataNas.getText().toString();
                    rgPd = findViewById(R.id.rgPD);
                    selectId = rgPd.getCheckedRadioButtonId();
                    rbPd = findViewById(selectId);
                    peDominante = rbPd.getText().toString();
                    String posicao = spnPosicao.getSelectedItem().toString();
                    String caracteristicas = edtCarac.getText().toString();
                    String result;
                    result = dbController.insertData(imgByte,nome,Integer.parseInt(idTime),dataNascimento, peDominante, posicao, caracteristicas);
                    Toast.makeText(InsertPlayer.this, result, Toast.LENGTH_SHORT).show();
                    clearAll();
                } catch (Exception ex) {
                    Toast.makeText(InsertPlayer.this, ex.getMessage(), Toast.LENGTH_LONG);
                }
            }
        });
    }

    public void onResume() {
        super.onResume();

        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        btnLoadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFromGallery();
            }
        });
    }

    private void loadFromGallery(){
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            resized = (Bitmap) extras.get("data");
            imageBitmap = Bitmap.createScaledBitmap(resized, 300, 500, true);
            img.setImageBitmap(imageBitmap);
        }

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            if (resultCode == RESULT_OK) {
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    imageBitmap = BitmapFactory.decodeStream(imageStream);
                    img.setImageBitmap(imageBitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(InsertPlayer.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }

            }else {
                Toast.makeText(InsertPlayer.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
            }
        }
    }

        public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
            return outputStream.toByteArray();
        }

        public void clearAll () {
            img.setImageResource(R.drawable.img_profile);
            edtName.setText("");
            edtDataNas.setText("");
            spnPosicao.setSelection(0);
            edtCarac.setText("");
            edtName.requestFocus();
        }
    }
