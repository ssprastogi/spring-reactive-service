package com.example.reactive.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;

@Configuration
public class WebClientConfig {

    private final ObjectMapper objectMapper;

    @Autowired
    public WebClientConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Value("${ws.employee.baseUrl}")
    private String empBaseUrl;
    @Value("${ws.employee.connectTimeOut}")
    private int empConnectTimeOut;
    @Value("${ws.employee.readTimeOut}")
    private int empReadTimeOut;

    @Value("${ws.department.baseUrl}")
    private String deptBaseUrl;
    @Value("${ws.department.connectTimeOut}")
    private int deptConnectTimeOut;
    @Value("${ws.department.readTimeOut}")
    private int deptReadTimeOut;

    @Bean
    public WebClient empWebClient() throws SSLException {
        return buildWebClient(empBaseUrl, empReadTimeOut, empConnectTimeOut);
    }


    @Bean
    public WebClient deptWebClient() throws SSLException {
        return buildWebClient(deptBaseUrl, deptReadTimeOut, deptConnectTimeOut);
    }

    public WebClient buildWebClient(String url, int readTimeOut, int connectionTimeOut) throws SSLException {
        SslContext sslContext= SslContextBuilder.forClient().protocols("TLSv1.2").build();
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeOut)
                .doOnConnected(connection -> connection.addHandlerLast(new ReadTimeoutHandler(readTimeOut)))
                .secure(sslSpec -> sslSpec.sslContext(sslContext));
        ClientHttpConnector httpConnector = new ReactorClientHttpConnector(httpClient);

        return WebClient.builder()
                .clientConnector(httpConnector)
                .baseUrl(url)
                .codecs(clientCodecConfigurer -> {
                    clientCodecConfigurer.defaultCodecs().maxInMemorySize(16*1024*1024);
                    clientCodecConfigurer.defaultCodecs().enableLoggingRequestDetails(true);
                    clientCodecConfigurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper,MediaType.APPLICATION_JSON));
                    clientCodecConfigurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper,MediaType.APPLICATION_JSON));
                })
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.ALL_VALUE)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

}
