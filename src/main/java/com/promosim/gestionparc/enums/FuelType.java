package com.promosim.gestionparc.enums;

public enum FuelType {
    DIESEL("Diesel"),
    GASOLINE("Essence"),
    ELECTRIC("Électrique"),
    HYBRID("Hybride");

    private final String label;

    FuelType(String label) {
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

