package methodsecuritynew.bookingapp.controller;

import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.Support;
import methodsecuritynew.bookingapp.model.statics.SupportType;
import methodsecuritynew.bookingapp.service.SupportService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/support")
public class SupportController {
    private final SupportService supportService ;

    @GetMapping()
    public String viewHomePage (Model model){
        model.addAttribute("supportList" , supportService.getAllSupport());
        return "admin/support/index";
    }
    @GetMapping("/create")
    public String viewCreateSupportPage(Model model){
        model.addAttribute("supportTypes" , SupportType.values());
        return "admin/support/create";
    }
    @GetMapping("/{id}/detail")
    public String viewDetailSupportPage(Model model, @PathVariable Integer id){
        Support support  = supportService.getSupportById(id);
        model.addAttribute("support" , support);
        model.addAttribute("supportTypes" , SupportType.values());
        return "admin/support/detail";
    }
}
