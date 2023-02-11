package com.genshin;

import com.genshin.entity.User;
import com.genshin.service.EncodeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureDataJpa
class WebApplicationTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    EncodeService encodeService;

    @Test
    void test_register(){
        User postUser=new User("genshin_artifact_recorder","genshin123","admin");
        final var responseUser = restTemplate.postForObject("/users/register",postUser, User.class);
        assertThat(responseUser.getAuthority()).isEqualTo(postUser.getAuthority());
        assertThat(responseUser.getName()).isEqualTo(postUser.getName());
        assertThat(encodeService.matches(postUser.getPassword(), responseUser.getPassword())).isTrue();
    }
}
