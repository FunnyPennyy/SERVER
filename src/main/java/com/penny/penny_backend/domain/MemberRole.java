//package com.penny.penny_backend.domain;
//
//@Entity(name = "member_role")
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//public class MemberRole {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "member_id")
//    private Long memberId;
//
//    @Column(name = "role_id")
//    private Long roleId;
//
//    @ManyToOne
//    @JoinColumn(name = "role_id", insertable = false, updatable = false)
//    private Role role;
//
//    @ManyToOne
//    @JoinColumn(name = "member_id", insertable = false, updatable = false)
//    private Member member;
//}