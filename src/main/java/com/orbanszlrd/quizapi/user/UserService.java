package com.orbanszlrd.quizapi.user;

import com.orbanszlrd.quizapi.user.dto.AddUser;
import com.orbanszlrd.quizapi.user.dto.GetUser;
import com.orbanszlrd.quizapi.user.dto.UpdateUser;
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

    public List<GetUser> findAll() {
        Type type = new TypeToken<List<GetUser>>() {
        }.getType();

        return modelMapper.map(userRepository.findAll(), type);
    }

    public GetUser add(AddUser addUser) {
        User user = modelMapper.map(addUser, User.class);
        user.setPassword(passwordEncoder.encode(addUser.getPassword()));
        user.setEnabled(true);
        userRepository.save(user);
        return modelMapper.map(user, GetUser.class);
    }

    public GetUser update(Long id, UpdateUser updateUser) {
        findById(id);

        User user = modelMapper.map(updateUser, User.class);
        user.setId(id);

        userRepository.save(user);

        return modelMapper.map(user, GetUser.class);
    }

    public GetUser findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return modelMapper.map(user, GetUser.class);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
