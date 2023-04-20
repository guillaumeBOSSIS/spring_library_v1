package fr.guillaumebossis.cefimtestcda2.auteur;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.guillaumebossis.cefimtestcda2.entities.Auteur;
import fr.guillaumebossis.cefimtestcda2.entities.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class AuteurTests {

    @Autowired
    private AuteurService auteurService;

    // Classe pour simuler des appels REST
    @Autowired
    private MockMvc mockMvc;

    // Classe de sérialisation / désérialisation
    @Autowired
    private ObjectMapper objectMapper;


    Auteur auteurTest = new Auteur("de Saint-Exupery", "Antoine", 1930);

    @Test
    void testHelloAuteur() {
        assert auteurService.helloAuteur().equals(("Hello Auteur !"));
    }

    @Test
    void testEquals() {
        Auteur auteur1 = new Auteur("Bossis", "Guillaume", 1982);
        Auteur auteur2 = new Auteur("Bossis", "Guillaume", 1982);
        assert auteurService.equalsAuteur(auteur1, auteur2);
    }

    @Test
    void testNotEquals() {
        Auteur auteur1 = new Auteur("Bossis", "Guillaume", 1982);
        Auteur auteur2 = new Auteur("Bossis", "Guillaume", 1980);
        assert !auteurService.equalsAuteur(auteur1, auteur2);
    }

    @Test
    void testGetAllAuteursByAPI() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/auteur/all");
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        List<Auteur> auteurs = Arrays.asList(objectMapper.readValue(contentAsString, Auteur[].class));
        assert auteurs.contains(auteurTest);
    }

//    @Test
//    void testGetAuteurById(int id) {
//        Auteur auteur = new Auteur("de Saint-Exupery", "Antoine", 1930);
//    }



    @Test
    void testFindAuteurOK() throws Exception {
        Auteur auteur1 = new Auteur("Dumas", "Alexandre", 1880);

        RequestBuilder request = MockMvcRequestBuilders.get("/api/auteur/all");
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        List<Auteur> auteurs = Arrays.asList(objectMapper.readValue(contentAsString, Auteur[].class));
        assert auteurs.contains(auteur1);
    }

    @Test
    void testFindAuteurKO() throws Exception {
        Auteur auteur1 = new Auteur("Guillaume", "Bossis", 1982);

        RequestBuilder request = MockMvcRequestBuilders.get("/api/auteur/all");
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        List<Auteur> auteurs = Arrays.asList(objectMapper.readValue(contentAsString, Auteur[].class));
        assert !auteurs.contains(auteur1);
    }

}
