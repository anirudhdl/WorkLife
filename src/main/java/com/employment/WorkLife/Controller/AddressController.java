package com.employment.WorkLife.Controller;

import com.employment.WorkLife.DTO.AddressDTO;
import com.employment.WorkLife.Models.Address;
import com.employment.WorkLife.Models.Departments;
import com.employment.WorkLife.Service.AddressService;
import com.employment.WorkLife.Service.DepartmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("address")
public class AddressController {
    @Autowired
    AddressService addressService;
    @GetMapping(path="allAddress", produces = "application/json")
    public List<AddressDTO> getAllAddress() {
        List<Address> addresses = addressService.getAllAddress();
        return addresses.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private AddressDTO convertToDTO(Address address) {
        AddressDTO dto = new AddressDTO();
        dto.setAddressId(address.getAddress_id());
        dto.setStreet(address.getStreet());
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setPostal_code(String.valueOf(address.getPostal_code()));
        // Map other fields as needed
        return dto;
    }

    @GetMapping("address_id/{address_id}")
    public ResponseEntity<?> getAddressById(@PathVariable Integer address_id) {
        Address address = addressService.getAddress_id(address_id);

        if (address == null) {
            String errorMessage = "Address not found with ID: " + address_id;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }

        return ResponseEntity.ok(convertToDTO(address));
    }

    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<?> handleNoSuchElementException(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }


    @DeleteMapping("address_id/{address_id}")
    public ResponseEntity<String> deleteByAddressId(@PathVariable Integer address_id) {
        Address address=addressService.getAddress_id(address_id);


        if(address!=null){
            String deletionMessage = addressService.deleteByAddress_id(address_id);
            if (deletionMessage != null && deletionMessage.equals("deleted")) {
                String message = "Address with ID " + address_id + " has been deleted";
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(message);
            }
            else {
                String errorMessage = "Address ID " + address_id+ "has been successfully deleted";
                return ResponseEntity.status(HttpStatus.OK).body(errorMessage);
            }
        }
        else {
            String errorMessage = "Address with ID " + address_id + " not found";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }

    }


    @PostMapping("add")
    public String addAddress(@RequestBody Address address){
        return addressService.addAddress(address);
    }
    @PutMapping("address_id/{address_id}")
    public ResponseEntity<String> updateAddress(@PathVariable Integer address_id, @RequestBody Address updatedAddress) {
        Address existingAddress = addressService.getAddress_id(address_id);

        if (existingAddress != null) {
            // Set the address_id of the updated address to match the path variable
            updatedAddress.setAddress_id(address_id);

            // Update the address using the service method
            addressService.updateAddress(updatedAddress);

            // Return a success message
            return ResponseEntity.ok("Address with ID " + address_id + " has been updated");
        } else {
            // Return a not found message if the address with the given ID doesn't exist
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Address with ID " + address_id + " not found");
        }
    }
}
