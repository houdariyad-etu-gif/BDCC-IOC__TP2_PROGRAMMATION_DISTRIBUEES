package net.riyad.bdccensetspringmvc.repository;


import net.riyad.bdccensetspringmvc.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
    //JpaRepository est une interface generique : entite qu on va gerer : Product | type de l id : Long

}
