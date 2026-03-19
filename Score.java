//package org.uob.a1;

public class Score {
    private int startingScore;
    private double currentScore;
    private int roomsVisited;
    private int puzzlesSolved;
    private final int PUZZLE_VALUE = 10; 
    
    public Score(int startingScore)
    {
        this.startingScore = startingScore;
        currentScore = startingScore;
        roomsVisited = 0;
        puzzlesSolved = 0;

    }

    public void visitRoom()
    {
        roomsVisited+=1;
    }

    public void solvePuzzle()
    {
        puzzlesSolved+=1;
        
    }

    public double getScore()
    {
        currentScore = startingScore - roomsVisited + (puzzlesSolved * PUZZLE_VALUE);
        return currentScore;
    }
    
        
}