package br.edu.fa7.heppyhour;

import br.edu.fa7.heppyhour.sms.Sms;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CheckInActivity extends Activity implements OnClickListener{
	
	private Button checkIn;
	private EditText evento;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_in);
		
		this.evento = (EditText) findViewById(R.id.tx_nome_evento);
		
		this.checkIn = (Button) findViewById(R.id.bt_checkin);
		this.checkIn.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.check_in, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.bt_checkin) {
			Sms.enviarSms(this, "8591847147", "Checkin HeppyHour no envento: " + evento.getText().toString() + " - Oi");
			Toast.makeText(this, "CheckIn feito.", Toast.LENGTH_LONG).show();
		}
		
	}
	
	
	 
}
