package ca.sheridancollege.andres.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.andres.beans.Book;
import ca.sheridancollege.andres.beans.Review;
import ca.sheridancollege.andres.beans.User;

@Repository
public class DatabaseAccess {
	@Autowired
	private NamedParameterJdbcTemplate jdbc;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	public List<Book> getBookList(){
		String sql = "SELECT * FROM Books";
		List<Book> books = jdbc.query(sql, new BeanPropertyRowMapper<Book>(Book.class));
		return books;
	
	}
	
	public List<Review> getReviewList(Long bookId){
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String sql = "SELECT * FROM reviews WHERE bookId=:bookId";
		parameters.addValue("bookId", bookId);
		List<Review> reviews = jdbc.query(sql, parameters, new BeanPropertyRowMapper<Review>(Review.class));
		return reviews;
	}
	
	public Book getBook(Long id) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String sql = "SELECT * FROM books WHERE id=:id";
		parameters.addValue("id", id);
		Book book = jdbc.queryForObject(sql, parameters, new BeanPropertyRowMapper<Book>(Book.class));
		return book;
	}
	
	public User getUser(Long id) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String sql = "SELECT * FROM users WHERE userId=:id";
		parameters.addValue("id", id);
		User user = jdbc.queryForObject(sql, parameters, new BeanPropertyRowMapper<User>(User.class));
		return user;
	}
	
	public void addReview(Review review) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String sql = "INSERT INTO reviews (bookId, text) values (:bookId, :text);";
		parameters.addValue("bookId", review.getBookId());
		parameters.addValue("text", review.getText());
		jdbc.update(sql, parameters);
	}
	
	public void addBook(Book book) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String sql = "INSERT INTO books (title, author) VALUES (:title, :author)";
		parameters.addValue("title", book.getTitle());
		parameters.addValue("author", book.getAuthor());
		jdbc.update(sql, parameters);
	}
	
	
	
	
	
	public User findUserAccount(String email) {
		MapSqlParameterSource parameters=new MapSqlParameterSource();
		String sql="SELECT * FROM users WHERE email=:email;";
		parameters.addValue("email", email);
		try {
			return jdbc.queryForObject(sql, parameters, new BeanPropertyRowMapper<User>(User.class));
		}
		catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	
	
	public List<String> getRolesById(Long userId) {
		MapSqlParameterSource parameters=new MapSqlParameterSource();
		String sql="SELECT r.roleName FROM roles r JOIN user_role ur ON r.roleId = ur.roleId AND userId =:userId;";
		parameters.addValue("userId", userId);
		List<String> roles = jdbc.queryForList(sql, parameters, String.class);
		return roles;
	}
	
	
	
	public void addUser(String email,String password) {
		MapSqlParameterSource parameters=new MapSqlParameterSource();
		String q1="INSERT INTO users(email,password,enabled) VALUES(:email,:password,true);";
		parameters.addValue("email",email);
		parameters.addValue("password",passwordEncoder.encode(password));
		jdbc.update(q1, parameters);
	}
	
	public void addRole(long userId,long roleId) {
		MapSqlParameterSource parameters=new MapSqlParameterSource();
		String q1="INSERT INTO user_role(userId,roleId) VALUES(:user,:role);";
		parameters.addValue("user",userId);
		parameters.addValue("role",roleId);
		jdbc.update(q1, parameters);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
