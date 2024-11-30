package com.penny.penny_backend.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponseDTO<T> {
    private String status; // "success" 또는 "error"
    private String message; // 추가적인 메시지
    private T data; // 반환할 데이터 (Account, List<Account>, String 등)
}
