package all.dao;

import all.models.Book;
import all.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addNewBook(Book book) {
        jdbcTemplate.update("INSERT INTO book(book_name, author, release_date) VALUES(?, ?, ?)",
                book.getBook_name(), book.getAuthor(), book.getRelease_date());
    }
    public List<Book> getAllBook() {
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book getBookById(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE book_id = ?",
                new BeanPropertyRowMapper<>(Book.class), id).stream().findAny().orElse(null);
    }

    public void deleteBook(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE book_id = ?", id);
    }

    public void editBook(int id, Book book) {
        jdbcTemplate.update(
                "UPDATE book SET book_name = ?, author = ?, release_date = ? WHERE person_id = ?",
                book.getBook_name(), book.getAuthor(), book.getRelease_date(), id);
    }

    public void assignBook(int bookId, Person personToAssign) {
        jdbcTemplate.update("UPDATE book SET person_id = ? WHERE book_id = ?",
        personToAssign.getPerson_id(), bookId);
    }

    public void freeBook(int bookId) {
        jdbcTemplate.update("UPDATE book SET person_id = null WHERE book_id = ?", bookId);
    }

    public Optional<Person> getBookOwner(int bookId) {
        return jdbcTemplate.query("SELECT person.* FROM Book JOIN Person ON " +
                "Book.person_id = Person.person_id WHERE Book.book_id = ?",
                new BeanPropertyRowMapper<>(Person.class), bookId).stream().findAny();
    }
}
