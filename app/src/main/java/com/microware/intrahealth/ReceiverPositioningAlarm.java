package com.microware.intrahealth;



import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ReceiverPositioningAlarm extends BroadcastReceiver {

	@Override
	public void onReceive(final Context context, Intent intent) {
		// TODO Auto-generated method stub
		// Toast.makeText(context, "new request received by receiver",
		// Toast.LENGTH_SHORT).show();

		Intent in = new Intent(context, ConnectionService.class);
		context.startService(in);

	}

}
