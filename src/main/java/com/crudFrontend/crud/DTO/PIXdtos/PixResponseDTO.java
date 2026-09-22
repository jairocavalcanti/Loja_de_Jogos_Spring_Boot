package com.crudFrontend.crud.DTO.PIXdtos;

import java.math.BigDecimal;

public record PixResponseDTO(Long pedidoId, BigDecimal valorTotal, String transactionId, String qrCodeBase64, String qrCodeCopyPaste) {

}
