package com.example.luisb.habilitabluetooth;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int CODIGO_SOLICITUD_PERMISO = 1;
    //estos dos codigos son independientes (no tienen relacion)
    private static final int CODIGO_SOLICITUD_PERMISO_HABILITAR_BTH = 0;
    private  Context context;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        activity = this;
    }

    public void habilitarBluetooth(View v){

        solicitarPermiso();
        //despues de solicitado el permiso lo gestionamos
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //si no tenemos bluethooth
        if(mBluetoothAdapter == null){
            Toast.makeText(MainActivity.this, "No existe Hardware para Bluethooth", Toast.LENGTH_SHORT).show();
        }

        //si NO esta activado
        if (!mBluetoothAdapter.isEnabled()){
            Intent habilitarBthIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);//configuramos intent

            startActivityForResult(habilitarBthIntent, CODIGO_SOLICITUD_PERMISO_HABILITAR_BTH);
        }

    }

    //Los permisos en las ultimas versiones de android ahora se activan de manera dinamica

    public boolean revisarStatusPermiso(){

        //vamos a preguntar si el permiso esta dado de alta
        int resultado = ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH);

        //¿El permiso esta dado?
        if(resultado == PackageManager.PERMISSION_GRANTED){
            return true;
        } else{
            return false;
        }
    }
    //Funcionalidad para solicitar el permiso de acceso al bluetooth
    public void solicitarPermiso(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.BLUETOOTH)){

            Toast.makeText(MainActivity.this,"El permiso ya fue otorgado, revisa los ajustes", Toast.LENGTH_SHORT).show();

        }else{//si el permiso no esta
            ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.BLUETOOTH}, CODIGO_SOLICITUD_PERMISO);
        }
    }

    //este metodo se ejecuta callbak por esta: requestPermissions
    //se ejecuta una vez dado de alta el permiso, aqui trabajaremos la gestion del mismo
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case CODIGO_SOLICITUD_PERMISO:
                //nuevamente verificamos si se dio el permiso
                if(revisarStatusPermiso()){
                    Toast.makeText(MainActivity.this, "El permiso para Bluethooth ya esta activado", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Permiso para Bluethooth se encuentra Denegado", Toast.LENGTH_SHORT).show();
                }

                break;

            //otro case si tenemos otra solicitud de permiso
        }
    }
}
