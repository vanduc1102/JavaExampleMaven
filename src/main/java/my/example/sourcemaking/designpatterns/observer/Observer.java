package my.example.sourcemaking.designpatterns.observer;

abstract class Observer {
	protected Subject subject;
	public abstract void update();
}
