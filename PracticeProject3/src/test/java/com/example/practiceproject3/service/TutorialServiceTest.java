package com.example.practiceproject3.service;

import com.example.practiceproject3.model.Tutorial;
import com.example.practiceproject3.repository.TutorialRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TutorialServiceTest {
    private TutorialService tutorialService;
    @Mock
    private TutorialRepository tutorialRepo;
    @BeforeEach
    void setUp() {
        tutorialService = new TutorialServiceImpl(tutorialRepo);
    }

    @Test
    void findAll() {
        //given
        var tut1 = new Tutorial(1l, "Title1", "Descript1", true);
        var tut2 = new Tutorial(2l, "Title2", "Descript2", false);
        BDDMockito.given(tutorialRepo.findAll())
                .willReturn(List.of(tut1, tut2));

        //when
        List<Tutorial> result = tutorialService.findAll();

        //then
        BDDAssertions.then(result).hasSize(2);
        result.forEach(t -> {
            BDDAssertions.then(t).isNotNull();
        });
    }

    @Test
    void findByTitleContaining() {
        //given
        var tut1 = new Tutorial(1l, "Title1", "Descript1", true);
        String keyword = "1";

        BDDMockito.given(tutorialRepo.findByTitleContainingIgnoreCase(keyword))
                .willReturn(List.of(tut1));
        //when
        List<Tutorial> result = tutorialService.findByTitleContaining(keyword);

        //then
        BDDAssertions.then(result).hasSize(1);

    }

    @Test
    void existById() {

    }

    @Test
    void deleteById() {
        //given
        long tutId = 100L;
        Tutorial tutorial = new Tutorial(tutId, "Title1", "Descript1", true);
        BDDMockito.given(tutorialRepo.findById(tutId))
                .willReturn(Optional.of(tutorial));

        //when
        tutorialService.deleteById(tutId);

        //then
        var tutIdArgumentCaptor = ArgumentCaptor.forClass(Tutorial.class);
        Mockito.verify(tutorialRepo).delete(tutIdArgumentCaptor.capture());
        BDDAssertions.then(tutIdArgumentCaptor.getValue().getId()).isEqualTo(tutId);
    }

    @Test
    void deleteAll() {
        //when
        tutorialService.deleteAll();

        //then
        Mockito.verify(tutorialRepo, Mockito.times(1)).deleteAll();
    }

    @Test
    void findByPublished() {
    }

    @Test
    void save() {
    }
}