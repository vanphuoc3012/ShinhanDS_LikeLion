package com.example.practiceproject3.service;


import com.example.practiceproject3.model.Tutorial;

import java.util.List;

public interface TutorialService {
    List<Tutorial> findAll();

    List<Tutorial> findByTitleContaining(String title);

    Tutorial findById(Long id);

    boolean existById(Long id);

    void deleteById(Long id);

    void deleteAll();

    List<Tutorial> findByPublished(boolean b);

    Tutorial save(Tutorial tutorial);
}
