package com.springboot.cms.mapper;


import com.springboot.cms.dto.MemberDto;
import com.springboot.cms.entity.MemberEntity;
import com.springboot.cms.entity.UserEntity;
import com.springboot.cms.exception.NotFoundUserException;
import com.springboot.cms.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Autowired
    public MemberMapper(ModelMapper modelMapper, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    public MemberDto mappedToDto(MemberEntity memberEntity) {
        Long userId = memberEntity.getUserEntity().getId();
        MemberDto memberDto = modelMapper.map(memberEntity, MemberDto.class);
        memberDto.setUserId(userId);
        return memberDto;
    }
    public MemberEntity mappedToEntity(MemberDto memberDto) {
        Long userId = memberDto.getUserId();
        MemberEntity memberEntity = modelMapper.map(memberDto, MemberEntity.class);

        if (userId != null) {
            UserEntity userEntity = userRepository.findById(userId)
                    .orElseThrow(() -> new NotFoundUserException(userId));
            memberEntity.setUserEntity(userEntity);
        } else {
            memberEntity.setUserEntity(null);
        }

        return memberEntity;
    }
}