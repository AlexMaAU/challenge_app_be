package com.example.challengeApp.service.implementation;

import com.example.challengeApp.entity.Challenge;
import com.example.challengeApp.exception.ResourceNotFoundException;
import com.example.challengeApp.payload.ChallengeDTO;
import com.example.challengeApp.payload.ChallengeResponse;
import com.example.challengeApp.payload.UpdateChallengeDTO;
import com.example.challengeApp.repository.ChallengeRepo;
import com.example.challengeApp.service.ChallengeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChallengeServiceImpl implements ChallengeService {

    @Autowired
    private ChallengeRepo challengeRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ChallengeResponse getAllChallenges(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        // Sort and Order
        Sort sortAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        // Paged list
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortAndOrder);
        // Get list content
        Page<Challenge> challengePage = challengeRepo.findAll(pageDetails);
        List<Challenge> challengeList = challengePage.getContent();

        List<ChallengeDTO> challengeDTOList = challengeList.stream().map(challenge->modelMapper.map(challenge, ChallengeDTO.class)).toList();

        // Set ChallengeResponse
        ChallengeResponse challengeResponse = new ChallengeResponse();
        challengeResponse.setChallengeDTOList(challengeDTOList);
        challengeResponse.setPageNumber(challengePage.getNumber());
        challengeResponse.setPageSize(challengePage.getSize());
        challengeResponse.setTotalPages(challengePage.getTotalPages());
        challengeResponse.setTotalElements(challengePage.getTotalElements());
        challengeResponse.setLastPage(challengePage.isLast());

        return challengeResponse;
    }

    @Override
    public ChallengeDTO addChallenge(ChallengeDTO challengeDTO) {
        Challenge challenge = modelMapper.map(challengeDTO, Challenge.class);
        challengeRepo.save(challenge);

        return modelMapper.map(challenge, ChallengeDTO.class);
    }

    @Override
    public ChallengeResponse getChallengesByMonth(String monthName, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortAndOrder);

        Page<Challenge> challengePage = challengeRepo.findByMonthNameIgnoreCase(monthName, pageDetails);
        List<Challenge> challengeList = challengePage.getContent();

        List<ChallengeDTO> challengeDTOList = challengeList.stream().map(challenge->modelMapper.map(challenge, ChallengeDTO.class)).toList();

        // Set ChallengeResponse
        ChallengeResponse challengeResponse = new ChallengeResponse();
        challengeResponse.setChallengeDTOList(challengeDTOList);
        challengeResponse.setPageNumber(challengePage.getNumber());
        challengeResponse.setPageSize(challengePage.getSize());
        challengeResponse.setTotalPages(challengePage.getTotalPages());
        challengeResponse.setTotalElements(challengePage.getTotalElements());
        challengeResponse.setLastPage(challengePage.isLast());

        return challengeResponse;
    }

    @Override
    public ChallengeDTO updateChallenge(UpdateChallengeDTO updateChallengeDTO, Long id) {
        Challenge challenge = challengeRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Challenge","id",id));

        if(updateChallengeDTO.getMonthName() != null) {
            challenge.setMonthName(updateChallengeDTO.getMonthName());
        }
        if(updateChallengeDTO.getDescription() != null) {
            challenge.setDescription(updateChallengeDTO.getDescription());
        }

        challengeRepo.save(challenge);

        return modelMapper.map(challenge, ChallengeDTO.class);
    }

    @Override
    public ChallengeDTO deleteChallenge(Long id) {
        Challenge challenge = challengeRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Challenge","id",id));
        challengeRepo.deleteById(id);

        return modelMapper.map(challenge, ChallengeDTO.class);
    }

}
