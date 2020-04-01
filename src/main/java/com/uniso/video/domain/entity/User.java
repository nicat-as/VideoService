package com.uniso.video.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "USER_INFO")
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "tenant_id", nullable = false)
    private Long tenantId;

    @Column(name = "video_id", nullable = false)
    private String videoId;

}
