package info.mzimmermann.libxposed;

import java.util.Arrays;
import java.util.HashSet;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;

public class LXTarget {

	public static void receivePreferences(SharedPreferences sp, Intent i) {
		Editor editor = sp.edit();
		Bundle extras = i.getExtras();
		for (String name : extras.keySet()) {
			Object value = extras.get(name);

			if (value instanceof Boolean) {
				editor.putBoolean(name, (Boolean) value);
			} else if (value instanceof Float) {
				editor.putFloat(name, (Float) value);
			} else if (value instanceof Integer) {
				editor.putInt(name, (Integer) value);
			} else if (value instanceof Long) {
				editor.putLong(name, (Long) value);
			} else if (value instanceof String) {
				editor.putString(name, (String) value);
			} else if (android.os.Build.VERSION.SDK_INT >= 11
					&& value instanceof String[]) {
				editor.putStringSet(name,
						new HashSet<String>(Arrays.asList((String[]) value)));
			} else {
				throw new IllegalArgumentException(value.getClass()
						.getCanonicalName() + " is not a supported Preference!");
			}
		}

		editor.commit();
	}

	public static void registerPreferenceChangeReceiver(Context context,
			final String action, final String prefKey) {
		IntentFilter filter = new IntentFilter();
		filter.addAction(action);
		context.registerReceiver(new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				if (intent.getAction().equals(action)) {
					receivePreferences(
							context.getSharedPreferences(prefKey, 0), intent);
				}
			}
		}, filter);
	}
}
