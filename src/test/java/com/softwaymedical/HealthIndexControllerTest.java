package com.softwaymedical;

import com.softwaymedical.controller.HealthIndexController;
import com.softwaymedical.exception.InvalidIndexException;
import com.softwaymedical.service.PathologyRoutingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * Classe de test unitaire pour le contrôleur {@link HealthIndexController}.
 * Utilise MockMvc pour tester les endpoints REST exposés par le contrôleur.
 * Ce test couvre les cas d'usage principaux tels que les indices valides, les indices invalides 
 * et les réponses vides.
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(HealthIndexController.class)
class HealthIndexControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PathologyRoutingService routingService;

    /**
     * Teste le cas où l'index est valide (par exemple 15).
     * Vérifie que le service renvoie correctement les pathologies associées ("Cardiologie, Traumatologie").
     * @throws Exception si une erreur survient lors de l'exécution de la requête.
     */
    @Test
    void testGetMedicalDepartments_ValidIndex() throws Exception {
        when(routingService.getMedicalDepartments(15)).thenReturn("Cardiologie, Traumatologie");

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/departement/{index}", 15))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("Cardiologie, Traumatologie"));

        assertNotNull(result);
    }

    /**
     * Teste le cas où l'index est invalide (par exemple -1).
     * Simule une exception levée par le service lorsque l'index est négatif ou non valide.
     * Vérifie que le contrôleur renvoie une réponse d'erreur avec un message approprié.
     * @throws Exception si une erreur survient lors de l'exécution de la requête.
     */
    @Test
    void testGetMedicalDepartments_InvalidIndex() throws Exception {
        when(routingService.getMedicalDepartments(-1))
                .thenThrow(new InvalidIndexException("L'index doit être un nombre entier positif."));

        mockMvc.perform(MockMvcRequestBuilders.get("/departement/{index}", -1))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$")
                        .value("getMedicalDepartments.index: must be greater than or equal to 1"));
    }

    /**
     * Teste le cas où l'index est valide mais ne correspond à aucun département médical connu (par exemple 7).
     * Vérifie que le service renvoie correctement une réponse indiquant qu'aucun département médical n'a été trouvé.
     * @throws Exception si une erreur survient lors de l'exécution de la requête.
     */
    @Test
    void testGetMedicalDepartments_ValidIndex_EmptyResponse() throws Exception {
        when(routingService.getMedicalDepartments(7)).thenReturn("Aucun département médical déterminé");

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/departement/{index}", 7))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("Aucun département médical déterminé"));

        assertNotNull(result);
    }
}