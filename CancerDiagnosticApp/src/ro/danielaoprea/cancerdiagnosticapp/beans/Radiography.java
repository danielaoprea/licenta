package ro.danielaoprea.cancerdiagnosticapp.beans;

public class Radiography {

	private long idRadiography;
	private String path;
	private long date;
	private String diagnostic;
	private String details;
	private String cnpPacient;

	public Radiography() {

	}

	public Radiography(long idRadiography, String path, long date,
			String diagnostic, String details, String cnpPacient) {

		this.idRadiography = idRadiography;
		this.path = path;
		this.date = date;
		this.diagnostic = diagnostic;
		this.details = details;
		this.cnpPacient = cnpPacient;
	}

	public long getIdRadiography() {
		return idRadiography;
	}

	public String getPath() {
		return path;
	}

	public long getDate() {
		return date;
	}

	public String getDiagnostic() {
		return diagnostic;
	}

	public String getDetails() {
		return details;
	}

	public String getCnpPacient() {
		return cnpPacient;
	}

	public void setIdRadiography(long idRadiography) {
		this.idRadiography = idRadiography;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public void setDiagnostic(String diagnostic) {
		this.diagnostic = diagnostic;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public void setCnpPacient(String cnpPacient) {
		this.cnpPacient = cnpPacient;
	}

	@Override
	public String toString() {
		return idRadiography + " " + path + " " + date + " " + diagnostic + " "
				+ details + " " + cnpPacient;
	}
}
