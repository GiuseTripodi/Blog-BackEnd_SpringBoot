package com.example.demo.controllers;

import com.example.demo.entities.BlogArticles;
import com.example.demo.entities.Token;
import com.example.demo.services.BlogArticlesService;
import com.example.demo.support.exception.ArticoloNonEsistenteException;
import com.example.demo.support.exception.ResponseMessage;
import com.example.demo.support.exception.tokenGiàEsistenteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HomeController {

    @Autowired
    private BlogArticlesService articlesService;

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity getArticoliPerCategoria(@PathVariable("categoria")String categoria){
        List<BlogArticles> ret = null;
        try {
            ret = articlesService.getArticoliPerCategoria(categoria);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseMessage("Categoria non esistente"), HttpStatus.BAD_REQUEST);
        }
        if(ret.size() <= 0){
            return new ResponseEntity<>(new ResponseMessage("No result"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ret, HttpStatus.OK);

    }

    @GetMapping(value = "/articolo/{articolo}")
    public ResponseEntity getArticoloByID(@PathVariable("articolo") Integer articolo ){
        BlogArticles ret = null;
        try {
            ret = articlesService.getArticoloByID(new Long(articolo));
        } catch (ArticoloNonEsistenteException e) {
            return new ResponseEntity<>(new ResponseMessage("Articolo non esistente"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    @GetMapping("/articoloPerLike")
    public ResponseEntity getArticoliOrdinatiPerLike(){
        List<BlogArticles> ret = articlesService.getArticoliPiuLike();
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    @GetMapping("/articoloPerData")
    public ResponseEntity getArticoliOrdinatiPerData(){
        List<BlogArticles> ret = articlesService.getArticoliDataPiuRecenti();
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }
    @GetMapping("/articoli")
    public ResponseEntity getAllArticoli(){
        List<BlogArticles> ret = articlesService.getAllArticoli();
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    @GetMapping("/categorie")
    public ResponseEntity getAllCategorie(){
        List<String> ret = articlesService.getAllCategorie();
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    @GetMapping("/addLike/{idArticolo}")
    public ResponseEntity addLike(@PathVariable("idArticolo") Long idArticolo,
                                  @RequestHeader("Authorization") String Authorization){
        //verifico se il token ricevuto è già memorizzato nel DB, in questo caso do errore
        Token token = new Token(); token.setAcces(Authorization); token.setIdarticolo(idArticolo.toString());
        try{
            articlesService.addToken(token);
        } catch (tokenGiàEsistenteException e) {
            return new ResponseEntity<>(new ResponseMessage("Utente ha già effettuato un operazione"), HttpStatus.BAD_REQUEST);
        }
        BlogArticles ret;

        try{
            ret = articlesService.addLikeAdArticolo(idArticolo);
        } catch (ArticoloNonEsistenteException e) {
            return new ResponseEntity<>(new ResponseMessage("Articolo non esistente"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(ret, HttpStatus.OK);

    }

    @GetMapping("/addDislike/{idArticolo}")
    public ResponseEntity addDislike(@PathVariable("idArticolo") Long idArticolo,
                                     @RequestHeader("Authorization") String Authorization){
        //verifico se il token ricevuto è già memorizzato nel DB, in questo caso do errore
        Token token = new Token(); token.setAcces(Authorization); token.setIdarticolo(idArticolo.toString());
        try{
            articlesService.addToken(token);
        } catch (tokenGiàEsistenteException e) {
            return new ResponseEntity<>(new ResponseMessage("Utente ha già effettuato un operazione"), HttpStatus.BAD_REQUEST);
        }
        BlogArticles ret;
        try{
            ret = articlesService.addDislikeAdArticolo(idArticolo);
        } catch (ArticoloNonEsistenteException e) {
            return new ResponseEntity<>(new ResponseMessage("Articolo non esistente"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(ret, HttpStatus.OK);

    }

}
