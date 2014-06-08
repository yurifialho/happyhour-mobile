package br.edu.fa7.heppyhour;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	EventoDAO eventoDAO;
	ConvidadoDAO convidadoDAO;
	
	private Button checkIn;
	private Button convidar;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eventoDAO = new EventoDAO(this);
        convidadoDAO = new ConvidadoDAO(this);
        
        checkIn = (Button) findViewById(R.id.buttonRealizarCheckin);
        checkIn.setOnClickListener(this);
        convidar = (Button) findViewById(R.id.buttonCadastrar);
        convidar.setOnClickListener(this);
        
  
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.buttonRealizarCheckin) {
			Intent i = new Intent(this, CheckInActivity.class);
			startActivity(i);
		} 
		if(v.getId() == R.id.buttonCadastrar) {
			Intent i = new Intent(this, ListaContatosActivity.class);
			startActivity(i);
		}
		
	}

}
