package com.library.management.config;

import com.library.management.entity.User;
import com.library.management.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    private final UserRepository userRepository;

    public CurrentUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getCurrentUser() {
        Authentication authentication = getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new AccessDeniedException("Authenticated user was not found: " + username);
        }

        return user;
    }

    public String getCurrentUsername() {
        return getAuthentication().getName();
    }

    public String getLoggedInUsername() {
        return getCurrentUsername();
    }

    public boolean isAdmin() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch("ROLE_ADMIN"::equals);
    }

    public void checkUserAccess(Long requestedUserId) {
        if (requestedUserId == null) {
            throw new AccessDeniedException("User ID is required");
        }

        if (isAdmin()) {
            return;
        }

        User currentUser = getCurrentUser();

        if (currentUser.getId() == null || !currentUser.getId().equals(requestedUserId)) {
            throw new AccessDeniedException("You cannot access another user's reservations");
        }
    }

    private Authentication getAuthentication() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication.getPrincipal() == null
                || "anonymousUser".equals(String.valueOf(authentication.getPrincipal()))) {
            throw new AccessDeniedException("User is not authenticated");
        }

        return authentication;
    }
}