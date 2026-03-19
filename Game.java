//package org.uob.a1;

import java.util.Scanner; 

public class Game 
{  
  

    public static void main(String args[]) {

        Room[][] roomLocations = initialiseRooms(); //This runs the intialiseRooms() method to generate a 2D array of Room objects. 
        
        final String[][] ALL_ITEMS = new String[][] {{"key", "A rusty old metal key."}, {"oar", "An old wooden oar."}, {"gold", "A pouch full of bright, shining gold."}, {"sword", "A shining silver sword, covered in jewels. It hums with power."}, {"tooth", "A dragon's tooth, it is bright, shining white and very sharp."}, {"ring", "A magic ring, it is bright glittering gold and you can feel it humming with power."}, {"cat", "You have found a rare cave cat! They are magic animals and very loyal."}, {"mushroom", "A small red mushroom covered in white dots."}}; //This 2D array contains a list of all items in the game and their descriptions (this is referenced when the user 'looks' at an item in their inventory) 
        
        final String INITIAL_TEXT = "\nWelcome! You are on a quest... to find adventure! (And a magic ring...) Explore the map to find puzzles to solve and heroic deeds to complete.\nType help to learn what different commands there are.\n\nYou are standing in a forest."; //This is the text displayed automatically when the game begins. 
        
        final String HELP_TEXT = "\nAll commands:\nmove (north/east/south/west) - this allows you to try to move in each respective direction.\nlook - this displays a description of the room/area you are in.\nlook (feature name) - this displays a description of a feature of a room you are in\nlook (item name) - this displays a description of an item in your inventory\ntake (item name) - this allows you to pick up an item from a room\nuse (item name) - this allows you to use an item\ndrop (item name) - this allows you to remove an item from your inventory and place it in a room (hint: unless there are too many items in the room)\napproach (character name) - this allows you to interact with some characters\nfight (creature name) - this allows you to fight a creature\ninventory - this allows you to view the items in your inventory (hint: you can only have a limited number)\nscore - this displays your current score\nmap - this displays the map of the world (hint: visiting more rooms unlocks more of the map)\nhelp - this displays all possible commands you can use\nquit - this ends the game and shows you your final score\n"; //This is the text displayed when a user types the help command. 
        
        final int INITIAL_SCORE = 0; //This is the initial score of the player.
        
        //These variables store the dimensions of the map. 
        final int MAP_SIZEX = 6; 
        final int MAP_SIZEY = 6;
        
        boolean gameOnGoing = true; //This variable is true until the player quits the game. 

        Player player = new Player(roomLocations, new Score(INITIAL_SCORE), new Inventory(), new Map(MAP_SIZEX,MAP_SIZEY)); //This creates a player object which also contains the 2D array of all the rooms objects, so they can be easily be referenced and updated along with a score, inventory and map object.
        //The player class contains methods such as move, look, user etc. which when a command has been checked to be valid, are ran to update all the relevant objects/variables accordingly. 

        //This allows the user to choose their name, in order to personalise the game slightly. 
        System.out.println("Enter your name:");
        player.setName(getUserInput()); //The method getUserInput() is run to get an input from the user, it returns a String. 
          
        System.out.println(INITIAL_TEXT);

        //The game runs a while loop until the user enters the quit command. On each iteration, a user input is taken, checked and then an approriate response given. 
        
        while(gameOnGoing)
            {
                String input = getUserInput();
                String[] output = parseUserInput(input); //This method splits the user input into a command and an argument, returned as an array containing two strings. If no argument has been given the argument (index 1 of the array) is set to "null". If the user enters too many words the returned command (index 0 of the array) is set as "invalid". 
                if(checkValidInput(output, player)) //This method checks the input is valid e.g. the command is a real command and the argument is a correct argument (For example, for the look command, the argument must be an item in the player's inventory or a feature in the room they're in.)
                {
                    if(output[1].equals("null")) //aka if the command does not have an argument e.g. the help or quit commands
                    {
                        switch(output[0]) //determines the (non-argument) command and runs the appropriate method/prints the appropriate text/changes the appropriate variable etc. 
                            {
                                case "look":
                                    System.out.println(player.look());
                                    break;
                                case "inventory":
                                    System.out.println("Items in inventory: "+player.inventory.displayInventory());
                                    break;
                                case "score":
                                    System.out.println("Score: "+ player.score.getScore());
                                    break;
                                case "map":
                                    System.out.println(player.map.display());
                                    break;
                                case "help":
                                    System.out.println(HELP_TEXT);
                                    break;
                                case "quit":
                                    System.out.println("\nGAME OVER\nFinal score: "+player.score.getScore());
                                    gameOnGoing = false; //ends the while loop by setting the gameOnGoing varible to false, thus ending the game
                                    break;
                                default:
                                    System.out.println("Not a valid command"); //this should never be run (due to the validation) but it is there in case of an error
                            }
                    }
                    else //runs if the command has an argument. I have used two seperate switch statements for commands with arguments and commands without arguments. Look can take or not take arguments, which is why it appears in both (note: the look method in the Player class is overloaded)
                    {
                        switch(output[0])
                            {
                                case "move":
                                    player.move(output[1]);
                                    break;
                                case "look":
                                    player.look(output[1], ALL_ITEMS);
                                    break;
                                case "use":
                                    player.use(output[1]);
                                    break;
                                case "drop":
                                    player.drop(output[1]);
                                    break;
                                case "take":
                                    player.take(output[1]);
                                    break;
                                case "approach":
                                    player.use(output[1]);
                                    break;
                                case "fight":
                                    fightDragon(player);
                                    break;
                                    
                                default:
                                    System.out.println("\nNot a valid command.");
                            }
                    }
                    
                }
                else
                {
                    System.out.println("\nNot a valid command."); //This is run if the command entered by the user has been found to be invalid.
                }
            }

        

    }


    //This method checks whether or not a user input is valid aka if it is a possible command and (if necessary) a possible argument for that command. 
    public static boolean checkValidInput(String[] input, Player player) 
    {
        String[] validSingleCommands = new String[] {"look", "inventory", "score", "map", "help", "quit"}; //an array of all possible commands without arguments
        String[] validCommandsWithArgs = new String[] {"move", "look", "use", "take", "drop", "approach", "fight"}; //an array of all possible commands with arguments

        boolean validCommand = false; //false unless the command has been determined to be a valid command
        boolean validArg = false; //false unless the argument is determined to be true for the given, valid, command
        boolean validInput = false; //set to true if the command and (where applicable) argument are valid
        
        boolean singleCommand = (input[1].equals("null")); //If the argument is "null" the command has no argument
        boolean commandWithArg = !(input[1].equals("null")); //Otherwise, the command does have an argument 

        //If the command has no argument it is just checked against an array of valid commands (that don't take arguments)
        //If it is in the array it is valid, and validCommand is set to true.
        if(singleCommand)
        {
            for(int i=0;i<validSingleCommands.length;i++) //This is done by looping through each element of the array.
                {
                    if(validSingleCommands[i].equals(input[0]))
                    {
                        validCommand = true;
                        break;
                    }
                }
        }
        //If the command has an argument, the command is checked, then the argument is checked based off the command 
        if(commandWithArg)
        {
            for(int i=0;i<validCommandsWithArgs.length;i++) //This loop checkes the command against an array of valid commands (which do take arguments)
                {
                    if(validCommandsWithArgs[i].equals(input[0]))
                    {
                        validCommand = true;
                        break;
                    }
                }
        }

        if(singleCommand && validCommand) //if the command is valid and it has no argument, the input is valid amd true is returned. 
        {
            validInput = true;
            return validInput;
        }
        else if(commandWithArg && validCommand) //if the command is valid and has an argument, the argument is checked within this else if statement
            //each argument is checked differently depending on the command
            //if the argument is valid, validArg is set to true
        {
            String command = input[0];
            String arg = input[1];

            if(command.equals("move"))
            {
                validArg = (arg.equals("north") || arg.equals("east") || arg.equals("south") || arg.equals("west"));
            }
            else if(command.equals("look"))
            {
                validArg = (player.inventory.hasItem(arg) != -1) || (player.getCurrentRoom().hasFeature(arg));
            }
            else if(command.equals("use"))
            {
                validArg = (player.inventory.hasItem(arg) != -1);
            }
            else if(command.equals("drop"))
            {
                validArg = (player.inventory.hasItem(arg) !=-1) && (player.getCurrentRoom().itemsInRoom.getCurrentNoItems() < 2);
            }
            else if(command.equals("take"))
            {
                validArg = (!player.inventory.isFull()) && (player.getCurrentRoom().itemsInRoom.hasItem(arg) != -1);
                if(player.inventory.isFull())
                {
                    System.out.println("\nYour inventory is full.");
                }
            }
            else if(command.equals("approach"))
            {
                validArg = (player.getCurrentRoom().getPuzzleItem().equals(arg));
            }
            else if(command.equals("fight"))
            {
                validArg = arg.equals("dragon") && player.getCurrentRoom().getName().equals("Dragon's lair") && (player.getCurrentRoom().getPuzzleCompleted() == false);
            }
            
            
            validInput = validCommand && validArg; //if the command and argument are valid the input is valid
            return validInput; 
        }
        

        return validInput; //returns false
    }


//This method uses the Scanner class to collect an String input from the user, then returns the String
    public static String getUserInput()
    {
        Scanner getInput = new Scanner(System.in);
        String userInput = getInput.nextLine();
        return userInput;
    }

    //this method takes the user's input, and turns it into a standard form
    //it returns the input as an array of two Strings - the command (which is set to "invalid" if the user entered too many words) and the argument (which is "null" is there was no argument)
    public static String[] parseUserInput(String input)
    {
        //the inputted String is made lower case and any whitespace from the start or end of the string is removed
        String stringToParse = input.toLowerCase();
        stringToParse = stringToParse.trim();
        
        int length = stringToParse.length();
        String[] output = new String[] {"invalid", "null"}; //default returned string
        int whiteSpaceIndex = -1; //this is used to determine the number of words entered by the user

        boolean valid = true; //a string is valid if it has two or one words 
        int count =0;
        for(int i=0;i<length;i++) //this loops through the string, counting the number of times whitespace appears (this does not count whitesapce at the start/end of the input as this has been removed)
            {
                if(stringToParse.charAt(i) == ' ')
                {
                    valid = (count == 0); //the string is valid if there is one or no instances of whitespace
                    whiteSpaceIndex = i; //this holds the index of the whitespace so that the two words can be split up into seperate Strings
                    count++;
                }
            }
        if(whiteSpaceIndex != -1 && valid) //if there two words this is run
        {
            output[0] = stringToParse.substring(0, whiteSpaceIndex);
            output[1] = stringToParse.substring(whiteSpaceIndex+1);
        }
        else if(whiteSpaceIndex == -1 && valid) //if there is one word this is run
        {
            output[0] = stringToParse;
        }
        else
        {
            output[0] = "invalid"; //if there are more than two words this is run
        }

        return output;
    }

    public static Room[][] initialiseRooms()
    {
        //This method contains the creation of all the room objects and returns a 2D array of the room objects 
        //Each room object is created using one of the constructor methods from the Room class
        
        //The multiple different constructor methods in the Room class allow for new rooms to be created very easily, as e.g. if you want a new Room with a puzzle all you need to do is use a certain contructor method where you pass in the item to be used, item to be 'given' (if applicable), the win message and any messages that should be updated when the puzzle is completed
        Room forest = new Room("Forest", "You are standing in a forest with sunlight pouring through the trees. It is full of the singing of birds, and the leaves are crunchy underfoot.", 'f', new Position(1,5), new boolean[] {true, false, false, false}, new String[] {"You walk out of the trees and into a meadow.","The growls of a bear are coming from that direction, best to avoid.","Behind you is your past, you don’t want to go back.","The trees are too densely packed"});

        Room island = new Room("Island", "You are standing on the shore of a small island in the middle of the lake. It is covered in yellow flowers, and what appears to be a chest...", 'i', new Position(2,5), new boolean[] {true, false, false, false}, new String[] {"You go back to the boat and row across the water, back to the lake's edge.", "There is a waterfall cascading down the side of a cliff.", "That way the water seems darker, creatures lurk below the surface.", "A forest rises up at the edge of the water, a bear sits at the edge, drinking from the lake."}, new String[][] {{"chest", "A large old metal chest covered in cobwebs. It is sturdy, and locked.", "An old metal chest, it is opened, and empty."}}, "key", new String[] {"","","",""}, "You have unlocked the chest, inside there is gold...", "gold");
        
        Room meadow = new Room("Meadow", "You are standing in a fresh grassy meadow, full of flowers and bees. The air smells sweet and you can see deer frolicking in the distance. Ahead you can see a castle surronded by a moat, luckily it appears the drawbridge is down.", 'm', new Position(1,4), new boolean[] {true, true, true, false}, new String[] {"You walk through the meadow towards the castle, till you reach the solider standing guard at the end of the drawbridge.", "You walk down to the lake’s edge.", "You walk down through the trees into the forest.", "There is a steep, unclimbable mountain – even for you."}, new String[][] {{"deer", "A mother doe wanders gracefully through the tall grass, while her fawn skips happily beside her."}});

        Room lake = new Room("Lake", "You are standing by the edge of a wide blue lake. The water glimmers in the sunlight and ducks swim happily on the surface. There is a boat beside you. In the middle of the lake there is a small island.",'l', new Position(2,4), new boolean[] {true, false, false, true}, new String[] {"You walk up to a cobbled path.", "The boat has no oars, and you cannot swim.", "The boat has no oars, and you cannot swim.", "You walk into the meadow."}, new String[][] {{"boat", "There is a boat sitting a the edge of the lake. It has no oars but it seems to be otherwise okay...", "There is a boat sitting on the edge of the lake with an old oar in it."}}, "oar", new String[] {"", "The lake turns into a waterfall, cascading over the edge of a tall cliff.", "TRUEYou row out across the lake to the island in the middle.", ""}, "You return the oar to the boat, you will be able to row in it now.");

        Room castleEntr = new Room("Castle entrance", "You are standing at the edge of the drawbridge which leads to the castle. The soldier glares at you.",'e', new Position(1,3), new boolean[] {false, true, true, false}, new String[] {"The soldier blocks your path.","You walk down a cobbled path.","You walk into the meadow.","There is a steep, unclimbable mountain – even for you."}, new String[][] {{"soldier", "A very angry looking soldier stares you down. You notice he is wearing very fine clothes and jewellery for a soldier. You wonder how he afford such extravagances with a guard's pay...", "The soldier looks at you somewhat sheepishly and stands aside to let you pass."}}, "gold", new String[] {"TRUEYou walk pass the soldier and through the castle gates, into the courtyard.", "", "", ""}, "You offer the gold to the soldier, he accepts. You will be able to enter the castle now.");
        

        Room cobbledPath = new Room("Cobbled path", "You are standing at the start of an old cobbled path. Shoots of grass and little wildflowers shoot up between stones.",'_', new Position(2,3), new boolean[] {true, true, true, true}, new String[] {"You follow the path to a market.","You follow the path to the edge of a tall cliff.","You follow the path to the edge of a lake.","You follow the path the the entrance of the castle."});

        Room cliffEdge = new Room("Cliff edge", "You are standing at the edge of a tall cliff. The drop is very far. Further along the lake flows over the side, causing a beautiful waterfall.",'|', new Position(3,3), new boolean[] {true, false, false, true}, new String[] {"You walk along a dirt path.","There is a long drop...","There is a long drop... And a waterfall.","You walk along a cobbled path."});

        Room courtyard = new Room("Courtyard", "You are standing in a bustling courtyard, most of the doors around you are heavily guarded - but not all of them...",'y', new Position(1,2), new boolean[] {true,false,true,false}, new String[] {"You see an open door, and start to walk down a long corridor","There is a large wooden door guarded by two well armed soldiers.", "You walk back out of the castle and along the drawbridge.","There is stone door guarded by a man larger than you've ever seen."});

        Room market = new Room("Market", "You are standing in a busy market. It is full of life, and noise, and overpriced bread.",'£', new Position(2,2), new boolean[] {true,true,true,false}, new String[] {"You walk past some fruit stalls onto a street.","You leave the market and walk along a dirt path.","You leave the market and walk along a cobbled path.","Behind the stalls there is the back walls of a street of houses."});

        Room dirtPath = new Room("Dirt path", "You are standing on an old dirt path, it doesn't look like it has been used much.",'_', new Position(3,2), new boolean[] {false,true,true,true}, new String[] {"There is a wall of dense bushes.","You reach the end of the dirt path and find the entrance to a cave, it looks dark and gloomy.","You follow the path to the edge of a cliff.","You follow the path to a market."});


        Room corridor = new Room("Corridor", "You are standing in a long corridor with a kitchen at one end and doors dotted along it. The carpet is covered in mud and the corners in cobwebs.",'c', new Position(1,1), new boolean[] {true,false,true,true}, new String[] {"You walk along the corridor to an unlocked door, inside is a bedroom.","There is nothing but a stone wall.","You follow the corridor back to the courtyard.", "You walk along the corridor to a kitchen, the door is wide open and people are rushing in and out.."});

        Room kitchen = new Room("Kitchen", "You are standing in a hectic kitchen. People rush around you, carrying plates of food. It smells of cooking meat.",'k', new Position(0,1), new boolean[] {false,true,false,false}, new String[] {"There is no exit that way, only a wall.","You walk out of the kitchen and along the corridor.","There is no exit that way, only a wall.","There is no exit that way, only a wall."});

        Room bedroom = new Room("Bedroom", "You are standing in a bedroom with a small window. The furniture looks old but someone has brightened up the room with a colourful rug, and paintings on the walls. Behind a tapestry, something gleams.",'b', new Position(1,0), new boolean[] {false,false,true,false}, new String[] {"There is no exit that way, only a wall.","There is no exit that way, only a wall.","You exit the bedroom and walk along the corridor.","There is no exit that way, only a wall."}, new String[][] {{"tapestry", "Behind the tapestry is a shining sword covered in jewels."}});
        bedroom.itemsInRoom.addItem("sword");

        Room street = new Room("Street", "You are standing on a street full of mismatched houses. There is a shop at one end, and one of the houses appears to have an open door...",'s', new Position(2,1), new boolean[] {true,true,true,false}, new String[] {"You walk into the house with an open door, inside there is a man.","You walk into the open shop.","You walk into a market.","There is a wall."});

        Room shop = new Room("Shop", "You are standing in a shop full of random items, on the side there is a note.",'£', new Position(3,1), new boolean[] {false,false,false,true}, new String[] {"There is wall.","There is wall.","There is wall.","You walk out of the shop onto the street."}, new String[][] {{"note", "There is a very old oar on the side with a note saying it is free to anyone who wants it. It looks like it is about to fall apart.", "There is a note saying an oar is free to anyone who wants it, but no oar..."}});
        shop.itemsInRoom.addItem("oar");

        Room house = new Room("House", "You area standing in a house jam-packed with random items. There is a man standing in the midst of the choas - he looks at you inquisitively.", 'h', new Position(2,0), new boolean[] {false, false, true, false}, new String[] {"There is a staircase, but the man is standing in front of it.", "There is wall.", "You walk out of the house and onto the street.", "There is wall."}, new String[][] {{"man", "The man is old, but his eyes are bright and full of life, you feel like you should approach him.", "The man smiles at you knowingly."}}, "man", new String[] {"","","",""}, "\nThe man asks you if you are looking for something.\nYou say a magic ring.\nThe man pauses and smiles, then gestures to a key.\n\"I don't think even I have one of those but have this for now...\"", "key");

          Room caveEntr = new Room("Cave entrance", "You are standing in the entrance of a cave, there are two gloomy tunnels either side of you. When you listen closely you think you can hear odd noises coming from within.", 'o', new Position(4,2), new boolean[] {true, false, true, true}, new String[] {"You walk along a gloomy tunnel and into... a well furnished room?", "There is the wall of the cave, convered in vines and weird markings.", "You walk along a pitch-black tunnel, which just seems to keep going and going...", "You walk out of the cave entrance and onto a dirt path."}, new String[][] {{"markings", "The markings are a red-ish brown colour and seem to be deliberate, but you are not quite sure what they are."}});

        Room passage = new Room("Passage", "You are standing in a pitch black passage, strange and unsettling noises come from deep within the cave.", 'p', new Position(4, 3), new boolean[] {true, false, true, false}, new String[] {"You walk back along the passage into the entrance of the cave.", "You walk along the passage and into a wide cavern.", "You walk along the passage and into a boiling hot cavern... As you enter a dragon turns to face you!", "There is only the face of the cave."});

        Room dragonLair = new Room("Dragon's lair", "You are standing in a wide cavern, well lit up by small fires dotted around it. In its centre sits a dragon...", 'd', new Position(4,4), new boolean[] {true, false, false, false}, new String[] {"You walk out of the dragon's lair and along the dark passage.", "There is only the face of the cave.","There is only the face of the cave.","There is only the face of the cave."}, new String[][] {{"dragon", "The dragon is covered in scales the colour of firey embers. It has long white fangs and massive black wings. It sits on a bed of golden coins and looks at you angrily.", "The dragon's body lies of the cave floor."}}, "N/A", new String[] {"","","",""}, "You have slain the dragon!", "tooth");
        
        Room dwarfHome = new Room("Dwarf's home", "You are standing in a well furnished cavern. There is a large red and gold rug on the floor, a spacious bed, a dining table and much more. Sat at a desk in the corner - is a dwarf!", 'w', new Position(4,1), new boolean[] {false, true, true, false}, new String[] {"There is only the wall of the cave - covered in pictures of (you assume) the dwarf's family.", "You open a door fitted into the cave and along a tunnel, into another cavern.", "You walk along a tunnel back to the cave's entrance.", "There is only the wall of the cave, covered in paintings and maps."}, new String[][] {{"dwarf", "The dwarf is sat at a little desk, pondering over old books and maps in a language you has never seen before.", "The dwarf is excitedly writing what seems to be a letter. He is scribling so furiously he is almost tearing through the parchment."}}, "dwarf", new String[] {"","","",""}, "\nYou ask the dwarf if he knows where to find a magic ring. He says there are rumours that a sacred shrine in one of the caverns holds one.\n\"But as my ma used to say... It can only be unlocked by the tooth of a dragon...\"");
        
        Room mushroomCavern = new Room("Mushroom cavern", "You are standing in a wide open cavern. It is covered in red and white dotted mushrooms. It is full of fireflies which give it a magic glow. There is a large pool of clear water in on one side, in which something swims... or someone...", 'O', new Position(5,1), new boolean[] {false, false, true, true}, new String[] {"Beyond the pool there is the only face of the cave, covered in vines.", "There is the only face of the cave.", "There is a tiny opening, you squeeze through and emerge into a new cavern...", "You walk back into the dwarf's home."}, new String[][] {{"water", "In the water swims a mermaid, she has beautiful blue-green scales and large sparkling eyes."}, {"pool", "In the water swims a mermaid, she has beautiful blue-green scales and large sparkling eyes."}}, "mermaid", new String[] {"TRUEThere is a little tunnel underwater you had not seen before, you swim through and emerge into...","","",""}, "You nod to the mermaid swimming in the pool, she smiles and gestures to you to look where she swims...");
        mushroomCavern.itemsInRoom.addItem("mushroom");

        Room shrineCavern = new Room("Shrine cavern", "You are standing in a dark cavern. It is dimly lit only by a stone statue in its centre.", 'S', new Position(5,2), new boolean[] {true, false, false, false}, new String[] {"You walk back to the cavern full of mushrooms.", "There is only the face of the cave, it is covered in strange but beautiful markings.","There is only the face of the cave.","There is only the face of the cave."}, new String[][] {{"statue", "The statue is carved out of a smooth stone with an almost blue tinge. It looks very old but also entirely undamaged by the elements. It resembles something vaguely human - but not quite..."}}, "tooth", new String[] {"", "", "", ""}, "The statue begins to move!\nIts hands (or are they claws?) unfurl to reveal... the magic ring!!", "ring");

        Room secretCavern = new Room("Secret cavern", "You are standing in a small pitch black space. You can feel a creature brush against your leg.", '?', new Position(0,5), new boolean[] {false, false, true, false}, new String[] {"???","???","You swim back through the tunnel and into the cavern full of mushrooms","???"}, new String[][] {{"creature", "The creature seems small and fluffy, you wonder what it could be... it seems to be purring...", "You appear to be alone in the cave."}}, "mushroom", new String[] {"","","",""}, "You offer a mushroom to the creature, it accepts... its a cave cat! They are magical beings, you didn't know any were left...","cat");
        
        

        Room offMap = new Room();


        //A 2D array of all the Room objects, corresponding to where they are on the map. This allows for all the rooms to be easily referenced and updated. 
        Room[][] allRooms = new Room[][] {{offMap,kitchen,offMap,offMap,offMap,offMap},
                                          {bedroom, corridor, courtyard, castleEntr, meadow, forest},
                                          {house, street, market, cobbledPath, lake, island},
                                          {offMap, shop, dirtPath, cliffEdge, offMap, offMap},
                                          {offMap, dwarfHome, caveEntr, passage, dragonLair, offMap},
                                          {secretCavern, mushroomCavern, shrineCavern, offMap, offMap, offMap}};
        return allRooms; //this method returns the 2D array of Room objects 
    }

    //This method is run when the player fights a dragon.
    //The user can use a different set of commands when fighting the dragon, so this method runs a smaller version of the while loop used in the main game. 
    
    public static void fightDragon(Player player)
    {
        System.out.println("\nYou are fighting the dragon! Attack it! Or escape...\nPress help for the commands you can use during battle...");
        int dragonHealth = 10000; 
        int playerHealth = 100;

        int damageDoneByDragon = 10;
        int damageDone = 10;
        boolean escape = false;

        //if the player has the sword item they do far more damage then without, it is effectively impossible for them to win the fight without having this item
        if(player.inventory.hasItem("sword") != -1)
        {
            damageDone = 5000;
            
        }
        else
        {
            System.out.println("Hint: you may want to come back when you have found a weapon..."); //This is a hint that the player needs to find the sword item
        }

        while(dragonHealth > 0 && !escape && playerHealth > 0) //the while loop runs until either the dragon or player's health is 0 or the player 'escapes' 
            {
                String[] input = parseUserInput(getUserInput()); //this works the same as the while loop in the main program
                if(!input[0].equals("invalid"))
                {
                    if(input[0].equals("attack") && input[1].equals("null") ) //If the player uses the 'attack' command, the player and the dragon both attack.
                    {
                        int playerAttackDamage= (int)(Math.random() * damageDone); //the damage done by the player and dragon is determined by generating a random number and multiplying it by their respective set damage  
                        int dragonAttackDamage= (int)(Math.random() * damageDoneByDragon);

                        //The damage done to either the player or dragon is either subtracted from their health, or if it is greater than their health, their health is set to zero (so their health cannot be negative)
                        playerHealth = (playerHealth - dragonAttackDamage > 0) ? playerHealth - dragonAttackDamage : 0;
                        dragonHealth = (dragonHealth - playerAttackDamage > 0) ? dragonHealth - playerAttackDamage : 0;
                        
                        
                        System.out.println("You and the dragon both attack!");
                    }
                    else if(input[0].equals("health") && input[1].equals("dragon"))
                    {
                        System.out.println("The dragon's health is "+dragonHealth+".");
                    }
                    else if(input[0].equals("health") && input[1].equals("null"))
                    {
                        System.out.println("Your health is "+playerHealth+".");
                    }
                    else if(input[0].equals("health") && input[1].equals("both"))
                    {
                        System.out.println("Your health is "+playerHealth+" and the dragon's health is "+dragonHealth+".");
                    }
                    else if((input[0].equals("escape") || input[0].equals("quit"))&& input[1].equals("null"))
                        //If the player uses the 'escape' command, the battle ends and the player is moved to the room north of the dragon's cavern - they can return to fight the dragon as many times as they want (until they kill it) and each time their health and dragon's health will be reset. 
                    {
                        System.out.println("\nYou escape!");
                        escape = true;
                        if(input[0].equals("quit"))
                        {
                            System.out.println("Enter quit again if you wish to end the game.");
                        }
                    }
                    else if(input[0].equals("help") && input[1].equals("null"))
                    {
                        System.out.println("\nCommands:\nattack - you attack the dragon, hint: you will do more damage if you have a weapon\nhealth - this will tell you your health\nhealth dragon - this will tell you the dragon's health\nhealth both - this will tell you your health and the dragon's health\nescape - this will allow you to escape and leave the dragon's lair, hint: this will reset the dragon's health as it will have time to heal");
                    }
                }
            }
        if(escape)
        {
            player.move("north");
        }
        else if(dragonHealth == 0)
        {
            player.getCurrentRoom().puzzleCompleted();
        }
        else
        {
            System.out.println("\nYour health is too low, you flee the battle to heal...");
            player.move("north");
        }
    }
    
}