package com.data.filtro.api.restcontroller;

import com.data.filtro.exception.api.contact.CantSendContactRequestException;
import com.data.filtro.model.Contact;
import com.data.filtro.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/api/v1/contact")
@RequiredArgsConstructor
public class ContactAPIRestController {
    private final ContactService contactService;

    @PostMapping
    public ResponseEntity<String> addContact(@RequestParam("name") String name,
                                     @RequestParam("email") String email,
                                     @RequestParam("subject") String subject,
                                     @RequestParam("message") String message,
                                     Model model) {

        try {
            Contact contact = new Contact();
            contact.setName(name);
            contact.setEmail(email);
            contact.setSubject(subject);
            contact.setMessage(message);
            contactService.createContact(contact);
            return ResponseEntity.ok("Send contact successfully!");
        } catch (Exception ex){
            throw new CantSendContactRequestException();
        }
    }
}
