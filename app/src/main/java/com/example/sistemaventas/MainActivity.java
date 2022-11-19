package com.example.sistemaventas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Instanciar la clase clsSales que contiene SQLiteOpenhelper con sus tablas
    //Casi siempre que instanciemos la clase clsSales vamos a mandar estos datos
    clsSales sohSales = new clsSales(this,"dbsales",null,1);
    String oldIdSeller;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText idseller = findViewById(R.id.etidseller);
        EditText fullname = findViewById(R.id.etfullname);
        EditText email = findViewById(R.id.etemail);
        EditText password = findViewById(R.id.etpassword);
        TextView totcomision = findViewById(R.id.tvtotcomision);
        ImageButton btnsave = findViewById(R.id.btnsave);
        ImageButton btnsearch = findViewById(R.id.btnsearch);
        ImageButton btnedit = findViewById(R.id.btnedit);
        ImageButton btndelete = findViewById(R.id.btndelete);
        ImageButton btnsales = findViewById(R.id.btnsales);


        //Deshabilitar botones para editar y eliminar

        btnedit.setEnabled(false);
        btndelete.setEnabled(false);
        btnsales.setEnabled(false);

        //Eventos
        btnsales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Buscar la identificacion en pantalla
                SQLiteDatabase dbr = sohSales.getReadableDatabase();
                //Generar una variable que contenga la instrucción para la busqueda por idseller
                String query = "SELECT idseller FROM seller WHERE idseller = '" + idseller.getText().toString() + "'";
                //Generar tabla cursor
                Cursor cursorSearch = dbr.rawQuery(query, null);
                //Chequear si la tabla al menos tiene un registro (si no lo encuentra)
                if (cursorSearch.moveToFirst()) {
                    //Generar un objeto basado en la clase intent para cambiar de pantalla (Activity)
                    //startActivity(new Intent(getApplicationContext(), sales.class));
                    // Pasar la identificación y el nombre para la actividad de ventas (sales)
                    Intent iSales = new Intent(getApplicationContext(),sales.class);
                    iSales.putExtra("midseller",idseller.getText().toString());
                    iSales.putExtra("mfullname",fullname.getText().toString());
                    startActivity(iSales);


                }else{
                    Toast.makeText(getApplicationContext(),"Id Vendedor no existe ...",Toast.LENGTH_SHORT).show();
                }

            }
        });




        //Eventos

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase dbSalesr = sohSales.getReadableDatabase();
                //Generar una variable  que contenga la instrucción para la busqueda por idseller
                //Tabla cursor se genera en la RAM
                String query = "SELECT idseller FROM seller WHERE idseller = '" + idseller.getText().toString() + "'";
                //Generar tabla cursor para almacenar los registros devueltos por el query anterior
                // --dbSalesr --- objeto de sqlitebase que va a trabajar en modo lectura
                Cursor cursorSearch = dbSalesr.rawQuery(query, null);
                //Chequear si la tabla cursor tiene al menos un registro
                if (!cursorSearch.moveToFirst()) {
                    if(cursorSearch.getInt(0)==0){
                        SQLiteDatabase dbw = sohSales.getWritableDatabase();
                        dbw.execSQL("DELETE FROM seller WHERE idseller = '"+idseller.getText().toString()+"'");
                        Toast.makeText(getApplicationContext(),"Vendedor eliminado exitosamente ...",Toast.LENGTH_SHORT).show();
                        idseller.setText("");
                        fullname.setText("");
                        email.setText("");
                        password.setText("");
                        idseller.requestFocus();
                        dbw.close();
                    }else{
                        Toast.makeText(getApplicationContext(),"Vendedor no se puede eliminar porque registra comisiones ...",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Id de Vendedor no existe ...",Toast.LENGTH_SHORT).show();
                }
                dbSalesr.close();
            }
        });


        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase dbw = sohSales.getWritableDatabase();
                //Verificar la identificación se cambió
                if(oldIdSeller.equals(idseller.getText().toString())){
                    dbw.execSQL("UPDATE seller SET fullname = '"+fullname.getText().toString()+"',email='"+email.getText().toString()+"',password = '"+password.getText().toString()+"' WHERE idseller = '"+oldIdSeller+"'");
                    Toast.makeText(getApplicationContext(),"Vendedor actualizado exitosamente ...",Toast.LENGTH_SHORT).show();
                }else{
                    SQLiteDatabase dbSalesr = sohSales.getReadableDatabase();
                    //Generar una variable  que contenga la instrucción para la busqueda por idseller
                    //Tabla cursor se genera en la RAM
                    String query = "SELECT idseller FROM seller WHERE idseller = '"+idseller.getText().toString()+"'";
                    //Generar tabla cursor para almacenar los registros devueltos por el query anterior
                    // --dbSalesr --- objeto de sqlitebase que va a trabajar en modo lectura
                    Cursor cursorSearch = dbSalesr.rawQuery(query,null);
                    //Chequear si la tabla cursor tiene al menos un registro
                    if(!cursorSearch.moveToFirst()) {//si no lo encontró
                        dbw.execSQL("UPDATE seller SET idseller ='" + idseller.getText().toString() + "',fullname = '" + fullname.getText().toString() + "',email='" + email.getText().toString() + "',password = '" + password.getText().toString() + "' WHERE idseller = '" + oldIdSeller + "'");
                        Toast.makeText(getApplicationContext(),"Vendedor actualizado exitosamente ...",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"Id de vendedor ya asignado ...",Toast.LENGTH_SHORT).show();
                    }
                    dbSalesr.close();
                }

                dbw.close();
            }
        });



        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Primero generar un objeto de SQLite
                SQLiteDatabase dbr = sohSales.getReadableDatabase();

                //Segundo creamos la consulta

                String query = "SELECT fullname, email, totcomision FROM seller WHERE idseller = '"+idseller.getText().toString()+"'";
                Cursor cursorSeller = dbr.rawQuery(query,null);
                if(cursorSeller.moveToFirst()){//Encontró el idseller
                    fullname.setText(cursorSeller.getString(0));
                    email.setText(cursorSeller.getString(1));
                    totcomision.setText(cursorSeller.getString(2));
                    oldIdSeller = idseller.getText().toString();
                    btnedit.setEnabled(true);
                    btndelete.setEnabled(true);
                    btnsales.setEnabled(true);
                }else{
                    Toast.makeText(getApplicationContext(),
                            "Id de vendedor inexistente",Toast.LENGTH_SHORT).show();
                }
            }
        });



        //Guardar
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!idseller.getText().toString().isEmpty() && !fullname.getText().toString().isEmpty()
                && !email.getText().toString().isEmpty() && !password.getText().toString().isEmpty()){




                        //Instanciar la clase SQLiteDatabase para abrir la bd en modo lectura o escritura(Insert,Delete,Update)
                        //Instanciar para buscar o recuperar
                        SQLiteDatabase dbSalesr = sohSales.getReadableDatabase();
                        //Generar una variable  que contenga la instrucción para la busqueda por idseller
                        //Tabla cursor se genera en la RAM
                        String query = "SELECT idseller FROM seller WHERE idseller = '"+idseller.getText().toString()+"'";
                        //Generar tabla cursor para almacenar los registros devueltos por el query anterior
                        // --dbSalesr --- objeto de sqlitebase que va a trabajar en modo lectura
                        Cursor cursorSearch = dbSalesr.rawQuery(query,null);
                        //Chequear si la tabla cursor tiene al menos un registro
                        if(!cursorSearch.moveToFirst()){//si no lo encontró
                            //Generar un objeto de SQLiteDataBase en modo escritura
                            SQLiteDatabase dbSalesw = sohSales.getWritableDatabase();
                            //ContentValues
                            ContentValues cvSeller = new ContentValues();
                            //Asignar a cada campo del ContentValues su referencia respectiva
                            cvSeller.put("idseller",idseller.getText().toString());
                            cvSeller.put("fullname",fullname.getText().toString());
                            cvSeller.put("email",email.getText().toString());
                            cvSeller.put("password",password.getText().toString());
                            cvSeller.put("totcomision",0);
                            //Guardar los datos del contentValues en la tabla física
                            dbSalesw.insert("seller",null,cvSeller);
                            //Siempre cierre la base de datos para que no lo hackeen
                            dbSalesw.close();
                            //dbSalesr.close();
                            Toast.makeText(getApplicationContext(),
                                    "Vendedor guardado correctamente",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),
                                    "Identificación esta asignada a otro vendedo, Intente con otro",Toast.LENGTH_SHORT).show();
                        }
                    dbSalesr.close();

                }else{
                    Toast.makeText(getApplicationContext(),
                            "Debe ingresar todos los datos",Toast.LENGTH_SHORT).show();
                }




            }
        });
    }
}