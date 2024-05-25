package com.udea.edu.co.busquedadevuelos.backendvirtual.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class UserData {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "VARCHAR(255)")
    private String id;

    @Positive
    @Column(
            name = "identification",
            unique = true,
            updatable = false,
            nullable = false
    )
    private String identification;

    @NotBlank
    @Column(
            name = "email",
            unique = true,
            updatable = false,
            length = 50,
            nullable = false
    )
    private String email;

    @NotBlank
    @Column(
            name = "complete_name",
            length = 50,
            nullable = false
    )
    private String name;

    @NotBlank
    @Column(
            name = "password",
            length = 300,
            nullable = false
    )
    private String password;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "active",
    columnDefinition = "boolean default true"
    )
    private Boolean active;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "detail_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleData> roles;
}
