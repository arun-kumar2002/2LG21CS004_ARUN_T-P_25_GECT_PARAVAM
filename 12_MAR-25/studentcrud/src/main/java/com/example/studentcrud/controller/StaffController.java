package com.example.studentcrud.controller;


import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.studentcrud.model.Staff;
import com.example.studentcrud.service.StaffService;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/staff")
public class StaffController {

    
    @Autowired
    private StaffService staffService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("staff",staffService.listAll() );
        return "staff/index"; 
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("staff", new Staff());
        return "staff/create";
    }

    @PostMapping("/save")
    public String saveStaff(
    @RequestParam("name") String name,
    @RequestParam("email") String email,
    @RequestParam("dob") String dob,
    @RequestParam("adress") String adress,
    @RequestParam("gender") String gender,
    @RequestParam("phone") String phone,@RequestParam("profilepic") MultipartFile profilePic) {
        String uploadDir = new File("src/main/resources/static/upload/").getAbsolutePath();
        try {
        String fileName = profilePic.getOriginalFilename();
        String filePath = uploadDir + File.separator + fileName;
        profilePic.transferTo(new File(filePath));
        Staff sta = new Staff();
        sta.setName(name);
        sta.setEmail(email);
        sta.setPhone(phone);
        sta.setName(dob);
        sta.setEmail(adress);
        sta.setPhone(gender);
        sta.setProfilepic("/upload/" + fileName);
        staffService.saveStaff(sta);
    } catch (IOException e) {
        e.printStackTrace();
    }
        return "redirect:/staff/list";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteStaff(@PathVariable Long id) {
        staffService.deleteStaff(id);
        return "redirect:/staff/list";
    }

    @GetMapping("/edit/{id}")
    public String editStaffString(@PathVariable Long id, Model model) {
        model.addAttribute("staff", staffService.getStaff(id));
        return "staff/edit";

    }

    @PostMapping("/update/{id}")
    public String updateStaff(@PathVariable Long id, @ModelAttribute Staff sta) {
        sta.setId(id);
        staffService.saveStaff(sta);
        return "redirect:/staff/list";
    }
}