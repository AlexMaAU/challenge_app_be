package com.example.challengeApp.controller;

import com.example.challengeApp.payload.ChallengeDTO;
import com.example.challengeApp.payload.ChallengeResponse;
import com.example.challengeApp.payload.UpdateChallengeDTO;
import com.example.challengeApp.service.ChallengeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChallengeControllerTest {

    // 创建一个要测试的类的实例，并将其依赖（用 @Mock 注解的类）注入到其中
    @InjectMocks
    ChallengeController challengeController;

    // 创建模拟对象
    @Mock
    private ChallengeService challengeService;

    @Test
    void testGetAllChallenges() {
        // 分别测试setChallengeDTOList是null和有值的情况
        ChallengeResponse mockResponse1 = new ChallengeResponse();
        mockResponse1.setChallengeDTOList(new ArrayList<>());
        ChallengeResponse mockResponse2 = new ChallengeResponse();
        mockResponse2.setChallengeDTOList(new ArrayList<>(List.of(new ChallengeDTO(1L,"April","sdfsdfsdfdsf"))));

        // 使用 Answer 返回不同的响应
        // 只能有一个when，第二个when会覆盖第一个when，多种情况需要使用thenAnswer来区分
        when(challengeService.getAllChallenges(anyInt(), anyInt(), anyString(), anyString()))
                .thenAnswer(invocation -> {
                    // 根据调用次数或其他逻辑来返回不同的 mockResponse
                    if (invocation.getArgument(0).equals(0)) {
                        return mockResponse1; // 返回空列表
                    } else {
                        return mockResponse2; // 返回非空列表
                    }
                });

        // 需要使用不同的参数，否则都会返回相同的 mockResponse
        ResponseEntity<ChallengeResponse> response1 = challengeController.getAllChallenges(0 ,10, "monthName", "asc");
        ResponseEntity<ChallengeResponse> response2 = challengeController.getAllChallenges(1 ,10, "monthName", "asc");

        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response1.getBody()).isNull();
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response2.getBody()).isEqualTo(mockResponse2);
    }

    @Test
    void testGetChallengesByMonth() {
        String monthName = "January";

        // 创建空的 ChallengeResponse
        ChallengeResponse mockResponseEmpty = new ChallengeResponse();
        mockResponseEmpty.setChallengeDTOList(new ArrayList<>());  // 确保是空列表

        // 创建非空的 ChallengeResponse
        ChallengeResponse mockResponseNonEmpty = new ChallengeResponse();
        mockResponseNonEmpty.setChallengeDTOList(new ArrayList<>(List.of(new ChallengeDTO(1L, "January", "Test Data"))));

        // 使用 Answer 返回不同的相应
        when(challengeService.getChallengesByMonth(eq(monthName), anyInt(), anyInt(), anyString(), anyString()))
                .thenAnswer(invocation -> {
                    // 根据调用次数或其他逻辑来返回不同的 mockResponse
                    if (invocation.getArgument(1).equals(0)) {
                        return mockResponseEmpty; // 返回空列表
                    } else {
                        return mockResponseNonEmpty; // 返回非空列表
                    }
                });

        // 测试空列表情况
        ResponseEntity<ChallengeResponse> response1 = challengeController.getChallengesByMonth(monthName, 0, 10, "monthName", "asc");
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response1.getBody()).isNull();

        // 测试非空列表情况
        ResponseEntity<ChallengeResponse> response2 = challengeController.getChallengesByMonth(monthName, 1, 10, "monthName", "asc");
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response2.getBody()).isEqualTo(mockResponseNonEmpty);
    }

    @Test
    void testAddChallenge() {
        // 创建一个要添加的 ChallengeDTO
        ChallengeDTO challengeDTO = new ChallengeDTO(1L, "January", "Test Data");

        // 创建一个保存后的 ChallengeDTO
        ChallengeDTO savedChallengeDTO = new ChallengeDTO(1L, "January", "Test Data");

        // 模拟 service 的行为
        when(challengeService.addChallenge(any(ChallengeDTO.class))).thenReturn(savedChallengeDTO);

        // 调用 controller 的方法
        ResponseEntity<ChallengeDTO> response = challengeController.addChallenge(challengeDTO);

        // 验证响应状态和内容
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(savedChallengeDTO);
    }

    @Test
    void testUpdateChallenge() {
        Long challengeId = 1L;
        UpdateChallengeDTO updateChallengeDTO = new UpdateChallengeDTO(null, "Updated Name", "Updated Description"); // 创建更新的数据

        // 创建一个更新后的 ChallengeDTO
        ChallengeDTO updatedChallengeDTO = new ChallengeDTO(challengeId, "Updated Name", "Updated Description");

        // 模拟 service 的行为
        when(challengeService.updateChallenge(any(UpdateChallengeDTO.class), eq(challengeId))).thenReturn(updatedChallengeDTO);

        // 调用 controller 的方法
        ResponseEntity<ChallengeDTO> response = challengeController.updateChallenge(updateChallengeDTO, challengeId);

        // 验证响应状态和内容
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(updatedChallengeDTO);
    }

    @Test
    void testDeleteChallenge() {
        Long challengeId = 1L; // 要删除的挑战的 ID

        // 创建一个被删除的 ChallengeDTO
        ChallengeDTO deletedChallengeDTO = new ChallengeDTO(challengeId, "Deleted Challenge", "Description");

        // 模拟 service 的行为
        when(challengeService.deleteChallenge(challengeId)).thenReturn(deletedChallengeDTO);

        // 调用 controller 的方法
        ResponseEntity<ChallengeDTO> response = challengeController.deleteChallenge(challengeId);

        // 验证响应状态和内容
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(deletedChallengeDTO);
    }

}
