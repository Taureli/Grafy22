package com.jakub.grafy;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	static ArrayList<ArrayList<?>> matrix = new ArrayList<ArrayList<?>>();
	static ArrayList<Integer> center = new ArrayList<>();
	static ArrayList<Integer> allVert = new ArrayList<>();
	static int radius;

	public static void main(String args[]) throws Exception {
		int command = -1;
		Scanner scan = new Scanner(System.in);

		while (command != 0) {
			System.out.println("\n0 - zakoñcz program;");
			System.out.println("1 - za³aduj macierz z pliku;");
			System.out.println("2 - znajdŸ centrum drzewa (Wersja 1);");
			System.out.println("Wybierz polecenie: ");
			command = scan.nextInt();

			switch (command) {
			case (1):
				loadMatrix();
				System.out.println("Wczytano macierz z pliku.");
				break;
			case(2):
				center.clear();
				allVert.clear();
				//Spisujê wszystkie wierzcho³ki na potrzeby listy pomocniczej
				for(int i = 0; i < matrix.size(); i++){
					allVert.add(i);
				}
				
				radius = findCenterV1(matrix, new ArrayList<Integer>(), allVert);
				System.out.print("Centrum drzewa: ");
				
				for(int i = 0; i < center.size(); i++){
					System.out.print(center.get(i) + " ");
				}
				
				System.out.println("");
				System.out.println("Œrednica: " + radius);
				break;
			}
		}
		scan.close();
	}

	// Wczytywanie macierzy z pliku
	@SuppressWarnings({ "unchecked" })
	public static void loadMatrix() throws Exception {
		File file = new File("Macierz.txt");
		Scanner sc = new Scanner(file);
		matrix.clear();

		// Tworzê macierz
		int lineCount = 0;
		while (sc.hasNextLine()) {
			String[] currentLine = sc.nextLine().trim().split(" ");
			matrix.add(new ArrayList<Object>());
			for (int i = 0; i < currentLine.length; i++) {
				((ArrayList<Integer>) matrix.get(lineCount)).add(Integer.parseInt(currentLine[i]));
			}
			lineCount++;
		}
		sc.close();
	}

	// Stopieñ wierzcho³ka
	@SuppressWarnings("unchecked")
	public static int vertDeg(int vert) {
		int deg;
		int loops = 0;
		int edges = 0;

		for (int i = 0; i < matrix.size(); i++) {
			if (i == vert)
				loops = ((ArrayList<Integer>) matrix.get(vert)).get(i);
			else
				edges += ((ArrayList<Integer>) matrix.get(vert)).get(i);
		}

		deg = 2 * loops + edges;
		return deg;
	}
	
	//Szukanie centrum
	@SuppressWarnings("unchecked")
	public static int findCenterV1(ArrayList<ArrayList<?>> tree, ArrayList<Integer> leaves, ArrayList<Integer> vert){
		int edges;
		
		//Znaleziono centrum drzewa
		if(vert.size() <= 2){
			center = vert;
			return 1;
		}

		//Usuwam krawêdzie przy wszystkich liœciach
		if(leaves.size() > 0){
			for(int i = 0; i < leaves.size(); i++){
				for(int j = 0; j < tree.size(); j++){
					((ArrayList<Integer>) tree.get(leaves.get(i))).set(j, 0);
					((ArrayList<Integer>) tree.get(j)).set(leaves.get(i), 0);
				}
				//Usuwam wierzcho³ek z listy wszystkich wierzcho³ków
				vert.remove(Integer.valueOf(leaves.get(i)));
			}
		}
		leaves.clear();
		
		//Wyszukujê wszystkich liœci
		for(int i = 0; i < tree.size(); i++){
			edges = 0;
			for(int j = 0; j < tree.size(); j++){
				if(((ArrayList<Integer>)tree.get(i)).get(j) == 1){
					edges++;
				}
			}
			if(edges == 1){
				leaves.add(i);
			}
		}
		
		return findCenterV1(tree, leaves, vert) + 1;
	}
}
