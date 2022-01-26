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

    private final CountryImportService countryImportService;

    @Bean
    public CommandLineRunner fillDatabase() {
        return args -> {
            countryImportService.fillDatabase();

            createDummyItQuizzes();
            createDummyGeographyQuizzes();
        };
    }

    private void createDummyGeographyQuizzes() {
        Category category = categoryRepository.save(new Category("Geography"));

        List<Country> countries = countryRepository.findAll().stream().filter(country -> country.getCapital() != null).collect(Collectors.toList());
        Collections.shuffle(countries);

        int nr = 0;

        Quiz quiz= new Quiz();

        for (int i = 0; i < countries.size(); i++) {
            Country country = countries.get(i);

            if (i % 20 == 0) {
                quiz = quizRepository.save(new Quiz("Capitals " + ++nr, 30, category));
            }

            Question question = questionRepository.save(new Question("What is the capital of " + country.getName() + "?", 1, (byte) 1, quiz));
            answerRepository.save(new Answer(country.getCapital(), true, question));

            for (int j=0; j<3; j++) {
                int randomIndex;

                do  {
                    randomIndex = (int)(Math.random() * countries.size());

                } while (randomIndex == i);

                answerRepository.save(new Answer(countries.get(randomIndex).getCapital(), false, question));
            }
        }
    }

    private void createDummyItQuizzes() {
        System.out.println("Fill the database with categories");
        Category it = new Category(1L, "IT");
        categoryRepository.save(it);

        System.out.println("Fill the database with quizzes");
        Quiz java = new Quiz(1L, "Java Basics", 30, it);
        quizRepository.save(java);

        Quiz javaScript = new Quiz(2L, "Javascript Basics", 30, it);
        quizRepository.save(javaScript);

        Quiz python = new Quiz(3L, "Python Basics", 30, it);
        quizRepository.save(python);

        System.out.println("Fill the database with questions");
        Question q1 = new Question(1L, "What is the range of short data type in Java? ", 1, (byte) 5, java);
        questionRepository.save(q1);

        System.out.println("Fill the database with answers");
        Answer a1 = new Answer(1L, "-128 to 127", false, q1);
        answerRepository.save(a1);

        Answer a2 = new Answer(2L, "-32 768 to 32 767", true, q1);
        answerRepository.save(a2);

        Answer a3 = new Answer(3L, "-2 147 483 648 to 2 147 483 647", false, q1);
        answerRepository.save(a3);

        Answer a4 = new Answer(4L, "None of the mentioned", false, q1);
        answerRepository.save(a4);
    }
}
