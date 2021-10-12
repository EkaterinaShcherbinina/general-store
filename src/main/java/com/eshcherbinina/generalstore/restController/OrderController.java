package com.eshcherbinina.generalstore.restController;

import com.eshcherbinina.generalstore.dto.OrderDTO;
import com.eshcherbinina.generalstore.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/order")
public class OrderController {

    private IOrderService orderService;
    private MessageSource messageSource;

    @Autowired
    public OrderController(IOrderService orderService,
                           MessageSource messageSource) {
        this.orderService = orderService;
        this.messageSource = messageSource;
    }

    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public List<OrderDTO> getOrderHistory() {
        return orderService.getOrderHistory();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public OrderDTO getOrder(@PathVariable long id) {
        return orderService.getOrder(id);
    }

    @RequestMapping(value = "/cancel/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> cancelOrder(@PathVariable long id) {
        orderService.cancelOrder(id);
        return new ResponseEntity<>(messageSource.getMessage("api.response.order.cancellation.successful",
                null, Locale.ENGLISH), HttpStatus.OK);
    }
}
