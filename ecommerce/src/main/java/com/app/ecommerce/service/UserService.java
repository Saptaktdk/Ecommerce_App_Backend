package com.app.ecommerce.service;

import com.app.ecommerce.dto.AddressDTO;
import com.app.ecommerce.dto.UserRequest;
import com.app.ecommerce.dto.UserResponse;
import com.app.ecommerce.model.Address;
import com.app.ecommerce.repository.UserRepository;
import com.app.ecommerce.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private UserResponse mapToUserResponse(User user) {
        UserResponse response = new UserResponse();

        response.setId(String.valueOf(user.getId()));
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());

        if (user.getAddress() != null) {
            AddressDTO addressDTO = new AddressDTO();

            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setZipcode(user.getAddress().getZipcode());

            response.setAddress(addressDTO);
        }

        return response;
    }

    private void updateUserFromRequest(User user, UserRequest userRequest) {
        user.setFirstName(Optional.ofNullable(userRequest.getFirstName()).orElse(user.getFirstName()));
        user.setLastName(Optional.ofNullable(userRequest.getLastName()).orElse(user.getLastName()));
        user.setEmail(Optional.ofNullable(userRequest.getEmail()).orElse(user.getEmail()));
        user.setPhone(Optional.ofNullable(userRequest.getPhone()).orElse(user.getPhone()));

        if (userRequest.getAddress() != null) {

            AddressDTO addressDTO = userRequest.getAddress();
            Address existingAddress = Optional.ofNullable(user.getAddress()).orElse(new Address());

            existingAddress.setStreet(Optional.ofNullable(addressDTO.getStreet()).orElse(existingAddress.getStreet()));
            existingAddress.setCity(Optional.ofNullable(addressDTO.getCity()).orElse(existingAddress.getCity()));
            existingAddress.setState(Optional.ofNullable(addressDTO.getState()).orElse(existingAddress.getState()));
            existingAddress.setCountry(Optional.ofNullable(addressDTO.getCountry()).orElse(existingAddress.getCountry()));
            existingAddress.setZipcode(Optional.ofNullable(addressDTO.getZipcode()).orElse(existingAddress.getZipcode()));

            user.setAddress(existingAddress);


        }
    }


    public List<UserResponse> fetchAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public Optional<UserResponse> fetchUser(Long id) {

        return userRepository.findById(id)
                .map(this::mapToUserResponse);
    }

    public void addUser(UserRequest userRequest) {
        User user = new User();
        updateUserFromRequest(user, userRequest);
        userRepository.save(user);
    }

    public boolean updateUser(Long id, UserRequest updatedUserRequest) {

        return userRepository.findById(id)
                .map(existingUser -> {
                    updateUserFromRequest(existingUser, updatedUserRequest);
                    userRepository.save(existingUser);
                    return true;
                }).orElse(false);
    }

}
