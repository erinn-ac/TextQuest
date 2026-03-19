//package org.uob.a1;

public class Room {
    private String name;
    private String description;
    private char symbol;
    private Position position;
    private boolean visited; //holds false if the player has not visited this room yet, and true if they have
    private boolean[] nesw; //holds a boolean value for if the player can move north/east/south/west from this room
    private String[] neswMessages; //holds the messages the game will display when the player moves/attempts to move north/east/south/west from this room
    private String[][] features; //holds the names and descriptions of features in the room
    private int noFeatures; //holds the number of features in the room
    
    private boolean puzzleCompleted; //this is auto set to true for rooms with no puzzle
    private String puzzleItem; //this is the item that when "use itemName" is typed by the user in the specific room, the room's puzzle is solved.
    private String[] updatedNeswMessages; //this holds any the new version of any nesw messages that should be changed if the room's puzzle is completed
    private String winMessage;
    private String rewardItem; //this is the item that will be added to the room if the puzzle is compelted
    private boolean isReward; //This boolean holds true if at item is added to the room when a puzzle is completed, and false otherwise 
    
    public Inventory itemsInRoom; //the items that are in a room and can be taken, some of these are automatically in a room when the game begins, others would be added if a player drops an item. 
    
    
//constructor for rooms which are enterable - so therefore have the nesw and neswMessages attributes, but that do not have any features
    public Room(String name, String description, char symbol, Position position, boolean[] nesw, String[] neswMessages)
    {
        this.name = name;
        this.description = description;
        this.symbol = symbol;
        this.position = position;
        this.visited = false;
        this.nesw = nesw;
        this.neswMessages = neswMessages;
        this.noFeatures = 0;
        this.puzzleCompleted = true;
        this.puzzleItem = "";
        this.itemsInRoom = new Inventory();
        
    }

//constructor for rooms which are enterable - so therefore have the nesw and neswMessages attributes, and that also have features
       public Room(String name, String description, char symbol, Position position, boolean[] nesw, String[] neswMessages, String[][] features)
    {
        this.name = name;
        this.description = description;
        this.symbol = symbol;
        this.position = position;
        this.visited = false;
        this.nesw = nesw;
        this.neswMessages = neswMessages;
        this.features = features;
        this.noFeatures = features.length;
        this.puzzleCompleted = false;
        this.itemsInRoom = new Inventory();
        
    }

    //constructor for rooms which are enterable - so therefore have the nesw and neswMessages attributes, and that also have features and a puzzle (that doesn't have a 'reward')
    public Room(String name, String description, char symbol, Position position, boolean[] nesw, String[] neswMessages, String[][] features, String puzzleItem, String[] updatedNeswMessages, String winMessage)
    {
        this.name = name;
        this.description = description;
        this.symbol = symbol;
        this.position = position;
        this.visited = false;
        this.nesw = nesw;
        this.neswMessages = neswMessages;
        this.features = features;
        this.noFeatures = features.length;
        this.puzzleItem = puzzleItem;
        this.updatedNeswMessages = updatedNeswMessages;
        this.winMessage = winMessage;
        this.itemsInRoom = new Inventory();
        this.isReward = false;
    }

//constructor for rooms which are enterable - so therefore have the nesw and neswMessages attributes, and that also have features and a puzzle which 'drops' an item -> adds an item to the rooms inventory 
       public Room(String name, String description, char symbol, Position position, boolean[] nesw, String[] neswMessages, String[][] features, String puzzleItem, String[] updatedNeswMessages, String winMessage, String rewardItem)
    {
        this.name = name;
        this.description = description;
        this.symbol = symbol;
        this.position = position;
        this.visited = false;
        this.nesw = nesw;
        this.neswMessages = neswMessages;
        this.features = features;
        this.noFeatures = features.length;
        this.puzzleItem = puzzleItem;
        this.updatedNeswMessages = updatedNeswMessages;
        this.winMessage = winMessage;
        this.itemsInRoom = new Inventory();
        this.rewardItem = rewardItem;
        this.isReward = true;
    }

    //this constructor is the constructor outlined in the assignment requirements. 
     public Room(String name, String description, char symbol, Position position)
    {
        this.name = name;
        this.description = description;
        this.symbol = symbol;
        this.position = position;
        this.visited = false;
        this.nesw = new boolean[] {false, false, false, false};
        
    }

    public Room() //this constructor is for 'rooms' off the map, this is used to create filler objects for the 2D array in the initialiseRooms method of the Game class
        {
        this.name = "off map";
        this.description = "You cannot go this way.";
        this.symbol = '.';
        this.position = new Position(-1,-1);
        this.visited = false;
        this.nesw = new boolean[] {false, false, false, false};
        }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public char getSymbol()
    {
        return symbol;
    }

    public Position getPosition()
    {
        return position;
    }

    public boolean getVisited()
    {
        return visited;
    }

    public String getPuzzleItem()
    {
        return puzzleItem;
    }

    public boolean getPuzzleCompleted()
    {
        return puzzleCompleted;
    }

    public boolean visit()  //returns false if the room has been visited before, and true if it has not
    {
        boolean firstVisit = false;
        if(!visited)
        {
            firstVisit = true;
            visited = true;
            
        }
        return firstVisit;
    }

    public boolean canMove(char direction) //returns true is the player is allowed to move in the respective direction, and false otherwise
    {
        switch(direction)
            {
                case 'n':
                    return nesw[0];
                case 'e':
                    return nesw[1];
                case 's':
                    return nesw[2];
                case 'w':
                    return nesw[3];
                default:
                    return false;
            }
    }

    public String directionText(char direction)
    { //Returns the message that is displayed when the user tries to move in a given direction (this is used even if the player can't move in that direction)
        
        switch(direction)
            {
                case 'n':
                    return neswMessages[0];
                case 'e':
                    return neswMessages[1];
                case 's':
                    return neswMessages[2];
                case 'w':
                    return neswMessages[3];
                default:
                    return "You stay where you are"; //This should never by run due to validation checks, but if here in case of an error.
            }
    }

    public boolean hasFeature(String featureName)
    //This method returns true if the room contains a certain feature, and false otherwise
        {
            if(noFeatures>0)
            {
                for(int i=0;i<noFeatures;i++)
                    {
                        if(features[i][0].equals(featureName))
                           {
                            return true;
                           }
                    }
            }
            return false;
        }

    public String getFeatureDescription(String featureName)
        {
            //this returns the description of a feature - it does not check the feature exists as this is done by the hasFeature method
            for(int i=0;i<noFeatures;i++)
                {
                    if(features[i][0].equals(featureName))
                    {
                        return features[i][1];
                    }
                }
            return "";
        }

    public boolean checkPuzzleAttempt(String item)
    {
        return (!puzzleCompleted && (puzzleItem.equals(item)));
        //returns true if the player has successfully completed the puzzle, and false otherwise
    }


    
    public void puzzleCompleted() //this method is run when the room's puzzle has been completed, it updates the messages for certain directions/features in the room, and also the boolean array to true if the player can now travel in a new direction. 
    {
        System.out.println(winMessage);
        puzzleCompleted = true;

        //This loop updates the messages for the different directions and also the boolean value if necessary. 
        for(int i=0;i<4;i++) 
            {
                if(!updatedNeswMessages[i].equals("")) //"" means that message shouldn't be changed
                {
                    if((updatedNeswMessages[i].substring(0, 4)).equals("TRUE")) //if the new Messages begins with TRUE, that direction is updated to true -> meaning that the player can now travel in that direction
                    {
                        nesw[i] = true;
                        neswMessages[i] = updatedNeswMessages[i].substring(4);
                    }
                    else
                    {
                        neswMessages[i] = updatedNeswMessages[i]; //otherise the message is just updated and the boolean array not changed. 
                    }
                }
            }

        //This loop updates the features by swapping the description stored in index 2 of the array to index 1 and vice versa (as a description of a feature that should be displayed before the room's puzzle is completed is initially stored in index 1, and the description that should be displayed after in index 2)
        //the feature description got by the getFeatureDescription method is always the one in index 1 
        for(int i=0;i<noFeatures;i++)
            {
                if(features[i].length > 2)
                {
                    String temp = features[i][1];
                    features[i][1] = features[i][2];
                    features[i][2] = temp;
                }
            }

        if(isReward && !itemsInRoom.isFull()) //shouldn't be possible for the room to be full of items as users can only drop an item in room if there are two or more spaces left, and rooms 'drop' at most one item
        {
            itemsInRoom.addItem(rewardItem);
        }

    }


}