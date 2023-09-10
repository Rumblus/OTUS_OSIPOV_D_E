package ru.otus.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Book;

import java.util.List;
import java.util.Optional;

@Repository
public class BookDaoJpa implements BookDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Book insertBook(Book book) {
        if (book.getId() == 0) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public Optional<Book> getBookById(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public Optional<Book> getBookByName(String name) {
        TypedQuery<Book> query = em.createQuery("select b from Book b where b.title = :name", Book.class);
        query.setParameter("name", name);
        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.ofNullable(null);
        }
    }

    @Override
    public List<Book> getAllBooks() {
        TypedQuery<Book> query = em.createQuery(
                "select b from Book b " +
                   "left join fetch b.author " +
                   "left join fetch b.genre " +
                   "left join fetch b.comments",
                    Book.class);
        return query.getResultList();
    }

    @Override
    public void updateBook(Book book) {
        em.merge(book);
    }

    @Override
    public void deleteBook(Book book) {
        Book refreshedBook = em.find(Book.class, book.getId());
        em.refresh(refreshedBook);
        em.remove(refreshedBook);
    }
}
