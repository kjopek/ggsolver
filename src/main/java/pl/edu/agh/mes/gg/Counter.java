/**
 * @(#)Counter.java
 *
 *
 * @author
 * @version 1.00 2011/6/13
 */

package pl.edu.agh.mes.gg;

public class Counter {
	int m_counter;
	Thread m_notify;

	public Counter(Thread Notify) {
		m_counter=0;
		m_notify = Notify;
	}

	public synchronized void inc() {
		m_counter++;
		System.out.println("Counter:"+m_counter);
	}

	public synchronized void dec() {
		if(m_counter>0)
			m_counter--;
		System.out.println("Counter:"+m_counter);

        if(m_counter==0) {
   		    System.out.println("Counter:notify()");
			notify();
		}
	}

	public synchronized void release() {
		try {
		  System.out.println("Counter:wait");

		  while (m_counter > 0) {
			  System.out.println("Counter: m_counter > 0!");
			  wait();
		  }

		  System.out.println("Counter:release");
		}
		catch(InterruptedException e){
			System.out.println("Counter:InterruptedException caught");
		}
	}
}


