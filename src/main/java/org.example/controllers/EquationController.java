package org.example.controllers;

import org.example.services.EquationService;
import org.example.services.RootService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/")
public class EquationController {

    private final EquationService equationService;
    private final RootService rootService;

    @Autowired
    public EquationController(EquationService equationService, RootService rootService) {
        this.equationService = equationService;
        this.rootService = rootService;
    }

    @GetMapping()
    public String index() {
        return "index";
    }

    @PostMapping("check")
    public String checkEquation(@ModelAttribute("equation") String equation, Model model) {
        boolean correctness =  equationService.checkForCorrectness(equation) && equationService.checkForBrackets(equation);
        Integer equationId = null;
        if (correctness) {
            equationId = equationService.saveEquation(equation);
        }
        Integer digitCount = equationService.numsCount(equation);
        model.addAttribute("correctness", correctness);
        model.addAttribute("equationId", equationId);
        model.addAttribute("digitCount", digitCount);
        return "check";
    }

    @PostMapping("check/{id}")
    public String checkRoot(@ModelAttribute("root") String root, Model model, @PathVariable("id") Integer equationId) {
        boolean isRoot = equationService.isRoot(Double.parseDouble(root), equationId);
        if (isRoot) {
            rootService.save(root, equationId);
        }
        model.addAttribute("isRoot", isRoot);
        model.addAttribute("root", root);
        return "result";
    }

//    @PostMapping("find")
//    public String findByRoots(@ModelAttribute("roots") String roots, Model model) {
//        List<Equation> equations = equationService.findEquationsByRoots(roots);
//        model.addAttribute("equations", equations);
//        return "find-result";
//    }
}
