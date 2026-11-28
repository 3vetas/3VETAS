package com.api.tresvetas.security.service;

/**
 *
 * @author Jabier Zurro Aduriz
 */
public interface MailService {

    void enviarCorreo(String destinatario, String asunto, String contenido);
}