package com.wordsearch;

import com.wordsearch.DAWG.DawgDS;
import com.wordsearch.DAWG.Node;

import java.io.*;

/**
 * Created by Nilay on 7/26/2017.
 */
public class WordSearch {

    private static Node rootNode;
    private static DawgDS dawg;
    public static void main(String[] args) {
        dawg = new DawgDS();
        rootNode = dawg.getRootNode();
        System.out.println("The dawg contains the word can: " + dawg.checkWord("can", rootNode));
        System.out.println("The dawg contains the word card: " + dawg.checkWord("card", rootNode));
        System.out.println("The dawg contains the word cat: " + dawg.checkWord("cat", rootNode));
        System.out.println("The dawg contains the word cars: " + dawg.checkWord("cars", rootNode));
        System.out.println("The dawg contains the word d: " + dawg.checkWord("d", rootNode));

        long startTime = System.currentTimeMillis();
        loadDictionary();
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime);
        System.out.println("DURATION: " + (float) duration /1000 + " seconds");
        System.out.println("******************* LOADED NEW DICTIONARY *****************");
        System.out.println("The dawg contains the word can: " + dawg.checkWord("can", rootNode));
        System.out.println("The dawg contains the word card: " + dawg.checkWord("card", rootNode));
        System.out.println("The dawg contains the word cat: " + dawg.checkWord("cat", rootNode));
        System.out.println("The dawg contains the word cars: " + dawg.checkWord("cars", rootNode));
        System.out.println("The dawg contains the word d: " + dawg.checkWord("d", rootNode));
        serializeDawg();


//        //DESERIALIZE THE DAWG
//        DawgDS dawgDeserialized = new DawgDS();
//        try {
//            FileInputStream fileIn = new FileInputStream("dawg.ser");
//            ObjectInputStream in = new ObjectInputStream(fileIn);
//            dawgDeserialized = (DawgDS) in.readObject();
//            in.close();
//            fileIn.close();
//        }catch(IOException i) {
//            i.printStackTrace();
//            return;
//        }catch(ClassNotFoundException c) {
//            System.out.println("DawgDS class not found");
//            c.printStackTrace();
//            return;
//        }
//        System.out.println("**********************************");
//        System.out.println("The dawg contains the word car: " + dawgDeserialized.checkWord("car", rootNode));
//        System.out.println("The dawg contains the word card: " + dawgDeserialized.checkWord("card", rootNode));
//        System.out.println("The dawg contains the word carpool: " + dawgDeserialized.checkWord("carpool", rootNode));
//        System.out.println("The dawg contains the word cars: " + dawgDeserialized.checkWord("cars", rootNode));
//        System.out.println("The dawg contains the word kids: " + dawgDeserialized.checkWord("kids", rootNode));


    }

    public static void loadDictionary() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/dict.txt"))) {
            String word;
            while ((word = br.readLine()) != null) {
                dawg.addWord(word, rootNode);
                System.out.println("added " + word);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void serializeDawg() {
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("dawg.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(dawg);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved to dawg.ser");
        }catch(IOException i) {
            i.printStackTrace();
        }
    }
}
