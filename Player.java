//package org.uob.a1;

//This class contains all objects/variables which are specific to the current game being played e.g. it contains an array of all the rooms, so they can be updated as they are visited, have items taken/added from them etc. 
//I have done this as I thought it would allow for, if my game was further developed, there to be multiple games a player could revisit, multiple players in a game etc. 
//It contains methods which are run when a user enters commands such as drop/take etc. 

public class Player
    {
        private String name;
        private final Position STARTING_POS = new Position(1,5);
        public Position currentPos;
        public Room[][] allRooms;
        public Score score;
        public Inventory inventory;
        public Map map;
        

        public Player(Room[][] allRooms, Score score, Inventory inventory, Map map)
        {
            currentPos = STARTING_POS;
            this.allRooms = allRooms;
            this.score = score;
            this.inventory = inventory;
            this.map = map;
            map.placeRoom(currentPos, getCurrentRoom().getSymbol());
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public String getName()
        {
            return name;
        }

        public Room getCurrentRoom()
        {
            return allRooms[currentPos.x][currentPos.y];
        }

        public void take(String item)
        {
            //adds the item to the player's inventory and removes it from the room
            //no validation is done as this is carried out in the Game class before this method is run, this goes for all the other methods as well.
            inventory.addItem(item);
            allRooms[currentPos.x][currentPos.y].itemsInRoom.removeItem(item);
            System.out.println("You pick up the "+item+".");
            if(item.equals("ring"))
               {
                System.out.println("\nCongratulations " +name+ "!! You have found the magic ring, you win the game!\nYou can continue to explore the map if you wish or type quit to end the game.");
               }
        }

        public void drop(String item)
        {
            //removes the item from the player's inventory and adds it to the room's inventory
            inventory.removeItem(item);
            allRooms[currentPos.x][currentPos.y].itemsInRoom.addItem(item);
            System.out.println("You have dropped your "+item+".");
            
        }

        public void use(String item)
        {
            //only possible if the item is the puzzle item for the room the player is in, and the puzzle has not been completed
            if(getCurrentRoom().checkPuzzleAttempt(item))
            {
                allRooms[currentPos.x][currentPos.y].puzzleCompleted();
                score.solvePuzzle();
                inventory.removeItem(item);
            }
        }

        public String look() 
        {
            return getCurrentRoom().getDescription();
        }

        public void look(String name, String[][] ALL_ITEMS) //this method is overloaded, it has a different output if an item/feature is passed in
        {
            Room currentRoom = getCurrentRoom();

            //checks if name is an item in the player's inventory first
            if(inventory.hasItem(name) != -1)
            {
                for(int i=0; i<ALL_ITEMS.length; i++)
                    {
                        if(ALL_ITEMS[i][0].equals(name))
                        {
                            System.out.println(ALL_ITEMS[i][1]); //if it is the description is printed 
                        }
                    }
            }
                //then if it is a feature of the current room
            else if(currentRoom.hasFeature(name))
            {
                System.out.println(currentRoom.getFeatureDescription(name)); //if it is, then the description is printed
            }
        }
        

        public boolean move(String directionString)
        {
            //this method returns true if the player is allowed to move that way, and false is that room is locked/off the map
            
            boolean ableToMove = false;
            char direction = directionString.charAt(0);
            
            ableToMove = (allRooms[currentPos.x][currentPos.y].canMove(direction));
            if(ableToMove)
            {
                System.out.println(allRooms[currentPos.x][currentPos.y].directionText(direction));
                switch(direction)
                    {
                        case 'n':
                            currentPos.y-=1;
                            break;
                        case 'e':
                            currentPos.x+=1;
                            break;
                        case 's':
                            currentPos.y+=1;
                            break;
                        case 'w':
                            currentPos.x-=1;
                            break;
                        default:
                            return false;
                    }
                
                if(allRooms[currentPos.x][currentPos.y].visit()) //if the player is able to visit, it checks if it is the player's first visit, and if it is, updates the score accordingly 
                {
                    score.visitRoom();
                    map.placeRoom(currentPos, allRooms[currentPos.x][currentPos.y].getSymbol());
                }
                return true;
            }
            System.out.println(allRooms[currentPos.x][currentPos.y].directionText(direction) + " You cannot go that way.");
            return false;
            
        }
    }