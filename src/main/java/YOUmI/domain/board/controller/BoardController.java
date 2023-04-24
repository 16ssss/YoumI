package YOUmI.domain.board.controller;

import YOUmI.common.dto.ResponseDTO;
import YOUmI.domain.board.model.dto.BoardCreateRequestDTO;
import YOUmI.domain.board.service.BoardService;
import YOUmI.util.enumeration.FAILURE;
import YOUmI.util.enumeration.SUCCESS;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/boards")
@Tag(name = "게시판", description = "게시판 관련 COMMAND API")
public class BoardController {
    private final BoardService boardService;

    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE})
//    @ApiOperation(value = "", notes = "게시판을 등록합니다.")
    @Operation(summary = "게시판 생성", description = "어드민은 게시판을 생성할 수 있습니다.", method = "POST")
    public ResponseEntity<ResponseDTO> createBoard(@RequestBody BoardCreateRequestDTO requestDTO) {
        // 유효성 검사
        // boardName 유효성 검사 (null, 빈문자 체크) => validated로 바꾸기
        if (!StringUtils.hasLength(requestDTO.getBoardName()) || requestDTO.getBoardName().equals("")) {
            return ResponseEntity.badRequest().body(ResponseDTO.builder().result(FAILURE.ABSENT_REQUIRED_VALUE).build());
        }

        // 서비스 호출
        int createdNo = boardService.createBoard(requestDTO);

        if(createdNo < 0){
            return ResponseEntity.badRequest().body(ResponseDTO.builder().result(FAILURE.FAILURE).build());
        }

        // 응답
        ResponseDTO responseDTO = ResponseDTO.builder()
                .result(SUCCESS.SUCCESS)
                .resultObject(createdNo)
                .build();
        return ResponseEntity.ok().body(responseDTO);
    }

}
