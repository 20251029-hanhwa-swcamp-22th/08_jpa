package com.ohgiraffers.mapping.section03.compositekey.subsection01.embeddedid;

import com.ohgiraffers.mapping.section03.compositekey.subsection02.idclass.Cart;
import com.ohgiraffers.mapping.section03.compositekey.subsection02.idclass.CartDTO;
import com.ohgiraffers.mapping.section03.compositekey.subsection02.idclass.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartRegistService {

    private CartRepository cartRepository;

    public CartRegistService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Transactional
    public void addItemToCart(CartDTO cartDTO) {
        Cart cart = new Cart(
                cartDTO.getCartOwnerMemberNo(),
                cartDTO.getAddedBookNo(),
                cartDTO.getQuantity()
        );

        cartRepository.save(cart);
    }
}
