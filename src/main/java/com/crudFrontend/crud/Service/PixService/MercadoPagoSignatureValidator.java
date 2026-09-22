package com.crudFrontend.crud.Service.PixService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Component
public class MercadoPagoSignatureValidator {

    @Value("${mercadopago.webhook.secret:}")
    private String secretKey;

    public boolean isAssinaturaValida(String xSignature, String xRequestId, String dataId) {
        // Se a chave não estiver configurada no application.properties, ignora a validação (útil para testes locais rápidos)
        if (secretKey == null || secretKey.isBlank()) {
            return true;
        }

        // Se a chave está configurada mas a requisição não enviou os headers necessários
        if (xSignature == null || xRequestId == null || dataId == null) {
            return false;
        }

        try {
            // O header x-signature vem no formato: "ts=...,v1=..."
            String ts = null;
            String v1 = null;
            String[] parts = xSignature.split(",");
            for (String part : parts) {
                String[] keyValue = part.trim().split("=", 2);
                if (keyValue.length == 2) {
                    if ("ts".equalsIgnoreCase(keyValue[0])) {
                        ts = keyValue[1];
                    } else if ("v1".equalsIgnoreCase(keyValue[0])) {
                        v1 = keyValue[1];
                    }
                }
            }

            if (ts == null || v1 == null) {
                return false;
            }

            // Manifesto oficial do Mercado Pago: id:[data.id];request-id:[x-request-id];ts:[ts];
            String manifest = String.format("id:%s;request-id:%s;ts:%s;", dataId, xRequestId, ts);

            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKeySpec);

            byte[] hmacBytes = mac.doFinal(manifest.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hmacBytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString().equalsIgnoreCase(v1);
        } catch (Exception e) {
            System.err.println("Erro ao validar assinatura do Webhook: " + e.getMessage());
            return false;
        }
    }
}