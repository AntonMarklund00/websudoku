package com.sudoku.service;

import com.sudoku.helper.Constants;
import com.sudoku.helper.Randomizer;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class SudokuService implements Constants {

    private static int[][] board = new int[SIZE][SIZE];

    public void createBoard(int[][] incomingBoard){
        board = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = incomingBoard[i][j];
            }
        } 
    }
    
    public static int[][] readFromFile() {
        int[][] newBoard = new int[SIZE][SIZE];
        JSONParser parser = new JSONParser();
        try {
            JSONArray rowList = (JSONArray) parser.parse(
                                            new FileReader(BASE_PATH + "board_"+
                                            Randomizer.INSTANCE.getRandomValue(((Number) Files.list(Paths.get(BASE_PATH)).count()).intValue())+
                                            ".json"));

            for (int i = 0; i < rowList.size(); i++) {
                JSONArray obj = (JSONArray) rowList.get(i);
                for (int j = 0; j < obj.size(); j++) {
                    newBoard[i][j] = ((Number) obj.get(j)).intValue();     
                }
            }
            return newBoard;

        } catch (FileNotFoundException e) {e.printStackTrace();}
          catch (IOException e) {e.printStackTrace();}
          catch (ParseException e) {e.printStackTrace();}
        return null;        
    }

    private boolean checkIfCorrect(int row, int col, int number){
        return !isInRow(row, number)  &&  !isInCol(col, number)  &&  !isInBox(row, col, number);
    }

    private boolean isInRow(int row, int number) {
		for (int i = 0; i < SIZE; i++)
			if (board[row][i] == number)
				return true;
		
		return false;
	}
	
	private boolean isInCol(int col, int number) {
		for (int i = 0; i < SIZE; i++)
			if (board[i][col] == number)
				return true;
		
		return false;
	}
	
	private boolean isInBox(int row, int col, int number) {
		int r = row - row % 3;
		int c = col - col % 3;
		
		for (int i = r; i < r + 3; i++)
			for (int j = c; j < c + 3; j++)
				if (board[i][j] == number)
					return true;
		
		return false;
	}

    public boolean solver(){
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if(board[i][j] == EMPTY){
                    for (int number = 1; number <= SIZE; number++) {
                        if(checkIfCorrect(i, j, number)){
                            board[i][j] = number;

                            if(solver()){
                                return true;
                            }else{
                                board[i][j] = EMPTY;
                            }
                        } 
                    }
                    return false;          
                }
            }
        }
        return true;
    }

    public void display() {
		for (int i = 0; i < SIZE; i++) {
            String row = "";
			for (int j = 0; j < SIZE; j++) {
                row += "%-2d".formatted(board[i][j]);
			}
		}
	}
}
