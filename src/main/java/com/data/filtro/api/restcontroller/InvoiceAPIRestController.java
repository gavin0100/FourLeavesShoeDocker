package com.data.filtro.api.restcontroller;

import com.data.filtro.Util.ExportPdf;
import com.data.filtro.exception.api.invoice.CantExportFilePDFInvoiceException;
import com.data.filtro.exception.api.invoice.CantFindInvoiceWithOrderIdException;
import com.data.filtro.exception.api.user.UserNotFoundOrAuthorizeException;
import com.data.filtro.model.Order;
import com.data.filtro.model.OrderDetail;
import com.data.filtro.model.User;
import com.data.filtro.service.CartService;
import com.data.filtro.service.InvoiceService;
import com.data.filtro.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/api/v1/invoice")
public class InvoiceAPIRestController {
    private final CartService cartService;

    private final OrderService orderService;

    private final InvoiceService invoiceService;

    @GetMapping("/{orderId}")
    public ResponseEntity<?> show(@PathVariable("orderId") long orderId) {
        try {
            User user = null;
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
                user = (User) authentication.getPrincipal();
            }
            if (user == null) {
                throw new UserNotFoundOrAuthorizeException();
            }
            Order order = orderService.getOrderById(orderId);
            List<OrderDetail> orderDetailList = order.getOrderDetails();
            int check = orderService.checkOrderStatusById(orderId);

            Map<String, Object> response = new HashMap<>();
            response.put("user", user);
            response.put("order", order);
            response.put("orderDetailList", orderDetailList);
            response.put("check", check);
            response.put("orderId", orderId);

            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            throw new CantFindInvoiceWithOrderIdException(orderId);
        }
    }

    @GetMapping(value = "/{orderId}/exportpdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> showOrderPdf(@PathVariable("orderId") long orderId) {
        try {
            Order order = orderService.getOrderById(orderId);
            List<OrderDetail> orderDetailList = order.getOrderDetails();

            ByteArrayInputStream bis = ExportPdf.employeesReport(order, orderDetailList);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment;filename=invoice.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(bis));
        } catch (Exception ex){
            throw new CantExportFilePDFInvoiceException(orderId);
        }

    }
}
