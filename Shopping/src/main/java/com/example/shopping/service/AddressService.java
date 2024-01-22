package com.example.shopping.service;

import com.example.shopping.model.dto.AddressFormDto;
import com.example.shopping.model.entity.AddressEntity;
import com.example.shopping.model.entity.UserEntity;
import com.example.shopping.repository.AddressRepository;
import com.example.shopping.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public AddressService(AddressRepository addressRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public void addAddress(AddressFormDto addressDto, String email){
        AddressEntity address = modelMapper.map(addressDto, AddressEntity.class);
        UserEntity user = this.userRepository.findByEmail(email).get();

        address.setUser(user);

        this.addressRepository.save(address);
    }
}
