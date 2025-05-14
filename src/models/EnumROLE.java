package models;

public enum EnumROLE {
	PATIENT,
	DOCTEUR,
	SCHEDULER,
	ADMINISTRATEUR;
	
	public static EnumROLE fromString(String role) {
		switch (role.toUpperCase()) {
			case "PATIENT":
				return PATIENT;
			case "DOCTEUR":
				return DOCTEUR;
			case "SCHEDULER":
				return SCHEDULER;
			case "ADMINISTRATEUR":
				return ADMINISTRATEUR;
			default:
				throw new IllegalArgumentException("Unknown role: " + role);
		}
	}

}
