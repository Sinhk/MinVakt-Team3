package minvakt.controller;

import minvakt.datamodel.EmployeeCategory;
import minvakt.repos.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by sindr on 17.01.2017.
 * in project: MinVakt-Team3
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryRepository categoryRepo;

    @Autowired
    public CategoryController(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @GetMapping
    public List<EmployeeCategory> getCategories() {
        return (List<EmployeeCategory>) categoryRepo.findAll();
    }

}

