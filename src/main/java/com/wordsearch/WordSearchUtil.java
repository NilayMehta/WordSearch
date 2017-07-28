package com.wordsearch;

import com.wordsearch.DAWG.DawgDS;
import com.wordsearch.DAWG.Node;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.ArrayList;

/**
 * Created by Nilay on 7/26/2017.
 */
public class WordSearchUtil {

    private static Node rootNode;
    private static DawgDS dawg;
    private static ArrayList<ArrayList<Node>> nodeEndings;

    public static void main(String[] args) {
        dawg = new DawgDS();
        rootNode = dawg.getRootNode();

        /** BENCHMAK LOAD DICTIOARY
        long startTime = System.currentTimeMillis();
        loadDictionary();
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime);
        System.out.println("DURATION: " + (float) duration /1000 + " seconds");
        System.out.println("******************* LOADED NEW DICTIONARY *****************");
         **/

        System.out.println("DAWG Node count: " + dawg.getNodeCount());

        /** BENCHMAK LOAD DICTIOARY
         System.out.println("**********************************************************************");
         System.out.println("**************** Starting Optimization of Structure *******************");
         System.out.println("**********************************************************************");
        long startTime = System.currentTimeMillis();
        dawg.optimize();
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime);
        System.out.println("DURATION: " + (float) duration /1000 + " seconds");
        System.out.println("*********************** Optimized Structure **************************");
        System.out.println("DAWG Node count After Optimization: " + dawg.getNodeCount());
        **/

        dawg = deserializeDawgGZ();

        checkDictionary();
//        serializeDawg();
//        serializeDawgGZ();

    }

    public static void loadDictionary() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/dict.txt"))) {
            nodeEndings = new ArrayList<ArrayList<Node>>(15);
            for(int i = 0; i < 15; i++) {
                nodeEndings.add(i, new ArrayList<>());
            }
            String word;
            while ((word = br.readLine()) != null) {
                dawg.addWord(word, rootNode);
                System.out.println("added " + word);
            }
            int runningNodeCount = 0;
            for(int i = 0; i < 15; i++) {
                System.out.println("For index " + i + " for each arraylist, there are " + nodeEndings.get(i).size() + " end nodes");
                runningNodeCount += nodeEndings.get(i).size();
            }
            System.out.println("Running Node Count: " + runningNodeCount);
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
        return dawgDeserialized;
    }

    public static DawgDS deserializeDawgGZ() {
        DawgDS dawgDeserialized = new DawgDS();
        try {
            FileInputStream fileIn = new FileInputStream("dawgOptimized.gz");
            GZIPInputStream gz = new GZIPInputStream(fileIn);
            ObjectInputStream in = new ObjectInputStream(gz);
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
        return dawgDeserialized;
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
}
