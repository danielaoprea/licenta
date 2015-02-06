package ro.softvision.cancerdiagnosticapp.beans;

public class Radiography {

	private long idRadiography;
	private String path;
	private long date;
	private boolean diagnostic;
	private String details;
	private long cnpPacient;

	public Radiography() {

	}

	public Radiography(long idRadiography, String path, long date,
			boolean diagnostic, String details, long cnpPacient) {

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

	public boolean getDiagnostic() {
		return diagnostic;
	}

	public String getDetails() {
		return details;
	}

	public long getCnpPacient() {
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

	public void setDiagnostic(boolean diagnostic) {
		this.diagnostic = diagnostic;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public void setCnpPacient(long cnpPacient) {
		this.cnpPacient = cnpPacient;
	}

	@Override
	public String toString() {
		return idRadiography + " " + path + " " + date + " " + diagnostic + " "
				+ details + " " + cnpPacient;
	}
}
