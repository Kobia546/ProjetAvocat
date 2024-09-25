package ci.projetAvocat.Controlleur;




import ci.projetAvocat.Entities.Agent;
import ci.projetAvocat.Repository.AgentRepository;
import ci.projetAvocat.Service.AgentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class Controlleur {

    private final AgentService agentService;
    private final AgentRepository  agentRepository;
    private final Agent agent;
    private static String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads";

    public Controlleur(AgentService agentService, AgentRepository agentRepository, Agent agent) {
        this.agentService = agentService;
        this.agentRepository = agentRepository;
        this.agent = agent;
    }

    @GetMapping("/agents/{id}")
    public String getAgentById(@PathVariable("id") Long id, Model model) {
        Agent agent = agentService.getAgentById(id);
        if (agent != null) {
            model.addAttribute("agent", agent);
            return "main";  // Correspond à la page Thymeleaf
        } else {
            return "error";  // Si l'agent n'est pas trouvé
        }
    }
    @GetMapping("/agents/form")
    public String showForm(Model model) {
        model.addAttribute("agent", new Agent());
        return "ajouter";  // Correspond à la page HTML pour le formulaire
    }
 /*   @PostMapping("/upload")
    public String handleImageUpload(@RequestParam("image") MultipartFile image, Agent agent) throws IOException {
        agent.setImage(image.getBytes()); // Convertir en byte array
        agentRepository.save(agent); // Sauvegarder l'image dans la base de données
        return "redirect:/success";
    }*/

    @PostMapping("/agents")
    public String addAgent(@RequestParam("nom") String nom,
                           @RequestParam("matricule") String matricule,
                           @RequestParam("numeroToge") String numeroToge,
                           @RequestParam("dateNaissance") String dateNaissance,
                           @RequestParam("lieuNaissance") String lieuNaissance,
                           @RequestParam("numeroSerie") String numeroSerie,
                           @RequestParam("statut") String statut,
                           @RequestParam("image") MultipartFile image) throws IOException {

        // Sauvegarder le fichier image
        String fileName = image.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR, fileName);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, image.getBytes());

        // Créer un nouvel agent
        Agent agent=new Agent();
        agent.setNom(nom);
        agent.setMatricule(matricule);
        agent.setNumeroToge(numeroToge);
        agent.setDateNaissance(dateNaissance);
        agent.setLieuNaissance(lieuNaissance);
        agent.setNumeroSerie(numeroSerie);
        agent.setStatut(statut);
        agent.setImagePath("/uploads/" + fileName);  // Stocke le chemin de l'image

        // Sauvegarde l'agent dans la base de données
        agentService.saveAgent(agent);

        return "redirect:/agents/" + agent.getId();  // Redirige vers la page de l'agent créé
    }
}
