package com.example.pm1e10811;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pm1e10811.Configuracion.SQLiteConexion;
import com.example.pm1e10811.tablas.Contactos;
import com.example.pm1e10811.tablas.Transacciones;

import java.util.ArrayList;

public class ActivityList extends AppCompatActivity {

    SQLiteConexion conexion;
    ListView listcontacto;
    ArrayList<Contactos> lista;
    ArrayList<String> listaconcatenada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        conexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);

        listcontacto = (ListView) findViewById(R.id.listcontacto);

        GetListContact();

        ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_1,listaconcatenada);
        listcontacto.setAdapter(adp);

        listcontacto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Toast.makeText(getApplicationContext(), listaconcatenada.get(i).toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), listaconcatenada.get(i).toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void GetListContact()
    {
        SQLiteDatabase db = conexion.getReadableDatabase(); // Base de datos en modo de lectura
        Contactos listcontactos = null;

        lista = new ArrayList<Contactos>();  // Lista de Objetos del tipo contactos

        Cursor cursor = db.rawQuery(Transacciones.GetContactos,null);

        while(cursor.moveToNext())
        {
            listcontactos = new Contactos();
            listcontactos.setId(cursor.getInt(0));
            listcontactos.setNombre(cursor.getString(1));
            listcontactos.setTelefono(cursor.getString(2));
            listcontactos.setNota(cursor.getString(3));
            //listcontactos.setFoto(cursor.getBlob(4));

            lista.add(listcontactos);
        }

        cursor.close();

        LLenarLista();

    }

    private void LLenarLista()
    {
        listaconcatenada = new ArrayList<String>();

        for(int i =0;  i < lista.size(); i++)
        {
            listaconcatenada.add(lista.get(i).getNombre() + " | " +
                    lista.get(i).getTelefono()+" "+
                    lista.get(i).getNota());
        }
    }

}