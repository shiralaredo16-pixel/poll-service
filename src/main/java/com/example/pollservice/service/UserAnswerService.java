package com.example.pollservice.service;

import com.example.pollservice.exception.ResourceNotFoundException;
import com.example.pollservice.model.Question;
import com.example.pollservice.model.UserAnswer;
import com.example.pollservice.repository.QuestionRepository;
import com.example.pollservice.repository.UserAnswerRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserAnswerService {

    private final UserAnswerRepository userAnswerRepository;
    private final QuestionRepository questionRepository;
    private final RestTemplate restTemplate;
    private final String userServiceUrl = "http://localhost:8080/users";

    public UserAnswerService(UserAnswerRepository userAnswerAnswerRepository,
                             QuestionRepository questionRepository,
                             RestTemplate restTemplate) {
        this.userAnswerRepository = userAnswerAnswerRepository;
        this.questionRepository = questionRepository;
        this.restTemplate = restTemplate;
    }

    public UserAnswer submitAnswer(UserAnswer userAnswer) {
        try {
            String url = userServiceUrl + "/" + userAnswer.getUserId();
            restTemplate.getForObject(url, Object.class);
        } catch (Exception e) {
            throw new ResourceNotFoundException("User with id " + userAnswer.getUserId() + " does not exist");
        }
        return userAnswerRepository.save(userAnswer);
    }

    public List<UserAnswer> getAnswersByUserId(Long userId) {
        return userAnswerRepository.findByUserId(userId);
    }

    public long countQuestionsAnsweredByUser(Long userId) {
        return userAnswerRepository.findByUserId(userId).size();
    }

    public long countTotalAnswersForQuestion(Long questionId) {
        return userAnswerRepository.findByQuestionId(questionId).size();
    }

    public Map<Integer, Long> countAnswersPerOption(Long questionId) {
        List<UserAnswer> answers = userAnswerRepository.findByQuestionId(questionId);
        Map<Integer, Long> optionCounts = new HashMap<>();

        for (int i = 0; i < 4; i++) {
            optionCounts.put(i, 0L);
        }

        for (UserAnswer answer : answers) {
            int selected = answer.getSelectedOptionIndex();
            optionCounts.put(selected, optionCounts.getOrDefault(selected, 0L) + 1);
        }

        return optionCounts;
    }

    public Map<Long, Map<Integer, Long>> getAllQuestionsOptionsCount() {
        List<Question> questions = questionRepository.findAll();
        Map<Long, Map<Integer, Long>> allReport = new HashMap<>();

        for (Question q : questions) {
            allReport.put(q.getId(), countAnswersPerOption(q.getId()));
        }

        return allReport;
    }

    public void deleteAnswersByUserId(Long userId) {
        List<UserAnswer> userAnswers = userAnswerRepository.findByUserId(userId);
        userAnswerRepository.deleteAll(userAnswers);
    }
}
