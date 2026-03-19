//package org.uob.a1;

public class Map {
    private int width;
    private int height;
    private char[][] mapArray;
    final private char EMPTY = '.';
    private Room[][] roomLocRef; //array of room objects

    public Map(int width, int height)
    {
        this.height = height;
        this.width = width;

        //this nested loop iterates over every element of the mapArray, setting every element to an intial value of EMPTY (".")
        mapArray = new char[width][height];
        for(int i=0;i<height;i++)
            {
            for(int j=0;j<width;j++)
                {
                mapArray[i][j] = EMPTY;
                }
            }
    }

    public void placeRoom(Position pos, char symbol)
    {
         mapArray[pos.x][pos.y] = symbol;
       
    }

    public void setUpMap(Room[][] roomLocRef)
    {
        this.roomLocRef = roomLocRef;
    }

    public String display() // as of 28-10-25 -> only method that doesn't pass tests
    {
        //this nested loop iterates over every element of the array, printing it out 
        
        //ask if map should also be displayed to screen by this method
        String returnMap = "";
        for(int i=0; i<height; i++)
            {
            
            for(int j=0; j<width; j++)
                {
                returnMap+=mapArray[j][i];
                }
            returnMap+="\n";
            }
         return returnMap;
            
        
    }

}