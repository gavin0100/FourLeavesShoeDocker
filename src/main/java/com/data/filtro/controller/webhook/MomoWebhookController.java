package com.data.filtro.controller.webhook;


import com.data.filtro.service.MomoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/momo/webhook")
@RequiredArgsConstructor
@Slf4j
public class MomoWebhookController {
    private final MomoService momoService;
    private final Logger logger = LoggerFactory.getLogger(MomoWebhookController.class);

    @PostMapping("/ipn")
    public ResponseEntity<String> momoWebhook(@RequestBody String request){
        momoService.processIPN(request);
        return ResponseEntity.noContent().build();
    }


}
