package com.izv.android.texteditor;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.izv.android.texteditor.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Principal extends Activity {

    EditText etTexto;
    String datos;
    String nombre_archivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);
        Intent intent = getIntent();
        Uri recurso = intent.getData();
        datos = recurso.getPath();
        etTexto = (EditText)findViewById(R.id.et_texto);
        etTexto.setText(leerArchivo(datos));
        setTitle(nombre_archivo);
    }

    private String leerArchivo(String datos){
        File archivo = new File(datos);
        nombre_archivo = archivo.getName();
        String texto ="";
        try {
            BufferedReader br = new
                    BufferedReader(new FileReader(archivo));
            String linea;
            while ((linea = br.readLine()) != null) {
                texto += linea; //StringBuilder
                texto += '\n';
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return  texto ;
    }

    public void guardarArchivo(View v){
        String texto = etTexto.getText().toString();
        File f = new File(datos);
        try {
            FileWriter fw = new FileWriter(f);
            fw.write(texto);
            fw.flush();
            fw.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        tostada("Texto guardado.");
        finish();
    }

    protected void onSaveInstanceState(Bundle guardaEstado) {
        if(guardaEstado != null){
            super.onSaveInstanceState(guardaEstado);
            String texto_guardado = etTexto.getText().toString();
            guardaEstado.putString("texto_guardado", texto_guardado);
        }
    }


    protected void onRestoreInstanceState(Bundle recuperaEstado) {
        if(recuperaEstado != null){
            super.onRestoreInstanceState(recuperaEstado);
            //recuperamos el String del Bundle
            String texto_guardado = recuperaEstado.getString("texto_guardado");
            //Seteamos el valor del EditText con el valor de nuestra cadena
            etTexto.setText(texto_guardado);
        }
    }


    private void tostada(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}
