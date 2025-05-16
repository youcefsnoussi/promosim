package com.promosim.gestionparc.enums;

public enum MissionStatus {
    ONGOING("En cours"),
    COMPLETED("Terminé");

    private final String label;

    MissionStatus(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
    @Override
    public String toString() {
        return label; // Ensure it prints the label when converted to String
    }


}
