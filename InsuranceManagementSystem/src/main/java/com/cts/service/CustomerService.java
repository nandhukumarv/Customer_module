package com.cts.service;

import com.cts.dto.CustomerDTO;
import com.cts.entity.Customer;
import com.cts.exception.CustomerNotFoundException;
import com.cts.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors; // Import Collectors

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    public CustomerDTO addCustomer(CustomerDTO dto) {
        Customer c = new Customer();
        c.setName(dto.getName());
        c.setEmail(dto.getEmail());
        c.setPhone(dto.getPhone());
        c.setAddress(dto.getAddress());
        repository.save(c);
        return convertToDto(c);
    }

    public CustomerDTO updateCustomer(Long id, CustomerDTO dto) { // Change return type to CustomerDTO
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with ID " + id + " not found"));
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        customer.setAddress(dto.getAddress());
        Customer updatedCustomer = repository.save(customer);
        return convertToDto(updatedCustomer); // Convert the updated entity back to DTO
    }

    public void deleteCustomer(Long id) {
        if (!repository.existsById(id)) {
            throw new CustomerNotFoundException("Customer with ID " + id + " not found for deletion");
        }
        repository.deleteById(id);
    }

    public CustomerDTO getCustomerById(Long id) { // Change return type to CustomerDTO
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with ID " + id + " not found"));
        return convertToDto(customer); // Convert entity to DTO
    }

    public List<CustomerDTO> searchCustomers(String name) { // Change return type to List<CustomerDTO>
        return repository.findByNameContainingIgnoreCase(name).stream()
                .map(this::convertToDto) // Use method reference for conversion
                .collect(Collectors.toList());
    }

    public List<CustomerDTO> getAllCustomers() { // Change return type to List<CustomerDTO>
        return repository.findAll().stream()
                .map(this::convertToDto) // Use method reference for conversion
                .collect(Collectors.toList());
    }

    // Helper method to convert Customer entity to CustomerDTO
    private CustomerDTO convertToDto(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        dto.setAddress(customer.getAddress());
        // Note: customerId is usually not part of CustomerDTO, but if you want to expose it, add it to DTO.
        // If customerId is needed, consider creating a CustomerResponseDTO.
        return dto;
    }
}