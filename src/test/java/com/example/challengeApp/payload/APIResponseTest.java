package com.example.challengeApp.payload;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class APIResponseTest {

    @Test
    void testAPIResponseConstructorAndGetter() {
        // 模拟创建一个APIResponse对象
        APIResponse apiResponse = new APIResponse("success", true);

        // 假设检验是否返回正确的值
        assertEquals("success", apiResponse.getMessage());
        assertEquals(true, apiResponse.getStatus());
    }

    @Test
    void testAPIResponseSetter() {
        APIResponse apiResponse = new APIResponse();
        apiResponse.setMessage("failed");
        apiResponse.setStatus(false);

        assertEquals("failed", apiResponse.getMessage());
        assertEquals(false, apiResponse.getStatus());
    }
}
