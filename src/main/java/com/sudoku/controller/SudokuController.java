package com.sudoku.controller;

import com.sudoku.dao.Board;
import com.sudoku.service.SudokuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SudokuController {

    @Autowired
    SudokuService sudokuService;

    @GetMapping("/get/initial/board")
    public int[][] getBoard(){
        return sudokuService.readFromFile();
    }

    @PostMapping("/get/solved/board")
    public int[][] solveBoard(@RequestBody int[][] board){
        return sudokuService.solveBoard(board);
    }
}
