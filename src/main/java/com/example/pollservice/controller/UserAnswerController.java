package com.example.pollservice.controller;

import com.example.pollservice.exception.ResourceNotFoundException;
import com.example.pollservice.model.Question;
import com.example.pollservice.model.UserAnswer;
import com.example.pollservice.repository.QuestionRepository;
import com.example.pollservice.service.UserAnswerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/answers")
public class UserAnswerController {

    private final UserAnswerService userAnswersService;
    private final QuestionRepository questionRepository;

    public UserAnswerController(UserAnswerService userAnswersService, QuestionRepository questionRepository) {
        this.userAnswersService = userAnswersService;
        this.questionRepository = questionRepository;
    }

    @PostMapping
    public ResponseEntity<?> submitAnswer(@RequestBody UserAnswer userAnswer) {
        boolean questionExists = questionRepository.existsById(userAnswer.getQuestionId());
        if (!questionExists) {
            throw new ResourceNotFoundException("Question with id " + userAnswer.getQuestionId() + " does not exist");
        }
        if (userAnswer.getSelectedOptionIndex() < 0 || userAnswer.getSelectedOptionIndex() > 3) {
            throw new IllegalArgumentException("Invalid option index");
        }

        UserAnswer saved = userAnswersService.submitAnswer(userAnswer);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserAnswer>> getAnswersByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userAnswersService.getAnswersByUserId(userId));
    }

    @GetMapping("/user/{userId}/count")
    public ResponseEntity<Long> countQuestionsAnsweredByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userAnswersService.countQuestionsAnsweredByUser(userId));
    }

    @GetMapping("/question/{questionId}/total")
    public ResponseEntity<Long> countTotalAnswersForQuestion(@PathVariable Long questionId) {
        return ResponseEntity.ok(userAnswersService.countTotalAnswersForQuestion(questionId));
    }

    @GetMapping("/question/{questionId}/options-count")
    public ResponseEntity<Map<Integer, Long>> countAnswersPerOption(@PathVariable Long questionId) {
        return ResponseEntity.ok(userAnswersService.countAnswersPerOption(questionId));
    }

    @GetMapping("/report/all-questions")
    public ResponseEntity<Map<Long, Map<Integer, Long>>> getAllQuestionsOptionsCount() {
        return ResponseEntity.ok(userAnswersService.getAllQuestionsOptionsCount());
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<String> deleteAnswersByUser(@PathVariable Long userId) {
        userAnswersService.deleteAnswersByUserId(userId);
        return ResponseEntity.ok("Answers deleted successfully for user id " + userId);
    }






}