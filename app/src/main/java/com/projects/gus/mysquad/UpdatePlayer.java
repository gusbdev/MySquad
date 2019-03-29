package com.projects.gus.mysquad;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
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

public class UpdatePlayer extends AppCompatActivity {
    EditText edtNome,edtDataNasc,edtCarac;
    Button btnUp,btnDel,btnTakePhoto,btnLoadPhoto;
    Spinner spnPos;
    private RadioGroup rgPd;
    private RadioButton rbPd,pdDireito,pdEsquerdo;
    Cursor cursor = null;
    DBController dbController;
    String idJogador,contentRadio,contentSpn;
    private int selectId,spnPosicaoId;
    static final int REQUEST_IMAGE_CAPTURE = 2;
    static final int RESULT_LOAD_IMAGE = 1;
    private byte[] imgByte;
    private ImageView img;
    private Bitmap imageBitmap = null;

    @Override
    public void onCreate(Bundle savedInstanceStace){
        super.onCreate(savedInstanceStace);
        setContentView(R.layout.activity_update_jogador);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        idJogador = this.getIntent().getStringExtra("idJogador");
        dbController = new DBController(getBaseContext());
        btnLoadPhoto = findViewById(R.id.btnLoad);
        btnTakePhoto = findViewById(R.id.btnTake);
        img = findViewById(R.id.imageView);
        edtNome = findViewById(R.id.edtName);
        edtDataNasc = findViewById(R.id.edtDataNas);
        rgPd = findViewById(R.id.rgPD);
        selectId = rgPd.getCheckedRadioButtonId();
        pdDireito = findViewById(R.id.pdDireito);
        pdEsquerdo = findViewById(R.id.pdEsquerdo);
        spnPos = findViewById(R.id.spnPD);
        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(this,R.array.posicao,android.R.layout.simple_spinner_item);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPos.setAdapter(adapterSpinner);
        edtCarac = findViewById(R.id.edtCarac);

        btnUp = findViewById(R.id.btnUp);
        btnDel = findViewById(R.id.btnDel);
        cursor = dbController.loadJogadorById(Integer.parseInt(idJogador));
        imgByte = cursor.getBlob(cursor.getColumnIndexOrThrow(Database.PHOTO));

        imageBitmap = getImage(imgByte);
        img.setImageBitmap(imageBitmap);
        edtNome.setText(cursor.getString(cursor.getColumnIndexOrThrow(Database.NOME)));
        edtDataNasc.setText(cursor.getString(cursor.getColumnIndexOrThrow(Database.DATA_NASCIMENTO)));
        contentRadio = cursor.getString(cursor.getColumnIndexOrThrow(Database.PE_DOMINANTE));
        switch (contentRadio){
            case "Direito":
                pdDireito.setChecked(true);
                pdEsquerdo.setChecked(false);
                break;
            case "Esquerdo":
                pdEsquerdo.setChecked(true);
                pdDireito.setChecked(false);
        }

        contentSpn = cursor.getString(cursor.getColumnIndexOrThrow(Database.POSICAO));
        switch (contentSpn){
            case "Goleiro":
                spnPosicaoId = 1;
                break;
            case "Fixo":
                spnPosicaoId = 2;
                break;
            case "Ala Direito":
                spnPosicaoId = 3;
                break;
            case "Ala Esquerdo":
                spnPosicaoId = 4;
                break;
            case "Pivô":
                spnPosicaoId = 5;
                break;
        }
        spnPos.setSelection(spnPosicaoId);
        edtCarac.setText(cursor.getString(cursor.getColumnIndexOrThrow(Database.CARACTERISTICAS)));

        lockComponents();

        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Click","Botão Update");
                rgPd = findViewById(R.id.rgPD);
                selectId = rgPd.getCheckedRadioButtonId();
                rbPd = findViewById(selectId);

                dbController.updateJogador(Integer.parseInt(idJogador),
                        imgByte = getBitmapAsByteArray(imageBitmap), edtNome.getText().toString(),edtDataNasc.getText().toString(),
                        rbPd.getText().toString(),spnPos.getSelectedItem().toString(),
                        edtCarac.getText().toString());
                Toast.makeText(UpdatePlayer.this,"Dados alterados!",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(UpdatePlayer.this, ListPlayers.class);
                startActivity(intent);
                finish();
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbController.deleteJogador(Integer.parseInt(idJogador));
                Toast.makeText(getApplicationContext(), "Registro excluído com sucesso!", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.update_menu:
                unlockComponents();
                return true;
        }
        return onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onStart();
    }

    public void onResume() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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
            imageBitmap = (Bitmap) extras.get("data");
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
                    Toast.makeText(UpdatePlayer.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }

            }else {
                Toast.makeText(UpdatePlayer.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
            }
        }
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public void unlockComponents(){
        img.setEnabled(true);
        edtNome.requestFocus();
        btnTakePhoto.setEnabled(true);
        btnLoadPhoto.setEnabled(true);
        edtNome.setEnabled(true);
        edtDataNasc.setEnabled(true);
        pdDireito.setEnabled(true);
        pdEsquerdo.setEnabled(true);
        spnPos.setEnabled(true);
        edtCarac.setEnabled(true);
        btnUp.setEnabled(true);
        btnDel.setEnabled(true);
    }

    public void lockComponents(){
        img.setEnabled(false);
        btnTakePhoto.setEnabled(false);
        btnLoadPhoto.setEnabled(false);
        edtNome.setEnabled(false);
        edtDataNasc.setEnabled(false);
        pdDireito.setEnabled(false);
        pdEsquerdo.setEnabled(false);
        spnPos.setEnabled(false);
        edtCarac.setEnabled(false);
        btnUp.setEnabled(false);
        btnDel.setEnabled(false);
    }
}