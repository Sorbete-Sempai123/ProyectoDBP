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

public class ActBorrarCliente extends AppCompatActivity {

    private EditText Index;

    private SQLiteDatabase conexion;
    private DatosOpenHelper datosOpenHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_borrar_cliente);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Index = (EditText)findViewById(R.id.edtID);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_borrar_cliente,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (id){
            case R.id.action_okb:
                //Toast.makeText(this, "Boton Ok Seleccionado", Toast.LENGTH_SHORT).show();
                if(bCamposCorrectos()){
                    try{
                        datosOpenHelper = new DatosOpenHelper((this));
                        conexion = datosOpenHelper.getWritableDatabase();
                        StringBuilder sql = new StringBuilder();
                        sql.append("DELETE FROM CLIENTE WHERE ID = '"+Index.getText().toString().trim()+"';");
                        Toast.makeText(this, Index.toString(), Toast.LENGTH_SHORT).show();
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
            case R.id.action_cancelarb:
                //Toast.makeText(this,"Boton Cancerlar Seleccionado",Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean bCamposCorrectos(){
        boolean res = true;
        if(Index.getText().toString().trim().isEmpty()){
            Index.requestFocus();
            res = false;
        }
        return res;
    }

}
