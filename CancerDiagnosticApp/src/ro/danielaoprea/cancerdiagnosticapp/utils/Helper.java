package ro.danielaoprea.cancerdiagnosticapp.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

public class Helper {

	public static class SecurityUtils {

		public static String sha1(String data) {
			try {
				byte[] b = data.getBytes();
				MessageDigest md = MessageDigest.getInstance("SHA-1");
				md.reset();
				md.update(b);
				byte messageDigest[] = md.digest();
				StringBuilder result = new StringBuilder();
				for (int i = 0; i < messageDigest.length; i++) {
					result.append(Integer.toString(
							(messageDigest[i] & 0xff) + 0x100, 16).substring(1));
				}

				return result.toString();

			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	public static class Picture {
		public static Bitmap getBitmapFromPath(String path, int viewWidth,
				int viewHeith) {
			Options opts = new Options();
			opts.inJustDecodeBounds = true;
			Bitmap bMap = BitmapFactory.decodeFile(path, opts);
			opts.inSampleSize = Math.max(opts.outWidth / viewWidth,
					opts.outHeight / viewHeith);
			opts.inJustDecodeBounds = false;
			bMap = BitmapFactory.decodeFile(path, opts);
			return bMap;
		}
	}

	public static class Date {
		private static final String FORMATTER_DD_MM_YYYY = "dd/MM/yyyy";
		private static final String FORMATTER_DD_MM_YYYY_HH_MM = "dd/MM/yyyy ' at ' h:mm a";

		public static String getDateDayMonthyear(long miliSeconds) {
			SimpleDateFormat formatter = new SimpleDateFormat(
					FORMATTER_DD_MM_YYYY);
			String dateString = formatter.format(miliSeconds);
			return dateString;
		}

		public static String getDateDayMonthYearHourMinutes(long miliSeconds) {
			SimpleDateFormat formatter = new SimpleDateFormat(
					FORMATTER_DD_MM_YYYY_HH_MM);
			String dateString = formatter.format(miliSeconds);
			return dateString;
		}
	}

}
