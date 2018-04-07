package my.example.sourcemaking.designpatterns.observer;

public class BinObserver extends Observer {
	public BinObserver( Subject subject) {
		this.subject = subject;
		this.subject.add(this);
	}

	@Override
	public void update() {
		System.out.println("" + Integer.toBinaryString(this.subject.getState()));
		
	}

}
