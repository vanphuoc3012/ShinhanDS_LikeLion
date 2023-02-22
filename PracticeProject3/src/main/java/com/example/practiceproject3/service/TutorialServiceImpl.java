package com.example.practiceproject3.service;

import com.example.practiceproject3.model.Tutorial;
import com.example.practiceproject3.repository.TutorialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TutorialServiceImpl implements TutorialService{
    private final TutorialRepository tutorialRepository;

    @Override
    public List<Tutorial> findAll() {
        return tutorialRepository.findAll();
    }

    @Override
    public List<Tutorial> findByTitleContaining(String title) {
        return tutorialRepository.findByTitleContainingIgnoreCase(title);
    }

    @Override
    public Tutorial findById(Long id) throws NoSuchElementException {
        return tutorialRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Not found any tutorial with id: " + id)
        );
    }

    @Override
    public boolean existById(Long id) {
        return tutorialRepository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        Tutorial tutorial = findById(id);
        tutorialRepository.delete(tutorial);
    }

    @Override
    public void deleteAll() {
        tutorialRepository.deleteAll();
    }

    @Override
    public List<Tutorial> findByPublished(boolean b) {
        return tutorialRepository.findByPublished(b);
    }

    @Override
    public Tutorial save(Tutorial tutorial) {
        return tutorialRepository.save(tutorial);
    }
}
