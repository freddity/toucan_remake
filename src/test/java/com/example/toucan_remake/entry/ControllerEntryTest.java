package com.example.toucan_remake.entry;

import com.example.toucan_remake.user.EntityUser;
import com.example.toucan_remake.user.RepositoryUser;
import com.example.toucan_remake.util.UtilJWT;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.HtmlUtils;
import org.thymeleaf.Thymeleaf;
import org.thymeleaf.spring5.expression.ThymeleafEvaluationContextWrapper;
import org.thymeleaf.spring5.view.ThymeleafView;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.spring5.view.reactive.ThymeleafReactiveView;
import org.thymeleaf.spring5.view.reactive.ThymeleafReactiveViewResolver;

import javax.servlet.http.Cookie;
import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for {@link ControllerEntry}.
 * @author Jakub Iwanicki
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
//@WebMvcTest(controllers = ControllerEntry.class) /*@WebMvcTest tests only controller (e.g. repositories beans aren't create) and @SpringBootTest allows to create integration tests*/
@AutoConfigureMockMvc
@SpringBootTest
public class ControllerEntryTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ApplicationContext applicationContext;
    @Autowired private RepositoryUser repositoryUser;
    @Autowired private UtilJWT utilJWT;

    @BeforeEach
    void printApplicationContext() {
        Arrays.stream(applicationContext.getBeanDefinitionNames())
                .map(name -> applicationContext.getBean(name).getClass().getName())
                .sorted()
                .forEach(System.out::println);
    }

    @Test
    public void getLandingPage_JWTNotOK_returnsLandingPage() throws Exception {

        File dashboard = new ClassPathResource("templates/landing_page.html").getFile();
        String html = new String(Files.readAllBytes(dashboard.toPath()));

        mockMvc.
                perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("landing_page"))
                .andDo(print());
    }

    @Test
    public void getLandingPage_JWTOK_returnsDashboard() throws Exception {

        repositoryUser.save(new EntityUser("email", "password"));

        File dashboard = new ClassPathResource("templates/dashboard.html").getFile();
        String html = new String(Files.readAllBytes(dashboard.toPath()));

        mockMvc.
                perform(get("/")
                        .cookie(new Cookie(
                                    "jwt",
                                    utilJWT.generateToken(repositoryUser.findByEmail("email"))
                                )
                        )
                )
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard"))
                .andDo(print());
    }

    @Test
    public void sendLoginPage_JWTNotOK_returnsLoginForm() throws Exception{

        File dashboard = new ClassPathResource("templates/login_form.html").getFile();
        String html = new String(Files.readAllBytes(dashboard.toPath()));

        mockMvc.
                perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login_form"))
                .andDo(print());
    }

    @Test
    public void sendLoginPage_JWTOK_returnsDashboard() throws Exception {

        repositoryUser.save(new EntityUser("email", "password"));

        mockMvc.
                perform(get("/login")
                        .cookie(new Cookie(
                                        "jwt",
                                        utilJWT.generateToken(repositoryUser.findByEmail("email"))
                                )
                        )
                )
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard"))
                .andDo(print());
    }

    @Test
    public void sendJoinPage_JWTNotOK_returnsJoinForm() throws Exception {

        mockMvc.
                perform(get("/join"))
                .andExpect(status().isOk())
                .andExpect(view().name("join_form"))
                .andDo(print());
    }

    @Test
    public void sendJoinPage_JWTOK_returnsDashboard() throws Exception {

        repositoryUser.save(new EntityUser("email", "password"));

        mockMvc.
                perform(get("/join")
                        .cookie(new Cookie(
                                        "jwt",
                                        utilJWT.generateToken(repositoryUser.findByEmail("email"))
                                )
                        )
                )
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard"))
                .andDo(print());
    }

}
