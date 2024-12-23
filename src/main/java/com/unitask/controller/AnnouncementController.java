package com.unitask.controller;


import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping(path = "announcement")
@AllArgsConstructor
public class AnnouncementController {

}
