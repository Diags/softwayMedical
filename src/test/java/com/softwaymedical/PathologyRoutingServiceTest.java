package com.softwaymedical;

import com.softwaymedical.exception.InvalidIndexException;
import com.softwaymedical.service.PathologyRoutingService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Classe de test unitaire pour PathologyRoutingService.
 * Cette classe utilise JUnit pour tester le comportement du service de routage des pathologies.
 */
class PathologyRoutingServiceTest {

	private PathologyRoutingService pathologyRoutingService;

	/**
	 * Méthode d'initialisation exécutée avant chaque test.
	 * Initialise une instance de PathologyRoutingService.
	 */
	@BeforeEach
	void setUp() {
		pathologyRoutingService = new PathologyRoutingService();
	}

	/**
	 * Teste si l'index 3 retourne correctement "Cardiologie".
	 * Vérifie que l'index est un multiple de 3 et que la pathologie associée est correcte.
	 */
	@Test
	void shouldReturnCardiologyWhenIndexIsMultipleOf3() {
		String result = pathologyRoutingService.getMedicalDepartments(3);
		assertEquals("Cardiologie", result, "L'index 3 doit renvoyer Cardiologie.");
	}

	/**
	 * Teste si l'index 5 retourne correctement "Traumatologie".
	 * Vérifie que l'index est un multiple de 5 et que la pathologie associée est correcte.
	 */
	@Test
	void shouldReturnTraumatologieWhenIndexIsMultipleOf5() {
		String result = pathologyRoutingService.getMedicalDepartments(5);
		assertEquals("Traumatologie", result, "L'index 5 doit renvoyer Traumatologie.");
	}

	/**
	 * Teste si l'index 15 retourne à la fois "Cardiologie" et "Traumatologie".
	 * Vérifie que l'index est multiple de 3 et 5, et que les deux pathologies sont retournées.
	 */
	@Test
	void shouldReturnCardiologieAndTraumatologieWhenIndexIsMultipleOf_3And5() {
		String result = pathologyRoutingService.getMedicalDepartments(15);
		assertEquals("Cardiologie, Traumatologie", result, "L'index 15 doit renvoyer Cardiologie, Traumatologie.");
	}

	/**
	 * Teste si une exception InvalidIndexException est levée pour un index négatif.
	 * Vérifie que le message d'exception est bien "L'index doit être un nombre entier positif.".
	 */
	@Test
	void testGetMedicalDepartments_InvalidIndex_Negative() {
		InvalidIndexException thrown = assertThrows(InvalidIndexException.class, () -> {
			pathologyRoutingService.getMedicalDepartments(-1);
		});
		assertEquals("L'index doit être un nombre entier positif.", thrown.getMessage());
	}

	/**
	 * Teste si une exception InvalidIndexException est levée pour un index égal à 0.
	 * Vérifie que le message d'exception est bien "L'index doit être un nombre entier positif.".
	 */
	@Test
	void testGetMedicalDepartments_InvalidIndex_Zero() {
		InvalidIndexException thrown = assertThrows(InvalidIndexException.class, () -> {
			pathologyRoutingService.getMedicalDepartments(0);
		});
		assertEquals("L'index doit être un nombre entier positif.", thrown.getMessage());
	}

	/**
	 * Teste si un index sans pathologie associée (comme 7) retourne "Aucune pathologie détectée".
	 * Vérifie que la réponse pour un index non valide est correctement gérée.
	 */
	@Test
	void testGetMedicalDepartments_NoDepartment() {
		String result = pathologyRoutingService.getMedicalDepartments(7);
		assertEquals("Aucune pathologie détectée", result, "L'index 7 ne doit pas correspondre à un département.");
	}
}