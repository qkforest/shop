package com.example.shop.service;

import com.example.shop.common.ResponseEntity;
import com.example.shop.member.Member;
import com.example.shop.member.MemberRepository;
import com.example.shop.member.MemberRequest;
import com.example.shop.member.MemberResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public ResponseEntity<List<MemberResponse>> findAll() {
        List<Member> members = memberRepository.findAll();
        List<MemberResponse> memberResponses = members.stream()
                .map(m -> new MemberResponse(m.getEmail(), m.getName(), m.getPhone(), m.getFlag()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(HttpStatus.OK.value(), memberResponses, memberResponses.size());
    }

    public ResponseEntity<MemberResponse> create(MemberRequest request) {
        Member member = new Member(
                UUID.randomUUID(),
                request.email(),
                request.name(),
                request.password(),
                request.phone(),
                request.saltKey(),
                request.flag()
        );

        Member member1 = memberRepository.save(member);
        MemberResponse memberResponse = new MemberResponse(member1.getEmail(), member1.getName(), member1.getPhone(), member1.getFlag());
        return new ResponseEntity<>(HttpStatus.OK.value(), memberResponse, 1);
    }

    public ResponseEntity<MemberResponse> update(MemberRequest request, String id) {
        Member member = memberRepository.findById(UUID.fromString(id)).orElseThrow();

        member.setEmail(request.email());
        member.setName(request.name());
        member.setPassword(request.password());
        member.setPhone(request.phone());
        member.setSaltKey(request.saltKey());
        member.setFlag(request.flag());

        Member member1 = memberRepository.save(member);
        MemberResponse memberResponse = new MemberResponse(member1.getEmail(), member1.getName(), member1.getPhone(), member1.getFlag());
        return new ResponseEntity<>(HttpStatus.OK.value(), memberResponse, 1);
    }

    public ResponseEntity<?> delete(String id) {
        memberRepository.deleteById(UUID.fromString(id));
        return null;
    }
}
