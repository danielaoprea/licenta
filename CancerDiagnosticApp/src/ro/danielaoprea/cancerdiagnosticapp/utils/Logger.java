package ro.danielaoprea.cancerdiagnosticapp.utils;

import android.util.Log;

public class Logger {

	private static boolean ENABLE = true;

	public static void d(String tag, String msg) {
		if (ENABLE) {
			Log.d(tag, msg);
		}
	}

	public static void e(String tag, String msg) {
		if (ENABLE) {
			Log.e(tag, msg);

		}
	}

	public static void e(String tag, String msg, Throwable tr) {
		if (ENABLE) {
			Log.e(tag, msg, tr);

		}
	}

	public static void i(String tag, String msg) {
		if (ENABLE) {
			Log.e(tag, msg);

		}
	}

	public static void v(String tag, String msg) {
		if (ENABLE) {
			Log.v(tag, msg);

		}
	}

	public static void w(String tag, String msg) {
		if (ENABLE) {
			Log.w(tag, msg);

		}
	}

}
