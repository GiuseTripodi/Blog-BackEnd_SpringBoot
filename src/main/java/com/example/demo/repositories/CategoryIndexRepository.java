package com.example.demo.repositories;

import com.example.demo.entities.CategoryIndex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryIndexRepository extends JpaRepository<CategoryIndex, Integer> {

    @Query("SELECT c FROM CategoryIndex c where c.category= :categoria")
    CategoryIndex getIDArticoliPerCategoria(@Param("categoria") String categoria);

}
