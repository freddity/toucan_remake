package com.example.toucan_remake.entry;

import com.example.toucan_remake.ToucanRemakeApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test class for {@link ControllerEntry}.
 * @author Jakub Iwanicki
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ControllerEntry.class)
@ContextConfiguration(classes= ToucanRemakeApplication.class)
@AutoConfigureMockMvc
public class ControllerEntryTest {

    @Test
    public void  () {

    }

}
