package ci.projetAvocat.Service;


import ci.projetAvocat.Entities.Agent;
import ci.projetAvocat.Repository.AgentRepository;
import org.springframework.stereotype.Service;

@Service
public class AgentServiceImpl implements AgentService {

    private final AgentRepository agentRepository;

    public AgentServiceImpl(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    /* public List<Agent> getAllAgents() {
         return agentRepository.findAll();
     }*/
    @Override
    public Agent getAgentById(Long id) {
        return agentRepository.findById(id).orElse(null);
    }
    @Override
    public void saveAgent(Agent agent) {
        agentRepository.save(agent);
    }
}
