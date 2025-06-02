package com.cts.controller;

import com.cts.dto.CustomerDTO;
import com.cts.entity.Customer; // Keep import if your addCustomer still returns Customer initially from service
import com.cts.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // Import HttpStatus
import org.springframework.http.ResponseEntity; // Import ResponseEntity
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @PostMapping
    public ResponseEntity<CustomerDTO> addCustomer(@RequestBody CustomerDTO dto) { // Return ResponseEntity<CustomerDTO>
        return new ResponseEntity<>(service.addCustomer(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public CustomerDTO updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO dto) { // Change return type to CustomerDTO
        return service.updateCustomer(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Indicate successful deletion with no content
    public void deleteCustomer(@PathVariable Long id) {
        service.deleteCustomer(id);
    }

    @GetMapping("/{id}")
    public CustomerDTO getCustomer(@PathVariable Long id) { // Change return type to CustomerDTO
        return service.getCustomerById(id);
    }

    @GetMapping("/search")
    public List<CustomerDTO> search(@RequestParam String name) { // Change return type to List<CustomerDTO>
        return service.searchCustomers(name);
    }

    @GetMapping
    public List<CustomerDTO> all() { // Change return type to List<CustomerDTO>
        return service.getAllCustomers();
    }
}