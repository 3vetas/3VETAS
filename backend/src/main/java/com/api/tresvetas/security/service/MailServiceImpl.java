package com.api.tresvetas.security.service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

/**
 *
 * @author Jabier Zurro Aduriz
 */
@Service
public class MailServiceImpl implements MailService {

    private final RestClient restClient;

    private final String remitente;
    private final String clientId;
    private final String clientSecret;
    private final String refreshToken;
    private final String tokenUri;
    private final String apiBaseUrl;

    public MailServiceImpl(
            RestClient.Builder restClientBuilder,
            @Value("${app.mail.gmail.sender}") String remitente,
            @Value("${app.mail.gmail.client-id}") String clientId,
            @Value("${app.mail.gmail.client-secret}") String clientSecret,
            @Value("${app.mail.gmail.refresh-token}") String refreshToken,
            @Value("${app.mail.gmail.token-uri}") String tokenUri,
            @Value("${app.mail.gmail.api-base-url}") String apiBaseUrl,
            @Value("${app.mail.gmail.connect-timeout}") int connectTimeout,
            @Value("${app.mail.gmail.read-timeout}") int readTimeout
    ) {
        // Configurar los tiempos de espera de las peticiones HTTP.
        SimpleClientHttpRequestFactory requestFactory =
                new SimpleClientHttpRequestFactory();

        requestFactory.setConnectTimeout(connectTimeout);
        requestFactory.setReadTimeout(readTimeout);

        this.restClient = restClientBuilder
                .requestFactory(requestFactory)
                .build();

        this.remitente = remitente;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.refreshToken = refreshToken;
        this.tokenUri = tokenUri;
        this.apiBaseUrl = apiBaseUrl;
    }

    @Override
    public void enviarCorreo(
            String destinatario,
            String asunto,
            String contenido
    ) {
        String accessToken = obtenerAccessToken();

        String mensaje = construirMensaje(
                destinatario,
                asunto,
                contenido
        );

        String mensajeCodificado = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(mensaje.getBytes(StandardCharsets.UTF_8));

        try {
            restClient.post()
                    .uri(apiBaseUrl + "/users/me/messages/send")
                    .headers(headers -> headers.setBearerAuth(accessToken))
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("raw", mensajeCodificado))
                    .retrieve()
                    .toBodilessEntity();

        } catch (Exception exception) {
            throw new ResponseStatusException(
                    HttpStatus.SERVICE_UNAVAILABLE,
                    "No se ha podido enviar el correo electrónico.",
                    exception
            );
        }
    }

    private String obtenerAccessToken() {

        Map<?, ?> respuesta;

        try {
            respuesta = restClient.post()
                    .uri(tokenUri)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(
                            "client_id=" + codificarParametro(clientId)
                            + "&client_secret=" + codificarParametro(clientSecret)
                            + "&refresh_token=" + codificarParametro(refreshToken)
                            + "&grant_type=refresh_token"
                    )
                    .retrieve()
                    .body(Map.class);

        } catch (Exception exception) {
            throw new ResponseStatusException(
                    HttpStatus.SERVICE_UNAVAILABLE,
                    "No se ha podido obtener la autorización de Gmail.",
                    exception
            );
        }

        if (respuesta == null
                || !(respuesta.get("access_token") instanceof String token)
                || token.isBlank()) {

            throw new ResponseStatusException(
                    HttpStatus.SERVICE_UNAVAILABLE,
                    "Google no ha devuelto un token de acceso válido."
            );
        }

        return token;
    }

    private String construirMensaje(
            String destinatario,
            String asunto,
            String contenido
    ) {
        validarCabecera(destinatario);
        validarCabecera(asunto);

        String asuntoCodificado = Base64.getEncoder()
                .encodeToString(asunto.getBytes(StandardCharsets.UTF_8));

        return "From: " + remitente + "\r\n"
                + "To: " + destinatario + "\r\n"
                + "Subject: =?UTF-8?B?" + asuntoCodificado + "?=\r\n"
                + "MIME-Version: 1.0\r\n"
                + "Content-Type: text/plain; charset=UTF-8\r\n"
                + "Content-Transfer-Encoding: base64\r\n"
                + "\r\n"
                + Base64.getMimeEncoder(76, "\r\n".getBytes(StandardCharsets.US_ASCII))
                        .encodeToString(contenido.getBytes(StandardCharsets.UTF_8));
    }

    private void validarCabecera(String valor) {
        if (valor == null
                || valor.contains("\r")
                || valor.contains("\n")) {

            throw new IllegalArgumentException(
                    "La cabecera del correo electrónico no es válida."
            );
        }
    }

    private String codificarParametro(String valor) {
        return java.net.URLEncoder.encode(
                valor,
                StandardCharsets.UTF_8
        );
    }
}