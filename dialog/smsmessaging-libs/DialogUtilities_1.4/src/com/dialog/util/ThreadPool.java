package com.dialog.util;
/**
 * Title		:Entertaintment Platform Version 3
 * Description	:General Thread pool implementation
 *  				
 * Copyright	:Copyright (c) 2003
 * Company		:MTN Networks (Pvt) Ltd.
 * Created on	:Apr 18, 2003
 * @author 	:chandimal_ibu
 * @version 	:3.0
 */
import java.util.*;

public class ThreadPool {

private int m_maxThreads 	= -1;	// max number of threads that can be created
private int m_minThreads 	= -1;	// min number of threads that must always be present
private int m_maxIdleTime 	= -1;	// max idle time after which a thread may be killed
private Vector m_pendingJobs 		= new Vector(10,1);	// A list i.e. vector of pending jobs
private Vector m_availableThreads 	= new Vector(10,1);	// A list i.e. vector of all available threads
private boolean m_debug 			= true;		// The debug flag

	// This class represents an element in the
	// available threads vector.
	private class ThreadElement {
		private boolean m_idle	= true;	// if true then the thread can process a new job
		private Thread m_thread;		// serves as the key

		public ThreadElement(Thread thread) {
			m_thread = thread;
			m_idle = true;
		}
		
		public int getID(){
			return ((PoolThread)m_thread).m_ID;
		}     
		
       public String getCurrentJobDesc(){
			return ((PoolThread)m_thread).m_currentJobDesc;
		}
	}

	// Each thread in the pool is an instance of
	// this class :
	// Threads in the POOL
	private class PoolThread extends Thread {
		private Object m_lock;
		private int m_ID;
        private String 	m_currentJobDesc="";
		// Pass in the pool instance for synchronization.
		// The threadpool's object lock is obtained
		//public PoolThread(Object lock) {
		//	m_lock = lock;
		//}
		
		public PoolThread(Object lock, int ID) {
			m_lock = lock;
			m_ID = ID;			
		}

		// This is where all the action is...
		public void run() {
			Runnable job = null;

			while( true ) {
				// Check available
				while( true ) {
					synchronized(m_lock) {
						// Keep processing jobs
						// until none availble
						if( m_pendingJobs.size() == 0 ) {

							if( m_debug )
								System.out.println("ThreadPool:Idle Thread..."+m_ID);

							int index = findMe();
							if( index == -1 )
								return;
							((ThreadElement)m_availableThreads.get(index)).m_idle = true;
							break;
						}

						// Remove the job from the pending list.
						job = (Runnable)m_pendingJobs.firstElement();
						m_pendingJobs.removeElementAt(0);
					}

					// Run the job
					m_currentJobDesc = job.toString();
					if( m_debug )
						System.out.println("ThreadPool:S:job->"+job);
					job.run();
					job = null;
					m_currentJobDesc = ""; 
					if( m_debug )
						System.out.println("ThreadPool:E:job->"+job);
				} // End loop inside

				// Wait until told !!
				try {
					synchronized(this) {
						// If no idle time specified,wait till notified.
						if( m_maxIdleTime == -1 ){
							wait();
						}else{
							wait(m_maxIdleTime);
						}
					} // End syn
				}catch( InterruptedException e ) {
					// Clean up if interrupted
					synchronized(m_lock) {
						if( m_debug )
							System.out.println("ThreadPool:Interrupted..."+m_ID);
						removeMe();
					}
					return;
				}

				// Just been notified or the wait timed out
				synchronized(m_lock) {
					// If there are no jobs, that means we "idled" out.
					if( m_pendingJobs.size() == 0 ) {
						// If more than min required remove this
						if( m_minThreads != -1 && m_availableThreads.size() > m_minThreads ) {
							if( m_debug ){
								System.out.println("ThreadPool:Thread timed out..."+m_ID);
							}
							removeMe();
							return;
						} // end if min thread
					}// endif pending
				}// end synch
			}// End while
		}// End run		

	}// End Class

	public ThreadPool(int maxThreads,int minThreads, int maxIdleTime, boolean debug ){
		m_maxThreads = maxThreads;
		m_minThreads = minThreads;
		m_maxIdleTime = maxIdleTime;
		m_debug = debug;
	}

	synchronized public void addJob(Runnable job) {
		m_pendingJobs.add(job);
		int index = findFirstIdleThread();
		if( index == -1 ) {
			// All threads are busy

			int l_size = m_availableThreads.size();

			if( m_maxThreads == -1 || l_size < m_maxThreads ) {
				l_size++;
				// We can create another thread...
				if( m_debug ){
					System.out.println("ThreadPool:Creating a new Thread..."+l_size);
				}


				ThreadElement e = new ThreadElement(new PoolThread(this,l_size));
				e.m_idle = false;
				e.m_thread.start();
				m_availableThreads.add(e);
				return;
			}

			// We are not allowed to create any more threads
			// so just return.
			// When one of the busy threads is done,
			// it will check the pending queue and will see
			// this job.
			if( m_debug ){
				System.out.println("ThreadPool:Max Threads created and all threads" +
				" in the pool are busy.");
			}
		}else{
			// There is at least one idle thread found.
			if( m_debug ){
				System.out.println("ThreadPool:Using an existing thread..."+index);
			}

			ThreadElement l_te = (ThreadElement)m_availableThreads.get(index);
			// Get existing and set idle false
			l_te.m_idle = false;

			// Get the thread and Notify
			Thread l_tmp = l_te.m_thread;
			synchronized(l_tmp) {
				l_tmp.notify();
			}
		}
	}

	synchronized public ThreadPoolStatus getStats() {
		ThreadPoolStatus stats = new ThreadPoolStatus();
		stats.maxThreads = m_maxThreads;
		stats.minThreads = m_minThreads;
		stats.maxIdleTime = m_maxIdleTime;
		stats.pendingJobs = m_pendingJobs.size();
		stats.numThreads = m_availableThreads.size();
		stats.jobsInProgress =
		m_availableThreads.size() - findNumIdleThreads();
		return(stats);
	}

	synchronized public String getBusyThreadIDs(){
		String retStr = "";
		int size = m_availableThreads.size();
		for( int i=0; i<size; i++ ) {
			ThreadElement te = (ThreadElement)m_availableThreads.get(i);
			if( !te.m_idle ){
					retStr += te.getID() + ":"+ te.getCurrentJobDesc() + "\n";
			}
				//retStr += te.m_thread.get ;
		}
		return retStr;
	}

	synchronized public void killAll(){
		String retStr = "";
		int size = m_availableThreads.size();
		for( int i=0; i<size; i++ ) {
			ThreadElement te = (ThreadElement)m_availableThreads.get(i);			
				te.m_thread.interrupt();
				System.out.println("ThreadPool:Thread id="+te.getID()+";INTERRUPTED !!!");
		}
	}

	synchronized public String killThread(int ID){
		String retStr = "";
		int size = m_availableThreads.size();
		for( int i=0; i<size; i++ ) {
			ThreadElement te = (ThreadElement)m_availableThreads.get(i);
			if( te.getID() == ID ){
				te.m_thread.interrupt();
				System.out.println("ThreadPool:Thread id="+ID+";INTERRUPTED !!!");
			}
			//retStr += te.m_thread.get ;
		}
		return retStr;
	}

	//*****************************************//
	// Important...
	// All private methods must always be
	// called from a synchronized method!!
	//*****************************************//

	// Called by the thread pool to find the number of
	// idle threads
	private int findNumIdleThreads() {
		int idleThreads = 0;
		int size = m_availableThreads.size();
		for( int i=0; i<size; i++ ) {
			if( ((ThreadElement)m_availableThreads.get(i)).m_idle )
				idleThreads++;
		}
		return(idleThreads);
	}

	// Called by the thread pool to find the first idle thread
	private int findFirstIdleThread() {
		int size = m_availableThreads.size();
		for( int i=0; i<size; i++ ) {
			if( ((ThreadElement)m_availableThreads.get(i)).m_idle )
				return(i);
		}
		return(-1);
	}

	// Called by a pool thread to find itself in the
	// vector of available threads.
	private int findMe() {
		int size = m_availableThreads.size();
		for( int i=0; i<size; i++ ) {
			if( ((ThreadElement)m_availableThreads.get(i)).m_thread
				== Thread.currentThread() )
				return(i);
		}
		return(-1);
	}

	// Called by a pool thread to remove itself from
	// the vector of available threads
	private void removeMe() {
		int size = m_availableThreads.size();
		for( int i=0; i<size; i++ ) {
			if( ((ThreadElement)m_availableThreads.get(i)).m_thread
				== Thread.currentThread() ) {
				m_availableThreads.remove(i);
				return;
			}
		}
	}// End method

}// End class
