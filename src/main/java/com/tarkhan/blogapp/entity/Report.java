package com.tarkhan.blogapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "reports")
public class Report {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotBlank(message = "Reason cannot be blank.")
        @Column(nullable = false)
        private String reason;

        @NotBlank(message = "Description cannot be blank.")
        @Column(nullable = false)
        private String description;

        @ManyToOne
        @JoinColumn(name = "reported_by_id", nullable = false)
        @NotNull(message = "Reported by user cannot be null.")
        private User reportedBy;

        @ManyToOne
        @JoinColumn(name = "post_id", nullable = false)
        @NotNull(message = "Post cannot be null.")
        private Post post;

        @Column(nullable = false)
        private LocalDateTime createdAt;
}
