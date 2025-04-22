package com.promosim.gestionparc.enums;

public enum VehicleType {
    TRUCK("Camion"),
    CAR("Voiture"),
    VAN("Van");

    private final String label;

    VehicleType(String label) {
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

