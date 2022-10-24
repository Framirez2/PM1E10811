package com.example.pm1e10811;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pm1e10811.Configuracion.SQLiteConexion;
import com.example.pm1e10811.tablas.Transacciones;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    /*Variables Foto*/
    static final int peticion_captura_imagen = 100;
    static final int peticion_acceso_cam = 201;
    ImageView ObjetoImagen;
    String PathImagen;

    Button btnsalvar,btntomarfoto,btnsalvados;
    EditText nombre,telefono,nota;

    private void config(){
        nombre = (EditText) findViewById(R.id.main_txtnombre);
        telefono = (EditText) findViewById(R.id.main_txttelefono);
        nota = (EditText) findViewById(R.id.main_txtnota);
        ObjetoImagen = (ImageView) findViewById(R.id.imageView);
        btnsalvados = (Button) findViewById(R.id.btnsalvados);
        btnsalvar = (Button) findViewById(R.id.btnsalvar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        config();

        //Spinner de paises
        Spinner spinner = findViewById(R.id.spin_pais);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.numbers, android.R.layout
                .simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        ObjetoImagen = (ImageView)findViewById(R.id.imageView);
        btntomarfoto = (Button) findViewById(R.id.btntomarfoto);


        btntomarfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permisos();
            }
        });


        btnsalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AgregarContacto();
            }
        });

        btnsalvados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), ActivityList.class);
                startActivity(intent);
            }
        });



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void permisos() {
        // Validar si el permiso esta otorgado o no para tomar fotos
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            // Otorgar el permiso si no se tiene el mismo
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, peticion_acceso_cam);
        }
        else
        {
            tomarfoto();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == peticion_acceso_cam)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                tomarfoto();
            }
        }
    }

    private void tomarfoto()
    {
        Intent intentfoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(intentfoto.resolveActivity(getPackageManager())!= null)
        {
            startActivityForResult(intentfoto, peticion_captura_imagen);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == peticion_captura_imagen)
        {
            Bundle extras = data.getExtras();
            Bitmap imagen = (Bitmap) extras.get("data");
            ObjetoImagen.setImageBitmap(imagen);
        }
    }

    private void AgregarContacto() {
        if(validar()){
            Toast.makeText(this, "Ingreso datos", Toast.LENGTH_SHORT).show();
            SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);
            SQLiteDatabase db = conexion.getWritableDatabase();

            ContentValues valores = new ContentValues();
            valores.put(Transacciones.nombre, nombre.getText().toString());
            valores.put(Transacciones.telefono, telefono.getText().toString());
            valores.put(Transacciones.nota, nota.getText().toString());

            Long resultado  = db.insert(Transacciones.TbContactos, Transacciones.id, valores);

            Toast.makeText(getApplicationContext(), "Contacto Agregado " + resultado.toString()
                    , Toast.LENGTH_SHORT).show();

            db.close();

            ClearScreen();
        }

    }

    private void ClearScreen() {
        nombre.setText("");
        telefono.setText("");
        nota.setText("");
    }



    public boolean validar(){
        boolean retorno = true;

        String c1=nombre.getText().toString();
        String c2=telefono.getText().toString();
        String c3=nota.getText().toString();

        if(c1.isEmpty())
        {
            nombre.setError("Debe escribir un nombre");
            retorno=false;
        }
        if(c2.isEmpty()){
            telefono.setError("Debe escribir un telefono");
            retorno=false;
        }

        if(c3.isEmpty()){
            nota.setError("Debe escribir una nota");
            retorno=false;
        }

        if(contienesololetras(c1)==false){
            Toast.makeText(this, "el nombre no debe tener numeros", Toast.LENGTH_SHORT).show();
            retorno=false;
        }

        if(validartelefono(c2)==false){
            Toast.makeText(this, "el numero no debe ser menor de 8 digitos ", Toast.LENGTH_SHORT).show();
            retorno=false;
        }




        return retorno;
    }

    private boolean validartelefono(String j) {

        for(int x=0;x< j.length(); x++){

            if (telefono.length() < 8)  {

                return false;
            }


        }
        return true;



    }



    private boolean contienesololetras(String cadena) {
        for(int x=0;x< cadena.length(); x++){
            char c= cadena.charAt(x);
            //si no esta entre a y z,ni entre a y  ,ni es espacio
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == ' ' || c == 'A' || c == 'Ñ'
                    || c == 'á' || c == 'é' || c == 'í' || c == 'ó' || c == 'ú'
                    || c == 'Á' || c == 'É' || c == 'Í' || c == 'Ó' || c == 'Ú')) {

                return false;
            }
        }
        return true;
    }


}