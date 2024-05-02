package com.sethu.blog.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @Email(message = "Invalid email format")
    @NotBlank
    private String email;
    private List<PostDTO> posts;
    private List<CommentDTO> comments;
    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$", message = "Invalid mobile number format")
    @NotBlank
    private String mobileNumber;
    @NotBlank
    private String role;
}
