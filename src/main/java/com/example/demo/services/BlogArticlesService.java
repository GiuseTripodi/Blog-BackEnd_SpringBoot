package com.example.demo.services;

import com.example.demo.entities.BlogArticles;
import com.example.demo.entities.CategoryIndex;
import com.example.demo.entities.Token;
import com.example.demo.repositories.ArticoliRepository;
import com.example.demo.repositories.CategoryIndexRepository;
import com.example.demo.repositories.TokenRepository;
import com.example.demo.support.exception.ArticoloGiaEsistenteException;
import com.example.demo.support.exception.ArticoloNonEsistenteException;
import com.example.demo.support.exception.tokenGiàEsistenteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

@Service
public class BlogArticlesService {

    @Autowired
    private ArticoliRepository articoliRepository;

    @Autowired
    private CategoryIndexRepository categoryIndexRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private EntityManager entityManager;

    /**
     *
     * @return gli articoli con un numero maggiore di like
     */
    @Transactional(readOnly = true)
    public List<BlogArticles> getArticoliPiuLike(){
        List<BlogArticles> articoli =  articoliRepository.findByOrderByNumLikeDesc();
        //prendo solo i 5 articoli piu recenti
        List<BlogArticles> ret = articoli.subList(0,4);
        return ret;
    }

    /**
     *
     * @return restituisce gli arcicoli più recenti
     */
    @Transactional(readOnly = true)
    public List<BlogArticles> getArticoliDataPiuRecenti(){
        List<BlogArticles> articoli =  articoliRepository.findByOrderByTimeDesc();
        //prendo solo i 5 articoli piu recenti
        List<BlogArticles> ret = articoli.subList(0,4);
        return ret;
    }

    /**
     * Il metodo restituisce tutti gli articoli presenti associati alla categoria indicata. Da notare
     * che gli articoli prima di essere restituiti sono ordinati dal più recente al meno recente.
     * @param categoria, nome della categoria di cui prendere gli aricoli
     * @return
     */
    @Transactional
    public List<BlogArticles> getArticoliPerCategoria(String categoria){
        //non faccio il controllo di esistenza della categoria perchè all'utente sono mostrate solo le categorie esistenti
        List<BlogArticles> ret =  articoliRepository.getArticolidiCategoria(categoria);
        ret.sort(new Comparator<BlogArticles>() {
            @Override
            public int compare(BlogArticles o1, BlogArticles o2) {
                return o2.getTime().compareTo(o1.getTime());
            }
        });
        return ret;

    }

    /**
     * Il metodo restituisce l'articolo associato all'ID dell'articolo.
     * @param articolo, Id dell'articolo richiesto
     * @return
     * @throws ArticoloNonEsistenteException
     */
    @Transactional(readOnly = true)
    public BlogArticles getArticoloByID(Long articolo) throws ArticoloNonEsistenteException {
        if(! articoliRepository.existsById(articolo))
            throw new ArticoloNonEsistenteException();
        //return articoliRepository.getOne(articolo);
        return articoliRepository.getBlogArticlesById(articolo);
    }

    /**
     *
     * @return sono resituiti tutti gli articoli del DB
     */
    @Transactional(readOnly = true)
    public List<BlogArticles> getAllArticoli(){
        return articoliRepository.findAll();
    }

    /**
     *
     * @return i nomi di tutte le categorie presenti
     */
    @Transactional(readOnly = true)
    public List<String> getAllCategorie(){
        LinkedList<String> ret = new LinkedList<>();
        ret.add("musica");
        ret.add("filosofia");
        ret.add("politica");
        ret.add("scienza");
        ret.add("storia");
        ret.add("giochi");
        ret.add("libri");
        ret.add("cibo");
        ret.add("internet");
        ret.add("economia");
        ret.add("medicina");
        ret.add("scuola");
        ret.add("legge");
        ret.add("datasource");
        return ret;
    }

    /**
     * Questo metodo serve per caricare un articolo all'interno del DB
     * @param articolo
     * @return
     * @throws ArticoloGiaEsistenteException
     */
    @Transactional( readOnly = false)
    public BlogArticles addArticolo(BlogArticles articolo) throws ArticoloGiaEsistenteException {
        if(articoliRepository.existsBlogArticlesByTitleAndAuthorAndText(articolo.getTitle(), articolo.getAuthor(), articolo.getAuthor()))
            throw new ArticoloGiaEsistenteException();
        BlogArticles ret;
        articolo.setNumLike(0L);articolo.setNumDislike(0L);

        //inserisco l'articolo nella tabbella degli articoli
        ret = articoliRepository.save(articolo);

    //Prendo la categoria dell'articolo caricato
        String categoriaA = getCategoriaDiArticolo(articolo);
        CategoryIndex index = categoryIndexRepository.getIDArticoliPerCategoria(categoriaA);

        //aggiungo l'articolo nella tabella degli indici
        //Inserisco l'ID del nuovo articolo nel primo spazio vuoto nel DB presente

        //creo quindi il metodo che mi consente di inserire il codice dell'articolo.
        //Non essendo un metodo fisso, ma dipendente dal numero di articoli presenti
        //devo usare un espediente per eseguirlo
        java.lang.reflect.Method metodo;
        Object obj = new Object();

        try{
            //sposto tutto gli articoli avanti di una posizione
            for(int i = 0; i< index.getNumArticoli(); i++){
                //definisco il metodo per prendere gli articoli di ID i
                metodo = index.getClass().getMethod("getA" + i);
                //involo il metodo e prendo Lìl'ID
                Long Idart = (Long) metodo.invoke(index);
                //definisco il metodo per settere l'ID
                Long set = i + 1L;
                metodo = index.getClass().getMethod("setA" + set , Long.class);
                //invoco il metodo per settare l'ID
                metodo.invoke(index, Idart);

            }
            //dopo aver spostato tutti gli articoli, inserisco il nuovo ID in prima posizione

            metodo = index.getClass().getMethod("setA" + 0, Long.class);
            //eseguo il metodo sull'oggetto index, quindi aggiungo L'ID del nuovo articolo
            metodo.invoke(index, ret.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }

        //incremento adesso il contatore degli articoli presenti
        index.setNumArticoli(index.getNumArticoli() +1L);


        entityManager.refresh(ret);


        return  ret;

    }

    /**
     * Questo è un metodo di utiltà che dato un articolo
     * il metodo restituisce la categoria dell'articolo valutando gli attributi
     * asimmetrici con le informazion sulle categorie.
     * dall'utente
     * @param a
     * @return
     */
    private String getCategoriaDiArticolo (BlogArticles a){
        if (a.getAttualitÃ() != null && a.getAttualitÃ() == 1L) return "attualità";
        if (a.getScuola() != null && a.getScuola() == 1L) return "scuola";
        if (a.getMusica() != null && a.getMusica() == 1L) return "musica";
        if (a.getFilosofia() != null && a.getFilosofia() == 1L) return "filosofia";
        if (a.getPolitica() != null &&a.getPolitica() == 1L) return "politica";
        if (a.getScienza() != null && a.getScienza() == 1L) return "scienza";
        if (a.getStoria() != null && a.getStoria() == 1L) return "storia";
        if (a.getLegge() != null && a.getLegge() == 1L) return "legge";
        if (a.getGiochi() != null && a.getGiochi() == 1L) return "giochi";
        if (a.getLibri() != null && a.getLibri() == 1L) return "libri";
        if (a.getCibo() != null && a.getCibo() == 1L) return "cibo";
        if (a.getInternet() != null && a.getInternet() == 1L) return "internet";
        if (a.getEconomia() != null && a.getEconomia() == 1L) return "economia";
        if (a.getMedicina() != null && a.getMedicina() == 1L) return "medicina";
        return null;

    }

    /**
     * Aggiunge un like all'articolo indicato dall'idArticolo
     * @param idArticolo
     * @return
     * @throws ArticoloNonEsistenteException
     */
    @Transactional( readOnly = false)
    public BlogArticles addLikeAdArticolo (Long idArticolo) throws ArticoloNonEsistenteException{
        BlogArticles articolo;
        if(! articoliRepository.existsById(idArticolo)) throw new ArticoloNonEsistenteException();
        articolo = articoliRepository.getBlogArticlesById(idArticolo);
        articolo.setNumLike(articolo.getNumLike() +1);

        //entityManager.refresh(articolo);
        return articolo;

    }

    /**
     * Aggiunge un dislike all'articolo indicato dall'idArticolo
     * @param idArticolo
     * @return
     * @throws ArticoloNonEsistenteException
     */
    @Transactional(readOnly = false)
    public BlogArticles addDislikeAdArticolo (Long idArticolo) throws ArticoloNonEsistenteException{
        BlogArticles articolo;
        if(! articoliRepository.existsById(idArticolo)) throw new ArticoloNonEsistenteException();
        articolo = articoliRepository.getBlogArticlesById(idArticolo);
        articolo.setNumDislike(articolo.getNumDislike() +1);

        //entityManager.refresh(articolo);
        return articolo;

    }


    /**
     * Per valutazione il metodo aggiunge un token al Db se uno stesso token non è
     * già presente
     * @param token
     * @throws tokenGiàEsistenteException
     */
    @Transactional(readOnly = false)
    public void addToken(Token token) throws tokenGiàEsistenteException {
        if(tokenRepository.existsTokenByAccesAndIdarticolo(token.getAcces(), token.getIdarticolo())) throw new tokenGiàEsistenteException();
        tokenRepository.save(token);
    }
}
