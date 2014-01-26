package info.mzimmermann.libxposed.apps.settings;

import java.util.List;
import android.preference.PreferenceActivity.Header;

public interface LXSettingsCallback {
	public void updateHeaderList(List<Header> target);
}
