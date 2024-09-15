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

@ExtendWith(SpringExtension.class)
@WebMvcTest(HealthIndexController.class)
class HealthIndexControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PathologyRoutingService routingService;

    @Test
    void testGetMedicalDepartments_ValidIndex() throws Exception {
        when(routingService.getMedicalDepartments(15)).thenReturn("Cardiologie, Traumatologie");

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/departement/{index}", 15))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("Cardiologie, Traumatologie"));

        assertNotNull(result);
    }

    @Test
    void testGetMedicalDepartments_InvalidIndex() throws Exception {
        when(routingService.getMedicalDepartments(-1))
                .thenThrow(new InvalidIndexException("L'index doit être un nombre entier positif."));

        mockMvc.perform(MockMvcRequestBuilders.get("/departement/{index}", -1))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$")
                        .value("getMedicalDepartments.index: must be greater than or equal to 1"));
    }

    @Test
    void testGetMedicalDepartments_ValidIndex_EmptyResponse() throws Exception {
        when(routingService.getMedicalDepartments(7)).thenReturn("Aucun département médical déterminé");

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/departement/{index}", 7))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("Aucun département médical déterminé"));

        assertNotNull(result);
    }
}
