package info.mzimmermann.libxposed;

import java.util.HashMap;
import java.util.Set;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class LXTools {

	public static final String LOG_TAG = "libxposed";

	public static void removeInvalidPreferences(
			HashMap<String, Class<?>> prefs, SharedPreferences sp) {
		Editor editor = sp.edit();

		for (String key : prefs.keySet()) {
			try {
				if (prefs.get(key) == Boolean.class)
					sp.getBoolean(key, false);
				else if (prefs.get(key) == Float.class)
					sp.getFloat(key, 0);
				else if (prefs.get(key) == Integer.class)
					sp.getInt(key, 0);
				else if (prefs.get(key) == Long.class)
					sp.getLong(key, 0);
				else if (prefs.get(key) == String.class)
					sp.getString(key, null);
				else if (prefs.get(key) == Set.class)
					sp.getStringSet(key, null);

			} catch (ClassCastException e) {
				Log.w(LOG_TAG, "Invalid key: " + key);
				editor.remove(key);
			}
		}

		editor.commit();
	}

}
