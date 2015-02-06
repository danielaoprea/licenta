package ro.softvision.cancerdiagnosticapp.beans;

public class Pacient {

	private long cnp;
	private String name;
	private String address;
	private String phoneNumber;
	private long idDoctor;

	public Pacient() {

	}

	public Pacient(long cnp, String name, String address, String phoneNumber,
			long idDoctor) {

		this.cnp = cnp;
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.idDoctor = idDoctor;
	}

	public long getCnp() {
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

	public long getIdDoctor() {
		return idDoctor;
	}

	public void setCnp(long cnp) {
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

	public void setIdDoctor(long idDoctor) {
		this.idDoctor = idDoctor;
	}

	@Override
	public String toString() {

		return cnp + " " + name + " " + address + " " + phoneNumber + " "
				+ idDoctor;
	}
}
