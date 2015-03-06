package ro.danielaoprea.cancerdiagnosticapp.utils;

public class Constants {

	public static class IntentExtras {

		public static final String EXTRA_AUTHENTICATION_DOCTOR_ID = "authentication_doctor_id";
		public static final String EXTRA_DOCTOR_ID = "doctor_id";
		public static final String EXTRA_USERNAME = "user_name";
		public static final String EXTRA_CNP = "pacient_cnp";
		public static final String EXTRA_PACIENT_DETAILS_CNP = "pacient_details_cnp";
		public static final String EXTRA_RADIOGRAPHY_ID = "radiography_id";
		public static final String EXTRA_RADIOGRAPHY_DATE = "radiography_date";
		public static final String EXTRA_RADIOGRAPHY_DIAGNOSTIC = "radiography_diagnostic";
		public static final String EXTRA_RADIOGRAPHY_DETAILS = "radiography_details";
	}

	public static class Password {
		public static final int PASSWORD_CHANGED = 1;
		public static final int WRONG_CURRENT_PASSWORD = 2;
		public static final int WRONG_CONFIRMED_PASSWORD = 3;
	}

	public static class Prefs {
		public static final String USER_PREFERENCES_FILE = "ro.danielaoprea.cancerdiagnosticapp.preferences_file";
		public static final String LOGGED_DOCTOR_ID = "doctor_id";
		public static final long NO_DOCTOR_LOGGED = -1;
	}
}
