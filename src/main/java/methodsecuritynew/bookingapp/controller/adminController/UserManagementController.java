package methodsecuritynew.bookingapp.controller.adminController;

import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.User;
import methodsecuritynew.bookingapp.entity.UserDelete;
import methodsecuritynew.bookingapp.repository.UserRepository;
import methodsecuritynew.bookingapp.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class UserManagementController {

       private final AuthService authService;
    @GetMapping
    public String viewUserManagementPage(Model model){
//        List<UserDelete> userDeletes = userDeleteRepository.findAll().stream()
//                .sorted((ht1, ht2)-> ht2.getCreatedAt().compareTo(ht1.getCreatedAt()))
//                .toList();
        model.addAttribute("listUser",authService.getAllUserRoleUser());
        return "admin/user/index";
    }
    @GetMapping("/detail/{id}")
    public String viewDetailUser(Model model, @PathVariable Integer id){
        User user = authService.getUserById(id);
        model.addAttribute("user",user);
        int currentYear = LocalDate.now().getYear();
        model.addAttribute("currentYear",currentYear);

        return "admin/user/detail";
    } @GetMapping("create")
    public String viewCreateUser(Model model){
//        List<UserDelete> userDeletes = userDeleteRepository.findAll().stream()
//                .sorted((ht1, ht2)-> ht2.getCreatedAt().compareTo(ht1.getCreatedAt()))
//                .toList();
        model.addAttribute("listUser",authService.getAllUserRoleUser());
        return "admin/user/create";
    }

}
