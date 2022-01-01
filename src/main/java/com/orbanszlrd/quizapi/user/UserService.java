package com.orbanszlrd.quizapi.user;

import com.orbanszlrd.quizapi.user.dto.InsertUserRequest;
import com.orbanszlrd.quizapi.user.dto.UserResponse;
import com.orbanszlrd.quizapi.user.dto.UpdateUserRequest;
import com.orbanszlrd.quizapi.user.error.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);

        return userOptional.map(AppUserDetails::new).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public List<UserResponse> findAll() {
        Type type = new TypeToken<List<UserResponse>>() {
        }.getType();

        return modelMapper.map(userRepository.findAll(), type);
    }

    public UserResponse add(InsertUserRequest insertUserRequest) {
        User user = modelMapper.map(insertUserRequest, User.class);
        user.setPassword(passwordEncoder.encode(insertUserRequest.getPassword()));
        user.setEnabled(true);
        userRepository.save(user);
        return modelMapper.map(user, UserResponse.class);
    }

    public UserResponse update(Long id, UpdateUserRequest updateUserRequest) {
        findById(id);

        User user = modelMapper.map(updateUserRequest, User.class);
        user.setId(id);

        userRepository.save(user);

        return modelMapper.map(user, UserResponse.class);
    }

    public UserResponse findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return modelMapper.map(user, UserResponse.class);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
