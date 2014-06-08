package br.edu.fa7.heppyhour.sms;

import java.util.ArrayList;
import java.util.List;

import br.edu.fa7.heppyhour.Convidado;
import br.edu.fa7.heppyhour.ConvidadoDAO;
import br.edu.fa7.heppyhour.Evento;
import br.edu.fa7.heppyhour.EventoDAO;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class Sms extends BroadcastReceiver {

	public static void enviarSms(Context context, String destino, String msg) {
		try {
			SmsManager smsManager = SmsManager.getDefault();
				PendingIntent pi = PendingIntent.getBroadcast(context, 0, new Intent(), 0);
				smsManager.sendTextMessage(destino, null, msg, pi, null);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		Bundle bundle = arg1.getExtras();
		SmsMessage[] msgs = null;
		
		String str = "";
		if(bundle != null) {
			Object[] pdus = (Object[]) bundle.get("pdus");
			msgs = new SmsMessage[pdus.length];
			for(int i = 0; i< msgs.length; i++) {
				msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
			}
			
			for(int i = 0; i<msgs.length; i++) {
				String tel = msgs[i].getOriginatingAddress();
				if(msgs[i].getMessageBody().matches("[Vv][Oo][Uu]")) {
					ConvidadoDAO cDAO = new ConvidadoDAO(arg0);
					List<Convidado> convidados = cDAO.buscarTodosConvidados();
						for(Convidado c : convidados) {
							if(tel.replaceAll("[- ()]", "").matches(c.getContato().replaceAll("[- ()]", ""))){
								c.setStatus("2");
								cDAO.atualizar(c);
								Toast.makeText(arg0, c.getNome() + " Confirmou a presenca!", Toast.LENGTH_LONG).show();
								break;
							}
						}
						
				}
				if(msgs[i].getMessageBody().matches("^[Oo][lL][aA].*")) {
					String [] x1 = msgs[i].getMessageBody().split(" ");
					Evento e = new Evento();
						e.setDono(tel);
					String[] x = new String[x1.length];
					int cont = 0;
					for(String k : x1) {
						
						if(k != null && !k.trim().equals("")) {
							x[cont] = k;
							cont++;
						}
					}
					int fim = 0;
					for(int j = 0; i < x.length; j++) {
						if(fim==4) {
							break;
						}
						if(x[j].trim().equals("")) {
							continue;
						}
						if(x[j].startsWith("Local:")) {
							String local = x[j+1].substring(0, x[j+1].lastIndexOf(":"));
							e.setLocal(local);
							fim+=1;
						}
						if(x[j].startsWith("Data:")) {
							String data = x[j+1].substring(0, x[j+1].lastIndexOf(":"));
							e.setData(data);
							fim+=1;
						}
						if(x[j].startsWith("Hora:")) {
							String hora = x[j+1].substring(0, x[j+1].lastIndexOf(":"));
							e.setHora(hora);
							fim+=1;
						}
						if(x[j].startsWith("Evento:")) {
							String evento = x[j+1].substring(0, x[j+1].lastIndexOf(":"));
							e.setDescricao(evento);
							fim+=1;
						}
					}
					if(e.getDescricao() != null && e.getDescricao().length()>1) {
						EventoDAO ev = new EventoDAO(arg0);
							ev.excluirAll();
							ev.inserir(e);
					}
				}
				if(msgs[i].getMessageBody().matches("^[Cc][Hh][Ee][Cc][kK][iI][nN].*")) {
					ConvidadoDAO cDAO = new ConvidadoDAO(arg0);
					List<Convidado> convidados = cDAO.buscarTodosConvidados();
						for(Convidado c : convidados) {
							if(tel.replaceAll("[- ()]", "").matches(c.getContato().replaceAll("[- ()]", ""))){
								c.setStatus("3");
								cDAO.atualizar(c);
								Toast.makeText(arg0, c.getNome() + " Confirmou a presenca!", Toast.LENGTH_LONG).show();
								break;
							}
						}
				}	
			}
		}
	}

	
}
