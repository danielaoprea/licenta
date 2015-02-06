package ro.softvision.cancerdiagnosticapp.beans;

public class Doctor {

	public static final int STATUS_LOGGED = 1;
	public static final int STATUS_NOT_LOGGED = 2;
	private long idDoc;
	private String name;
	private String password;
	private String phoneNumber;
	private int authenticationStatus;

	public Doctor() {

	}

	public Doctor(long idDoctor, String name, String password,
			String phoneNumber, int authenticationStatus) {
		this.idDoc = idDoctor;
		this.name = name;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.authenticationStatus = authenticationStatus;
	}

	public long getIdDoc() {
		return idDoc;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public int getAuthenticationStatus() {
		return authenticationStatus;
	}

	public void setIdDoc(long idDoc) {
		this.idDoc = idDoc;
	}

	public void setAuthenticationStatus(int authenticationStatus) {
		this.authenticationStatus = authenticationStatus;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return idDoc + " " + name + " " + password + " " + phoneNumber;
	}
}
