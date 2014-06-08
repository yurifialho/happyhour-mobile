package br.edu.fa7.heppyhour;

import java.util.ArrayList;
import java.util.List;

import br.edu.fa7.heppyhour.sms.Sms;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


public class ListaContatosActivity extends Activity implements  OnItemLongClickListener  {
	
	private List<Convidado> contatos = new ArrayList<Convidado>();
	private ListView lista;
	
	private EditText evento;
	private EditText local;
	private EditText hora;
	private EditText data;
	
	private Button enviar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.layout_contatos);
		
		Button btnContatos = (Button) findViewById(R.id.btnContatos);
		lista = (ListView) findViewById(R.id.listaContatos);
		lista.setOnItemLongClickListener(this);
		
		btnContatos.setOnClickListener(new  Button.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Uri uri = Uri.parse("content://com.android.contacts/contacts/");
				Intent intent  = new Intent(Intent.ACTION_PICK,uri);
				startActivityForResult(intent,1);
				
				
			}
				
		});
		final EventoDAO eventoDAO = new EventoDAO(this);
		
		evento = (EditText) findViewById(R.id.dtEvento);
		local = (EditText) findViewById(R.id.dtLocal);
		data = (EditText) findViewById(R.id.edtextData);
		hora = (EditText) findViewById(R.id.editText1);
		
		
		enviar = (Button) findViewById(R.id.btnEnviar);
		enviar.setOnClickListener(new  Button.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				contatos = new ConvidadoDAO(ListaContatosActivity.this).buscarTodosConvidados();
				if(contatos != null && !contatos.isEmpty()) {
					for(Convidado c : contatos) {
						Sms.enviarSms(ListaContatosActivity.this, c.getContato(), "Ola " + c.getNome() + ". Voce foi convidado para Evento: " + evento.getEditableText() 
								+ ": Local: " + local.getEditableText() 
								+ ": Data: " + data.getEditableText() 
								+ ": Hora: " + hora.getEditableText() + " ! Envie a palavra VOU via SMS para confirmar sua presenca.");
						
						eventoDAO.excluirAll();
						Evento mevento = new Evento(local.getEditableText().toString(), data.getEditableText().toString(), hora.getEditableText().toString(), evento.getEditableText().toString());
						eventoDAO.inserir(mevento);
					}
					Toast.makeText(ListaContatosActivity.this, "Todos os Convites foram enviados", Toast.LENGTH_LONG).show();
				}
				
			}
				
		});
		
		
		List<Evento> es = eventoDAO.buscarTodosEventos();
		if(es != null && !es.isEmpty()) {
			for(Evento e : es) {
				evento.setText(e.getDescricao());
				local.setText(e.getLocal());
				data.setText(e.getData());
				hora.setText(e.getHora());
			}
		}
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	
			
		ConvidadoDAO convidadoDAO = new ConvidadoDAO(this);
			contatos = convidadoDAO.buscarTodosConvidados();
			 
		 lista.setAdapter(new ContatosAdapter(this,contatos));  //atualiza a listaview
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if(data == null){
			
			Toast.makeText(this, "Nenhum Contato Selecionado", Toast.LENGTH_SHORT).show();
			return;
			
		}
		
		Uri uri = data.getData();
		
		
		Cursor c = getContentResolver().query(uri,null,null,null, null);
		
		c.moveToNext();
		
		int nomeId = c.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME);
		int numId = c.getColumnIndex(ContactsContract.Contacts._ID);
		String nome = c.getString(nomeId);
		String id = c.getString(numId);
		
		Cursor n = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? ", new String[] {id}, null);
		String phone = null;
		while (n.moveToNext()) {
			phone = n.getString(n.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
		}
		
		ConvidadoDAO convidadoDAO  = new ConvidadoDAO(this);
		Convidado convidado	= new Convidado(nome,"1",phone,evento.getEditableText().toString());
			convidadoDAO.inserir(convidado);
		
		Toast.makeText(this, "Contato Selecionado: "+ nome, Toast.LENGTH_LONG).show();
		
		
		
		
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		
		final int pos = arg2;
		
		AlertDialog.Builder confirm = new AlertDialog.Builder(this);
		confirm.setTitle("ATENCAO!");
		confirm.setMessage("Realmente deseja excluir este item da lista?");
		
		confirm.setPositiveButton("Sim", new DialogInterface.OnClickListener(){
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				new ConvidadoDAO(ListaContatosActivity.this).delete(contatos.get(pos));
				contatos.remove(pos);
				//new LancamentoDB(this).delete(this..get(arg2));
				//this.lancamentos.remove(arg2);
			
			onResume();
			
			}});
		
        confirm.setNegativeButton("Nao", new DialogInterface.OnClickListener(){
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub		
			
			}});
			
		confirm.show();	
		
		return false;

	}

	

}
