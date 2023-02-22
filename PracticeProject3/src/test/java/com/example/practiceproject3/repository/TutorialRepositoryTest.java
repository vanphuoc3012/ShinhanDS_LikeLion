package com.example.practiceproject3.repository;

import com.example.practiceproject3.model.Tutorial;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TutorialRepositoryTest {
    @Autowired
    private TutorialRepository tutorialRepo;

    @Test
    void findByPublished() {
        Tutorial tutorial = tutorialRepo.getReferenceById(50L);
        Assertions.assertThat(tutorial).isNotNull();
    }

    @Test
    void findByTitleContainingIgnoreCase() {
        List<Tutorial> tutorialList = tutorialRepo.findByTitleContainingIgnoreCase("basic");

        tutorialList.forEach(t ->
                Assertions.assertThat(t.getTitle()).containsIgnoringCase("basic")
        );
    }
}