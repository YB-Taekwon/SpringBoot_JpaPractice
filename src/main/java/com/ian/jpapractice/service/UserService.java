package com.ian.jpapractice.service;

import com.ian.jpapractice.domain.User;
import com.ian.jpapractice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 회원 가입
     */
    @Transactional
    public Long signUp(User user) {
        validateDuplicateUser(user);
        userRepository.save(user);
        return user.getId();
    }

    private void validateDuplicateUser(User user) {
        List<User> findUser = userRepository.findByName(user.getName());
        if (!findUser.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 조회
     */
    // 회원 전체 조회
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // 회원 단일 조회
    public User findUser(Long id) {
        return userRepository.findById(id);
    }
}
