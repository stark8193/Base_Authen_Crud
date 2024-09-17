package com.devteria.identity_service.service;

import com.devteria.identity_service.dto.request.RoleRequest;
import com.devteria.identity_service.dto.response.RoleResponse;
import com.devteria.identity_service.exception.AppException;
import com.devteria.identity_service.exception.ErrorCode;
import com.devteria.identity_service.mapper.RoleMapper;
import com.devteria.identity_service.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;

    public RoleResponse create(RoleRequest request) {
        var role = roleMapper.toRole(request);
        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAll(Integer page, Integer size, String sort) {
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by("employeeName").ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by("employeeName").descending();
        }
        Pageable pageable = PageRequest.of(page, size, sortable);
        return roleRepository.findAll(pageable).stream().map(roleMapper::toRoleResponse).toList();
    }

    public RoleResponse getRole(String id) {
        return roleMapper.toRoleResponse(
                roleRepository.findById(id).orElseThrow());
    }

    public void delete(String role) {
        roleRepository.deleteById(role);
    }
}
