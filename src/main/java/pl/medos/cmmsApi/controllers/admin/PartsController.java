package pl.medos.cmmsApi.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.medos.cmmsApi.dto.PartListDto;
import pl.medos.cmmsApi.model.Part;
import pl.medos.cmmsApi.service.PartService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/parts")
public class PartsController {
    @Autowired
    private PartService partService;

    @GetMapping(value = "/all")
    public String showAll(Model model) {
        model.addAttribute("parts", partService.findAll());
        return "list-items";
    }

    @GetMapping(value = "/create")
    public String showCreateForm(Model model) {
        PartListDto itemsForm = new PartListDto();

        for (int i = 1; i <= 3; i++) {
            itemsForm.addPart(new Part());
        }

        model.addAttribute("form", itemsForm);
        return "create-items";
    }

    @GetMapping(value = "/edit")
    public String showEditForm(Model model) {
        List<Part> parts = new ArrayList<>();
        partService.findAll()
                .iterator()
                .forEachRemaining(parts::add);

        model.addAttribute("form", new PartListDto(parts));

        return "edit-items";
    }

    @PostMapping(value = "/save")
    public String saveBooks(@ModelAttribute PartListDto form, Model model) {
        partService.saveAll(form.getParts());

        model.addAttribute("parts", partService.findAll());

        return "redirect:/parts/all";
    }
}
