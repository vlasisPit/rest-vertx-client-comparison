package org.test.clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.mutiny.Uni;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.core.buffer.Buffer;
import io.vertx.mutiny.ext.web.client.HttpResponse;
import io.vertx.mutiny.ext.web.client.WebClient;
import org.jboss.logging.Logger;
import org.test.model.Country;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.Duration;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class MutinyCountryVertxClient {

    private static final Logger LOG = Logger.getLogger(MutinyCountryVertxClient.class);

    @Inject
    Vertx vertx;

    private WebClient client;
    private ObjectMapper objectMapper;

    @PostConstruct
    void initialize() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.client = WebClient.create(vertx,
                /*new WebClientOptions().setDefaultHost("restcountries.eu")
                        .setDefaultPort(443).setSsl(true).setTrustAll(true));*/
            new WebClientOptions()
                    .setDefaultHost("localhost")
                    .setDefaultPort(3000)
                    .setSsl(false)
                    .setTrustAll(true)
                    .setMaxPoolSize(150)
        );
    }

    public Uni<List<Country>> getCountryByName(String name) {
        return client.get("/rest/v2/name/" + name)
                .send()
                .onItem().transform(bufferHttpResponse -> {
                    if (bufferHttpResponse.statusCode()==200) {
                        return deserializeResponse(bufferHttpResponse);
                    }
                    return Collections.emptyList();
                });
    }

    private List<Country> deserializeResponse(HttpResponse<Buffer> bufferHttpResponse) {
        try {
            return objectMapper.readValue(bufferHttpResponse.bodyAsString(), new TypeReference<List<Country>>() {});
        } catch (JsonProcessingException e) {
            LOG.error("Error...", e);
            return Collections.emptyList();
        }
    }
}
