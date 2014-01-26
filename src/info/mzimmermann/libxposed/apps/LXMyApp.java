package info.mzimmermann.libxposed.apps;

import android.content.Intent;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;

public class LXMyApp {

	public static boolean transferPreferences(String action,
			Preference preference, Object value) {
		Intent i = new Intent(action);

		if (value instanceof Boolean) {
			i.putExtra(preference.getKey(), (Boolean) value);
		} else if (value instanceof Float) {
			i.putExtra(preference.getKey(), (Float) value);
		} else if (value instanceof Integer) {
			i.putExtra(preference.getKey(), (Integer) value);
		} else if (value instanceof Long) {
			i.putExtra(preference.getKey(), (Long) value);
		} else if (value instanceof String) {
			i.putExtra(preference.getKey(), (String) value);
		} else if (value instanceof String[]) {
			i.putExtra(preference.getKey(), (String[]) value);
		} else {
			throw new IllegalArgumentException(value.getClass()
					.getCanonicalName() + " is not a supported Preference!");
		}

		preference.getContext().sendBroadcast(i);
		return true;
	}

	public static void setTransferOnPreferenceChangeListener(
			final String action, Preference p) {
		setTransferOnPreferenceChangeListener(action, p, null);
	}

	public static void setTransferOnPreferenceChangeListener(
			final String action, Preference p,
			final OnPreferenceChangeListener l) {
		p.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

			@Override
			public boolean onPreferenceChange(Preference preference,
					Object newValue) {

				transferPreferences(action, preference, newValue);

				if (l != null)
					return l.onPreferenceChange(preference, newValue);

				return true;
			}
		});
	}
}
