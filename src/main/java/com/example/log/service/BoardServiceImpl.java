package com.example.log.service;

import com.example.log.dto.BoardDto;
import com.example.log.entity.Board;
import com.example.log.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    public Long register(BoardDto dto, String username) {
        Board board = dtoToEntity(dto);
        board.changeWriter(username);
        Board savedBoard = boardRepository.save(board);
        return savedBoard.getId();
    }

    @Override
    public BoardDto read(Long gno) {
        Optional<Board> result = boardRepository.findById(gno);
        return (result.isPresent()) ? entityToDTO(result.get()) : null;
    }

    @Override
    public void modify(BoardDto dto) {
        Optional<Board> result = boardRepository.findById(dto.getId());
        if (result.isPresent()) {
            Board board = result.get();
            board.changeTitle(dto.getTitle());
            board.changeContent(dto.getContent());
            boardRepository.save(board);
        }
    }

    @Override
    public void remove(Long id) {
        boardRepository.deleteById(id);
    }
}


