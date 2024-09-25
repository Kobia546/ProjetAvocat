package ci.projetAvocat.Service;


import ci.projetAvocat.Entities.Agent;

public interface AgentService {
    /* public List<Agent> getAllAgents() {
             return agentRepository.findAll();
         }*/
    Agent getAgentById(Long id);

    void saveAgent(Agent agent);
}
