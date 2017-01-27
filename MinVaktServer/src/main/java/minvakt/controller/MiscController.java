package minvakt.controller;

import minvakt.datamodel.tables.pojos.Department;
import minvakt.repos.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by sindr on 12.01.2017.
 * in project: MinVakt-Team3
 */
@RestController
public class MiscController {

    final
    DepartmentRepository depRepo;
    private static final Logger log = LoggerFactory.getLogger(MiscController.class);

    @Autowired
    public MiscController(DepartmentRepository depRepo) {
        this.depRepo = depRepo;
    }

    @GetMapping("/departments")
    public List<Department> getDepartments(){
        return depRepo.findAll();
    }
}

