package com.aegis.nexus.core.search;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenSearchConfig {

    @Value("${opensearch.host:localhost}")
    private String host;

    @Value("${opensearch.port:9200}")
    private int port;

    @Value("${opensearch.scheme:http}")
    private String scheme;

    @Bean
    public org.opensearch.client.RestClient opensearchRestClient() {
        return org.opensearch.client.RestClient.builder(
            new org.apache.http.HttpHost(host, port, scheme)
        ).build();
    }
}
