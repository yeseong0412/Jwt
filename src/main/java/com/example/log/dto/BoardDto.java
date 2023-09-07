package com.example.log.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class BoardDto {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime regDate, modDate;
}
