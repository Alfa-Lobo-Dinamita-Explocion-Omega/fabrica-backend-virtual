package com.udea.edu.co.busquedadevuelos.backendvirtual.services;

import com.udea.edu.co.busquedadevuelos.backendvirtual.domain.Role;
import com.udea.edu.co.busquedadevuelos.backendvirtual.domain.User;
import com.udea.edu.co.busquedadevuelos.backendvirtual.entities.RoleData;
import com.udea.edu.co.busquedadevuelos.backendvirtual.entities.UserData;
import com.udea.edu.co.busquedadevuelos.backendvirtual.mappers.RoleMapper;
import com.udea.edu.co.busquedadevuelos.backendvirtual.mappers.UserMapper;
import com.udea.edu.co.busquedadevuelos.backendvirtual.repositories.RoleRepository;
import com.udea.edu.co.busquedadevuelos.backendvirtual.repositories.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Builder(toBuilder = true)
@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<UserData> userOptional = this.userRepository.findByEmail(email);
        return userOptional.map(this.userMapper::toEntity);

    }

    @Override
    public Optional<User> findById(String id) {
        Optional<UserData> userOptional = this.userRepository.findOneById(id);
        return userOptional.map(this.userMapper::toEntity);
    }

    @Override
    public User save(User entity) {
        UserData userToSave = this.userMapper.toData(entity);
        Set<RoleData> roles = entity.getAuthorities()
                .stream()
                .map((authority) -> {
                    System.out.println(authority.getAuthority());
                    return this.roleRepository.findByRole(Role.valueOf(authority.getAuthority()));
                })
                .map((roleData) -> roleData.orElseThrow(() -> new RuntimeException("Role not found.")))
                .collect(Collectors.toSet());
        userToSave.setRoles(roles);
        UserData saved = this.userRepository.save(userToSave);
        return this.userMapper.toEntity(saved);
    }
}
