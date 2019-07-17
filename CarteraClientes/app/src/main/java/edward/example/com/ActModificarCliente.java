package edward.example.com;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import edward.example.com.BaseDatos.DatosOpenHelper;

public class ActModificarCliente extends AppCompatActivity {

    private EditText edtNombre;
    private EditText edtDireccion;
    private EditText edtEmail;
    private EditText edtTelefono;
    private EditText edtID;

    private SQLiteDatabase conexion;
    private DatosOpenHelper datosOpenHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_modificar_cliente);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edtNombre = (EditText)findViewById(R.id.edtNNombre);
        edtDireccion = (EditText)findViewById(R.id.edtNDireccion);
        edtEmail = (EditText)findViewById(R.id.edtNEmail);
        edtTelefono = (EditText)findViewById(R.id.edtNTelefono);
        edtID = (EditText)findViewById(R.id.edtNID);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_modificar_cliente,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (id){
            case R.id.action_okm:
                //Toast.makeText(this, "Boton Ok Seleccionado", Toast.LENGTH_SHORT).show();
                if(bCamposCorrectos()){
                    try{
                        datosOpenHelper = new DatosOpenHelper((this));
                        conexion = datosOpenHelper.getWritableDatabase();
                        StringBuilder sql = new StringBuilder();
                        sql.append("UPDATE CLIENTE SET NOMBRE ='"+edtNombre.getText().toString().trim()+"',");
                        sql.append("DIRECCION ='"+edtDireccion.getText().toString().trim()+"',");
                        sql.append("EMAIL ='"+edtEmail.getText().toString().trim()+"',");
                        sql.append("TELEFONO ='"+edtTelefono.getText().toString().trim()+"'");
                        sql.append("WHERE ID ='"+edtID.getText().toString().trim()+"';");

                        conexion.execSQL(sql.toString());
                        conexion.close();

                        finish();
                    } catch (Exception ex){
                        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                        dlg.setTitle("Aviso");
                        dlg.setMessage(ex.getMessage());
                        dlg.setNeutralButton("OK",null);
                        dlg.show();
                    }

                }
                else {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                    dlg.setTitle("Aviso");
                    dlg.setMessage("Existen campos vacios");
                    dlg.setNeutralButton("OK",null);
                    dlg.show();
                }
                break;
            case R.id.action_cancelarm:
                //Toast.makeText(this,"Boton Cancerlar Seleccionado",Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean bCamposCorrectos(){
        boolean res = true;
        if(edtNombre.getText().toString().trim().isEmpty()){
            edtNombre.requestFocus();
            res = false;
        }
        if(edtDireccion.getText().toString().trim().isEmpty()){
            edtDireccion.requestFocus();
            res = false;
        }
        if(edtEmail.getText().toString().trim().isEmpty()&& !edtEmail.getText().toString().contains("@")){
            edtEmail.requestFocus();
            res = false;
        }
        if(edtTelefono.getText().toString().trim().isEmpty()){
            edtTelefono.requestFocus();
            res = false;
        }
        if(edtID.getText().toString().trim().isEmpty()){
            edtID.requestFocus();
            res = false;
        }
        return res;
    }

}
