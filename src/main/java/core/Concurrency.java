package core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Concurrency {

	{
/**
Since its early days, Java has supported multi-threading programming using the Thread class. 
In 2004, Java 5 was released and the Concurrency API was introduced in the
java.util.concurrent package.

The Concurrency API has grown over the years to include numerous classes and
frameworks to assist you in developing complex, multi-threaded applications.
 */
		
/**
Introducing Threads, 

-A task is a single unit of work performed by a thread.
-A thread is the smallest unit of execution that can be scheduled by the operating system.
-A process is a group of associated threads that execute in the same, shared environment.

By shared environment, we mean that the threads in the same process share the same
memory space and can communicate directly with one another.
 */
		
/**
Distinguishing Thread Types

-A system thread is created by the JVM and runs in the background of the application.
For example, the garbage-collection thread is a system thread that is created by the JVM
and runs in the background

-A user-defined thread is one created by the application developer to accomplish a specific task.

-Although not required knowledge for the exam, 
a daemon thread is one that will not prevent the JVM from exiting when the program finishes. 
A Java application terminates when the only threads that are running are daemon threads. 
For example, if the garbage-collection thread is the only thread left running, 
the JVM will automatically shut down. 
Both system and user-defined threads can be marked as daemon threads.
 */
		
/**
Understanding Thread Concurrency

The property of executing multiple threads and processes at the same time is referred to as concurrency.
Operating systems use a thread scheduler to determine which threads should be currently executing

A thread scheduler may employ a round-robin schedule in which each available thread receives an equal number of CPU
cycles with which to execute, with threads visited in a circular order

When a thread's allotted time is complete but the thread has not finished processing, a context switch occurs.
A context switch is the process of storing a thread’s current state and later restoring the state of the thread to continue execution.

Finally, a thread can interrupt or supersede another thread if it has a higher thread
priority than the other thread. A thread priority is a numeric value associated with a thread
that is taken into consideration by the thread scheduler when determining which threads
should currently be executing.

Java thread priority constants
Thread.MIN_PRIORITY 	1
Thread.NORM_PRIORITY 	5
Thread.MAX_PRIORITY 	10
*/
		
/**
Introducing Runnable

java.lang.Runnable , or Runnable for short, is a functional interface that takes no arguments and returns no data.

@FunctionalInterface 
public interface Runnable {
	void run();
}

A task will commonly be implemented as a lambda expression
()->System.out.println("Hello World")
()->{int i=10; i++;}
()->{return;}
()->{}

 */

/**
Creating a Thread
The simplest way to execute a thread is by using the java.lang.Thread class, or Thread for short. 
Executing a task with Thread is a two-step process. 
-First you define the Thread with the corresponding task to be done. 
-Then you start the task by using the Thread.start() method.


Defining the task, or work, that a Thread instance will execute can be done two ways in Java:
-Provide a Runnable object or lambda expression to the Thread constructor.
-Create a class that extends Thread and overrides the run() method.

 */
//Using Runnable as the Task		
		class PrintData implements Runnable{
			@Override
			public void run() {
				for(int i=0; i<3; i++) System.out.println("Printing record: "+i);
			}
		}
		//(new Thread(new PrintData())).start();
		
//Extending Thread
		class ReadInventoryThread extends Thread{
			public void run() {
				System.out.println("Printing zoo inventory");
			}
		}
		//(new ReadInventoryThread()).start();
/**
In general, you should extend the Thread class only under very specific circumstances,
such as when you are creating your own priority-based thread. In most situations, you
should implement the Runnable interface rather than extend the Thread class.
*/
		
/**
Polling with Sleep

Oftentimes, you need a thread to poll for a result to finish. 
Polling is the process of intermittently checking data at some fixed interval

*/		
		
		/*
		new Thread(() -> {
			for(int i=0; i<500; i++) CheckResults.counter++;
			}).start();
		
		while(CheckResults.counter<100) {System.out.println("Not reached yet");}
		System.out.println("Reached!");
		*/
		
		
/**
Creating Threads with the ExecutorService

With the announcement of the Concurrency API, Java introduced the ExecutorService ,
which creates and manages threads for you. You first obtain an instance of an
ExecutorService interface, and then you send the service tasks to be processed. The
framework includes numerous useful features, such as thread pooling and scheduling,
which would be cumbersome for you to implement in every project. Therefore, it is
recommended that you use this framework anytime you need to create and execute a
separate task, even if you need only a single thread.


Introducing the Single-Thread Executor

Since ExecutorService is an interface, how do you obtain an instance of it? 
The Concurrency API includes the Executors factory class that can be used 
to create instances of the ExecutorService object.
 */		
		
		ExecutorService service = null;
		try{
			service = Executors.newSingleThreadExecutor();
			System.out.println("begin");
			service.execute(() -> System.out.println("Printing zoo inventory"));
			service.execute(() -> {for(int i=0; i<3; i++) System.out.println("Printing record: "+i);} );
			service.execute(() -> System.out.println("Printing zoo inventory"));
			System.out.println("end");
		}finally{
			service.shutdown();
		}
/*		
 With a "single-thread executor", results are guaranteed to be executed in the order in which
 they are added to the executor service.
 
Notice that the end text is output while our thread executor tasks are still running. 
This is because the main() method is still an independent thread from the ExecutorService, 
and it can perform tasks while the other thread is running.
*/
		
/**
Shutting Down a Thread Executor

Once you have finished using a thread executor, it is important that you call the
shutdown() method. A thread executor creates a non-daemon thread on the first task that
is executed, so failing to call shutdown() will result in your application never terminating.		
		
The shutdown process for a thread executor involves first rejecting any new tasks submitted to
the thread executor while continuing to execute any previously submitted tasks.
(the reject throw a java.util.concurrent.RejectedExecutionException)

During this time, calling isShutdown() will return true , while isTerminated() will return false.

If a new task is submitted to the thread executor while it is shutting down, 
a RejectedExecutionException will be thrown. 
Once all active tasks have been completed, isShutdown() and isTerminated() will both return true.

For the exam, you should be aware that shutdown() does not actually stop any tasks that
have already been submitted to the thread executor

What if you want to cancel all running and upcoming tasks? 
The ExecutorService provides a method called shutdownNow() , 
which attempts to stop all running tasks and discards any that have not been started yet. 
Note that shutdownNow() attempts to stop all running tasks. 

It is possible to create a thread that will never terminate, so any attempt to interrupt it may be ignored. 

Lastly, shutdownNow() returns a List<Runnable> of tasks that were submitted to the thread executor 
but that were never started.
 */
		
		
/**
Submitting Tasks

You can submit tasks to an ExecutorService instance multiple ways.

>The first method we presented, execute() , is inherited from the Executor interface, 
which the ExecutorService interface extends. 
The execute() method takes a Runnable lambda expression or instance and completes the task asynchronously.

Because the return type of the method is void, it does not tell us anything about the result of the task. 
It is considered a "fire-and-forget" method, as once it is submitted, 
the results are not directly available to the calling thread

Fortunately, the writers of the Java added submit() methods to the ExecutorService interface, 
which, like execute() , can be used to complete tasks asynchronously. 
Unlike execute(), though, submit() returns a Future object that can be used to determine if the task is complete.
It can also be used to return a generic result object after the task has been completed.

ExecutorService methods
=======================
-void execute(Runnable command) 
Executes a Runnable task at some point in the future

-Future<?> submit(Runnable task)
Executes a Runnable task at some point in the future 
and returns a Future representing the task

-<T> Future<T> submit(Callable<T> task)
Executes a Callable task at some point in the future and returns a Future
representing the pending results of the task

-<T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException
Executes the given tasks, synchronously returning the results of all tasks
as a Collection of Future objects, in the same order they were in the original collection

-<T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException
Executes the given tasks, synchronously returning the result of one of finished tasks, 
cancelling any unfinished tasks


Submitting Tasks: execute() vs submit()
---------------------------------------
As you might have noticed, the execute() and submit() methods are nearly identical when
applied to Runnable expressions. The submit() method has the obvious advantage of doing
the exact same thing execute() does, but with a return object that can be used to track the
result. Because of this advantage and the fact that execute() does not support Callable
expressions, we tend to prefer submit() over execute() , even if you don't store the Future
reference


Submitting Task Collections
The last two methods you should know for the exam are invokeAll() and invokeAny(). 
Both of these methods take a Collection object containing a list of tasks to execute. 
Both of these methods also execute synchronously. By synchronous, we mean that
unlike the other methods used to submit tasks to a thread executor, these methods will wait
until the results are available before returning control to the enclosing program.

The invokeAll() method executes all tasks in a provided collection and returns a List of
ordered Future objects, with one Future object corresponding to each submitted task, in the
order they were in the original collection. Even though Future.isDone() returns true for each
element in the returned List , a task could have completed normally or thrown an exception.

The invokeAny() method executes a collection of tasks and returns the result of one of
the tasks that successfully completes execution, cancelling all unfinished tasks. While the
first task to finish is often returned, this behavior is not guaranteed, as any completed task
can be returned by this method.

Finally, the invokeAll() method will wait indefinitely until all tasks are complete,
while the invokeAny() method will wait indefinitely until at least one task completes.
The ExecutorService interface also includes overloaded versions of invokeAll() and
invokeAny() that take a timeout value and TimeUnit parameter.
 */
		
		
		
		
	}
	public static void main(String[] args) {
		new Concurrency();
	}
}

class CalculateAverage implements Runnable {
	@Override
	public void run() {
		// Define work here
	}
}

class CheckResults{
	public static int counter = 0;
}
