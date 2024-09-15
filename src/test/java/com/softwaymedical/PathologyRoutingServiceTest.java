package com.softwaymedical;

import com.softwaymedical.exception.InvalidIndexException;
import com.softwaymedical.service.PathologyRoutingService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PathologyRoutingServiceTest {

	private PathologyRoutingService pathologyRoutingService;

	@BeforeEach
	void setUp() {
		pathologyRoutingService = new PathologyRoutingService();
	}

	@Test
	void shouldReturnCardiologyWhenIndexIsMultipleOf3() {
		String result = pathologyRoutingService.getMedicalDepartments(3);
		assertEquals("Cardiologie", result, "L'index 3 doit renvoyer Cardiologie.");
	}

	@Test
	void shouldReturnTraumatologieWhenIndexIsMultipleOf5() {
		String result = pathologyRoutingService.getMedicalDepartments(5);
		assertEquals("Traumatologie", result, "L'index 5 doit renvoyer Traumatologie.");
	}

	@Test
	void shouldReturnCardiologieAndTraumatologieWhenIndexIsMultipleOf_3And5() {
		String result = pathologyRoutingService.getMedicalDepartments(15);
		assertEquals("Cardiologie, Traumatologie", result, "L'index 15 doit renvoyer Cardiologie, Traumatologie.");
	}

	@Test
	void testGetMedicalDepartments_InvalidIndex_Negative() {
		InvalidIndexException thrown = assertThrows(InvalidIndexException.class, () -> {
			pathologyRoutingService.getMedicalDepartments(-1);
		});
		assertEquals("L'index doit être un nombre entier positif.", thrown.getMessage());
	}

	@Test
	void testGetMedicalDepartments_InvalidIndex_Zero() {
		InvalidIndexException thrown = assertThrows(InvalidIndexException.class, () -> {
			pathologyRoutingService.getMedicalDepartments(0);
		});
		assertEquals("L'index doit être un nombre entier positif.", thrown.getMessage());
	}

	@Test
	void testGetMedicalDepartments_NoDepartment() {
		String result = pathologyRoutingService.getMedicalDepartments(7);
		assertEquals("Aucune pathologie détectée", result,
				"L'index 7 ne doit pas correspondre à un département.");
	}
}