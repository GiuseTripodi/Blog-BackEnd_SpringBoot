package com.example.demo.controllers;

import com.example.demo.entities.BlogArticles;
import com.example.demo.services.BlogArticlesService;
import com.example.demo.support.exception.ArticoloGiaEsistenteException;
import com.example.demo.support.exception.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OwnerController {

    @Autowired
    private BlogArticlesService articlesService;

    @PostMapping("/create_articolo")
    public ResponseEntity createArticolo(@RequestBody BlogArticles articolo){
        BlogArticles ret;
        try{
            ret = articlesService.addArticolo(articolo);
        } catch (ArticoloGiaEsistenteException e) {
            return new ResponseEntity<>(new ResponseMessage("Articolo già esistente"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }
}
