package info.mzimmermann.libxposed.apps.settings;

import java.util.List;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import android.preference.PreferenceActivity.Header;

public class LXAppSettings {

	private static boolean settingsAppHooked = false;

	public static void registerSettingsAppHook(LoadPackageParam lpparam,
			final LXSettingsCallback iface) {
		if (!lpparam.packageName.equals("com.android.settings"))
			return;

		if (settingsAppHooked)
			return;

		Class<?> SettingsClazz = XposedHelpers.findClass(
				"com.android.settings.Settings", lpparam.classLoader);
		XposedBridge.hookAllMethods(SettingsClazz, "updateHeaderList",
				new XC_MethodHook() {
					@SuppressWarnings("unchecked")
					@Override
					protected void beforeHookedMethod(MethodHookParam param)
							throws Throwable {
						List<Header> headers = (List<Header>) param.args[0];
						iface.updateHeaderList(headers);
					}
				});

		settingsAppHooked = true;
	}
}
