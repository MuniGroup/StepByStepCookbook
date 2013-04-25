package cz.muni.muniGroup.cookbook.entities;

public class AssignedStep {

	private Thread thread;
	private Step step;
	private Integer position;
	
	
	public Thread getThread() {
		return thread;
	}
	public void setThread(Thread thread) {
		this.thread = thread;
	}
	public Step getStep() {
		return step;
	}
	public void setStep(Step step) {
		this.step = step;
	}
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
}