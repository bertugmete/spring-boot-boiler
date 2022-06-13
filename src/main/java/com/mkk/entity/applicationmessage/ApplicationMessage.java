package com.mkk.entity.applicationmessage;

import com.mkk.enums.ApplicationMessageTypeEnum;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "APPLICATION_MESSAGE")
public class ApplicationMessage {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "CODE", nullable = false)
    private String code;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "MESSAGE", nullable = false)
    private String message;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private ApplicationMessageTypeEnum type;
}
