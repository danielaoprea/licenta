package ro.danielaoprea.cancerdiagnosticapp.beans;

public class User implements Categorizable {

	public static final String DOCTOR = "doctor";
	public static final String ADMIN = "admin";
	private long id;
	private String name;
	private String password;
	private String username;
	private String email;
	private String phoneNumber;
	private String role;

	public User() {

	}

	public User(long id, String name, String password, String username,
			String email, String phoneNumber, String role) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.username = username;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.role = role;
	}

	public long getId() {
		return id;
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

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getRole() {
		return role;
	}

	public void setId(long idDoc) {
		this.id = idDoc;
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

	public void setUsername(String username) {
		this.username = username;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return id + " " + name + " " + password + " " + phoneNumber;
	}

	@Override
	public Type getType() {
		return Type.USER;
	}
}
