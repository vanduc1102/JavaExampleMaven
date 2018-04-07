package my.example.sourcemaking.designpatterns.observer;

import java.util.Scanner;

public class ObserverDemo {
	private static Scanner scanner;

	public static void main(String[] args) {
		Subject subject = new Subject();
		new OctObserver(subject);
		new HexObserver(subject);
		new BinObserver(subject);
		scanner = new Scanner(System.in);
		for(int i = 0 ; i < 5; i++) {
			System.out.println("\nEnter a number: ");
			subject.setState(scanner.nextInt());
			
		}
		
	}
	
}
