package com.app.ecommerce.service;

import com.app.ecommerce.dto.AddressDTO;
import com.app.ecommerce.dto.UserRequest;
import com.app.ecommerce.dto.UserResponse;
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

    private void updtateUserFromRequest(User user, UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());

        if (userRequest.getAddress() != null) {

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
        updtateUserFromRequest(user, userRequest);
        userRepository.save(user);
    }

    public boolean updateUser(Long id, User updatedUser) {

        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setFirstName(updatedUser.getFirstName());
                    existingUser.setLastName(updatedUser.getLastName());
                    userRepository.save(existingUser);
                    return true;
                }).orElse(false);
    }

    private UserResponse mapToUserResponse(User user) {
        UserResponse response = new UserResponse();

        response.setId(String.valueOf(user.getId()));
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        user.setPhone(user.getPhone());
        user.setRole(user.getRole());

        if (user.getAddress() != null) {
            AddressDTO addressDTO = new AddressDTO();

            addressDTO.setStreet(addressDTO.getStreet());
            addressDTO.setCity(addressDTO.getCity());
            addressDTO.setState(addressDTO.getState());
            addressDTO.setCountry(addressDTO.getCountry());
            addressDTO.setZipcode(addressDTO.getZipcode());

            response.setAddress(addressDTO);
        }

        return response;
    }
}
