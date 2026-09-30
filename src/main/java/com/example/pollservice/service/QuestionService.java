package com.example.pollservice.service;

import com.example.pollservice.model.Question;
import com.example.pollservice.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }

    public Question updateQuestion(Long id, Question updatedQuestion) {
        return questionRepository.findById(id).map(question -> {
            question.setPrompt(updatedQuestion.getPrompt());
            question.setOption1(updatedQuestion.getOption1());
            question.setOption2(updatedQuestion.getOption2());
            question.setOption3(updatedQuestion.getOption3());
            question.setOption4(updatedQuestion.getOption4());
            question.setCorrectAnswerIndex(updatedQuestion.getCorrectAnswerIndex());
            return questionRepository.save(question);
        }).orElseThrow(() -> new RuntimeException("Question not found with id " + id));
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }
}