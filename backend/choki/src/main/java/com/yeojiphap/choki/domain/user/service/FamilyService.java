package com.yeojiphap.choki.domain.user.service;

import com.yeojiphap.choki.domain.user.domain.Family;
import com.yeojiphap.choki.domain.user.domain.User;
import com.yeojiphap.choki.domain.user.dto.InviteCodeResponse;
import com.yeojiphap.choki.domain.user.exception.UserNotFoundException;
import com.yeojiphap.choki.domain.user.repository.FamilyRepository;
import com.yeojiphap.choki.domain.user.repository.UserRepository;
import com.yeojiphap.choki.global.auth.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FamilyService {

    private final UserRepository userRepository;
    private final FamilyRepository familyRepository;

    public InviteCodeResponse createFamily() {
        User user = findCurrentUser();

        Family family = Family.createWithInviteCode();
        familyRepository.save(family);

        user.assignFamily(family);
        userRepository.save(user);

        return new InviteCodeResponse(family.getInviteCode());
    }

    private User findCurrentUser() {
        return userRepository.findByUserId(SecurityUtil.getCurrentUserId())
                .orElseThrow(UserNotFoundException::new);
    }
}