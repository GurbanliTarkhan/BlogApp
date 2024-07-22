package com.tarkhan.blogapp.entity;

import jakarta.persistence.*;
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

        private String reason;
        private String description;

        @ManyToOne
        @JoinColumn(name = "reported_by_id")
        private User reportedBy;

        @ManyToOne
        @JoinColumn(name = "post_id")
        private Post post;

        private LocalDateTime createdAt;
}
