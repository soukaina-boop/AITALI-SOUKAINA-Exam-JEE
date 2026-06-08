package org.aitali.soukaina.examjee.web;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.aitali.soukaina.examjee.dtos.ClientDTO;
import org.aitali.soukaina.examjee.dtos.CreditDTO;
import org.aitali.soukaina.examjee.services.CreditBancaireService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@Tag(name = "Clients", description = "Gestion des clients")
@SecurityRequirement(name = "bearerAuth")
public class ClientRestController {

    private final CreditBancaireService creditBancaireService;

    public ClientRestController(CreditBancaireService creditBancaireService) {
        this.creditBancaireService = creditBancaireService;
    }

    @GetMapping
    public List<ClientDTO> getClients() {
        return creditBancaireService.getClients();
    }

    @GetMapping("/{id}")
    public ClientDTO getClient(@PathVariable Long id) {
        return creditBancaireService.getClient(id);
    }

    @PostMapping
    public ResponseEntity<ClientDTO> saveClient(@RequestBody ClientDTO clientDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(creditBancaireService.saveClient(clientDTO));
    }

    @PutMapping("/{id}")
    public ClientDTO updateClient(@PathVariable Long id, @RequestBody ClientDTO clientDTO) {
        return creditBancaireService.updateClient(id, clientDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        creditBancaireService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/credits")
    public List<CreditDTO> getCreditsByClient(@PathVariable Long id) {
        return creditBancaireService.getCreditsByClient(id);
    }
}
