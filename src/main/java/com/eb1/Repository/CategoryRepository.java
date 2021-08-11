package com.eb1.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eb1.Model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

}
