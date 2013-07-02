/**
 * @(#)OneDConcurrency.java
 *
 * OneDConcurrency application
 *
 * @author
 * @version 1.00 2011/6/13
 */

package pl.edu.agh.mes.gg.oned;


public class OneDConcurrency {

    public static void main(String[] args) {

		Executor e = new Executor();
		e.start();
	}
}

