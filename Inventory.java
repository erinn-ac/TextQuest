//package org.uob.a1;

public class Inventory {
    final int MAX_ITEMS = 3; //I was told that this was allowed to be altered from 10
    private int currentNoItems;
    private String[] itemsArray;

    public Inventory()
    {
        currentNoItems = 0;

        itemsArray = new String[MAX_ITEMS];
        for(int i=0; i<MAX_ITEMS; i++)
            {
            itemsArray[i] = "";
            }
    }

    public int getCurrentNoItems()
    {
        return currentNoItems;
    }

    public void addItem(String item)
    {
        if(currentNoItems < MAX_ITEMS)
        {
            for(int i=0; i<MAX_ITEMS;i++)
                {
                if(itemsArray[i].equals(""))
                {
                    itemsArray[i] = item;
                    currentNoItems+=1;
                    break;
                }
                }
        }
    }

    public int hasItem(String item)
        {
        int hasItem = -1;
        for(int i=0;i<MAX_ITEMS;i++)
            {
            if(itemsArray[i].equals(item))
            {
                hasItem = i;
                break;
            }
            }

        return hasItem;
        }

    public void removeItem(String item)
        {
        for(int i=0;i<MAX_ITEMS;i++)
            {
            if(itemsArray[i].equals(item))
            {
                itemsArray[i] = "";
                currentNoItems-=1;
                break;
            }
            }
        }

    public String displayInventory()
        {
        String returnString = "";
        for(int i=0;i<MAX_ITEMS;i++)
            {
            if(!itemsArray[i].equals(""))
               {
                returnString = returnString + itemsArray[i] + " ";
               }
            }
        return returnString;
        }

    public boolean isFull()
    {
        return (MAX_ITEMS == currentNoItems);
        //Returns true if the inventory is full, and false otherwise. 
    }
        

}