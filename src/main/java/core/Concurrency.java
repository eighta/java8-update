package core;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

public class Concurrency {

	{
/**
Since its early days, Java has supported multi-threading programming using the Thread class. 
In 2004, Java 5 was released and the Concurrency API was introduced in the
java.util.concurrent package.

The Concurrency API has grown over the years to include numerous classes and
frameworks to assist you in developing complex, multi-threaded applications.

Introducing Threads
------------------- 

-A task is a single unit of work performed by a thread.
-A thread is the smallest unit of execution that can be scheduled by the operating system.
-A process is a group of associated threads that execute in the same, shared environment.

By shared environment, we mean that the threads in the same process share the same
memory space and can communicate directly with one another.
 
Distinguishing Thread Types
---------------------------
-A system thread is created by the JVM and runs in the background of the application.
For example, the garbage-collection thread is a system thread that is created by the JVM
and runs in the background

-A user-defined thread is one created by the application developer to accomplish a specific task.

-Although not required knowledge for the exam, a daemon thread 
is one that will not prevent the JVM from exiting when the program finishes. 
A Java application terminates when the only threads that are running are daemon threads. 
For example, if the garbage-collection thread is the only thread left running, 
the JVM will automatically shut down. 

Both system and user-defined threads can be marked as daemon threads.

Understanding Thread Concurrency
--------------------------------
The property of executing multiple threads and processes at the same time is referred to as concurrency.
Operating systems use a thread scheduler to determine which threads should be currently executing

A thread scheduler may employ a round-robin schedule in which each available thread receives an equal number of CPU
cycles with which to execute, with threads visited in a circular order

When a thread's allotted time is complete but the thread has not finished processing, a context switch occurs.
A context switch is the process of storing a thread’s current state and later restoring the state of the thread to continue execution.

Finally, a thread can interrupt or supersede another thread if it has a higher thread priority than the other thread. 
A thread priority is a numeric value associated with a thread that is taken into consideration by the thread scheduler 
when determining which threads should currently be executing.

Java thread priority constants
Thread.MIN_PRIORITY 	1
Thread.NORM_PRIORITY 	5
Thread.MAX_PRIORITY 	10

Introducing Runnable
--------------------
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

Creating a Thread
-----------------
The simplest way to execute a thread is by using the java.lang.Thread class, or Thread for short. 
Executing a task with Thread is a two-step process. 
-First you define the Thread with the corresponding task to be done. 
-Then you start the task by using the Thread.start() method.

Defining the task, or work, that a Thread instance will execute can be done two ways in Java:
-Provide a Runnable object or lambda expression to the Thread constructor.
-Create a class that extends Thread and overrides the run() method.
 */

//Using Runnable as the Task
		System.out.println(">>>Using Runnable as the Task<<<");
		class PrintData implements Runnable{
			@Override
			public void run() {
				System.out.println("1[PrintData] Dont know when start");
				for(int i=0; i<3; i++) System.out.println("Printing record: "+i);
			}
		}
		new Thread(new PrintData()).start();
		
//Extending Thread
		System.out.println(">>>Extending Thread<<<");
		class ReadInventoryThread extends Thread{
			public void run() {
				System.out.println("2[ReadInventoryThread] Dont know when start");
				System.out.println("Printing zoo inventory");
			}
		}
		new ReadInventoryThread().start();
/**
In general, you should extend the Thread class only under very specific circumstances,
such as when you are creating your own priority-based thread. In most situations, you
should implement the Runnable interface rather than extend the Thread class.

Polling with Sleep
------------------
Oftentimes, you need a thread to poll for a result to finish. 
Polling is the process of intermittently checking data at some fixed interval

*/		
		
		/*
		new Thread(() -> {
			for(int i=0; i<500; i++) CheckResults.counter++;
			}).start();
		
		while(CheckResults.counter<100) {
			System.out.println("Not reached yet");
		}
		System.out.println("Reached!");
		*/
		
		
/**
Creating Threads with the ExecutorService
-----------------------------------------
With the announcement of the Concurrency API, Java introduced the ExecutorService,
which creates and manages threads for you. You first obtain an instance of an
ExecutorService interface, and then you send the service tasks to be processed. 
The framework includes numerous useful features, such as thread pooling and scheduling,
which would be cumbersome for you to implement in every project. Therefore, it is
recommended that you use this framework anytime you need to create and execute a
separate task, even if you need only a single thread.

Introducing the Single-Thread Executor
--------------------------------------
Since ExecutorService is an interface, how do you obtain an instance of it? 
The Concurrency API includes the Executors factory class that can be used 
to create instances of the ExecutorService object.
*/		
		
		System.out.println(">>>Using Executors.newSingleThreadExecutor(...)<<<");
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
-------------------------------
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
 
Submitting Tasks
----------------
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
expressions, we tend to prefer submit() over execute(), even if you don't store the Future
reference

Submitting Task Collections
---------------------------
The last two methods you should know for the exam are invokeAll() and invokeAny(). 
Both of these methods take a Collection object containing a list of tasks to execute. 
Both of these methods also execute synchronously. By synchronous, we mean that
unlike the other methods used to submit tasks to a thread executor, these methods will wait
until the results are available before returning control to the enclosing program.

The invokeAll() method executes all tasks in a provided collection and returns a List of
ordered Future objects, with one Future object corresponding to each submitted task, in the
order they were in the original collection. Even though Future.isDone() returns true for each
element in the returned List, a task could have completed normally or thrown an exception.

The invokeAny() method executes a collection of tasks and returns the result of one of
the tasks that successfully completes execution, cancelling all unfinished tasks. While the
first task to finish is often returned, this behavior is not guaranteed, as any completed task
can be returned by this method.

Finally, the invokeAll() method will wait indefinitely until all tasks are complete,
while the invokeAny() method will wait indefinitely until at least one task completes.

The ExecutorService interface also includes overloaded versions of invokeAll() and
invokeAny() that take a timeout value and TimeUnit parameter.

Waiting for Results
-------------------
How do we know when a task submitted to an ExecutorService is complete? 
As mentioned in the last section, the submit() method returns a 
java.util.concurrent.Future<V> object, or Future<V> for short, that can be used to determine this result:
		
Future<?> future = service.submit(() -> System.out.println("Hello Zoo"));

The Future class includes methods that are useful in determining the state of a task:

-boolean isDone()
Returns true if the task was completed, threw an exception, or was cancelled.

-boolean isCancelled()
Returns true if the task was cancelled before it completely­ normally.

-boolean cancel()
Attempts to cancel execution of the task.

-V get()
Retrieves the result of a task, waiting endlessly if it is not yet available.

-V get(long timeout,TimeUnit unit)
Retrieves the result of a task, waiting the specified amount of time. 
If the result is not ready by the time the timeout is reached, 
a checked TimeoutException will be thrown.

What is the return value of this task? 
As Future<V> is a generic class, the type V is determined by the return type of the Runnable method. 
Since the return type of Runnable.run() is void , the get() method always returns null.

The get() method can take an optional value and enum type java.util.concurrent.TimeUnit.

-TimeUnit.NANOSECONDS
Time in one-billionth of a second (1/1,000,000,000)

-TimeUnit.MICROSECONDS
Time in one-millionth of a second (1/1,000,000)

-TimeUnit.MILLISECONDS
Time in one-thousandth of a second (1/1,000)

-TimeUnit.SECONDS
Time in seconds

-TimeUnit.MINUTES
Time in minutes

-TimeUnit.HOURS
Time in hours

-TimeUnit.DAYS
Time in days
 
Introducing Callable
--------------------
When the Concurrency API was released in Java 5, the new java.util.concurrent.Callable interface was added, 
or Callable for short, which is similar to Runnable except
that its call() method returns a value and can throw a checked exception. 
As you may remember from the definition of Runnable, the run() method returns void 
and cannot throw any checked exceptions. 
Along with Runnable, Callable was also made a functional interface in Java 8

@FunctionalInterface 
public interface Callable<V> {
	V call() throws Exception;
}

The Callable interface was introduced as an alternative to the Runnable interface,
since it allows more details to be retrieved easily from the task after it is completed. 
The ExecutorService includes an overloaded version of the submit() method that takes a
Callable object and returns a generic Future<T> object.		
 
Ambiguous Lambda Expressions: Callable vs. Supplier
---------------------------------------------------
You may remember from Chapter 4 that the Callable functional interface strongly
resembles the Supplier functional interface, in that they both take no arguments and
return a generic type. One difference is that the method in Callable can throw a checked
Exception . How do you tell lambda expressions for these two apart? 
The answer is sometimes you can't. PAG 342 example
 */

		service = Executors.newSingleThreadExecutor();
		Callable<Integer> callable = () -> 30+11;
		Future<Integer> result = service.submit(callable);
		try {
			Integer integerResult = result.get();
			System.out.println("GET RESULT: " + integerResult);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}finally {
			service.shutdown();
		}

/**
Checked Exceptions in Callable and Runnable
-------------------------------------------
Besides having a return type, the Callable interface also supports checked exceptions,
whereas the Runnable interface does not without an embedded try/catch block. Given
an instance of ExecutorService called service, 
which of the following lines of code will or will not compile?

service.submit(() -> {Thread.sleep(1000); return null;});
service.submit(() -> {Thread.sleep(1000);});

The first line will compile, while the second line will not. Why? 
Recall that Thread.sleep() throws a checked InterruptedException . 
Since the first lambda expression has a return type, 
the compiler treats this as a Callable expression that supports checked exceptions. 

The second lambda expression does not return a value; therefore,
the compiler treats this as a Runnable expression. Since Runnable methods do not 
support checked exceptions, the compiler will report an error trying to compile this code
snippet.
 
Waiting for All Tasks to Finish
-------------------------------
After submitting a set of tasks to a thread executor, it is common to wait for the results.
As you saw in the previous sections, one solution is to call get() on each Future object
returned by the submit() method. If we don’t need the results of the tasks and are finished
using our thread executor, there is a simpler approach.

First, we shut down the thread executor using the shutdown() method. 
Next, we use the awaitTermination(long timeout, TimeUnit unit) method available for all thread executors.
The method waits the specified time to complete all tasks, returning sooner if all
tasks finish or an InterruptedException is detected.

<CODE>
ExecutorService service = Executors.newSingleThreadExecutor();
// Add tasks to the thread executor
service.shutdown();
service.awaitTermination(1, TimeUnit.MINUTES);
if(service.isTerminated())
	System.out.println("All tasks finished");
else
	System.out.println("At least one task is still running");
</CODE>
 
Scheduling Tasks
----------------
Oftentimes in Java, we need to schedule a task to happen at some future time.
We might even need to schedule the task to happen repeatedly, at some set interval.

The ScheduledExecutorService , which is a subinterface of ExecutorService, 
can be used for just such a task.
	
Like ExecutorService, we obtain an instance of ScheduledExecutorService using a
factory method in the Executors class
 */
	ScheduledExecutorService scheduledService = Executors.newSingleThreadScheduledExecutor();
	scheduledService.shutdown();
	
/**
ScheduledExecutorService Methods
--------------------------------
-schedule(Callable<V> callable, long delay, TimeUnit unit)
Creates and executes a Callable task after the given delay

-schedule(Runnable command, long delay, TimeUnit unit)
Creates and executes a Runnable task after the given delay

-scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit)
Creates and executes a Runnable task after the given
initial delay, creating a new task every period value that passes.

-scheduleAtFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit)
Creates and executes a Runnable task after the given
initial delay and subsequently with the given delay
between the termination of one execution and the commencement of the next

The first two schedule() methods take a Callable or Runnable, respectively, 
perform the task after some delay, and return a ScheduledFuture<V> instance. 
ScheduledFuture<V> is identical to the Future<V> class, 
except that it includes a getDelay() method that returns the delay set when the process was created.

While these tasks are scheduled in the future, the actual execution may
be delayed. For example, there may be no threads available to perform
the task, at which point they will just wait in the queue. Also, if the
ScheduledExecutorService is shut down by the time the scheduled task
execution time is reached, they will be discarded.

The last two methods might be a little confusing if you have not seen them before. 
Conceptually, they are very similar as they both perform the same task repeatedly,
after completing some initial delay. The difference is related to the timing of the process and
when the next task starts.

The scheduleAtFixedRate() method creates a new task and submits it to the executor
every period, regardless of whether or not the previous task finished. 
The following example executes a Runnable task every minute, 
following an initial five minute delay:

service.scheduleAtFixedRate(command,5,1,TimeUnit.MINUTE);

One risk of using this method is the possibility a task could consistently take lon-
ger to run than the period between tasks. What would happen if the task consis-
tently took fi ve minutes to execute? Despite the fact that the task is still running, the
ScheduledExecutorService would submit a new task to be started every minute. If a
single-thread executor was used, over time this would result in endless set tasks being
scheduled, which would run back to back assuming that no other tasks were submitted to
the ScheduledExecutorService .
On the other hand, the scheduleAtFixedDelay() method creates a new task after the
previous task has fi nished. For example, if the fi rst task runs at 12:00 and takes fi ve min-
utes to fi nish, with a period of 2 minutes, then the second task will start at 12:07.

service.scheduleAtFixedDelay(command,0,2,TimeUnit.MINUTE);

Notice that neither of the methods, scheduleAtFixedDelay() and
scheduleAtFixedRate() , take a Callable object as an input parameter.
Since these tasks are scheduled to run infinitely, as long as the
ScheduledExecutorService is still alive, they would generate an endless
series of Future objects.

Each of the ScheduledExecutorService methods is important and has real-world applications.
For example, you can use the schedule() command to check on the state of processing a task and
send out notifications if it is not finished or even call schedule() again to delay processing.

The scheduleAtFixedRate() is useful for tasks that need to be run at specific intervals,
such as checking the health of the animals once a day. Even if it takes two hours to examine
an animal on Monday, this doesn’t mean that Tuesday’s exam should start any later.

Finally, scheduleAtFixedDelay() is useful for processes that you want to happen
repeatedly but whose specific time is unimportant. For example, imagine that we have a zoo
cafeteria worker who periodically restocks the salad bar throughout the day. The process can
take 20 minutes or more, since it requires the worker to haul a large number of items from the
back room. Once the worker has filled the salad bar with fresh food, he doesn’t need to check
at some specific time, just after enough time has passed for it to become low on stock again.
*/
	
/**
Increasing Concurrency with Pools
---------------------------------
Now present three additional factory methods in the Executors class that act on a
pool of threads, rather than on a single thread.

A thread pool is a group of pre-instantiated reusable threads that are available to perform a set of arbitrary tasks.

[[Executors methods]]

-newSingleThreadExecutor():ExecutorService
Creates a single-threaded executor that uses a single worker thread operating off an unbounded queue. 
Results are processed sequentially in the order in which they are submitted.

-newSingleThreadScheduledExecutor():ScheduledExecutorService
Creates a single-threaded executor that can schedule commands to run after a given delay 
or to execute periodically.

-newCachedThreadPool():ExecutorService
Creates a thread pool that creates new threads as needed, 
but will reuse previously constructed threads when they are available.

-newFixedThreadPool(int nThreads):ExecutorService
Creates a thread pool that reuses a fixed number of threads operating off a shared unbounded queue.

-newScheduledThreadPool(int nThreads):ScheduledExecutorService
Creates a thread pool that can schedule commands to run after a given delay or to execute periodically.

 */
	Executors.newSingleThreadExecutor();
	Executors.newSingleThreadScheduledExecutor();
	Executors.newCachedThreadPool();
	Executors.newFixedThreadPool(5);
	Executors.newScheduledThreadPool(5);
//There are also overloaded versions of each of the methods 
//that create threads using a ThreadFactory input parameter.
	ThreadFactory threadFactory = Executors.defaultThreadFactory();
	Executors.newSingleThreadExecutor(threadFactory);
	Executors.newSingleThreadScheduledExecutor(threadFactory);
	//...
/**
The difference between a single-thread and a pooled-thread executor is what happens
when a task is already running. While a single-thread executor will wait for an available
thread to become available before running the next task, a pooled-thread executor can
execute the next task concurrently.

If the pool runs out of available threads, the task will be queued by the thread executor and wait to be completed.
 */
	
/**
The newCachedThreadPool() method will create a thread pool of unbounded size,
allocating a new thread anytime one is required or all existing threads are busy. 
This is commonly used for pools that require executing many short-lived asynchronous tasks. 
For long-lived processes, usage of this executor is strongly discouraged, as it could grow to
encompass a large number of threads over the application life cycle.	
 */
	
/**
The newFixedThreadPool() takes a number of threads and allocates them all upon creation. 
As long as our number of tasks is less than our number of threads, all tasks will be executed concurrently. 
If at any point the number of tasks exceeds the number of threads in the pool, 
they will wait in similar manner as you saw with a single-thread executor. 

In fact, calling newFixedThreadPool() with a value of 1 is equivalent to calling newSingleThreadExecutor().	
 */

/**
The newScheduledThreadPool() is identical to the newFixedThreadPool() method,
except that it returns an instance of ScheduledExecutorService and is therefore
compatible with scheduling tasks. This executor has subtle differences in the way that the
scheduleAtFixedRate() performs. For example, recall our previous example in which
tasks took five minutes to complete:

ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
service.scheduleAtFixedRate(command,3,1,TimeUnit.MINUTE);

Whereas with a single-thread executor and a five-minute task execution time, an endless
set of tasks would be scheduled over time. With a pooled executor, this can be avoided. If
the pool size is sufficiently large, 10 for example, then as each thread finishes, it is returned
to the pool and results in new threads available for the next tasks as they come up.
*/
	
/**
Choosing a Pool Size
--------------------
In practice, it can be quite difficult to choose an appropriate pool size. In general, you
want at least a handful more threads than you think you will ever possibly need. On the
other hand, you don't want to choose so many threads that your application uses up too
many resources or too much CPU processing power. Oftentimes, the number of CPUs
available is used to determine the thread size using this command:

Runtime.getRuntime().availableProcessors()

It is a common practice to allocate thread pools based on the number of CPUs, as well
as how CPU intensive the task is. For example, if you are performing very CPU-intensive
tasks, then creating a 16-thread pool in a 2-CPU computer will cause the computer to per-
form quite slowly, as your process is chewing up most of the CPU bandwidth available for
other applications. Alternatively, if your tasks involve reading/writing data from disk or a
network, a 16-thread pool may be appropriate, since most of the waiting involves external
resources.

Fortunately, most tasks are dependent on some other resources, such as a database, file
system, or network. In those situations, creating large thread pools is generally safe, as
the tasks are not CPU intensive and may involve a lot of waiting for external resources to
become available.
 */

	//return 8 in LINUX
	System.out.println(Runtime.getRuntime().availableProcessors());

	//LINUX
	//$ cat /proc/cpuinfo | grep 'model name' | uniq
	//model name	: Intel(R) Core(TM) i7-4790 CPU @ 3.60GHz
	//Cores:4, Threads: 8

/**
Synchronizing Data Access
-------------------------
Recall that thread safety is the property of an object that guarantees safe execution by multiple
threads at the same time. Now that we have multiple threads capable of accessing the same
objects in memory, we have to make sure to organize our access to this data such that we don’t
end up with invalid or unexpected results. Since threads run in a shared environment and
memory space, how do we prevent two threads from interfering with each other?

page 350...

the unexpected result of two tasks executing at the same time is referred to as a race condition
*/
	
/**
Protecting Data with Atomic Classes
-----------------------------------
With the release of the Concurrency API, Java added a new java.util.concurrent.atomic
package to help coordinate access to primitive values and object references. As with most
classes in the Concurrency API, these classes are added solely for convenience.

Atomic is the property of an operation to be carried out as a single unit of execution
without any interference by another thread. A thread-safe atomic version of the 
increment operator would be one that performed the read and write of the variable as a single
operation, not allowing any other threads to access the variable during the operation.

Any thread trying to access the sheepCount variable while an atomic operation 
is in process will have to wait until the atomic operation on the variable is complete. 
Of course, this exclusivity applies only to the threads trying to access
the sheepCount variable, with the rest of the memory space not affected by this operation.

Since accessing primitives and references in Java is common in shared environments, the
Concurrency API includes numerous useful classes that are conceptually the same 
as our primitive classes but that support atomic operations.

Atomic classes
--------------
-AtomicBoolean: A boolean value that may be updated atomically
-AtomicInteger: An int value that may be updated atomically
-AtomicIntegerArray: An int array in which elements may be updated atomically

-AtomicLong, AtomicLongArray, 

-AtomicReference, AtomicReferenceArray

How do we use an atomic class? Each class includes numerous methods that are equiva-
lent to many of the primitive built-in operators that we use on primitives, such as the
assignment operator = and the increment operators ++

Common atomic methods
---------------------

get(): 				Retrieve the current value
set():				Set the given value, equivalent to the assignment = operator
getAndSet() 		Atomically sets the new value and returns the old value
incrementAndGet() 	For numeric classes, atomic pre-increment operation equivalent to ++value
getAndIncrement() 	For numeric classes, atomic post-increment operation equivalent to value++
decrementAndGet() 	For numeric classes, atomic pre-decrement operation equivalent to --value
getAndDecrement() 	For numeric classes, atomic post-decrement operation equivalent to value--

PAGE 354

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
