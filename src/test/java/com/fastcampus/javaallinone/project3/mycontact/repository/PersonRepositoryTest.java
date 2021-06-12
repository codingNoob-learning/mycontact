package com.fastcampus.javaallinone.project3.mycontact.repository;

import com.fastcampus.javaallinone.project3.mycontact.domain.Person;
import com.fastcampus.javaallinone.project3.mycontact.domain.dto.Birthday;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonRepositoryTest {
    @Autowired
    private PersonRepository personRepository;

    @Test
    void findByName() {
        List<Person> people = personRepository.findByName("tony");
        Assertions.assertThat(people.size()).isEqualTo(1);

        Person person = people.get(0);
        assertAll(
                () -> Assertions.assertThat(person.getName()).isEqualTo("tony"),
                () -> Assertions.assertThat(person.getHobby()).isEqualTo("reading"),
                () -> Assertions.assertThat(person.getAddress()).isEqualTo("seoul"),
                () -> Assertions.assertThat(person.getBirthday()).isEqualTo(Birthday.of(LocalDate.of(1991,7,10))),
                () -> Assertions.assertThat(person.getJob()).isEqualTo("officer"),
                () -> Assertions.assertThat(person.getPhoneNumber()).isEqualTo("010-2222-5555"),
                () -> Assertions.assertThat(person.isDeleted()).isEqualTo(false)
        );
    }

    @Test
    void findByNameIfDeleted() {
        List<Person> people = personRepository.findByName("andrew");
        Assertions.assertThat(people.size()).isEqualTo(0);
    }

    @Test
    void findByMonthOfBirthday() {
        List<Person> people = personRepository.findByMonthOfBirthday(7);

        Assertions.assertThat(people.size()).isEqualTo(2);
        assertAll(
                () -> Assertions.assertThat(people.get(0).getName()).isEqualTo("david"),
                () -> Assertions.assertThat(people.get(1).getName()).isEqualTo("tony")
        );
    }

    @Test
    void findPeopleDeleted() {
        List<Person> people = personRepository.findPeopleDeleted();

        Assertions.assertThat(people.size()).isEqualTo(1);
        Assertions.assertThat(people.get(0).getName()).isEqualTo("andrew");
    }
}