package com.example.trip.controller;

import com.example.trip.dto.Board;
import com.example.trip.dto.Item;
import com.example.trip.service.BoardService;
import com.example.trip.service.ItemApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final ItemApiService itemApiService;
    private final BoardService boardService;

    @GetMapping("/")
    public String index(Model model){
        //API를 통해 각 카테고리별 데이터 가져오기
        List<Item> tripItems1 = itemApiService.searchItem("여행");
        List<Item> tripItems2 = itemApiService.searchItem("여행 메가세일");
        List<Item> tripItems3 = itemApiService.searchItem("여행 타임딜");
        List<Item> tripItems4 = itemApiService.searchItem("여행 홈쇼핑");
        //최근 게시글 4개 가져오기
        List<Board> recentBoard = boardService.getPosts(4);

        if(tripItems4.size() > 5){
            tripItems4 = tripItems4.subList(6,11);
        }else{
            tripItems4 = tripItems4;
        }

        model.addAttribute("tripItems1", tripItems1);
        model.addAttribute("tripItems2", tripItems2);
        model.addAttribute("tripItems3", tripItems3);
        model.addAttribute("tripItems4", tripItems4);
        model.addAttribute("recentBoard", recentBoard);
        return "index";
    }
}
