package com.examples.form.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.examples.form.model.User;
import com.examples.form.service.UserService;
import com.examples.form.validator.UserFormValidator;

@Controller
public class UserController {

//	@Autowired
//	UserFormValidator userFormValidator;
//
//	@InitBinder
//	protected void initBinder(WebDataBinder binder) {
//		binder.setValidator(userFormValidator);
//	}

	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		return "redirect:/users";
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public String showAllUsers(Model model) {

		model.addAttribute("users", userService.findAll());
		return "users/list";

	}

	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public String saveOrUpdateUser(@ModelAttribute("userForm") @Validated User user, BindingResult result, Model model,
			final RedirectAttributes redirectAttributes) throws Exception {


		if (result.hasErrors()) {
			return "users/userform";
		} else {

			redirectAttributes.addFlashAttribute("css", "success");
			if (user.isNew()) {
				redirectAttributes.addFlashAttribute("msg", "User added successfully!");
			} else {
				redirectAttributes.addFlashAttribute("msg", "User updated successfully!");
			}

			userService.saveOrUpdate(user);
			
			return "redirect:/users/" + user.getId();
		}

	}

	@RequestMapping(value = "/users/add", method = RequestMethod.GET)
	public String showAddUserForm(Model model) {


		User user = new User();

		// set default value
		user.setName("examples123");
		user.setEmail("test@gmail.com");
		user.setAddress("abc 88");

		model.addAttribute("userForm", user);

		return "users/userform";

	}

	@RequestMapping(value = "/users/{id}/update", method = RequestMethod.GET)
	public String showUpdateUserForm(@PathVariable("id") int id, Model model) throws Exception {


		User user = userService.findById(id);
		if (user == null) {
			throw new Exception("User doesn't exist to update");
		}
		model.addAttribute("userForm", user);

		return "users/userform";

	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public String showUser(@PathVariable("id") int id, Model model) throws Exception {


		User user = userService.findById(id);
		if (user == null) {
			throw new Exception("User doesn't exist to show");
		}
		model.addAttribute("user", user);

		return "users/show";

	}
}