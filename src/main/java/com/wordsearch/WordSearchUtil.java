package com.wordsearch;

import com.wordsearch.DAWG.Node;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * Created by Nilay on 7/28/2017.
 */
public class WordSearchUtil {

    private char[][] board;
    private List<Match> matches;


    public WordSearchUtil(char[][] board) {
        this.board = board;
        matches = new ArrayList<>();
    }

    public WordSearchUtil() {
        this.board = new char[5][5];
        matches = new ArrayList<>();
    }

    public void solveWordSearch() {
        Node rootNode = WordSearch.getRootNode();
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                ArrayList<Match> tempMatches = new ArrayList<>();
                if(i > 1) {
                    Character c = board[i][j];
                    System.out.println(c + " at row: " + i + " and col: " + j);
                    tempMatches = searchUp(c, rootNode.getChildNode(board[i][j]),new Coordinate(i, j), new Coordinate(i, j), c.toString() , tempMatches);
                    if (tempMatches.size() != 0) {
                        matches.add(tempMatches.get(tempMatches.size() - 1));
                    }
                }
            }
        }
        System.out.println(matches.size());
        for(Match each: matches) {
            System.out.println(each.getWord());
        }
    }

    public ArrayList<Match> searchUp(Character c, Node node, Coordinate start, Coordinate curCoord, String word, ArrayList<Match> tempMatches) {
        if(node.isWord()) {
            tempMatches.add(new Match(start.getRow(), start.getCol(), curCoord.getRow(), curCoord.getCol(), word));
            System.out.println("Word added: " + word);
        }
        if (curCoord.getRow() == 0) {
            return tempMatches;
        }
        Character nextChar = board[curCoord.goUp()][curCoord.getCol()];
        System.out.println("NextCar Up: " + nextChar);
        Node nextNode = node.getChildNode(nextChar);
        if(nextNode == null || !nextNode.getValue().equals(nextChar)) {
            return tempMatches;
        }
        word = word + nextChar.toString();
//        word.concat(nextChar.toString());
        return searchUp(nextChar, nextNode, start, new Coordinate(curCoord.goUp(), curCoord.getCol()), word, tempMatches);
    }

    public void populateBoardRandom() {
        ArrayList<Character> alphabet = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            alphabet.add((char)((int)'a' + i));
        }
        Random rand = new Random();
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                board[i][j] = alphabet.get(rand.nextInt(alphabet.size()));
            }
        }
        board[3][0] = 'c';
        board[2][0] = 'a';
        board[1][0] = 'r';
        board[0][0] = 't';
        this.printBoard();
    }

    public void printBoard() {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void addMatch(int startRow, int startCol, int endRow, int endCol, String word) {
        matches.add(new Match(startRow, startCol,  endRow, endCol, word));
    }
}
