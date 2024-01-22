package com.example.shopping.service;

import com.example.shopping.model.dto.CreditCardForm;
import com.example.shopping.model.entity.CreditCardEntity;
import com.example.shopping.model.entity.UserEntity;
import com.example.shopping.repository.CreditCardRepository;
import com.example.shopping.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CreditCardService {
    private final CreditCardRepository creditCardRepository;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CreditCardService(CreditCardRepository creditCardRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.creditCardRepository = creditCardRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void addCreditCard(CreditCardForm creditCardForm, String email) {
        UserEntity user = this.userRepository.findByEmail(email).orElse(null);

        CreditCardEntity card = new CreditCardEntity();
        card.setCardNumber(creditCardForm.getCardNumber().replace(" ", ""))
                .setCvcCode(passwordEncoder.encode(creditCardForm.getCvcCode()))
                .setUser(user)
                .setOwnerName(creditCardForm.getOwnerName())
                .setExpDate(LocalDate.of(creditCardForm.getYear(), Integer.parseInt(creditCardForm.getMonth()), 1));

        this.creditCardRepository.save(card);
    }
}
