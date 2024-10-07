package com.example.challengeApp.repository;

import com.example.challengeApp.entity.Challenge;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

// 启用 Mockito 的功能 - 主要用于创建和管理模拟对象（mocks），使得测试不需要和真实数据库交互
// 如果你的测试类上添加了 @ExtendWith(MockitoExtension.class)，可以简化测试设置过程，因为它自动处理 Mockito 注解的初始化, 可以省略 setUp 方法
@ExtendWith(MockitoExtension.class)
public class ChallengeRepoTest {

    // 使用 Mockito 的注解（如 @Mock）来创建模拟对象
    @Mock
    private ChallengeRepo challengeRepo;

    @Test
    void testFindByMonthNameIgnoreCase() {
        // 创建分页请求
        Pageable pageable = PageRequest.of(0 ,10);

        // 设置模拟行为
        // 当调用 challengeRepo.findByMonthNameIgnoreCase("january", pageable) 时，将返回一个 PageImpl 对象，这个对象包含了一条记录：Challenge(1L, "January", "Test 1 Data")。这个模拟结果用于测试
        when(challengeRepo.findByMonthNameIgnoreCase("january", pageable))
                .thenReturn(new PageImpl<>(List.of(new Challenge(1L, "January", "Test 1 Data")), pageable, 1));

        // 调用方法
        Page<Challenge> result = challengeRepo.findByMonthNameIgnoreCase("january", pageable);

        // 验证结果
        // assertThat 提供了更多的断言选项，支持检查集合、字符串、异常等，提供了一系列的断言方法
        // 下面等同于 assertEquals(expectedValue, actualValue)
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getMonthName()).isEqualToIgnoringCase("January");
    }
}
