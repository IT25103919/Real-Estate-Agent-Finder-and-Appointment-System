package com.example.realestate.controller;

import com.example.realestate.models.Inquiry;
import com.example.realestate.service.InquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inquiries")
@CrossOrigin(origins = "*")
public class InquiryController {

    @Autowired
    private InquiryService inquiryService;

    @GetMapping
    public List<Inquiry> getAll() {
        return inquiryService.getAllInquiries();
    }

    @PostMapping
    public ResponseEntity<Inquiry> create(@RequestBody Inquiry inquiry) {
        return ResponseEntity.ok(inquiryService.createInquiry(inquiry));
    }

    @PutMapping("/{id}/reply")
    public ResponseEntity<Inquiry> reply(@PathVariable Long id, @RequestParam String message) {
        return ResponseEntity.ok(inquiryService.replyToInquiry(id, message));
    }
}