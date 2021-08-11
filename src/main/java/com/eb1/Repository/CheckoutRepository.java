package com.eb1.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eb1.Model.CheckoutCart;


@Repository
public interface CheckoutRepository  extends JpaRepository<CheckoutCart, Long> {
	@Query("Select checkCart FROM CheckoutCart checkCart WHERE checkCart.user_id=:user_id")
	List<CheckoutCart> getCartByUserId(Long user_id);
}
