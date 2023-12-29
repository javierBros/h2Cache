package com.cache.api;

import com.cache.model.DataModel;
import com.cache.service.NationalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;

@RestController
@RequestMapping("/api")
public class NationalityController {

    private final NationalityService nationalityService;

    @Autowired
    public NationalityController(NationalityService nationalityService){
        this.nationalityService = nationalityService;
    }

    @GetMapping(value = "/nationalities")
    public ResponseEntity<DataModel> getData(@RequestParam String name){
        return new ResponseEntity<>(
                this.nationalityService.getData(name), HttpStatus.OK);
    }
}
