package com.example.qnaboard.member.entity;

import com.example.qnaboard.board.entity.Answer;
import com.example.qnaboard.board.entity.Question;
import com.example.qnaboard.order.entity.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false, length = 5)
    private String name;

    @Column(unique = true, nullable = false, updatable = false)
    private String email;

    @Column(nullable = false, length = 20)
    private String password;

    @Column(unique = true, nullable = false, length = 13)
    private String phone;

    @Enumerated(value = EnumType.STRING)
    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> roles = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    @OneToOne
    private Stamp stamp;

    @OneToMany(mappedBy = "member")
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Answer> answers = new ArrayList<>();

    public void addOrders(Order order) {
        this.orders.add(order);
        if(order.getMember() != this) {
            order.setMember(this);
        }
    }

    public void setStamp(Stamp stamp) {
        this.stamp = stamp;
        if(stamp.getMember() != this) {
            stamp.setMember(this);
        }
    }


    public enum MemberStatus {
        MEMBER_ACTIVE("활동중"),
        MEMBER_SLEEP("휴면 상태"),
        MEMBER_QUIT("탈퇴 상태");

        @Getter
        private String status;

        MemberStatus(String status) {
            this.status = status;
        }
    }
}
