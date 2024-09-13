package kz.segizbay.springlibrary.services;

import kz.segizbay.springlibrary.models.Book;
import kz.segizbay.springlibrary.models.Person;
import kz.segizbay.springlibrary.repositories.PeopleRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> finaAll(){
        return peopleRepository.findAll();
    }

    public Optional<Person> findByName(String fullName){
        return peopleRepository.findByFullName(fullName);
    }

    @Transactional(readOnly = true)
    public Person findById(int id){
        return peopleRepository.findById(id).orElse(null);
    }

    public List<Book> findBooksByPersonId(int id){
        Optional<Person> person = peopleRepository.findById(id);

        if (person.isPresent()){
            List<Book> books = person.get().getBooks();
//            Hibernate.initialize(person.get().getBooks());
            for (Book book : books){
                Date nowDate = new Date();
                long different = (nowDate.getTime()-book.getDateOfTaken().getTime())/(1000*60*60*24);
                if (different>=10){
                    book.setOverdue(true);
                } else {
                    book.setOverdue(false);
                }
            }
            return person.get().getBooks();
        }
        return Collections.emptyList();
    }

    @Transactional
    public void save(Person person){
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id,Person updatedPerson){
        Person person = peopleRepository.getOne(id);
        person.setFullName(updatedPerson.getFullName());
        person.setYearOfBirth(updatedPerson.getYearOfBirth());

        peopleRepository.save(person);
    }

    @Transactional
    public void delete(int id){
        peopleRepository.deleteById(id);
    }
}
