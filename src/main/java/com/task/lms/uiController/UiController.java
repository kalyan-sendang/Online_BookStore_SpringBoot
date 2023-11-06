package com.task.lms.uiController;

 import com.task.lms.model.User;
 import com.task.lms.service.UserService;
import com.task.lms.utils.UserDTO;
 import jakarta.validation.Valid;
 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
 import org.springframework.ui.Model;
 import org.springframework.validation.BindingResult;
 import org.springframework.web.bind.annotation.*;

 import java.util.List;

@Controller
public class UiController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        List<UserDTO> usersDTO = userService.getAllUser();
        model.addAttribute("alluserlist", usersDTO);
        return "index";
    }

    @GetMapping("/adduser")
    public String addNewUser(@Valid Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "signUp";
    }
    @PostMapping("/save")
    public String saveUser(@Valid @ModelAttribute("user")User user, BindingResult result){
        if(result.hasErrors()){
            return "/signUp";
        }
        userService.insertUser(user);
        return "redirect:/";
    }
    @GetMapping("/update/{id}")
    public String updateUser( @PathVariable("id")int id,@Valid Model model){
        User user = userService.getAUserById(id);
        model.addAttribute("user", user);
        return "update";
    }
    @PostMapping("/update/{id}")
    public String update(@PathVariable("id")int id,@Valid @ModelAttribute("user")User user, BindingResult result){
        if (result.hasErrors()) {
            return "/update";
        }
        userService.update(id, user);
        return "redirect:/";
    }
    @GetMapping("/delete/{id}")
    public String deleteThroughId(@PathVariable(value = "id") int id) {
        userService.deleteUser(id);
        return "redirect:/";
    }
}
