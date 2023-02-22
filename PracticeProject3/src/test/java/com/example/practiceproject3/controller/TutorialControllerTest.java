package com.example.practiceproject3.controller;

import com.example.practiceproject3.model.Tutorial;
import com.example.practiceproject3.repository.TutorialRepository;
import com.example.practiceproject3.service.TutorialService;
import com.example.practiceproject3.service.TutorialServiceImpl;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(TutorialController.class)
class TutorialControllerTest {
    @MockBean
    private TutorialServiceImpl tutorialService;

    @Autowired
    private JacksonTester<Tutorial> jsonTutorial;

    @Autowired
    private JacksonTester<List<Tutorial>> jsonListTutorial;

    @Autowired
    private MockMvc mvc;

    @Test
    void postValidTutorialTest() throws Exception {
        // given
        var tut1 = new Tutorial( "Title1", "Descript1", true);
        var expectedTut1 = new Tutorial( 1l, "Title1", "Descript1", true);
        BDDMockito.given(tutorialService.save(ArgumentMatchers.eq(tut1)))
                .willReturn(expectedTut1);

        //when
        MockHttpServletResponse response = mvc.perform(post("/api/tutorials")
                        .contentType(APPLICATION_JSON)
                        .content(jsonTutorial.write(tut1).getJson())
                        .accept(APPLICATION_JSON))
                .andReturn()
                .getResponse();
        //then
        BDDAssertions.then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void postInvalidTutorialTest() throws Exception {
        //given
        var tut1 = new Tutorial( "", "Descript1", true);

        //when
        MockHttpServletResponse response = mvc.perform(post("/api/tutorials")
                        .contentType(APPLICATION_JSON)
                        .content(jsonTutorial.write(tut1).getJson()))
                .andReturn().getResponse();

        //then
        BDDAssertions.then(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        BDDAssertions.then(response.getContentAsString()).containsIgnoringCase("Title is mandatory");
    }

    @Test
    void findAllTutorialsTest() throws Exception {
        //given
        var tut1 = new Tutorial(1l, "Title1", "Descript1", true);
        var tut2 = new Tutorial(2l, "Title2", "Descript2", false);

        List<Tutorial> tutorialList = List.of(tut1, tut2);
        BDDMockito.given(tutorialService.findAll())
                .willReturn(tutorialList);

        //when
        var response = mvc.perform(get("/api/tutorials").accept(APPLICATION_JSON))
                .andReturn()
                .getResponse();

        //then
        BDDAssertions.then(response.getContentAsString()).isEqualTo(
                jsonListTutorial.write(tutorialList).getJson());
    }

    @Test
    void findAllTutorialsByTitleTest() throws Exception {
        //given
        var tut1 = new Tutorial(1l, "Basic 1", "Descript1", true);
        var tut2 = new Tutorial(2l, "Basic 2", "Descript2", false);

        List<Tutorial> tutorialList = List.of(tut1, tut2);
        String keyword = "basic";
        BDDMockito.given(tutorialService.findByTitleContaining(keyword))
                .willReturn(tutorialList);

        //when
        var response = mvc.perform(get("/api/tutorials?title=" + keyword).accept(APPLICATION_JSON))
                .andReturn()
                .getResponse();

        //then
        BDDAssertions.then(response.getContentAsString()).isEqualTo(
                jsonListTutorial.write(tutorialList).getJson());
    }
    @Test
    void getTutorialById() throws Exception {
        //given
        long foundTutWithId = 10l;
        var tut1 = new Tutorial(foundTutWithId, "Title1", "Descript1", true);
        BDDMockito.given(tutorialService.findById(foundTutWithId))
                .willReturn(tut1);

        //when
        var response = mvc.perform(get("/api/tutorials/"+foundTutWithId)
                .accept(APPLICATION_JSON))
                .andReturn()
                .getResponse();

        //then
        BDDAssertions.then(response.getContentAsString()).isEqualTo(jsonTutorial.write(tut1).getJson());
    }

    @Test
    void getTutorialByIdAndNotFound() throws Exception {
        //given
        long notFoundTutId = 9l;
        BDDMockito.given(tutorialService.findById(notFoundTutId))
                .willThrow(new NoSuchElementException("Not found any tutorial with id: " + notFoundTutId));

        //when
        var response = mvc.perform(get("/api/tutorials/"+notFoundTutId)
                .accept(APPLICATION_JSON))
                .andReturn()
                .getResponse();

        //then
        BDDAssertions.then(response.getContentAsString()).isEqualTo("Not found any tutorial with id: " + notFoundTutId);
    }

    @Test
    void updateTutorial() {

    }

    @Test
    void deleteTutorialTest() throws Exception {
        //given
        long id = 10l;
        var tut = new Tutorial(id, "Title1", "Descript1", true);
        BDDMockito.given(tutorialService.findById(id))
                .willReturn(tut);

        //when
        MockHttpServletResponse response = mvc.perform(delete("/api/tutorials/" + id))
                .andReturn()
                .getResponse();

        //then
        Mockito.verify(tutorialService).deleteById(id);
        BDDAssertions.then(response.getContentAsString()).isEqualTo("Tutorial has been deleted");
    }

    @Test
    void deleteAllTutorialTest() throws Exception {
        //when
        MockHttpServletResponse response = mvc.perform(delete("/api/tutorials"))
                .andReturn()
                .getResponse();

        //then
        Mockito.verify(tutorialService).deleteAll();
        BDDAssertions.then(response.getContentAsString()).isEqualTo("All tutorials has been deleted");
    }

    @Test
    void findPublishedTutorials() {
    }
}