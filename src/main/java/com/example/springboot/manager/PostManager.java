package com.example.springboot.manager;

import com.example.springboot.dto.AuthorDTO;
import com.example.springboot.dto.GeoDTO;
import com.example.springboot.dto.PostRequestDTO;
import com.example.springboot.dto.PostResponseDTO;
import com.example.springboot.entity.GeoEmbeddable;
import com.example.springboot.entity.PostEntity;
import com.example.springboot.entity.UserEntity;
import com.example.springboot.exception.ForbiddenException;
import com.example.springboot.exception.PostNotFoundException;
import com.example.springboot.repository.PostRepository;
import com.example.springboot.repository.UserRepository;
import com.example.springboot.security.Authentication;
import com.example.springboot.security.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Transactional
@RequiredArgsConstructor

public class PostManager {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final Function<PostEntity, PostResponseDTO> postEntityToPostResponseDTO = postEntity -> new PostResponseDTO(
            postEntity.getId(),
            new AuthorDTO(postEntity.getAuthor().getId(), postEntity.getAuthor().getLogin()),
            postEntity.getName(),
            postEntity.getContent(),
            postEntity.getTags(),

            Optional.ofNullable(postEntity.getGeo()).map(o -> new GeoDTO(o.getLat(), o.getLng())).orElse(null)
    );

    public List<PostResponseDTO> getAll(final Authentication authentication) {
        if (!authentication.hasRole(Roles.ROLE_ADMIN)) {
            throw new ForbiddenException();
        }
        return postRepository.findAll().stream()
                .map(postEntityToPostResponseDTO)
                .collect(Collectors.toList())
                ;
    }

    public PostResponseDTO getById(final long id, final Authentication authentication) {
        if (!authentication.hasRole(Roles.ROLE_ADMIN)) {
            throw new ForbiddenException();
        }
        return postRepository.findById(id).
                map(postEntityToPostResponseDTO)
                .orElseThrow(PostNotFoundException::new)
                ;
    }

    public PostResponseDTO create(final PostRequestDTO requestDTO, final Authentication authentication) {
        final UserEntity userEntity = userRepository.getReferenceById(authentication.getId());
        if (!authentication.hasRole(Roles.ROLE_ADMIN)) {
            throw new ForbiddenException();
        }
        final PostEntity postEntity = new PostEntity(
                0,
                userEntity,
                requestDTO.getName(),
                requestDTO.getContent(),
                requestDTO.getTags(),
                Optional.ofNullable(requestDTO.getGeo()).map(o -> new GeoEmbeddable(o.getLat(), o.getLng())).orElse(null)

        );
        final PostEntity savedEntity = postRepository.save(postEntity);
        return postEntityToPostResponseDTO.apply(savedEntity);
    }

    public PostResponseDTO update(final PostRequestDTO requestDTO, final Authentication authentication) {
        if (!authentication.hasRole(Roles.ROLE_ADMIN)) {
            throw new ForbiddenException();
        }
        final PostEntity postEntity = postRepository.getReferenceById(requestDTO.getId());
        postEntity.setName(requestDTO.getName());
        postEntity.setContent(requestDTO.getContent());
        postEntity.setTags(requestDTO.getTags());
        postEntity.setGeo(
                Optional.ofNullable(requestDTO.getGeo()).map(o -> new GeoEmbeddable(o.getLat(), o.getLng())).orElse(null)
        );
        return postEntityToPostResponseDTO.apply(postEntity);
    }

    public void deleteById(final long id, final Authentication authentication) {
        if (!authentication.hasRole(Roles.ROLE_ADMIN)) {
            throw new ForbiddenException();
        }
        postRepository.deleteById(id);
    }
}
