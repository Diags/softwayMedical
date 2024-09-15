package com.softwaymedical.service;


import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import com.softwaymedical.enums.HealthIndexType;
import com.softwaymedical.exception.InvalidIndexException;


/**
 * Service pour déterminer les départements médicaux en fonction de l'index de santé.
 */
@Service
public class PathologyRoutingService {

    /**
     * Obtient les départements médicaux appropriés en fonction de l'index de santé.
     * @param index l'index de santé.
     * @return les départements médicaux associés.
     * @throws InvalidIndexException si l'index est invalide.
     */
    public String getMedicalDepartments(Integer index) {
        if (index == null || index <= 0) {
            throw new InvalidIndexException("L'index doit être un nombre entier positif.");
        }

        String department = HealthIndexType.getDepartmentsForIndex(index);
        if (Strings.isBlank(department)) {
            return "Département médical par defaut ";
        }

        return department;
    }
}
