package edward.example.com;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import edward.example.com.BaseDatos.DatosOpenHelper;

public class ActMain extends AppCompatActivity {
   private ListView lstDatos;
   private ArrayAdapter<String> adaptador;
   private ArrayList<String> clientes;

   private SQLiteDatabase conexion;
   private DatosOpenHelper datosOpenHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(ActMain.this,ActNuevoCliente.class);
                startActivityForResult(it,0);
            }
        });
        FloatingActionButton fab2=findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(ActMain.this,ActBorrarCliente.class);
                startActivityForResult(it,0);
                Toast.makeText(ActMain.this, "asdasd", Toast.LENGTH_SHORT).show();
            }
        });
        FloatingActionButton fab3=findViewById(R.id.fab3);
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(ActMain.this,ActModificarCliente.class);
                startActivityForResult(it,0);
                Toast.makeText(ActMain.this, "123123", Toast.LENGTH_SHORT).show();
            }
        });
        actualizar();
    }

    private void actualizar(){
        lstDatos = (ListView) findViewById(R.id.lstDatos);
        clientes = new ArrayList<>();

        try {
            datosOpenHelper = new DatosOpenHelper(this);
            conexion = datosOpenHelper.getWritableDatabase();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM CLIENTE");
            String sNombre;
            String sTelefono;
            String sDireccion;
            String sEmail;
            String sID;

            Cursor resultado = conexion.rawQuery(sql.toString(),null);

            if(resultado.getCount() > 0){
                resultado.moveToFirst();
                do{
                    sNombre = resultado.getString(resultado.getColumnIndex("NOMBRE"));
                    sTelefono = resultado.getString(resultado.getColumnIndex("TELEFONO"));
                    sDireccion = resultado.getString(resultado.getColumnIndex("DIRECCION"));
                    sEmail = resultado.getString(resultado.getColumnIndex("EMAIL"));
                    sID = resultado.getString(resultado.getColumnIndex("ID"));
                    clientes.add(sID+" : "+sNombre+": "+sTelefono+"\n"+sEmail);
                }
                while (resultado.moveToNext());
            }

            adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,clientes);
            lstDatos.setAdapter((adaptador));


        } catch (Exception ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Aviso");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton("OK",null);
            dlg.show();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        actualizar();
    }

}
