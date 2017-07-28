package com.wordsearch;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.wordsearch.DAWG.DawgDS;
import com.wordsearch.DAWG.Node;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.ArrayList;

/**
 * Created by Nilay on 7/26/2017.
 */
public class WordSearch {

    private static Node rootNode;
    private static DawgDS dawg;
    private static ArrayList<ArrayList<Node>> nodeEndings;

    public static void main(String[] args) {

        nodeEndings = new ArrayList<ArrayList<Node>>(15);
        for(int i = 0; i < 15; i++) {
            nodeEndings.add(i, new ArrayList<>());
        }
        dawg = new DawgDS();
        rootNode = dawg.getRootNode();

//        System.out.println("The dawg contains the word can: " + dawg.checkWord("can", rootNode));
//        System.out.println("The dawg contains the word card: " + dawg.checkWord("card", rootNode));
//        System.out.println("The dawg contains the word cat: " + dawg.checkWord("cat", rootNode));
//        System.out.println("The dawg contains the word cars: " + dawg.checkWord("cars", rootNode));
//        System.out.println("The dawg contains the word d: " + dawg.checkWord("d", rootNode));


//        long startTime = System.currentTimeMillis();
        loadDictionary();
//        long endTime = System.currentTimeMillis();
//        long duration = (endTime - startTime);
//        System.out.println("DURATION: " + (float) duration /1000 + " seconds");
//        System.out.println("******************* LOADED NEW DICTIONARY *****************");



        System.out.println("**********************************************************************");
        System.out.println("**********************************************************************");
        System.out.println("**********************************************************************");
        int runningNodeCount = 0;
        for(int i = 0; i < 15; i++) {
            System.out.println("For index " + i + " for each arraylist, there are " + nodeEndings.get(i).size() + " end nodes");
            runningNodeCount += nodeEndings.get(i).size();
        }
        System.out.println("Running Node Count: " + runningNodeCount);
//        System.out.println("DAWG Node count: " + dawg.getNodeCount());


//        System.out.println("**********************************************************************");
//        System.out.println("**************** Starting Optimization of Structure *******************");
//        System.out.println("**********************************************************************");
//        startTime = System.currentTimeMillis();
//        dawg.optimize();
//        endTime = System.currentTimeMillis();
//        duration = (endTime - startTime);
//        System.out.println("DURATION: " + (float) duration /1000 + " seconds");
//        System.out.println("**********************************************************************");
//        System.out.println("**********************************************************************");
//        System.out.println("*********************** Optimized Structure **************************");
//        System.out.println("**********************************************************************");
//        System.out.println("**********************************************************************");
//        System.out.println("DAWG Node count After Optimization: " + dawg.getNodeCount());


//        deserializeDawgGZ();
//        rootNode = dawg.getRootNode();
        checkDictionary();


        System.out.println("The dawg contains the word can: " + dawg.checkWord("can", rootNode));
        System.out.println("The dawg contains the word card: " + dawg.checkWord("card", rootNode));
        System.out.println("The dawg contains the word cat: " + dawg.checkWord("cat", rootNode));
        System.out.println("The dawg contains the word cars: " + dawg.checkWord("cars", rootNode));
        System.out.println("The dawg contains the word d: " + dawg.checkWord("d", rootNode));


//        checkDictionary();
//        serializeDawg();
//        serializeDawgGZ();


//        System.out.println("**********************************");
//        System.out.println("The dawg contains the word car: " + dawgDeserialized.checkWord("car", rootNode));
//        System.out.println("The dawg contains the word card: " + dawgDeserialized.checkWord("card", rootNode));
//        System.out.println("The dawg contains the word carpool: " + dawgDeserialized.checkWord("carpool", rootNode));
//        System.out.println("The dawg contains the word cars: " + dawgDeserialized.checkWord("cars", rootNode));
//        System.out.println("The dawg contains the word kids: " + dawgDeserialized.checkWord("kids", rootNode));

        WordSearchUtil wordSearch = new WordSearchUtil();
        wordSearch.populateBoardRandom();
        wordSearch.solveWordSearch();

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

    public static void checkDictionary() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/dict.txt"))) {
            String word;
            int trueResults = 0;
            int falseResults = 0;
            while ((word = br.readLine()) != null) {
                boolean check = dawg.checkWord(word, rootNode);
                if (check) {
                    trueResults++;
                } else if (!check) {
                    falseResults++;
                }
            }
            System.out.println("True Results from dictionary: " + trueResults);
            System.out.println("False Results from dictionary: " + falseResults);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static DawgDS deserializeDawg() {
        DawgDS dawgDeserialized = new DawgDS();
        try {
            FileInputStream fileIn = new FileInputStream("dawgOptimized.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            dawgDeserialized = (DawgDS) in.readObject();
            in.close();
            fileIn.close();
        }catch(IOException i) {
            i.printStackTrace();
            return null;
        }catch(ClassNotFoundException c) {
            System.out.println("DawgDS class not found");
            c.printStackTrace();
            return null;
        }
        return (DawgDS) dawgDeserialized;
    }

    public static void deserializeDawgGZ() {
        try {
            FileInputStream fileIn = new FileInputStream("dawgOptimized.gz");
            GZIPInputStream gz = new GZIPInputStream(fileIn);
            ObjectInputStream in = new ObjectInputStream(gz);
            dawg = (DawgDS) in.readObject();
            in.close();
            fileIn.close();
        }catch(IOException i) {
            i.printStackTrace();
        }catch(ClassNotFoundException c) {
            System.out.println("DawgDS class not found");
            c.printStackTrace();
        }
//        return (DawgDS) dawgDeserialized;
    }

    public static void serializeDawgGZ(){
        try{
            FileOutputStream fos = new FileOutputStream("dawgOptimized.gz");
            GZIPOutputStream gz = new GZIPOutputStream(fos);
            ObjectOutputStream oos = new ObjectOutputStream(gz);
            oos.writeObject(dawg);
            oos.close();
            System.out.println("Done");
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }



    public static void serializeDawg() {
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("dawgOptimized.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(dawg);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved to dawgOptimized.ser");
        }catch(IOException i) {
            i.printStackTrace();
        }
    }

    public static ArrayList<ArrayList<Node>> getNodeEndings() {
        return nodeEndings;
    }

    public static Node getRootNode() {
        return rootNode;
    }

    public static void setRootNode(Node rootNode) {
        WordSearch.rootNode = rootNode;
    }
}
