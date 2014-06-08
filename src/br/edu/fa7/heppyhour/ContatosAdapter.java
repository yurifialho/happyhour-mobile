package br.edu.fa7.heppyhour;

import java.util.List;

import br.edu.fa7.heppyhour.R.drawable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ContatosAdapter extends BaseAdapter{

	private Context contexto;
	private List<Convidado> lista;
	
	
	public  ContatosAdapter(Context contexto, List<Convidado> lista) {
		// TODO Auto-generated constructor stub
		
		this.contexto = contexto;
		this.lista = lista;

	}
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lista.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return lista.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	//M�todo que faz a adapta��o entre o xml de layout e a listView
	@Override
	public View getView(int posicao, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub

		Convidado contato = lista.get(posicao);
		
		LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.layout_contatos_adapter, null);
		
		TextView tvContato = (TextView) v.findViewById(R.id.tvContato);
		TextView tvNumero = (TextView) v.findViewById(R.id.tvNumero);
		ImageView img = (ImageView) v.findViewById(R.id.imgCheck);
		tvContato.setText(contato.getNome());
		tvNumero.setText(contato.getContato());
		if(contato.getStatus().equals("2")) {
			img.setImageResource(R.drawable.clean);
//			linha.setBackgroundColor(Color.YELLOW);
		} else if(contato.getStatus().equals("3")){
			img.setImageResource(R.drawable.sc20558);
//			linha.setBackgroundColor(Color.GREEN);
		} else {
			img.setImageResource(R.drawable.quick_restart);
//			linha.setBackgroundColor(Color.RED);
		}
	
		
		return v;
	}



}
