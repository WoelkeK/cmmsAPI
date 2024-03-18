package pl.medos.cmmsApi.controllers;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.medos.cmmsApi.model.Pass;
import pl.medos.cmmsApi.service.ImageService;
import pl.medos.cmmsApi.service.PassService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Controller
@RequestMapping("/przepustki")
@SessionAttributes(names = {"images", "przepustki"})
@RequiredArgsConstructor
public class PassWebcontroller {

    private static final Logger LOGGER = Logger.getLogger(PassWebcontroller.class.getName());
    private final PassService passService;
    private final ImageService imageService;

    @GetMapping("/read/{id}")
    public String findPassById(@PathVariable(name = "id") Long id, Model model) {
        LOGGER.info("findPassById() " + id);
        Pass passById = passService.findPassById(id);
        model.addAttribute("pass", passById);
        return "view-pass.html";
    }

    @GetMapping("/update/{id}")
    public String updateView(@PathVariable(name = "id") Long id, Model model) {
        LOGGER.info("update()");
        Pass passById = passService.findPassById(id);
        model.addAttribute("pass",passById);
        return "update-pass.html";
    }

    @PostMapping("/update")
    public String updatePass(@ModelAttribute(name = "pass") Pass pass, MultipartFile image) throws IOException {
        LOGGER.info("updatePass()");
        passService.updatePass(pass, pass.getId());
        Pass processedPass = imageService.prepareImage(pass, image);
        passService.createPass(processedPass);
        return "redirect:/przepustki";
        }

    @GetMapping
    public String listView(Model model){
        LOGGER.info("listView()");
        return findPagesPasses(1,"name", "desc", model);
    }

    @GetMapping(value = "/page/{pageNo}")
    public String findPagesPasses(@PathVariable(name = "pageNo") int pageNo,
                                         @RequestParam(name = "sortField") String sortField,
                                         @RequestParam(name = "sortDir") String sortDir,
                                         Model model) {
        LOGGER.info("findPagesPasses()");
        int size =10;
        Page<Pass> passes = passService.findPagePasses(pageNo, size, sortField, sortDir);
        List<Pass> passesList = passes.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", passes.getTotalPages());
        model.addAttribute("totalItems", passes.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("passes", passesList);
        Map<Long, String> jobBase64Images = new HashMap<>();
        for (Pass pass :passesList) {
            jobBase64Images.put(pass.getId(), Base64.getEncoder().encodeToString(pass.getResizedImage()));
        }
        model.addAttribute("images", jobBase64Images);
        return "main-pass.html";
    }

    @GetMapping("/create")
    public String createView(Model model) {
        LOGGER.info("createView");
        model.addAttribute("pass", new Pass());
        return "create-pass.html";
    }

    @PostMapping("/create")
    public String createPass(@Valid @ModelAttribute(name = "pass") Pass pass,
                                     BindingResult result,
                                     Model model,
                                     MultipartFile image) throws IOException {
        LOGGER.info("createPass()");
        if (result.hasErrors()) {
            LOGGER.info("create: result has erorr()" +pass+ " "+ result.getFieldError());
            model.addAttribute("pass", pass);
            return "create-pass";
        }
        Pass processedPass = imageService.prepareImage(pass, image);
        passService.createPass(processedPass);
        return "redirect:/przepustki";
    }

    @GetMapping("delete/{id}")
    public String deletePass(@PathVariable Long id) {
        LOGGER.info("deletePass()");
        passService.deletePass(id);
        return "redirect:/przepustki";
    }

    @GetMapping(value = "/downloadfile")
    public void downloadFile(@Param(value = "id") Long id, Model model, HttpServletResponse response) throws IOException {
        Pass passById = passService.findPassById(id);
        InputStream inputStream = new ByteArrayInputStream(passById.getResizedImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        IOUtils.copy(inputStream, response.getOutputStream());
    }

    @GetMapping("/search/{query}")
    public String searchByQuery(@RequestParam(name = "query") String query, Model model) {
        LOGGER.info("searchByQuery()");
        List<Pass> passesByName = passService.findPassByQuery(query);
        model.addAttribute("passes", passesByName);
        return "main-pass.html";
    }
}
