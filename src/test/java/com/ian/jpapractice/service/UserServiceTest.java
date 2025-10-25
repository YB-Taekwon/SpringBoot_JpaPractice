package com.ian.jpapractice.service;

import com.ian.jpapractice.domain.User;
import com.ian.jpapractice.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("회원 가입 성공")
    @Rollback(false)
    void sign_up_test() {
        // given
        User user = new User();
        user.setName("test");

        // when
        Long userId = userService.signUp(user);

        // then
        assertThat(user).isEqualTo(userRepository.findById(userId));
    }

    @Test
    @DisplayName("중복된 회원이 가입 시도 시, 예외 발생")
    void duplicate_user_exception_test() {
        // given
        User user1 = new User();
        user1.setName("test");

        User user2 = new User();
        user2.setName("test");

        userService.signUp(user1);

        // when & then
        assertThatThrownBy(() -> userService.signUp(user2))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("이미 존재하는 회원입니다.");
    }


}