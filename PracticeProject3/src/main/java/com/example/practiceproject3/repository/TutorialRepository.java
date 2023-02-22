package com.example.practiceproject3.repository;

import com.example.practiceproject3.model.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TutorialRepository extends JpaRepository<Tutorial, Long> {

    List<Tutorial> findByPublished(boolean published);

    List<Tutorial> findByTitleContainingIgnoreCase(String keyword);
}
