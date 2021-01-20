package com.example.toucan_remake.entry;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for {@link ControllerEntry}.
 * @author Jakub Iwanicki
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ControllerEntry.class)
@AutoConfigureMockMvc
public class ControllerEntryTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ApplicationContext applicationContext;

    @BeforeEach
    void printApplicationContext() {
        Arrays.stream(applicationContext.getBeanDefinitionNames())
                .map(name -> applicationContext.getBean(name).getClass().getName())
                .sorted()
                .forEach(System.out::println);
    }

    @Test
    public void getLandingPage_JWTOK_returnsDashboard() throws Exception {

        File dashboard = new ClassPathResource("templates/dashboard.html").getFile();
        String html = new String(Files.readAllBytes(dashboard.toPath()));

        mockMvc.
                perform(get(""))
                .andExpect(status().isOk())
                .andExpect(content().string(html))
                .andDo(print());
    }

    @Test
    public void getLandingPage_JWTNotOK_returnsLandingPage() {

    }

}
