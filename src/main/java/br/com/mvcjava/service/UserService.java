package br.com.mvcjava.service;

import br.com.mvcjava.DTO.UserDTO;
import br.com.mvcjava.model.UserModel;
import br.com.mvcjava.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Iterable<UserModel> getUsers() {
        log.info("getUsers");
        return userRepository.findAll();
    }

    public UserModel getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> {
            log.error("User not found with id {}", id);
            return new RuntimeException("User not found with id " + id);
        });
    }

    public UserModel create(UserDTO userDTO) {
        UserModel userModel = UserModel.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .build();
        return userRepository.save(userModel);
    }

    public UserModel update(Long id, UserDTO userDTO) {
        UserModel userModel = userRepository.findById(id).orElseThrow(() -> {
            log.error("User not found with id {}", id);
            return new RuntimeException("User not found with id " + id);
        });
        userModel.setName(userDTO.getName());
        userModel.setEmail(userDTO.getEmail());
        return userRepository.save(userModel);
    }

    public UserModel delete(Long id) {
        UserModel userModel = userRepository.findById(id).orElseThrow(() -> {
            log.error("User not found with id {}", id);
            return new RuntimeException("User not found with id " + id);
        });
        userRepository.delete(userModel);
        return userModel;
    }
}

