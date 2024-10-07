package com.example.challengeApp.service;

import com.example.challengeApp.entity.Challenge;
import com.example.challengeApp.exception.ResourceNotFoundException;
import com.example.challengeApp.payload.ChallengeDTO;
import com.example.challengeApp.payload.ChallengeResponse;
import com.example.challengeApp.payload.UpdateChallengeDTO;
import com.example.challengeApp.repository.ChallengeRepo;
import com.example.challengeApp.service.implementation.ChallengeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class testChallengeServiceImpl {

    // 创建模拟对象
    @Mock
    private ChallengeRepo challengeRepo;

    @Mock
    private ModelMapper modelMapper;

    // 创建一个要测试的类的实例，并将其依赖（用 @Mock 注解的类）注入到其中
    @InjectMocks
    private ChallengeServiceImpl challengeService;

    @Test
    public void testGetAllChallenges() {
        // Arrange
        Challenge challenge = new Challenge();  // 假设有一个 Challenge 实体
        ChallengeDTO challengeDTO = new ChallengeDTO();

        when(challengeRepo.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(Collections.singletonList(challenge)));
        when(modelMapper.map(challenge, ChallengeDTO.class)).thenReturn(challengeDTO);

        Integer pageNumber = 0;
        Integer pageSize = 10;
        String sortBy = "id"; // 替换为你需要的排序字段
        String sortOrder = "asc";

        // Act
        ChallengeResponse response = challengeService.getAllChallenges(pageNumber, pageSize, sortBy, sortOrder);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getChallengeDTOList().size());
        assertEquals(challengeDTO, response.getChallengeDTOList().get(0));
        assertEquals(0, response.getPageNumber());
        assertEquals(1, response.getPageSize());
        assertEquals(1, response.getTotalPages());
        assertEquals(1, response.getTotalElements());
        assertTrue(response.getLastPage());
    }

    @Test
    public void testAddChallenge() {
        // Arrange
        ChallengeDTO challengeDTO = new ChallengeDTO(); // 假设有一个 ChallengeDTO
        Challenge challenge = new Challenge(); // 假设有一个 Challenge 实体

        // 模拟模型映射
        when(modelMapper.map(challengeDTO, Challenge.class)).thenReturn(challenge);
        when(challengeRepo.save(challenge)).thenReturn(challenge);
        when(modelMapper.map(challenge, ChallengeDTO.class)).thenReturn(challengeDTO);

        // Act
        ChallengeDTO result = challengeService.addChallenge(challengeDTO);

        // Assert
        assertNotNull(result);
        assertEquals(challengeDTO, result);
        verify(challengeRepo).save(challenge); // 验证保存方法被调用
        verify(modelMapper).map(challengeDTO, Challenge.class); // 验证映射调用
        verify(modelMapper).map(challenge, ChallengeDTO.class); // 验证映射调用
    }

    @Test
    public void testGetChallengesByMonth() {
        // Arrange
        String monthName = "January";
        Integer pageNumber = 0;
        Integer pageSize = 10;
        String sortBy = "id"; // 替换为你需要的排序字段
        String sortOrder = "asc";

        Challenge challenge = new Challenge();  // 假设有一个 Challenge 实体
        ChallengeDTO challengeDTO = new ChallengeDTO(); // 假设有一个 ChallengeDTO

        // 模拟返回的分页结果
        when(challengeRepo.findByMonthNameIgnoreCase(eq(monthName), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(challenge)));
        when(modelMapper.map(challenge, ChallengeDTO.class)).thenReturn(challengeDTO);

        // Act
        ChallengeResponse response = challengeService.getChallengesByMonth(monthName, pageNumber, pageSize, sortBy, sortOrder);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getChallengeDTOList().size()); // 返回的 ChallengeDTO 列表应该包含一个元素
        assertEquals(challengeDTO, response.getChallengeDTOList().get(0));
        assertEquals(0, response.getPageNumber());
        assertEquals(1, response.getPageSize());
        assertEquals(1, response.getTotalPages()); // 只有一个挑战，所以总页数是 1
        assertEquals(1, response.getTotalElements()); // 总元素数也是 1
        assertTrue(response.getLastPage()); // 这是最后一页

        // 验证方法调用
        verify(challengeRepo).findByMonthNameIgnoreCase(eq(monthName), any(Pageable.class));
        verify(modelMapper).map(challenge, ChallengeDTO.class);
    }

    @Test
    public void testUpdateChallenge() {
        // Arrange
        Long challengeId = 1L;
        UpdateChallengeDTO updateChallengeDTO = new UpdateChallengeDTO();
        updateChallengeDTO.setMonthName("February");
        updateChallengeDTO.setDescription("Updated Description");

        Challenge existingChallenge = new Challenge(); // 假设有一个 Challenge 实体
        existingChallenge.setMonthName("January");
        existingChallenge.setDescription("Original Description");

        Challenge updatedChallenge = new Challenge(); // 假设有一个 Challenge 实体
        updatedChallenge.setMonthName(updateChallengeDTO.getMonthName());
        updatedChallenge.setDescription(updateChallengeDTO.getDescription());

        ChallengeDTO challengeDTO = new ChallengeDTO(); // 假设有一个 ChallengeDTO

        // 模拟 repo 的行为
        when(challengeRepo.findById(challengeId)).thenReturn(Optional.of(existingChallenge));
        when(challengeRepo.save(existingChallenge)).thenReturn(updatedChallenge);
        when(modelMapper.map(updatedChallenge, ChallengeDTO.class)).thenReturn(challengeDTO);

        // Act
        ChallengeDTO result = challengeService.updateChallenge(updateChallengeDTO, challengeId);

        // Assert
        assertNotNull(result);
        assertEquals(challengeDTO, result);
        assertEquals("February", existingChallenge.getMonthName());
        assertEquals("Updated Description", existingChallenge.getDescription());

        // 验证方法调用
        verify(challengeRepo).findById(challengeId);
        verify(challengeRepo).save(existingChallenge);
        verify(modelMapper).map(updatedChallenge, ChallengeDTO.class);
    }

    @Test
    public void testUpdateChallenge_NotFound() {
        // Arrange
        Long challengeId = 1L;
        UpdateChallengeDTO updateChallengeDTO = new UpdateChallengeDTO();

        // 模拟找不到挑战
        when(challengeRepo.findById(challengeId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            challengeService.updateChallenge(updateChallengeDTO, challengeId);
        });

        // 验证方法调用
        verify(challengeRepo).findById(challengeId);
        verify(challengeRepo, never()).save(any());
        verify(modelMapper, never()).map(any(), any());
    }

    @Test
    public void testDeleteChallenge() {
        // Arrange
        Long challengeId = 1L;
        Challenge challenge = new Challenge(); // 假设有一个 Challenge 实体
        ChallengeDTO challengeDTO = new ChallengeDTO(); // 假设有一个 ChallengeDTO

        // 模拟 repo 的行为
        when(challengeRepo.findById(challengeId)).thenReturn(Optional.of(challenge));
        when(modelMapper.map(challenge, ChallengeDTO.class)).thenReturn(challengeDTO);

        // Act
        ChallengeDTO result = challengeService.deleteChallenge(challengeId);

        // Assert
        assertNotNull(result);
        assertEquals(challengeDTO, result);

        // 验证方法调用
        verify(challengeRepo).findById(challengeId);
        verify(challengeRepo).deleteById(challengeId);
        verify(modelMapper).map(challenge, ChallengeDTO.class);
    }

    @Test
    public void testDeleteChallenge_NotFound() {
        // Arrange
        Long challengeId = 1L;

        // 模拟找不到挑战
        when(challengeRepo.findById(challengeId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            challengeService.deleteChallenge(challengeId);
        });

        // 验证方法调用
        verify(challengeRepo).findById(challengeId);
        verify(challengeRepo, never()).deleteById(any());
        verify(modelMapper, never()).map(any(), any());
    }

}
