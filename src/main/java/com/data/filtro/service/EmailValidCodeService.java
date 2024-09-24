package com.data.filtro.service;

import com.data.filtro.model.Account;
import com.data.filtro.model.EmailValidCode;
import com.data.filtro.repository.EmailValidCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

//import java.util.Date;

@Service
@RequiredArgsConstructor
public class EmailValidCodeService {

    private final EmailValidCodeRepository emailValidCodeRepository;

    public void create(EmailValidCode emailValidCode){
        EmailValidCode newEmailValidCode = new EmailValidCode();
        newEmailValidCode.setEmail(emailValidCode.getEmail());
        newEmailValidCode.setValidCode(emailValidCode.getValidCode());
        emailValidCodeRepository.save(newEmailValidCode);

    }

    public EmailValidCode findByEmail(String email){
        return emailValidCodeRepository.findByEmail(email);
    }

    public void changeValidCode(EmailValidCode emailValidCode){
        EmailValidCode newEmailValidCode = findByEmail(emailValidCode.getEmail());
        newEmailValidCode.setValidCode(emailValidCode.getValidCode());
        emailValidCodeRepository.save(newEmailValidCode);
    }
}
