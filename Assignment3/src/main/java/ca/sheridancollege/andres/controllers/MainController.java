package ca.sheridancollege.andres.controllers;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.andres.beans.Book;
import ca.sheridancollege.andres.beans.Review;
import ca.sheridancollege.andres.beans.User;
import ca.sheridancollege.andres.database.DatabaseAccess;

@Controller
public class MainController {
	
	private DatabaseAccess database;
	@Autowired
	public MainController(DatabaseAccess database) {
		this.database = database;
	}
	
	@GetMapping("/")
	public String indexPage(Model model) {
		List<Book> books = database.getBookList();
		model.addAttribute("books", books);
		return "index.html";
	}
	
	@GetMapping("/bookReviews/{id}")
	public String viewReviews(Model model, @PathVariable(name = "id") Long id) {
		Book book = database.getBook(id);
		List<Review> reviews = database.getReviewList(id);
		
		model.addAttribute("reviews", reviews);
		model.addAttribute("book", book);
		return "view-book.html";
	}
	
	@GetMapping("/login")
	public String loginPage() {
		return "login.html";
	}
	
	@GetMapping("/permissionDenied")
	public String permissionDeniedPage() {
		return "/error/permission-denied.html";
	}
	
	@GetMapping("/user/{id}")
	public String createReviewPage(Model model, @PathVariable(name = "id") Long id) {
		
		Review review = new Review();
		review.setBookId(id);
		model.addAttribute("Review", review);
		return "/user/add-review.html";
	}
	
	@PostMapping("/addReview")
	public String addReviewPage(@ModelAttribute Review review) {
		
		database.addReview(review);
		return "redirect:/bookReviews/" + review.getBookId();
	}
	
	
	@GetMapping("/admin")
	public String addBookPage(Model model) {
		model.addAttribute("book", new Book());
		return "/admin/add-book.html";
	}
	
	@PostMapping("/addBook")
	public String addBook(@ModelAttribute Book book) {
		database.addBook(book);
		return "redirect:/";
	}
	
	
	
	@GetMapping("/register")
	public String getRegister() {
	return "register.html";
	}
	
	
	@PostMapping("/registers")
	public String processRegForm(@RequestParam(name = "username") String username,@RequestParam(name = "password") String password) {
		database.addUser(username,password);
		Long userId=database.findUserAccount(username).getUserId();
		database.addRole(userId, Long.valueOf(2)); 
		return "redirect:/";
	}
	
	
	
	
	
}
