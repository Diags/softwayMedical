package com.softwaymedical.enums;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Enumération représentant les différents numéros d'index et les pathologies associées.
 */
public enum HealthIndexType {
    CARDIOLOGY(3, "Cardiologie"),
    TRAUMATOLOGY(5, "Traumatologie");

    private final int indexValue;
    private final String department;

    HealthIndexType(int indexValue, String department) {
        this.indexValue = indexValue;
        this.department = department;
    }

    public int getIndexValue() {
        return indexValue;
    }

    public String getDepartment() {
        return department;
    }
   /**
     * Retourne la liste des pathologies associées à un index donné.
     *
     * @param index l'index de santé à évaluer
     * @return une chaîne de caractères représentant les départements associés
     */
    /**
     * Retourne une chaîne de départements correspondant à l'index ou "Aucune pathologie détectée".
     *
     * @param index l'index de santé à évaluer
     * @return une chaîne de départements ou un message d'absence de pathologie
     */
    public static String getDepartmentsForIndex(int index) {
        String result = Arrays.stream(values())
            .filter(type -> index % type.indexValue == 0)
            .map(HealthIndexType::getDepartment)
            .collect(Collectors.joining(", "));

        return result.isEmpty() ? "Aucune pathologie détectée" : result;
    }
}