package com.orbanszlrd.quizapi.service;

import com.orbanszlrd.quizapi.modules.answer.model.Answer;
import com.orbanszlrd.quizapi.modules.answer.repository.AnswerRepository;
import com.orbanszlrd.quizapi.modules.category.model.Category;
import com.orbanszlrd.quizapi.modules.category.repository.CategoryRepository;
import com.orbanszlrd.quizapi.modules.country.model.Country;
import com.orbanszlrd.quizapi.modules.country.repositroy.CountryRepository;
import com.orbanszlrd.quizapi.modules.country.service.CountryImportService;
import com.orbanszlrd.quizapi.modules.question.model.Question;
import com.orbanszlrd.quizapi.modules.question.repository.QuestionRepository;
import com.orbanszlrd.quizapi.modules.quiz.model.Quiz;
import com.orbanszlrd.quizapi.modules.quiz.repository.QuizRepository;
import com.orbanszlrd.quizapi.modules.user.service.UserImportService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImportService {
    private final CountryRepository countryRepository;
    private final CategoryRepository categoryRepository;
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    private final UserImportService userImportService;
    private final CountryImportService countryImportService;

    @Bean
    public CommandLineRunner fillDatabase() {
        return args -> {

            userImportService.createUsers();

            countryImportService.fillDatabase();

            createDummyItQuizzes();
            createDummyGeographyQuizzes();
        };
    }

    @Transactional
    private void createDummyGeographyQuizzes() {
        Category category = categoryRepository.save(new Category("Geography"));

        List<Country> countries = countryRepository.findAll().stream().filter(country -> country.getCapital() != null).collect(Collectors.toList());
        Collections.shuffle(countries);

        Quiz countriesQuiz = quizRepository.save(new Quiz("Countries", 30, category));
        Quiz capitalsQuiz = quizRepository.save(new Quiz("Capitals", 30, category));
        Quiz areasQuiz = quizRepository.save(new Quiz("Areas", 30, category));
        Quiz populationQuiz = quizRepository.save(new Quiz("Population", 30, category));

        for (int i = 0; i < countries.size(); i++) {
            Country country = countries.get(i);

            Question countryQuestion = questionRepository.save(new Question("Which country's capital is " + country.getCapital() + "? The country is part of " + country.getRegion(), 1, (byte) 1, countriesQuiz));
            answerRepository.save(new Answer(country.getName(), true, countryQuestion));

            Question capitalQuestion = questionRepository.save(new Question("What is the capital of " + country.getName() + "?", 1, (byte) 1, capitalsQuiz));
            answerRepository.save(new Answer(country.getCapital(), true, capitalQuestion));

            Question areaQuestion = questionRepository.save(new Question("What is the area of " + country.getName() + "?", 1, (byte) 1, areasQuiz));
            answerRepository.save(new Answer(String.valueOf(country.getArea()), true, areaQuestion));

            Question populationQuestion = questionRepository.save(new Question("What is the population of " + country.getName() + "?", 1, (byte) 1, populationQuiz));
            answerRepository.save(new Answer(String.valueOf(country.getPopulation()), true, populationQuestion));

            for (int j = 0; j < 3; j++) {
                answerRepository.save(new Answer(countries.get(getRandomIndex(i, countries)).getName(), false, countryQuestion));
                answerRepository.save(new Answer(countries.get(getRandomIndex(i, countries)).getCapital(), false, capitalQuestion));
                answerRepository.save(new Answer(String.valueOf(countries.get(getRandomIndex(i, countries)).getArea()), false, areaQuestion));
                answerRepository.save(new Answer(String.valueOf(countries.get(getRandomIndex(i, countries)).getPopulation()), false, populationQuestion));
            }
        }
    }

    private <T> int getRandomIndex(int currentIndex, Collection<T> collection) {
        if (collection == null || collection.isEmpty()) {
            throw new IllegalArgumentException("The collection must not be null or empty.");
        }

        int randomIndex;

        do {
            randomIndex = (int) (Math.random() * collection.size());

        } while (randomIndex == currentIndex);

        return randomIndex;
    }

    @Transactional
    private void createDummyItQuizzes() {
        System.out.println("Fill the database with categories");
        Category it = new Category("IT");
        categoryRepository.save(it);

        System.out.println("Fill the database with quizzes");
        Quiz java = new Quiz("Java Basics", 30, it);
        quizRepository.save(java);

        Quiz javaScript = new Quiz("Javascript Basics", 30, it);
        quizRepository.save(javaScript);

        Quiz python = new Quiz("Python Basics", 30, it);
        quizRepository.save(python);

        System.out.println("Fill the database with questions");
        Question q1 = new Question("What is the range of short data type in Java? ", 1, (byte) 5, java);
        questionRepository.save(q1);

        System.out.println("Fill the database with answers");
        Answer a1 = new Answer( "-128 to 127", false, q1);
        answerRepository.save(a1);

        Answer a2 = new Answer("-32 768 to 32 767", true, q1);
        answerRepository.save(a2);

        Answer a3 = new Answer("-2 147 483 648 to 2 147 483 647", false, q1);
        answerRepository.save(a3);

        Answer a4 = new Answer("None of the mentioned", false, q1);
        answerRepository.save(a4);
    }
}
