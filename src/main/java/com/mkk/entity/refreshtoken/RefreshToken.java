package com.mkk.entity.refreshtoken;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mkk.entity.user.MkkUser;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "REFRESH_TOKEN")
public class RefreshToken {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MkkUser user;

    @Column(name = "TOKEN", nullable = false)
    private String token;

    @Column(name = "EXPIRE_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expireDate;
}
