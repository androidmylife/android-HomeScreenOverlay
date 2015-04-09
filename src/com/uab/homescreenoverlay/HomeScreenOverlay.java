package com.uab.homescreenoverlay;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class HomeScreenOverlay extends Service {

	private CustomImageView chatHead;

	@Override
	public IBinder onBind(Intent intent) {
		// Not used
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		chatHead = new CustomImageView(this);
		chatHead.setImageResource(R.drawable.ic_sticky);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		
		if (chatHead != null)
			chatHead.removeView();
	}
}
