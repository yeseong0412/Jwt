package com.example.log.service;

import com.example.log.dto.BoardDto;
import com.example.log.entity.Board;

public interface BoardService {
    // C
    Long register(BoardDto dto);
    // R
    BoardDto read(Long gno);
    // U
    void modify(BoardDto dto);
    // D
    void remove(Long gno);

    default Board dtoToEntity(BoardDto dto) {
        return Board.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
    }

    default BoardDto entityToDTO(Board entity) {
        return BoardDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();
    }

}

