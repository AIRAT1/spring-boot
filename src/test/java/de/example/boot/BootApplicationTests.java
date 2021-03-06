package de.example.boot;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class BootApplicationTests {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private CatRepository catRepository;
    @Before
    public void before() throws Exception {
        Stream.of("Felix", "Garfield", "Whiskers").forEach(x -> catRepository.save(new Cat(x)));
    }

    @Test
    void catsReflectedInRead() throws Exception{
        MediaType halJson = MediaType.parseMediaType("application/hal+json");
        this.mvc
                .perform(get("/cats"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(halJson))
                .andExpect(
                        mvcResult -> {
                            String contentAsString = mvcResult.getResponse().getContentAsString();
                            assertTrue(contentAsString.split("totalElements")[1].split(":")[1].trim()
                            .split(",")[0].equals("0"));
                        }
                );
    }

}
