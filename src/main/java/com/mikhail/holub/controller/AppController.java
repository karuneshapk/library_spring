package com.mikhail.holub.controller;

import com.mikhail.holub.model.Book;
import com.mikhail.holub.model.User;
import com.mikhail.holub.model.UserProfile;
import com.mikhail.holub.service.BookService;
import com.mikhail.holub.service.UserProfileService;
import com.mikhail.holub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;



@Controller
@RequestMapping("/")
@SessionAttributes("roles")
public class AppController {

	@Autowired
	UserService userService;

	@Autowired
	BookService bookService;

	@Autowired
	UserProfileService userProfileService;
	
	@Autowired
	MessageSource messageSource;

	@Autowired
	PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;
	
	@Autowired
	AuthenticationTrustResolver authenticationTrustResolver;
	
	
	/**
	 * This method will list all existing users.
	 */
	@RequestMapping(value = { "/userlist" }, method = RequestMethod.GET)
	public String listUsers(ModelMap model) {

		List<User> users = userService.findAllUsers();
		model.addAttribute("users", users);
		model.addAttribute("loggedinuser", getPrincipal());
		return "userlist";
	}

	/**
	 * This method will list all existing books.
	 */
	@RequestMapping(value = { "/", "/booklist" }, method = RequestMethod.GET)
	public String listBooks(ModelMap model) {

		List<Book> books = bookService.findAllBooks();
		model.addAttribute("books", books);
		return "booklist";
	}

	/**
	 * This method will provide the medium to add a new user.
	 */
	@RequestMapping(value = { "/newuser" }, method = RequestMethod.GET)
	public String newUser(ModelMap model) {
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("edit", false);
		model.addAttribute("loggedinuser", getPrincipal());
		return "registration";
	}

	/**
	 * This method will be called on form submission, handling POST request for
	 * saving user in database. It also validates the user input
	 */
	@RequestMapping(value = { "/newuser" }, method = RequestMethod.POST)
	public String saveUser(@Valid User user, BindingResult result,
			ModelMap model) {

		if (result.hasErrors()) {
			return "registration";
		}

		if(!userService.isUserNickNameUnique(user.getId(), user.getNickNameId())){
			FieldError nickNameError =new FieldError("user","nickNameId",messageSource.getMessage("non.unique.nickNameId", new String[]{user.getNickNameId()}, Locale.getDefault()));
		    result.addError(nickNameError);
			return "registration";
		}

		userService.saveUser(user);

		model.addAttribute("success", "User " + user.getFirstName() + " "+ user.getLastName() + " registered successfully");
		model.addAttribute("loggedinuser", getPrincipal());
		//return "success";
		return "registrationsuccess";
	}

	/**
	 * This method will provide the medium to add a new book.
	 */
	@RequestMapping(value = { "/newbook" }, method = RequestMethod.GET)
	public String newBook(ModelMap model) {
		Book book = new Book();
		model.addAttribute("book", book);
		model.addAttribute("edit", false);
		return "addingbook";
	}

	/**
	 * This method will be called on form submission, handling POST request for
	 * saving book in database. It also validates the book input
	 */
	@RequestMapping(value = { "/newbook" }, method = RequestMethod.POST)
	public String saveBook(@Valid Book book, BindingResult result,
						   ModelMap model) {

		if (result.hasErrors()) {
			return "addingbook";
		}

		bookService.saveBook(book);

		model.addAttribute("success", "Book " + book.getBookName() + " "+ book.getAghtorNameL() + " added successfully");
		return "registrationsuccess";
	}

	/**
	 * This method will provide the medium to update an existing user.
	 */
	@RequestMapping(value = { "/edit-user-{nickNameId}" }, method = RequestMethod.GET)
	public String editUser(@PathVariable String nickNameId, ModelMap model) {
		User user = userService.findByNickName(nickNameId);
		model.addAttribute("user", user);
		model.addAttribute("edit", true);
		model.addAttribute("loggedinuser", getPrincipal());
		return "registration";
	}
	
	/**
	 * This method will be called on form submission, handling POST request for
	 * updating user in database. It also validates the user input
	 */
	@RequestMapping(value = { "/edit-user-{nickNameId}" }, method = RequestMethod.POST)
	public String updateUser(@Valid User user, BindingResult result,
			ModelMap model, @PathVariable String nickNameId) {

		if (result.hasErrors()) {
			return "registration";
		}

		userService.updateUser(user);

		model.addAttribute("success", "User " + user.getFirstName() + " "+ user.getLastName() + " updated successfully");
		model.addAttribute("loggedinuser", getPrincipal());
		return "registrationsuccess";
	}

    /**
     * This method will provide the medium to update an existing book.
     */
    @RequestMapping(value = { "/edit-book-{id}" }, method = RequestMethod.GET)
    public String editBook(@PathVariable int id, ModelMap model) {
        Book book = bookService.findById(id);
        model.addAttribute("book", book);
        model.addAttribute("edit", true);
        return "addingbook";
    }

    /**
     * This method will be called on form submission, handling POST request for
     * updating book in database. It also validates the book input
     */
    @RequestMapping(value = { "/edit-book-{id}" }, method = RequestMethod.POST)
    public String updateBook(@Valid Book book, BindingResult result,
                             ModelMap model, @PathVariable int id) {
        if (result.hasErrors()) {
            return "addingbook";
        }
        bookService.updateBook(book);
        model.addAttribute("success", "Book " + book.getBookName() + " "+ book.getAghtorNameL() +" " + book.getAghtorNameF() + "updated successfully");
        return "registrationsuccess";
    }

    /**
	 * This method will delete an user by it's NickNameID value.
	 */
	@RequestMapping(value = { "/delete-user-{nickNameId}" }, method = RequestMethod.GET)
	public String deleteUser(@PathVariable String nickNameId) {
		userService.deleteUserByNickName(nickNameId);
		return "redirect:/userlist";
	}

    /**
     * This method will delete an book by it's bookId value.
     */
    @RequestMapping(value = { "/delete-book-{bookId}" }, method = RequestMethod.GET)
    public String deleteUser(@PathVariable int bookId) {
        bookService.deleteBookById(bookId);
        return "redirect:/booklist";
    }

//
//	/**
//	 * This method will provide to reserve an selected book.
//	 */
//	@RequestMapping(value = { "/reserve-book-{id}" }, method = RequestMethod.GET)
//	public String editReservingBook(@PathVariable int id, ModelMap model) {
//
//	}
//
//	/**
//	 * This method will be called on form submission, handling POST request for
//	 * updating book in database. It also validates the book input
//	 */
//	@RequestMapping(value = { "/reserve-book-{id}" }, method = RequestMethod.POST)
//	public String updateReservingBook(@Valid Book book, BindingResult result,
//							 ModelMap model, @PathVariable int id) {
//
//	}

	/**
	 * This method will provide UserProfile list to views
	 */
	@ModelAttribute("roles")
	public List<UserProfile> initializeProfiles() {
		return userProfileService.findAll();
	}
	
	/**
	 * This method handles Access-Denied redirect.
	 */
	@RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
	public String accessDeniedPage(ModelMap model) {
		model.addAttribute("loggedinuser", getPrincipal());
		return "accessDenied";
	}

	/**
	 * This method handles login GET requests.
	 * If users is already logged-in and tries to goto login page again, will be redirected to list page.
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage() {
		if (isCurrentAuthenticationAnonymous()) {
			return "login";
	    } else {
	    	return "redirect:/list";  
	    }
	}

	/**
	 * This method handles logout requests.
	 * Toggle the handlers if you are RememberMe functionality is useless in your app.
	 */
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){    
			//new SecurityContextLogoutHandler().logout(request, response, auth);
			persistentTokenBasedRememberMeServices.logout(request, response, auth);
			SecurityContextHolder.getContext().setAuthentication(null);
		}
		return "redirect:/login?logout";
	}

	/**
	 * This method returns the principal[user-name] of logged-in user.
	 */
	private String getPrincipal(){
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails)principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}
	
	/**
	 * This method returns true if users is already authenticated [logged-in], else false.
	 */
	private boolean isCurrentAuthenticationAnonymous() {
	    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    return authenticationTrustResolver.isAnonymous(authentication);
	}
}