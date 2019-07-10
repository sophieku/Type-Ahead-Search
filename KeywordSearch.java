
import java.util.*;
import java.io.*;

public class KeywordSearch {

    public void quickSort(int arr[], int begin, int end) {
	if (begin < end) {
	    int partitionIndex = partition(arr, begin, end);
 
	    quickSort(arr, begin, partitionIndex-1);
	    quickSort(arr, partitionIndex+1, end);
	}
    }

    private int partition(int arr[], int begin, int end) {
	int pivot = arr[end];
	int i = (begin-1);
 
	for (int j = begin; j < end; j++) {
	    if (arr[j] <= pivot) {
		i++;
 
		int swapTemp = arr[i];
		arr[i] = arr[j];
		arr[j] = swapTemp;
	    }
	}
 
	int swapTemp = arr[i+1];
	arr[i+1] = arr[end];
	arr[end] = swapTemp;
 
	return i+1;
    }

    public List Union(List l1, List l2) {
         List finalList;
        

	//union block of Keyword Search
	int i = 0;
	int j = 0;
	//for (int k = 0; k < (l1.length() + l2.length()) {
	while (l1[i] != null && l2[j] != null) {
            if (l1[i].getKey() == l2[j].getKey()) {
                finalList[i] = l1[i].getKey();
		++i;
		++j;
	    } else if (l1[i].getKey()< l2[j].getKey() ) {
		finalList[i] = l1[i].getKey(); 
		++i;
	    } else {
		finalList[i] = l2[j].getKey();
		++j;
	    }	
	}
	// append rest of list to final list if one was longer
	if ( l1[i] == null) {
            
	}
    }
    
    public static void main {
        // get input word from user, save as searchString
        // parse string into keywords
        // find children trienodes that have isEndOfWord == true and save in List
	// parse through list
	// List trieChildrenList = find(word in list);

           
	//Intersection block of keyword
	int i, j = 0;
   while (l1.length() != null && l2.length() != null) {

	     }
    }







}
