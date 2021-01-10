package org.test.clients;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.Single;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.client.WebClient;
import org.test.model.Country;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class CountryVertxClient {

    @Inject
    Vertx vertx;

    private WebClient client;
    private ObjectMapper objectMapper;

    @PostConstruct
    void initialize() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.client = WebClient.create(vertx,
                new WebClientOptions().setDefaultHost("restcountries.eu")
                        .setDefaultPort(443).setSsl(true).setTrustAll(true));
    }

    public Single<List<Country>> getCountryByName(String name) {
        return client.get("/rest/v2/name/" + name)
                .rxSend()
                .map(bufferHttpResponse -> {
                    if (bufferHttpResponse.statusCode()==200) {
                        return objectMapper.readValue(bufferHttpResponse.bodyAsString(), new TypeReference<List<Country>>() {});
                    }
                    return Collections.emptyList();
                });
    }

}
