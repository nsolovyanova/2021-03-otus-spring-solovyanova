package ru.otus.spring.repositories;

import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryJpa implements BookRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Book> getAll() {
        TypedQuery<Book> query = entityManager.createQuery("select a from Book a", Book.class);
        return query.getResultList();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            entityManager.persist(book);
            return book;
        }
        return entityManager.merge(book);
    }

    @Override
    public Optional<Book> getById(Long id) {
        return Optional.ofNullable(entityManager.find(Book.class, id));
    }

    @Override
    public Optional<Book> getByName(String name) {
        TypedQuery<Book> query = entityManager.createQuery("select a from Book a where a.name = :name", Book.class);
        query.setParameter("name", name);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public List<Book> getAllBookByAuthor(Author author) {
        TypedQuery<Book> query = entityManager.createQuery("select b from Book b " +
                                                               "join fetch Author a " +
                                                               "where a.id = :id", Book.class);
        query.setParameter("id", author.getId());
        return query.getResultList();
    }

    @Override
    public List<Book> getAllBookByGenre(Genre genre) {
        TypedQuery<Book> query = entityManager.createQuery("select b from Book b " +
                "join fetch Genre a " +
                "where a.id = :id", Book.class);
        query.setParameter("id", genre.getId());
        return query.getResultList();
    }

    @Override
    public void delete(Book book) {
        entityManager.remove(book);
    }
}
