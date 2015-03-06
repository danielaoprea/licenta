package ro.danielaoprea.cancerdiagnosticapp.beans;

public class Pacient implements Categorizable {

	private String cnp;
	private String name;
	private String address;
	private String phoneNumber;
	private String email;
	private long idDoctor;

	public Pacient() {

	}

	public Pacient(String cnp, String name, String address, String phoneNumber,
			String email, long idDoctor) {

		this.cnp = cnp;
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.idDoctor = idDoctor;
	}

	public String getCnp() {
		return cnp;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public long getIdDoctor() {
		return idDoctor;
	}

	public void setCnp(String cnp) {
		this.cnp = cnp;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setIdDoctor(long idDoctor) {
		this.idDoctor = idDoctor;
	}

	@Override
	public Type getType() {
		return Type.PACIENT;
	}

}
