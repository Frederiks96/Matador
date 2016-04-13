package main;

public class Main {

	public static void main(String[] args) {
		
		Player p1 = new Player("John");
		
		CardStack deck = new CardStack();
		System.out.println(deck.draw(p1));
		System.out.println(deck.draw(p1));
		System.out.println(deck.drawAll());
		while (p1.getCard()!=null) {
			p1.getCard().toString();
			p1.takeCard();
		}
	}

}