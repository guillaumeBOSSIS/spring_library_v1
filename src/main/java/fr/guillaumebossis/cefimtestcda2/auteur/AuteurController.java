package fr.guillaumebossis.cefimtestcda2.auteur;

import fr.guillaumebossis.cefimtestcda2.entities.Auteur;
import fr.guillaumebossis.cefimtestcda2.entities.repository.AuteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auteur")
@Validated
public class AuteurController {

    @Autowired
    private AuteurService auteurService;

    @Autowired
    private AuteurRepository auteurRepository;

    @GetMapping("/all")
    public List<Auteur> getAllAuteurs() {
        return auteurService.getAll();
    }

    @GetMapping("/name")
    public List<Auteur> getAuteursByName(@RequestParam String name) {
        return auteurService.getAuteursByName(name);
    }

    @GetMapping("/{id}")
    public AuteurWithBooks getAuteurWithBooks(@PathVariable("id") Integer auteurId) {
        return auteurService.getAuteurWithBooks(auteurId);
    }
}

