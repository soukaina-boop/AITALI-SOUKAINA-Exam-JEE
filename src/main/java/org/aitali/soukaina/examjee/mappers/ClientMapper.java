package org.aitali.soukaina.examjee.mappers;

import org.aitali.soukaina.examjee.dtos.ClientDTO;
import org.aitali.soukaina.examjee.entities.Client;

import java.util.List;

public final class ClientMapper {

    private ClientMapper() {
    }

    public static ClientDTO toDto(Client client) {
        if (client == null) {
            return null;
        }
        return new ClientDTO(
                client.getId(),
                client.getNom(),
                client.getEmail()
        );
    }

    public static Client toEntity(ClientDTO clientDTO) {
        if (clientDTO == null) {
            return null;
        }
        Client client = new Client();
        client.setId(clientDTO.id());
        client.setNom(clientDTO.nom());
        client.setEmail(clientDTO.email());
        return client;
    }

    public static List<ClientDTO> toDtos(List<Client> clients) {
        return clients.stream()
                .map(ClientMapper::toDto)
                .toList();
    }
}
