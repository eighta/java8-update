package core;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.stream.Stream;

@SuppressWarnings("unused")
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

While these tasks are scheduled in the future, the actual execution may be delayed. 
For example, there may be no threads available to perform the task, 
at which point they will just wait in the queue. 
Also, if the ScheduledExecutorService is shut down by the time the scheduled task
execution time is reached, they will be discarded.

The last two methods might be a little confusing if you have not seen them before. 
Conceptually, they are very similar as they both perform the same task repeatedly,
after completing some initial delay. 
The difference is related to the timing of the process and when the next task starts.

The scheduleAtFixedRate() method creates a new task and submits it to the executor every period, 
regardless of whether or not the previous task finished.
 
The following example executes a Runnable task every minute, 
following an initial five minute delay:

	service.scheduleAtFixedRate(command,5,1,TimeUnit.MINUTE);

One risk of using this method is the possibility a task could consistently take 
longer to run than the period between tasks. 

What would happen if the task consistently took five minutes to execute? 
Despite the fact that the task is still running, the ScheduledExecutorService would 
submit a new task to be started every minute. 

If a single-thread executor was used, over time this would result in endless set tasks being scheduled, 
which would run back to back assuming that no other tasks were submitted to the ScheduledExecutorService.

On the other hand, the scheduleAtFixedDelay() method creates a new task after the
previous task has finished. For example, if the first task runs at 12:00 and takes five minutes 
to finish, with a period of 2 minutes, then the second task will start at 12:07.

	service.scheduleAtFixedDelay(command,0,2,TimeUnit.MINUTE);

Notice that neither of the methods, scheduleAtFixedDelay() and scheduleAtFixedRate(), 
take a Callable object as an input parameter. Since these tasks are scheduled to run infinitely, 
as long as the ScheduledExecutorService is still alive, they would generate an endless
series of Future objects.

Each of the ScheduledExecutorService methods is important and has real-world applications.
For example, you can use the schedule() command to check on the state of processing a task and
send out notifications if it is not finished or even call schedule() again to delay processing.

The scheduleAtFixedRate() is useful for tasks that need to be run at specific intervals,
such as checking the health of the animals once a day. Even if it takes two hours to examine
an animal on Monday, this doesn’t mean that Tuesday’s exam should start any later.

Finally, scheduleAtFixedDelay() is useful for processes that you want to happen
repeatedly but whose specific time is unimportant. For example, imagine that we have a zoo
cafeteria worker who periodically restocks the salad bar throughout the day. 
The process can take 20 minutes or more, since it requires the worker to haul 
a large number of items from the back room. Once the worker has filled the salad bar with fresh food, 
he doesn’t need to check at some specific time, just after enough time has passed 
for it to become low on stock again.

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
	//Singles
	ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
	ScheduledExecutorService newSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
	
	//Pooled
	ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
	ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(5);
	ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(5);
	
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
 
***The newCachedThreadPool() method will create a thread pool of unbounded size,
allocating a new thread anytime one is required or all existing threads are busy. 
This is commonly used for pools that require executing many short-lived asynchronous tasks. 

For long-lived processes, usage of this executor is strongly discouraged, as it could grow to
encompass a large number of threads over the application life cycle.	
 
***The newFixedThreadPool() takes a number of threads and allocates them all upon creation. 
As long as our number of tasks is less than our number of threads, all tasks will be executed concurrently. 

If at any point the number of tasks exceeds the number of threads in the pool, 
they will wait in similar manner as you saw with a single-thread executor. 

In fact, calling newFixedThreadPool() with a value of 1 is equivalent to calling newSingleThreadExecutor().	

***The newScheduledThreadPool() is identical to the newFixedThreadPool() method,
except that it returns an instance of ScheduledExecutorService and is therefore
compatible with scheduling tasks. 
This executor has subtle differences in the way that the scheduleAtFixedRate() performs. 
For example, recall our previous example in which tasks took five minutes to complete:

	ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
	service.scheduleAtFixedRate(command,3,1,TimeUnit.MINUTE);

Whereas with a single-thread executor and a five-minute task execution time, an endless
set of tasks would be scheduled over time. With a pooled executor, this can be avoided. 
If the pool size is sufficiently large, 10 for example, then as each thread finishes, 
it is returned to the pool and results in new threads available for the next tasks as they come up.

Choosing a Pool Size
--------------------
In practice, it can be quite difficult to choose an appropriate pool size. In general, you
want at least a handful more threads than you think you will ever possibly need. On the
other hand, you don't want to choose so many threads that your application uses up too
many resources or too much CPU processing power. Oftentimes, the number of CPUs
available is used to determine the thread size using this command:
*/
	int availableProcessors = Runtime.getRuntime().availableProcessors();
	System.out.println("availableProcessors: " + availableProcessors);
	
	//return 8 in LINUX
	
	//LINUX
	//$ cat /proc/cpuinfo | grep 'model name' | uniq
	//model name	: Intel(R) Core(TM) i7-4790 CPU @ 3.60GHz
	//Cores:4, Threads: 8

/**
It is a common practice to allocate thread pools based on the number of CPUs, as well
as how CPU intensive the task is. For example, if you are performing very CPU-intensive
tasks, then creating a 16-thread pool in a 2-CPU computer will cause the computer to 
perform quite slowly, as your process is chewing up most of the CPU bandwidth available for
other applications. Alternatively, if your tasks involve reading/writing data from disk or a
network, a 16-thread pool may be appropriate, since most of the waiting involves external
resources.

Fortunately, most tasks are dependent on some other resources, such as a database, 
file system, or network. In those situations, creating large thread pools is generally safe, as
the tasks are not CPU intensive and may involve a lot of waiting for external resources to
become available.

Synchronizing Data Access
-------------------------
Recall that thread safety is the property of an object that guarantees safe execution by multiple
threads at the same time. Now that we have multiple threads capable of accessing the same
objects in memory, we have to make sure to organize our access to this data such that we don’t
end up with invalid or unexpected results. Since threads run in a shared environment and
memory space, how do we prevent two threads from interfering with each other?

sample at page 350...

the unexpected result of two tasks executing at the same time is referred to as a race condition

Protecting Data with Atomic Classes
-----------------------------------
With the release of the Concurrency API, Java added a new 
java.util.concurrent.atomic package to help coordinate access to primitive values and object references. 
As with most classes in the Concurrency API, these classes are added solely for convenience.

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
*/
	//A boolean value that may be updated atomically
	AtomicBoolean atomicBoolean = new AtomicBoolean(false);
	
	//An int value that may be updated atomically
	AtomicInteger atomicInteger = new AtomicInteger(1);
	//An int array in which elements may be updated atomically
	int length = 4;
	AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(length); 

	//A long value that may be updated atomically
	AtomicLong atomicLong = new AtomicLong(2L);
	//A long array in which elements may be updated atomically
	AtomicLongArray atomicLongArray = new AtomicLongArray(length); 

	//A generic object reference that may be updated atomically
	AtomicReference<Object> atomicReference = new AtomicReference<>(new Object());
	//An array of generic object references in which elements may be updated atomically
	AtomicReferenceArray<Object> atomicReferenceArray = new AtomicReferenceArray<>(new Object[]{});

/**	
How do we use an atomic class? Each class includes numerous methods that are 
equivalent to many of the primitive built-in operators that we use on primitives, such as the
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

sample at PAGE 354

Improving Access with Synchronized Blocks
-----------------------------------------
How do we improve the results so that each worker is able to increment and report the
results in order? The most common technique is to use a monitor, also called a lock, 
to synchronize access. 

A monitor is a structure that supports mutual exclusion or the property
that at most one thread is executing a particular segment of code at a given time.

In Java, any Object can be used as a monitor, along with the synchronized keyword, as
shown in the following example:
*/
	
	Object obj = new Object();
	synchronized(obj){
		// Work to be completed by one thread at a time
	}
/**	
This example is referred to as a synchronized block. Each thread that arrives will first
check if any threads are in the block. In this manner, a thread “acquires the lock” for the
monitor. If the lock is available, a single thread will enter the block, acquiring the lock and
preventing all other threads from entering. While the first thread is executing the block, all
threads that arrive will attempt to acquire the same lock and wait for first thread to finish.
Once a thread finishes executing the block, it will release the lock, allowing one of the
waiting threads to proceed.

sample PAGE 355

Synchronizing Methods
---------------------
In the previous example, we established our monitor using synchronized(this) around the
body of the method. Java actually provides a more convenient compiler enhancement for
doing so. We can add the synchronized modifier to any instance method to synchronize
automatically on the object itself. 

For example, the following two method definitions are equivalent:
<CODE>
***1
private void incrementAndReport() {
	synchronized(this) {
		System.out.print((++sheepCount)+" ");
	}
}
***2
private synchronized void incrementAndReport() {
	System.out.print((++sheepCount)+" ");
}

</CODE>

The first uses a synchronized block, whereas the second uses the synchronized method
modifier. Which you use is completely up to you.

We can also add the synchronized modifier to static methods. 
What object is used as the monitor when we synchronize on a static method? 
The class object, of course! For example, the following two methods are equivalent 
for static synchronization inside our SheepManager class:

<CODE>
***1
public static void printDaysWork() {
	synchronized(SheepManager.class) {
		System.out.print("Finished work");
	}
}
***2
public static synchronized void printDaysWork() {
	System.out.print("Finished work");
}
</CODE>

As before, the first uses a synchronized block, with the second example using the synchronized modifier. 
You can use static synchronization if you need to order thread access across all instances, 
rather than a single instance.

Understanding the Cost of Synchronization
-----------------------------------------
We complete this section by noting that synchronization, while useful, may be costly in practice. 
While multi-threaded programming is about doing multiple things at the same time, 
synchronization is about taking multiple threads and making them perform in a more
single-threaded manner.

For example, let’s say that we have a highly concurrent class with numerous methods
that synchronize on the same object. Let’s say that 50 concurrent threads access it. 
Let’s also say that, on average, each thread takes a modest 100 milliseconds to execute.
In this example, if all of the threads try to access the monitor at the same time, how long
will it take for them to complete their work, assuming that 50 threads are available in the
thread pool?

50 threads x 100 milliseconds 
= 5,000 milliseconds = 5 seconds

Even though five seconds may not seem like a lot, it’s actually pretty long in 
computer time. What if 50 new tasks are created before the five seconds are up? 
This will pile onto the workload, resulting in most threads constantly entering a waiting or
“stuck” state. 

In the application, this may cause tasks that would normally be quick to finish 
in a non-synchronized environment to take a significantly long amount of time to complete.

Synchronization is about protecting data integrity at the cost of performance. 
In many cases, performance costs are minimal, but in extreme scenarios the application could
slow down significantly due to the inclusion of synchronization. Being able to identify
synchronization problems, including finding ways to improve performance in synchronized
multi-threaded environments, is a valuable skill in practice.

Using Concurrent Collections
============================
Besides managing threads, the Concurrency API includes interfaces and classes that help
you coordinate access to collections across multiple tasks. By collections, we are of course
referring to the Java Collections Framework. 

In this section, we will demonstrate many of the new concurrent classes
available to you when using the Concurrency API.

Introducing Concurrent Collections
----------------------------------
The first question you might be asking is “Do we really need new concurrent collection
classes?” After all, in the previous section you saw that we can use the synchronized
keyword on any method or block, so couldn’t we do the same for our existing collection
classes? The short answer is “We could.”

So then, why use a concurrent collection class? Like using ExecutorService to man-
age threads for us, using the concurrent collections is extremely convenient in practice. It
also prevents us from introducing mistakes in own custom implementation, such as if we
forgot to synchronize one of the accessor methods. In fact, the concurrent collections often
include performance enhancements that avoid unnecessary synchronization. Accessing
collections from across multiple threads is so common that the writers of Java thought it
would be a good idea to have alternate versions of many of the regular collections classes
just for multi-threaded access.

Understanding Memory Consistency Errors
---------------------------------------
The purpose of the concurrent collection classes is to solve common memory consistency errors. 
A memory consistency error occurs when two threads have inconsistent views of what should be the same data. 
Conceptually, we want writes on one thread to be available to another thread if it accesses the concurrent 
collection after the write has occurred.

When two threads try to modify the same non-concurrent collection, the JVM may
throw a ConcurrentModificationException at runtime. In fact, it can happen with a single
thread. Take a look at the following code snippet:

<CODE>
Map<String, Object> foodData = new HashMap<String, Object>();
foodData.put("penguin", 1);
foodData.put("flamingo", 2);
for(String key: foodData.keySet())
	foodData.remove(key);
</CODE>

This snippet will throw a ConcurrentModificationException at runtime, since the
iterator keyset() is not properly updated after the first element is removed. 
Changing the first line to use a ConcurrentHashMap will prevent the code from throwing an exception at
runtime:

<CODE>
Map<String, Object> foodData = new ConcurrentHashMap<String, Object>();
foodData.put("penguin", 1);
foodData.put("flamingo", 2);
for(String key: foodData.keySet())
	foodData.remove(key);
</CODE>

Although we don’t usually modify a loop variable, this example highlights the fact that
the ConcurrentHashMap is ordering read/write access such that all access to the class is
consistent. In this code snippet, the iterator created by keySet() is updated as soon as an
object is removed from the Map .

The concurrent classes were created to help avoid common issues in which multiple
threads are adding and removing objects from the same collections. At any given instance,
all threads should have the same consistent view of the structure of the collection.

Working with Concurrent Classes
-------------------------------
There are numerous collection classes with which you should be familiar for the exam.
Luckily, you already know how to use most of them, as the methods available are a superset
to the non-concurrent collection classes that you learned about in Chapter 3.
You should use a concurrent collection class anytime that you are going to have multiple
threads modify a collections object outside a synchronized block or method, even if you
don’t expect a concurrency problem. On the other hand, if all of the threads are accessing an
established immutable or read-only collection, a concurrent collection class is not required.
In the same way that we instantiate an ArrayList object but pass around a List reference,
it is considered a good practice to instantiate a concurrent collection but pass it around using
a non-concurrent interface whenever possible. This has some similarities with the factory
pattern that you learned about in Chapter 2, as the users of these objects may not be aware
of the underlying implementation. In some cases, the callers may need to know that it is a
concurrent collection so that a concurrent interface or class is appropriate, but for the majority
of circumstances, that distinction is not necessary.

Concurrent Collections
----------------------
ConcurrentHashMap
ConcurrentLinkedDeque
ConcurrentLinkedQueue

ConcurrentSkipListMap
ConcurrentSkipListSet

CopyOnWriteArrayList
CopyOnWriteArraySet

LinkedBlockingDeque
LinkedBlockingQueue

The ConcurrentHashMap implements the ConcurrentMap interface, also found in the Concurrency API.
You can use either reference type, Map or ConcurrentMap , to access a ConcurrentHashMap object, 
depending on whether or not you want the caller to know anything about the underlying implementation. 

For example, a method signature may require a ConcurrentMap reference to ensure that object passed to 
it is properly supported in a multi-threaded environment.

Understanding Blocking Queues
-----------------------------
Two queue classes that implement blocking interfaces: LinkedBlockingQueue and LinkedBlockingDeque. 
The BlockingQueue is just like a regular Queue, except that it includes methods that will wait 
a specific amount of time to complete an operation.

Since BlockingQueue inherits all of the methods from Queue , we skip the inherited
methods and present the new waiting methods:

-offer(E e, long timeout, TimeUnit unit)
Adds item to the queue waiting the specified time, returning false if time elapses before space is available

-poll(long timeout, TimeUnit unit)
Retrieves and removes an item from the queue, waiting the specified time, returning null if the time elapses
before the item is available

PAGE 361 

Understanding SkipList Collections
----------------------------------
The SkipList classes, ConcurrentSkipListSet and ConcurrentSkipListMap, are concurrent versions of 
their sorted counterparts, TreeSet and TreeMap, respectively. 

They maintain their elements or keys in the natural ordering of their elements. 
When you see SkipList or SkipSet on the exam, just think "sorted" concurrent collections 
and the rest should follow naturally.

Like other queue examples, it is recommended that you assign these objects to interface references, 
such as SortedMap or NavigableSet. 
In this manner, using them is the same as the code that you worked with in Chapter 3.

Understanding CopyOnWrite Collections
-------------------------------------
The classes CopyOnWriteArrayList and CopyOnWriteArraySet, 
behave a little differently than the other concurrent examples that you have seen. 
These classes copy all of their elements to a new underlying structure anytime an element is
added, modified, or removed from the collection. 

By a modified element, we mean that the reference in the collection is changed. 
Modifying the actual contents of the collection will not cause a new structure to be allocated.

Although the data is copied to a new underlying structure, our reference to the object
does not change. This is particularly useful in multi-threaded environments that need
to iterate the collection. 

Any iterator established prior to a modification will not see the changes, 
but instead it will iterate over the original elements prior to the modification.
Let’s take a look at how this works with an example:

<CODE>
List<Integer> list = new CopyOnWriteArrayList<>(Arrays.asList(4,3,52));
for(Integer item: list) {
	System.out.print(item+" ");
	list.add(9);
}

System.out.println("Size: "+list.size());

</CODE>

When executed as part of a program, this code snippet outputs the following:
4 3 52
Size: 6

Despite adding elements to the array while iterating over it, only those elements in the
collection at the time the for() loop was created were accessed. Alternatively, if we had
used a regular ArrayList object, a ConcurrentModificationException would have been
thrown at runtime. With either class, though, we avoid entering an infinite loop in which
elements are constantly added to the array as we iterate over them.

The CopyOnWrite classes are similar to the immutable object pattern that
you saw in Chapter 2 , as a new underlying structure is created every time
the collection is modified. Unlike the immutable object pattern, though, the
reference to the object stays the same even while the underlying data is
changed. Therefore, strictly speaking, this is not an immutable object pat-
tern, although it shares many similarities.

The CopyOnWrite classes can use a lot of memory, since a new collection structure needs
be allocated anytime the collection is modifi ed. They are commonly used in multi-threaded
environment situations where reads are far more common than writes.


Obtaining Synchronized Collections
----------------------------------
Besides the concurrent collection classes that we have covered, the Concurrency API also
includes methods for obtaining synchronized versions of existing non-concurrent collection
objects. These methods, defined in the Collections class, contain synchronized methods
that operate on the inputted collection and return a reference that is the same type as the
underlying collection.

>>>Synchronized collections methods<<<
--------------------------------------
synchronizedCollection(Collection<T> c)
synchronizedList(List<T> list)
synchronizedMap(Map<K,V> m)
synchronizedNavigableMap(NavigableMap<K,V> m)
synchronizedNavigableSet(NavigableSet<T> s)
synchronizedSet(Set<T> s)
synchronizedSortedMap(SortedMap<K,V> m)
synchronizedSortedSet(SortedSet<T> s)

While this methods synchronize access to the data elements, 
such as the get() and set() methods, they do not synchronize access on any iterators that you
may create from the synchronized collection. Therefore, it is imperative that you use a
synchronization block if you need to iterate over any of the returned collections, 
as shown in the following example:

<CODE>
List<Integer> list = Collections.synchronizedList(new ArrayList<>(Arrays.asList(4,3,52)));
synchronized(list) {
	for(int data: list)
		System.out.print(data+" ");
}
</CODE>

Unlike the concurrent collections, the synchronized collections also throw an exception
if they are modified within an iterator by a single thread. For example, take a look at the
following modification of our earlier example:

<CODE>
Map<String, Object> foodData = new HashMap<String, Object>();
foodData.put("penguin", 1);
foodData.put("flamingo", 2);

Map<String,Object> synchronizedFoodData = Collections.synchronizedMap(foodData);

for(String key: synchronizedFoodData.keySet())
	synchronizedFoodData.remove(key);
</CODE>
	
This code throws a ConcurrentModificationException at runtime, 
whereas our example that used ConcurrentHashMap did not. 
Other than iterating over the collection, the objects returned by the methods in Table 7.12 
are inherently safe to use among multiple threads.

Working with Parallel Streams
=============================
In Chapter 4 , you learned that the Streams API enabled functional programming in Java 8. 
One of the most powerful features of the Streams API is that it has built-in concurrency support. 
Up until now, all of the streams with which you have worked have been serial streams. 

A serial stream is a stream in which the results are ordered, with only one entry being processed at a time.

A parallel stream is a stream that is capable of processing results concurrently, 
using multiple threads. For example, you can use a parallel stream and the stream map() method
to operate concurrently on the elements in the stream, vastly improving performance over
processing a single element at a time.

Using a parallel stream can change not only the performance of your application but
also the expected results. As you shall see, some operations also require special handling to
be able to be processed in a parallel manner.

NOTE: By default, the number of threads available in a parallel stream is related to
the number of available CPUs in your environment. In order to increase the
thread count, you would need to create your own custom class.

Creating Parallel Streams
-------------------------
The Streams API was designed to make creating parallel streams quite easy. For the exam,
you should be familiar with the two ways of creating a parallel stream.

-parallel()
The first way to create a parallel stream is from an existing stream. 
You just call parallel() on an existing stream to convert it to one that supports 
multi-threaded processing, as shown in the following code:
*/
	Stream<Integer> stream = Arrays.asList(1,2,3,4,5,6).stream();
	System.out.println("stream.isParallel(): " + stream.isParallel());
	Stream<Integer> parallelStream = stream.parallel();
	System.out.println("parallelStream.isParallel(): " + parallelStream.isParallel());
	System.out.println(stream == parallelStream);
/**
Be aware that parallel() is an intermediate operation that operates on the original stream.

-parallelStream()
The second way to create a parallel stream is from a Java collection class. 
The Collection interface includes a method parallelStream() that can be called on any collection and
returns a parallel stream. 

The following is a revised code snippet that creates the parallel stream directly from the List object:
 */
	Stream<Integer> parallelStream2 = Arrays.asList(1,2,3,4,5,6).parallelStream();
	System.out.println("parallelStream2.isParallel(): " + parallelStream2.isParallel());
/**	
The Stream interface includes a method isParallel() that can be used to test 
if the instance of a stream supports parallel processing. 

Some operations on streams preserve the parallel attribute, while others do not. 
For example, the Stream.concat(Stream s1, Stream s2) is parallel if either s1 or s2 is parallel. 
*/
	Stream<String> numberStream = Arrays.asList("1","2","3").stream();
	Stream<String> alphaStream = Arrays.asList("A","B","C").stream();
	Stream<String> numberAlphaStream = Stream.concat(numberStream, alphaStream);
	System.out.println("numberAlphaStream.isParallel(): " + numberAlphaStream.isParallel());
	
	Stream<String> aStream = Arrays.asList("1","2","3").stream();
	Stream<String> pStream = Arrays.asList("A","B","C").parallelStream();
	Stream<String> anotherParallelStream = Stream.concat(aStream, pStream); 
	System.out.println("anotherParallelStream.isParallel(): " + anotherParallelStream.isParallel());
	
/**
On the other hand, flatMap() creates a new stream that is not parallel by default, 
regardless of whether the underlying elements were parallel.

	CREAR UN EJEMPLO DE LO ANTERIOR!
	
Processing Tasks in Parallel
============================
As you may have noticed, creating the parallel stream is the easy part. 
The interesting part comes in using it. 

Let’s take a look at a serial example:

<CODE>
	Arrays.asList(1,2,3,4,5,6).stream()
		.forEach(s -> System.out.print(s+" "));
</CODE>

What do you think this code will output when executed as part of a main() method?
Let’s take a look:
1 2 3 4 5 6

As you might expect, the results are ordered and predictable because we are using a
serial stream. 

What happens if we use a parallel stream, though?	
	
 */
	Arrays.asList(1,2,3,4,5,6).parallelStream()
		.forEach(s -> System.out.print(s+" "));
	
/**
With a parallel stream, the forEach() operation is applied across multiple elements of the stream concurrently.	

The following are each sample outputs of this code snippet:
4 1 6 5 2 3
5 2 1 3 6 4
1 2 6 4 5 3

As you can see, the results are no longer ordered or predictable. If you compare this to
earlier parts of the chapter, the forEach() operation on a parallel stream is equivalent to
submitting multiple Runnable lambda expressions to a pooled thread executor.

Ordering forEach Results
------------------------
The Streams API includes an alternate version of the forEach() operation called forEachOrdered(), 
which forces a parallel stream to process the results in order at the cost of performance. 

For example, take a look at the following code snippet:
	Arrays.asList(1,2,3,4,5,6)
		.parallelStream()
		.forEachOrdered(s -> System.out.print(s+" "));
		
Like our starting example, this outputs the results in order:
	1 2 3 4 5 6

Since we have ordered the results, we have lost some of the performance gains of using a parallel stream, 
so why use this method? You might be calling this method in a section of your application that takes 
both serial and parallel streams, and you need to ensure that the results are processed in a particular order. 

Also, stream operations that occur before/after the forEachOrdered() can still gain performance improvements 
for using a parallel stream.

Understanding Performance Improvements
--------------------------------------
Let’s look at another example to see how much using a parallel stream may improve per-
formance in your applications. Let’s say that you have a task that requires processing 4,000
records, with each record taking a modest 10 milliseconds to complete. The following is a
sample implementation that uses Thread.sleep() to simulate processing the data:

*/
		// Process the data (SERIAL)
		WhaleDataCalculator.processAllData(Arrays.asList(1,2,3,4,5,6,7,8,9,10).stream());
		
		// Process the data (parallel)
		WhaleDataCalculator.processAllData(Arrays.asList(1,2,3,4,5,6,7,8,9,10).parallelStream());

/**
(THE REAL EXAMPLE IS IN PAGE 421)
Given that there are 4,000 records, and each record takes 10 milliseconds to process,
by using a serial stream() , the results will take approximately 40 seconds to complete this
task. Each task is completed one at a time: Tasks completed in: 40.044 seconds
 
If we use a parallel stream, though, the results can be processed concurrently:

Depending on the number of CPUs available in your environment, the following is a
possible output of the code using a parallel stream:
Tasks completed in: 10.542 seconds

You see that using a parallel stream can have a four-fold improvement in the results.
Even better, the results scale with the number of processors. 

Scaling is the property that, as we add more resources such as CPUs, the results gradually improve.
Does that mean that all of your streams should be parallel? Not exactly. 
Parallel streams tend to achieve the most improvement when the number of elements in the stream is
significantly large. For small streams, the improvement is often limited, as there are some
overhead costs to allocating and setting up the parallel processing.
		
As with earlier examples in this chapter, the performance of using parallel
streams will vary with your local computing environment. 
There is never a guarantee that using a parallel stream will improve performance. 
In fact, using a parallel stream could slow the application due to the overhead
of creating the parallel processing structures. That said, in a variety of
circumstances, applying parallel streams could result in significant performance gains.

Understanding Independent Operations
------------------------------------
Parallel streams can improve performance because they rely on the property that many stream
operations can be executed independently. By independent operations, we mean that the results
of an operation on one element of a stream do not require or impact the results of another
element of the stream. 

For example, in the previous example, each call to processRecord() can be executed separately, 
without impacting any other invocation of the method.

As another example, consider the following lambda expression supplied to the map() method, 
which maps the stream contents to uppercase strings:
<CODE>
Arrays.asList("jackal","kangaroo","lemur")
	.parallelStream()
	.map(s -> s.toUpperCase())
	.forEach(System.out::println);
</CODE>
	
In this example, mapping jackal to JACKAL can be done independently of mapping kangaroo to KANGAROO. 
In other words, multiple elements of the stream can be processed at
the same time and the results will not change.

Many common streams including map(), forEach(), and filter() can be processed independently, 
although order is never guaranteed.

Consider the following modified version of our previous stream code:
<CODE>
Arrays.asList("jackal","kangaroo","lemur")
	.parallelStream()
	.map(s -> {System.out.println(s); return s.toUpperCase();})
	.forEach(System.out::println);
</CODE>
This example includes an embedded print statement in the lambda passed to the map() method. 
While the return values of the map() operation are the same, the order in which
they are processed can result in very different output. 

We might even print terminal results before the intermediate operations have finished, 
as shown in the following generated output:
	kangaroo
	KANGAROO
	lemur
	jackal
	JACKAL
	LEMUR

When using streams, you should avoid any lambda expressions that can produce side effects.

For the exam, you should remember that parallel streams can process
results independently, although the order of the results cannot be determined ahead of time.

Avoiding Stateful Operations
----------------------------
Side effects can also appear in parallel streams if your lambda expressions are stateful. 
A stateful lambda expression is one whose result depends on any state that might change 
during the execution of a pipeline. 

On the other hand, a stateless lambda expression is one whose result does not depend on any state 
that might change during the execution of a pipeline.

Let’s take a look an example to see why stateful lambda expressions should be avoided in parallel streams:
<CODE>
	List<Integer> data = Collections.synchronizedList(new ArrayList<>());
	Arrays.asList(1,2,3,4,5,6).parallelStream()
		.map(i -> {data.add(i); return i;}) // AVOID STATEFUL LAMBDA EXPRESSIONS!
		.forEachOrdered(i -> System.out.print(i+" "));

	System.out.println();
	for(Integer e: data) {
		System.out.print(e+" ");
	}
</CODE>

The following is a sample generation of this code snippet using a parallel stream:
1 2 3 4 5 6
2 4 3 5 6 1

The forEachOrdered() method displays the numbers in the stream sequentially, whereas the
order of the elements in the data list is completely random. You can see that a stateful lambda
expression, which modifies the data list in parallel, produces unpredictable results at runtime.

Note that this would not have been noticeable with a serial stream, where the results
would have been the following:
1 2 3 4 5 6
1 2 3 4 5 6

It strongly recommended that you avoid stateful operations when using parallel streams,
so as to remove any potential data side effects. In fact, they should generally be avoided in
serial streams wherever possible, since they prevent your streams from taking advantage of
parallelization.

Using Concurrent Collections with Parallel Streams
--------------------------------------------------
We applied the parallel stream to a synchronized list in the previous example. 
Anytime you are working with a collection with a parallel stream, it is recommended that you use
a concurrent collection. 

For example, if we had used a regular ArrayList rather than a synchronized one, 
we could have seen output such as the following:
1 2 3 4 5 6
null 2 4 5 6 1

For an ArrayList object, the JVM internally manages a primitive array of the same type. 
As the size of the dynamic ArrayList grows, a new, larger primitive array is periodically required. 
If two threads both trigger the array to be resized at the same time, a result can be lost, 
producing the unexpected value shown here. 

As briefly mentioned earlier, and also discussed later in this chapter, 
the unexpected result of two tasks executing at the same time is a race condition.

Processing Parallel Reductions
==============================
PAGE 372

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

class WhaleDataCalculator {
	private static int processRecord(int input) {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return input+1;
	}

	public static void processAllData(Stream<Integer> dataStream) {
		long start = System.currentTimeMillis();
		dataStream.map(a -> processRecord(a)).count();
		double time = (System.currentTimeMillis()-start)/1000.0;
		// Report results
		System.out.println("\nTasks completed in: "+time+" seconds");
	}
}
