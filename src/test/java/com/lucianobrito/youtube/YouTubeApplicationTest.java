package com.lucianobrito.youtube;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

class YouTubeApplicationTest {

    private static MockedStatic<SpringApplication> mockedYouTubeApplication;

    private ConfigurableApplicationContext context;

    @BeforeAll
    static void setUp() {
        mockedYouTubeApplication = Mockito.mockStatic(SpringApplication.class);
    }

    @AfterAll
    static void tearDown() {
        mockedYouTubeApplication.close();
    }

    @Test
    void main() {
        String[] args = { "application test" };
        mockedYouTubeApplication.when(() -> SpringApplication.run(eq(YouTubeApplication.class), any(String[].class))).thenReturn(context);

        YouTubeApplication.main(args);

        mockedYouTubeApplication.verify(() -> SpringApplication.run(eq(YouTubeApplication.class), any(String[].class)));
    }
}