package br.com.fmatheus.app.controller.resource;

import br.com.fmatheus.app.controller.dto.response.ServiceQueueResponse;
import br.com.fmatheus.app.controller.enumerable.StatusQueueEnum;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class ServiceQueueResourceTest {

    private final static String URL = "/queue";

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private Flyway flyway;

    private UUID idServiceQueue;


    @BeforeEach
    void setUp() {
        flyway.migrate();
        idServiceQueue = UUID.fromString("1841ae8f-e278-4d32-bb82-aeb908731cad");
        /*webTestClient = WebTestClient.bindToServer()
                .responseTimeout(Duration.ofSeconds(50))
                .build();*/
    }

    @Test
    @Order(1)
    @DirtiesContext
    @DisplayName("Listando fila.")
    void listQueueTest() {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path(URL)
                        .queryParam("status", StatusQueueEnum.AGUARDANDO_ATENDIMENTO)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ServiceQueueResponse.class)
                .value(response -> assertThat(response).isNotEmpty())
        ;
    }

}
