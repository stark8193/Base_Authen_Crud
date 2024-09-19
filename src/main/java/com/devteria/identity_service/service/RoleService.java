package com.devteria.identity_service.service;

import com.devteria.identity_service.dto.ApiQueryResponse;
import com.devteria.identity_service.dto.request.RoleRequest;
import com.devteria.identity_service.dto.response.RoleResponse;
import com.devteria.identity_service.dto.response.UserResponse;
import com.devteria.identity_service.entity.Role;
import com.devteria.identity_service.entity.User;
import com.devteria.identity_service.exception.AppException;
import com.devteria.identity_service.exception.ErrorCode;
import com.devteria.identity_service.mapper.RoleMapper;
import com.devteria.identity_service.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<String, Object> getAllRolesWithPage(Integer page, Integer size, String sort) {
        Sort sortable = null;
        if (sort.equalsIgnoreCase("ASC")) {
            sortable = Sort.by("roleName").ascending();
        } else if (sort.equalsIgnoreCase("DESC")) {
            sortable = Sort.by("roleName").descending();
        }

        Pageable pageable = PageRequest.of(page, size, sortable);

        Page<Role> rolePage = roleRepository.findAll(pageable);

        List<RoleResponse> roles = rolePage.getContent()
                .stream()
                .map(roleMapper::toRoleResponse)
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("data", roles);
        response.put("totalItems", rolePage.getTotalElements());
        response.put("totalPages", rolePage.getTotalPages());
        response.put("currentPage", rolePage.getNumber());

        return response;
    }

    public RoleResponse getRole(String id) {
        return roleMapper.toRoleResponse(
                roleRepository.findById(id).orElseThrow());
    }
    public List<ApiQueryResponse> query(){
        List<ApiQueryResponse> list = roleRepository.findAll().stream()
                .map(role -> ApiQueryResponse.builder()
                        .id(role.getId())
                        .value(role.getRoleName())
                        .build())
                .toList();
        return list;
    }
    public void delete(String role) {
        roleRepository.deleteById(role);
    }
}
