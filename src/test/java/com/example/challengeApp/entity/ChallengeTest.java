package com.example.challengeApp.entity;

import org.junit.jupiter.api.Test;

// 例如，assertEquals() 可以直接使用，而不是写成 Assertions.assertEquals()。
import static org.junit.jupiter.api.Assertions.*;  // import static 语句可以让你直接使用类中的静态成员，而无需在调用时指定类名

public class ChallengeTest {

    @Test
    void testChallengeConstructorAndGetter() {
        // 模拟创建一个Challenge对象
        Challenge challenge = new Challenge(1L,"January","New Year Challenge");

        // 假设检验是否返回正确的值
        assertEquals(1L, challenge.getId());
        assertEquals("January", challenge.getMonthName());
        assertEquals("New Year Challenge", challenge.getDescription());
    }

    @Test
    void testChallengeSetters() {
        Challenge challenge = new Challenge();
        challenge.setId(2L);
        challenge.setMonthName("April");
        challenge.setDescription("Test description");

        assertEquals(2L, challenge.getId());
        assertEquals("April", challenge.getMonthName());
        assertEquals("Test description", challenge.getDescription());
    }
}
