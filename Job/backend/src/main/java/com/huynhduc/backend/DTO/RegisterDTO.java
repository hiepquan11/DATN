package com.huynhduc.backend.DTO;

import java.util.List;

public class RegisterDTO {
    private String username;
    private String email;
    private String password;
    private List<GroupDTO> groups;

    public RegisterDTO() {
    }

    public RegisterDTO(String username, String email, String password, List<GroupDTO> groups) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.groups = groups;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<GroupDTO> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupDTO> groups) {
        this.groups = groups;
    }

    public static class GroupDTO {
        private String name;

        public GroupDTO() {
        }

        public GroupDTO(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
