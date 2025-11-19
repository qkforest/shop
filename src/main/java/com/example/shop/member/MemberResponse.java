package com.example.shop.member;

public record MemberResponse(String email,
                             String name,
                             String phone,
                             String flag) {
}
