package br.edu.fa7.heppyhour.sms;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

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
				Log.i("XTP", msgs[i].getMessageBody());
			}
			
		}
	}
	
}
