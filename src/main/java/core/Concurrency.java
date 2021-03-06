package core;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
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
Besides possibly improving performance and modifying the order of operations, 
using parallel streams can impact how you write your application. 

Reduction operations on parallel streams are referred to as parallel reductions. 
The results for parallel reductions can be different from what you expect when working with serial streams.

Performing Order-Based Tasks
----------------------------
Since order is not guaranteed with parallel streams, methods such as findAny() on parallel
streams may result in unexpected behavior. 

Let’s take a look at the results of findAny() applied to a serial stream:

	System.out.print(Arrays.asList(1,2,3,4,5,6).stream().findAny().get());

This code consistently outputs the first value in the serial stream, 1 

With a parallel stream, the JVM can create any number of threads to process the stream.
When you call findAny() on a parallel stream, the JVM selects 
the first thread to finish the task and retrieves its data:

	System.out.print(Arrays.asList(1,2,3,4,5,6).parallelStream().findAny().get());	

The result is that the output could be 4, 1, or really any value in the stream. 
You can see that with parallel streams, the results of findAny() are no longer predictable.

Any stream operation that is based on order, including findFirst(), limit(), or skip(), 
may actually perform more slowly in a parallel environment. This is a result of a
parallel processing task being forced to coordinate all of its threads in a synchronized-like fashion.

On the plus side, the results of ordered operations on a parallel stream will be consistent
with a serial stream. For example, calling skip(5).limit(2).findFirst() will return the
same result on ordered serial and parallel streams.

Creating Unordered Streams
--------------------------
All of the streams with which you have been working are considered ordered by default.
It is possible to create an unordered stream from an ordered stream, similar to how you
create a parallel stream from a serial stream:
	
	Arrays.asList(1,2,3,4,5,6).stream().unordered();
	
This method does not actually reorder the elements; it just tells the JVM that if an
order-based stream operation is applied, the order can be ignored. 

For example, calling skip(5) on an unordered stream will skip any 5 elements, 
not the first 5 required on an ordered stream.

For serial streams, using an unordered version has no effect, but on parallel streams, 
the results can greatly improve performance:
	
	Arrays.asList(1,2,3,4,5,6).stream().unordered().parallel();

Even though unordered streams will not be on the exam, if you are developing applications 
with parallel streams, you should know when to apply an unordered stream to improve performance.


Combining Results with reduce() (:-D)
-------------------------------
As you learned in Chapter 4, the stream operation reduce() combines a stream into a
single object. Recall that first parameter to the reduce() method is called the identity, 
the second parameter is called the accumulator, and the third parameter is called the combiner.

We can concatenate a string using the reduce() method to produce wolf, 
as shown in the following example:

	System.out.println(
		Arrays.asList('w', 'o', 'l', 'f').stream()
			.reduce(	"",					//IDENTITY
						(c,s1) -> c + s1,	//ACCUMULATOR
						(s2,s3) -> s2 + s3) //COMBINER
	);
	
The naming of the variables in this stream example is not accidental. 
The variable c is interpreted as a char, whereas s1, s2, and s3 are String values. 
Recall that in the three-argument version of reduce(), 
the accumulator is a BiFunction, while the combiner is BinaryOperator .	
	
On parallel streams, the reduce() method works by applying the reduction to pairs of
elements within the stream to create intermediate values and then combining those 
intermediate values to produce a final result. 

Whereas with a serial stream, wolf was built one character at a time, 
in a parallel stream, the intermediate strings wo and lf could have been
created and then combined.

With parallel streams, though, we now have to be concerned about order. 
What if the elements of a string are combined in the wrong order to produce wlfo or flwo? 
The Streams API prevents this problem, while still allowing streams to be processed in parallel,
as long as the arguments to the reduce() operation adhere to certain principles.

Requirements for reduce() Arguments
-----------------------------------
-The identity must be defined such that for all elements in the stream u,
combiner.apply(identity, u) is equal to u

-The accumulator operator op must be associative and stateless such that "(a op b) op c"
is equal to "a op (b op c)" 

(La propiedad asociativa aparece en el contexto del álgebra 
y se aplica a dos tipos de operaciones: la suma y la multiplicación. 
Esta propiedad indica que, cuando existen tres o más cifras en estas operaciones, 
el resultado no depende de la manera en la que se agrupan los términos)

-The combiner operator must also be associative and stateless and compatible with the
identity, such that for all u and t combiner.apply(u,accumulator.apply(identity,t))
is equal to accumulator.apply(u,t) 

If you follow these principles when building your reduce() arguments, then the
operations can be performed using a parallel stream and the results will be ordered as they
would be with a serial stream. Note that these principles still apply to the identity and
accumulator when using the one- or two-argument version of reduce() on parallel streams.

While the requirements for the input arguments to the reduce() method
hold true for both serial and parallel streams, you may not have noticed
any problems in serial streams because the result was always ordered.

With parallel streams, though, order is no longer guaranteed, and an 
argument that violates one of these rules is much more likely to produce side
effects and/or unpredictable results.

Let’s take a look at an example using a non-associative accumulator. 
In particular, subtracting numbers (restar numeros) is not an associative operation; 
therefore the following code can output different values depending 
on whether you use a serial or parallel stream:

	System.out.println(
		Arrays.asList(1,2,3,4,5,6)
			.parallelStream()
			.reduce(0,(a,b) -> (a-b))); // NOT AN ASSOCIATIVE ACCUMULATOR

It may output -21 , 3 , or some other value, as the accumulator function violates 
the associativity property.

You can see other problems if we use an identity parameter that is not truly an identity value. 
For example, what do you expect the following code to output?

	System.out.println(
		Arrays.asList("w","o","l","f")
			.parallelStream()
			.reduce("X",String::concat));
			
In fact, it can output XwXoXlXf. As part of the parallel process, the identity is applied to
multiple elements in the stream, resulting in very unexpected data.

Using the Three-Argument reduce() Method
----------------------------------------
Although the one- and two-argument versions of reduce() do support parallel processing, 
it is recommended that you use the three-argument version of reduce() when
working with parallel streams. Providing an explicit combiner method allows the JVM to
partition the operations in the stream more efficiently.

Combing Results with collect()
==============================
Like reduce(), the Streams API includes a three-argument version of collect() that takes
accumulator and combiner operators, along with a supplier operator instead of an identity.

Also like reduce(), the accumulator and combiner operations must be associative 
and stateless, with the combiner operation compatible with the accumulator operator, 
as previously discussed. In this manner, the three-argument version of collect() can be performed
as a parallel reduction, as shown in the following example:

	Stream<String> stream = Stream.of("w", "o", "l", "f").parallel();
	SortedSet<String> set = stream.collect(
								ConcurrentSkipListSet::new, 
								Set::add,
								Set::addAll);
								
	System.out.println(set); // [f, l, o, w]
	
Recall that elements in a ConcurrentSkipListSet are sorted according to their natural ordering.
You should use a concurrent collection to combine the results, ensuring that the results
of concurrent threads do not cause a ConcurrentModificationException 

Using the One-Argument collect() Method
---------------------------------------
Recall that the one-argument version of collect() takes a collector argument, as shown in
the following example:
	
	Stream<String> stream = Stream.of("w", "o", "l", "f").parallel();
	Set<String> set = stream.collect(Collectors.toSet());
	System.out.println(set); // [f, w, l, o]
	
Performing parallel reductions with a collector requires additional considerations. 
For example, if the collection into which you are inserting is an ordered data set, such as a List, 
then the elements in the resulting collection must be in the same order, regardless of
whether you use a serial or parallel stream. This may reduce performance, though, as some
operations are unable to be completed in parallel.

The following rules ensure that a parallel reduction will be performed efficiently in Java
using a collector.

Requirements for Parallel Reduction with collect():
-The stream is parallel.

-The parameter of the collect operation has the 
Collector.Characteristics.CONCURRENT characteristic.

-Either the stream is unordered, or the collector has the characteristic
Collector.Characteristics.UNORDERED

Any class that implements the Collector interface includes a characteristics()
method that returns a set of available attributes for the collector. 

While Collectors.toSet() does have the UNORDERED characteristic, 
it does not have the CONCURRENT characteristic; therefore the previous collector example 
will not be performed as a concurrent reduction.

The Collectors class includes two sets of methods for retrieving collectors
that are both UNORDERED and CONCURRENT, 
Collectors.toConcurrentMap() and Collectors.groupingByConcurrent(), 
and therefore it is capable of performing parallel reductions efficiently. 

Like their non-concurrent counterparts, there are overloaded versions
that take additional arguments.

Here is a rewrite of an example from Chapter 4 to use 
a parallel stream and parallel reduction:

	Stream<String> ohMy = Stream.of("lions", "tigers", "bears").parallel();
	ConcurrentMap<Integer, String> map = 
			ohMy.collect(
				Collectors.toConcurrentMap(
					String::length, 
					k -> k,
					(s1, s2) -> s1 + "," + s2));
					
	System.out.println(map); // {5=lions,bears, 6=tigers}
	System.out.println(map.getClass()); // java.util.concurrent.ConcurrentHashMap

We use a ConcurrentMap reference, although the actual class returned is likely
ConcurrentHashMap. The particular class is not guaranteed; it will just be a class that
implements the interface ConcurrentMap.

Finally, we can rewrite our groupingBy() example from Chapter 4 to use a parallel
stream and parallel reduction:

	Stream<String> ohMy = Stream.of("lions", "tigers", "bears").parallel();
	ConcurrentMap<Integer, List<String>> map = 
		ohMy.collect(
				Collectors.groupingByConcurrent(String::length));
				
	System.out.println(map); // {5=[lions, bears], 6=[tigers]}
	
As before, the returned object can be assigned a ConcurrentMap reference.

Encouraging Parallel Processing
-------------------------------
Guaranteeing that a particular stream will perform reductions in a parallel, as opposed
to single-threaded, is often difficult in practice. For example, the one-argument reduce()
operation on a parallel stream may perform concurrently even when there is no explicit
combiner argument. Alternatively, you may expect some collectors to perform well on a
parallel stream, resorting to single-threaded processing at runtime.

The key to applying parallel reductions is to encourage the JVM to take advantage of
the parallel structures, such as using a groupingByConcurrent() collector on a parallel
stream rather than a groupingBy() collector. By encouraging the JVM to take advantage
of the parallel processing, we get the best possible performance at runtime.

Managing Concurrent Processes
=============================
The Concurrency API includes classes that can be used to coordinate tasks among a group
of related threads. These classes are designed for use in specific scenarios, 
similar to many of the design patterns that you saw in Chapter 2. In this section, 
we present two classes with which you should be familiar for the exam, 
CyclicBarrier and ForkJoinPool.

Creating a CyclicBarrier
------------------------
Our zoo workers are back, and this time they are cleaning pens. 
Imagine that there is a lion pen that needs to emptied, cleaned, and then filled back up with the lions. 

To complete the task, we have assigned four zoo workers. 
Obviously, we don’t want to start cleaning the cage while a lion is roaming in it, lest we end up losing a zoo worker! 
Furthermore, we don’t want to let the lions back into the pen while it is still being cleaned.

We could have all of the work completed by a single worker, but this would be slow and
ignore the fact that we have three zoo workers standing by to help. A better solution would
be to have all four zoo employees work concurrently, pausing between the end of one set of
tasks and the start of the next.

To coordinate these tasks, we can use the CyclicBarrier class. 
For now, let’s start with a code sample without a CyclicBarrier:
*/

		if(false){
			try {
				service = Executors.newFixedThreadPool(4);
				LionPenManager manager = new LionPenManager();		
				for(int i=0; i<4; i++)
					service.submit(() -> manager.performTask());
			} finally {
				if(service != null) service.shutdown();
			}
		}
		
/**
The following is sample output based on this implementation:
	Removing animals
	Removing animals
	Cleaning the pen
	Adding animals
	Removing animals
	Cleaning the pen
	Adding animals
	Removing animals
	Cleaning the pen
	Adding animals
	Cleaning the pen
	Adding animals

Although within a single thread the results are ordered, among multiple workers the
output is entirely random. We see that some animals are still being removed while the cage
is being cleaned, and other animals are added before the cleaning process is finished. 

In our conceptual example, this would be quite chaotic and would not lead to a very clean cage.
We can improve these results by using the CyclicBarrier class. 

The CyclicBarrier takes in its constructors a limit value, indicating the number of threads to wait for. 
As each thread finishes, it calls the await() method on the cyclic barrier. 
Once the specified number of threads have each called await(), 
the barrier is released and all threads can continue.

The following is a reimplementation of our LionPenManager class that uses
CyclicBarrier objects to coordinate access:
*/
		
		System.out.println(">>Using CyclicBarrier<<");
		try {
			service = Executors.newFixedThreadPool(4);
			LionPenManager2 manager = new LionPenManager2();
			
			CyclicBarrier c1 = new CyclicBarrier(4);
			CyclicBarrier c2 = new CyclicBarrier(4,
									() -> System.out.println("*** Pen Cleaned!"));
			for(int i=0; i<4; i++)
				service.submit(() -> manager.performTask(c1,c2));
			
		} finally {
			if(service != null) service.shutdown();
		}
/**
As you can see, all of the results are now organized. Removing the animals all happens in one step, 
as does cleaning the pen and adding the animals back in. 

In this example, we used two different constructors for our CyclicBarrier objects, 
the latter of which called a Runnable method upon completion.

Thread Pool Size and Cyclic Barrier Limit
-----------------------------------------
If you are using a thread pool, make sure that you set the number of available threads to
be at least as large as your CyclicBarrier limit value. 

For example, what if we changed the code to allocate only two threads, such as in the following snippet?

	ExecutorService service = Executors.newFixedThreadPool(2);
	
In this case, the code will hang indefinitely. The barrier would never be reached as the
only threads available in the pool are stuck waiting for the barrier to be complete. As you
shall see in the next section, this is a form of deadlock

The CyclicBarrier class allows us to perform complex, multi-threaded tasks, while all
threads stop and wait at logical barriers. This solution is superior to a single-threaded solu-
tion, as the individual tasks, such as removing the animals, can be completed in parallel by
all four zoo workers.

There is a slight loss in performance to be expected from using a CyclicBarrier . For
example, one worker may be incredibly slow at removing lions, resulting in the other three
workers waiting for him to finish. Since we can’t start cleaning the pen while it is full of
lions, though, this solution is about as concurrent as we can make it.

Reusing CyclicBarrier
---------------------
After a CyclicBarrier is broken, all threads are released and the number of threads waiting 
on the CyclicBarrier goes back to zero. At this point, the CyclicBarrier may be
used again for a new set of waiting threads. For example, if our CyclicBarrier limit is 5
and we have 15 threads that call await() , then the CyclicBarrier will be activated a total
of three times

Applying the Fork/Join Framework
================================
Suppose that we need to measure the weight of all of the animals in our zoo. 
Further suppose that we ask exactly one person to perform this task and complete it in an hour.
What’s the first thing that person is likely to do? Probably ask for help!

In most of the examples in this chapter, we knew at the start of the process exactly how
many threads and tasks we needed to perform. Sometimes, we aren’t so lucky. It may be
that we have five threads, or five zoo workers in our example, but we have no idea how
many tasks need to be performed. When a task gets too complicated, we can split the task
into multiple other tasks using the fork/join framework.

Introducing Recursion
---------------------
The fork/join framework relies on the concept of recursion to solve complex tasks.
Recursion is the process by which a task calls itself to solve a problem. 

A recursive solution is constructed with a base case and a recursive case:

-Base case: A non-recursive method that is used to terminate the recursive path

-Recursive case: A recursive method that may call itself one or multiple times to solve a problem

For example, a method that computes the factorial of a number can be expressed as a recursive function. 

In mathematics, a factorial is what you get when you multiply a number by all of the integers below it. 

The factorial of 5 is equal to 5 * 4 * 3 * 2 * 1 = 120.

The following is a recursive factorial function in Java:

	public static int factorial(int n)
		if(n<=1) return 1;
		else return n * factorial(n-1);
	}

In this example, you see that 1 is the base case, and any integer value 
greater than 1 triggers the recursive case.

One challenge in implementing a recursive solution is always to make sure that the
recursive process arrives at a base case. 

For example, if the base case is never reached, the solution will continue infinitely 
and the program will hang. In Java, this will result in a
StackOverflowError anytime the application recurses too deeply.
---

Let’s use an array of Double values called weights. 
For simplicity, let’s say that there are 10 animals in the zoo; thus our array is of size 10.

	Double[] weights = new Double[10];
	
We are further constrained by the fact that the animals are spread out, 
and a single person can weigh at most three animals in an hour. 

If we want to complete this task in an hour, our zoo worker is going to need some help.

Conceptually, we start off with a single zoo worker who realizes that they cannot
perform all 10 tasks in time. They perform a recursive step by dividing the set of 10
animals into two sets of 5 animals, one set for each zoo worker. 
The two zoo workers then further subdivide the set until each zoo worker has at most three animals to weigh, 
which is the base case in our example.

Applying the fork/join framework requires us to perform three steps:
1. 	 Create a ForkJoinTask .
2. 	 Create the ForkJoinPool .
3. 	 Start the ForkJoinTask .

The first step is the most complex, as it requires defining the recursive process.
Fortunately, the second and third steps are easy and can each be completed with a single
line of code. 

For the exam, you should know how to implement the fork/join solution by
extending one of two classes, RecursiveAction and RecursiveTask, 
both of which implement the ForkJoinTask interface.

The first class, RecursiveAction, is an abstract class that requires us to implement 
the compute() method, which returns void, to perform the bulk of the work. 

The second class, RecursiveTask , is an abstract generic class that requires us to implement 
the compute() method, which returns the generic type, to perform the bulk of the work. 

As you might have guessed, the difference between RecursiveAction and RecursiveTask is analogous to
the difference between Runnable and Callable, respectively, 
which you saw at the start of the chapter.

Let’s define a WeighAnimalAction that extends the fork/join class RecursiveAction:
*/

class WeighAnimalAction extends RecursiveAction {

	private static final long serialVersionUID = 1L;
	private int start;
	private int end;
	private Double[] weights;
	
	public WeighAnimalAction(Double[] weights, int start, int end) {
		this.start = start;
		this.end = end;
		this.weights = weights;
	}
	
	protected void compute() {
		if(end-start <= 3)
			for(int i=start; i<end; i++) {
				weights[i] = (double)new Random().nextInt(100);
				System.out.println("Animal Weighed: "+i);
			}
		else {
			int middle = start+((end-start)/2);
			System.out.println("[start="+start+",middle="+middle+",end="+end+"]");
			invokeAll(new WeighAnimalAction(weights,start,middle),
			new WeighAnimalAction(weights,middle,end));
		}
	}
}
/**

We start off by defining the task and the arguments on which the task will operate, such as
start , end , and weights . We then override the abstract compute() method, defining our base
and recursive processes. For the base case, we weigh the animal if there are at most three left
in the set. For simplicity, this base case assigns a random number from 0 to 100 as the weight.
For the recursive case, we split the work from one WeighAnimalAction object into two
WeighAnimalAction instances, dividing the available indices between the two tasks. 

Some subtasks may end up with little or no work to do, which is fine, 
as long as they terminate in a base case.

----------------------------------------------------------------------
Dividing tasks into recursive subtasks may not always result in evenly
divided sets. In our zoo example, one zoo worker may end up with three
animals to weigh, while others may have only one animal to weigh. The
goal of the fork/join framework is to break up large tasks into smaller ones,
not to guarantee every base case ends up being exactly the same size.
----------------------------------------------------------------------

Once the task class is defined, creating the ForkJoinPool and starting the task is quite easy. 
The following main() method performs the task on 10 records and outputs the results:
 */

	System.out.println("\n\n\n Pesando los animales");

	Double[] weights = new Double[10];
	ForkJoinTask<?> task = new WeighAnimalAction(weights,0,weights.length);
	ForkJoinPool pool = new ForkJoinPool();
	pool.invoke(task);
	
	// Print results
	System.out.println();
	System.out.print("Weights: ");
	Arrays.asList(weights).stream().forEach(
	d -> System.out.print(d.intValue()+" "));
		
/**
The key concept to take away from this example is that the process was started as a
single task, and it spawned additional concurrent tasks to split up the work after it had
already started. As you may have noticed in the sample output, some tasks reached their
base case while others were still performing recursive work. Likewise, the order of the
output cannot be guaranteed, since some zoo workers may finish before others.

Working with a RecursiveTask
============================
Let’s say that we want to compute the sum of all weight values while processing the data.
Instead of extending RecursiveAction , we could extend the generic RecursiveTask to
calculate and return each sum in the compute() method. The following is an updated
implementation that uses RecursiveTask<Double> :
*/

class WeighAnimalTask extends RecursiveTask<Double> {
	private static final long serialVersionUID = 1L;
	private int start;
	private int end;
	private Double[] weights;
	
	public WeighAnimalTask(Double[] weights, int start, int end) {
		this.start = start;
		this.end = end;
		this.weights = weights;
	}
	
	protected Double compute() {
		if(end-start <= 3) {
			double sum = 0;
			for(int i=start; i<end; i++) {
				weights[i] = (double)new Random().nextInt(100);
				System.out.println("Animal Weighed: "+i);
				sum += weights[i];
			}
			return sum;
		} else {
			int middle = start+((end-start)/2);
			System.out.println("[start="+start+",middle="+middle+",end="+end+"]");
			RecursiveTask<Double> otherTask = new WeighAnimalTask(weights,start,middle);
			otherTask.fork();
			return new WeighAnimalTask(weights,middle,end).compute() + otherTask.join();
		}
	}
}

/**
While our base case is mostly unchanged, except for returning a sum value, 
the recursive case is quite different. 

Since the invokeAll() method doesn’t return a value, 
we instead issue a fork() and join() command to retrieve the recursive data. 

The fork() method instructs the fork/join framework to complete the task in a separate thread, 
while the join() method causes the current thread to wait for the results.

In this example, we compute the [middle,end] range using the current thread, 
since we already have one available, and the [start,middle] range using a separate thread. 
We then combine the results, waiting for the otherTask to complete. 
We can then update our main() method to include the results of the entire task:
 */
	
ForkJoinTask<Double> task2 = new WeighAnimalTask(weights,0,weights.length);
ForkJoinPool pool2 = new ForkJoinPool();
Double sum = pool2.invoke(task2);
System.out.println("Sum: "+sum);

/**
Given our previous sample run, the total sum would have been 617.

OJO OJO OJO OJO OJO OJO OJO OJO OJO OJO OJO OJO OJO OJO OJO OJO OJO OJO
=======================================================================
One thing to be careful about when using the fork() and join() methods is the order in
which they are applied. For instance, while the previous example was multi-threaded, 
the following variation operates with single-threaded performance:

	RecursiveTask<Double> otherTask = new WeighAnimalTask(weights,start,middle);
	Double otherResult = otherTask.fork().join();
	return new WeighAnimalTask(weights,middle,end).compute() + otherResult;

In this example, the current thread calls join(), causing it to wait for the [start,middle]
subtask to finish before starting on the [middle,end] subtask. In this manner, the results are
actually performed in a single-threaded manner. 

For the exam, make sure that fork() is called before the current thread begins 
a subtask and that join() is called after it finishes retrieving the results, 
in order for them to be done in parallel.

Identifying Fork/Join Issues
============================
Unlike many of our earlier Concurrency API classes and structures, the fork/join framework
can be a bit overwhelming for developers who have not seen it before. With that in mind, we
have created the following list of tips for identifying issues in a fork/join class on the exam.

Tips for Reviewing a Fork/Join Class:
-The class should extend RecursiveAction or RecursiveTask.

-If the class extends RecursiveAction, then it should override a protected compute()
method that takes no arguments and returns void

-If the class extends RecursiveTask, then it should override a protected compute()
method that takes no arguments and returns a generic type listed in the class definition

-The invokeAll() method takes two instances of the fork/join class and does not return a result

-The fork() method causes a new task to be submitted to the pool and is similar to the
thread executor submit() method

-The join() method is called after the fork() method and causes the current thread to
wait for the results of a subtask

-Unlike fork(), calling compute() within a compute() method causes the task to wait
for the results of the subtask

-The fork() method should be called before the current thread performs a compute()
operation, with join() called to read the results afterward

-Since compute() takes no arguments, the constructor of the class is often used to pass
instructions to the task

Identifying Threading Problems
==============================
A threading problem can occur in multi-threaded applications when two or more threads
interact in an unexpected and undesirable way. For example, two threads may block each
other from accessing a particular segment of code.

The Concurrency API was created to help eliminate potential threading issues common
to all developers. As you have seen, the Concurrency API creates threads and manages complex 
thread interactions for you, often in just a few lines of code.

Although the Concurrency API reduces the potential for threading issues, it does not
eliminate it. In practice, finding and identifying threading issues within an application is
often one of the most difficult tasks a developer can undertake.

Understanding Liveness
----------------------
As you have seen in this chapter, many thread operations can be performed independently,
but some require coordination. For example, synchronizing on a method requires all
threads that call the method to wait for other threads to finish before continuing. 

You also saw earlier in the chapter that threads in a CyclicBarrier will each wait for the barrier
limit to be reached before continuing.

What happens to the application while all of these threads are waiting? 
In many cases, the waiting is ephemeral and the user has very little idea that any delay has occurred. 
In other cases, though, the waiting may be extremely long, perhaps infinite.

Liveness is the ability of an application to be able to execute in a timely manner.
Liveness problems, then, are those in which the application becomes unresponsive or in
some kind of “stuck” state. 

For the exam, there are three types of liveness issues with
which you should be familiar: deadlock, starvation, and livelock.

***Deadlock*** occurs when two or more threads are blocked forever, each waiting on the other.
We can illustrate this principle with the following example. Imagine that our zoo has two
foxes: Foxy and Tails. Foxy likes to eat first and then drink water, while Tails likes to drink
water first and then eat. Furthermore, neither animal likes to share, and they will finish
their meal only if they have exclusive access to both food and water.

The zookeeper places the food on one side of the environment and the water on the other side. 
Although our foxes are fast, it still takes them 100 milliseconds to run from one
side of the environment to the other.

What happens if Foxy gets the food first and Tails gets the water first? The following
application models this behavior:

EXAMPLE: PAGE 388

Preventing Deadlocks
--------------------
How do you fix a deadlock once it has occurred? The answer is that you can't in most situations. 
On the other hand, there are numerous strategies to help prevent deadlocks from ever
happening in the first place. One common strategy to avoid deadlocks is for all threads to
order their resource requests. For example, if both foxes have a rule that they need to obtain
food before water, then the previous deadlock scenario will not happen again. Once one of
the foxes obtained food, the second fox would wait, leaving the water resource available.

There are some advanced techniques that try to detect and resolve a deadlock in real time, 
but they are often quite difficult to implement and have limited success in practice. 

In fact, many operating systems ignore the problem altogether and pretend that deadlocks never happen.

***Starvation*** occurs when a single thread is perpetually denied access to a shared resource
or lock. The thread is still active, but it is unable to complete its work as a result of other
threads constantly taking the resource that they trying to access.
In our fox example, imagine that we have a pack of very hungry, very competitive foxes
in our environment. Every time Foxy stands up to go get food, one of the other foxes sees her
and rushes to eat before her. Foxy is free to roam around the enclosure, take a nap, and howl
for a zookeeper but is never able to obtain access to the food. In this example, Foxy literally
and figuratively experiences starvation. Good thing that this is just a theoretical example!

***Livelock*** occurs when two or more threads are conceptually blocked forever, although they
are each still active and trying to complete their task. Livelock is a special case of resource
starvation in which two or more threads actively try to acquire a set of locks, 
are unable to do so, and restart part of the process.

Livelock is often a result of two threads trying to resolve a deadlock. Returning to our
fox example, imagine that Foxy and Tails are both holding their food and water resources,
respectively. They each realize that they cannot finish their meal in this state, so they both
let go of their food and water, run to opposite side of the environment, and pick up the
other resource. Now Foxy has the water, Tails has the food, and neither is able to finish
their meal!

If Foxy and Tails continue this process forever, it is referred to as livelock. 
Both Foxy and Tails are active, running back and forth across their area, 
but neither is able to finish their meal. 

Foxy and Tails are executing a form of failed deadlock recovery. 
Each fox notices that they are potentially entering a deadlock state and responds by releasing all of
its locked resources. Unfortunately, the lock and unlock process is cyclical, and the two
foxes are conceptually deadlocked.
In practice, livelock is often very difficult issue to detect. Threads in a livelock state appear
active and able to respond to requests, even when they are in fact stuck in an endless cycle.

Managing Race Conditions
------------------------
A race condition is an undesirable result that occurs when two tasks, which should be
completed sequentially, are completed at the same time. We encountered two examples of race
conditions earlier in the chapter when we introduced synchronization and parallel streams.

While Figure 7.3 shows a classical thread-based example of a race condition, 
we now provide a more illustrative example. Imagine two zoo patrons, 
Olivia and Sophia, are signing up for an account on the zoo’s new visitor website. 

Both of them want to use the same username, ZooFan, and they each send requests to create the account at the same
time, as shown.

----------------------------------------------------
OLIVIA	>> createUser("ZooFan") >>
									>> ZooWeb Server
SOPHIA 	>> createUser("ZooFan")	>>
----------------------------------------------------
What result does the web server return when both users attempt to create an account
with the same username in last example?

Possible Outcomes for This Race Condition
-----------------------------------------
-Both users are able to create accounts with username ZooFan.

-Both users are unable to create an account with username ZooFan, returning an error
message to both users.

-One user is able to create the account with the username ZooFan, while the other user
receives an error message.

Which of these results is most desirable when designing our web server? The first possibility,
in which both users are able to create an account with the same username, could cause serious
problems and break numerous invariants in the system. Assuming that the username is
required to log into the website, how do they both log in with the same username and different
passwords? In this case, the website cannot tell them apart. This is the worst possible outcome
to this race condition, as it causes significant and potentially unrecoverable data problems.

What about the second scenario? If both users are unable to create the account, both
will receive error messages and be told to try again. In this scenario, the data is protected
since no two accounts with the same username exist in the system. The users are free to
try again with the same username, ZooFan, since no one has been granted access to it.
Although this might seem like a form of livelock, there is a subtle difference. When the
users try to create their account again, the chances of them hitting a race condition tend
to diminish. For example, if one user submits their request a few seconds before the other,
they might avoid another race condition entirely by the system informing the second user
that the account name is already in use.

The third scenario, in which one user obtains the account while the other does not, is
often considered the best solution to this type of race condition. Like the second situation,
we preserve data integrity, but unlike the second situation, at least one user is able to move
forward on the first request, avoiding additional race condition scenarios. Also unlike the
previous scenario, we can provide the user who didn’t win the race with a clearer error
message because we are now sure that the account username is no longer available in the
system.

For the third scenario, which of the two users should gain access to the account? 
For race conditions, it often doesn't matter as long as only one player “wins” the race. 
A common practice is to choose whichever thread made the request first, whenever possible.

Exam Essentials
===============
-Create concurrent tasks with a thread executor service using Runnable and Callable.

-An ExecutorService creates and manages a single thread or a pool of threads
 
-Instances of Runnable and Callable can both be submitted to a thread executor and will be completed
using the available threads in the service. 

-Callable differs from Runnable in that Callable returns a generic data type and can throw a checked exception. 

-A ScheduledExecutorService can be used to schedule tasks at a fixed rate or a fixed interval between executions.

-Be able to synchronize blocks and methods.   

-A monitor can be used to ensure that only one thread processes a particular section of code at a time.
 
-In Java, monitors are commonly implemented as synchronized blocks or using synchronized methods.
 
-In order to achieve synchronization, two threads must synchronize on the same shared object.

-Be able to apply the atomic classes.    

-An atomic operation is one that occurs without interference by another thread. 
The Concurrency API includes a set of atomic classes that are similar to the primitive classes, 
except that they ensure that operations on them are performed atomically.

-Be able to use the concurrent collection classes.    
The Concurrency API includes numerous collections classes that include built-in support for multi-threaded processing, 
such as ConcurrentHashMap and ConcurrentDeque. 
It also includes a class CopyOnWriteArrayList that creates a copy of its underlying list structure 
every time it is modified and is useful in highly concurrent environments.

-Understand the impact of using parallel streams.    
The Streams API allows for easy creation of parallel streams. 
Using a parallel stream can cause unexpected results, since the order of operations may no longer be predictable. 

Some operations, such as reduce() and collect(), require special consideration to achieve 
optimal performance when applied to a parallel stream.

Manage process with the CyclicBarrier class and the fork/join framework.    
The CyclicBarrier class can be used to force a set of threads to wait until they are at a certain
stage of execution before continuing. The fork/join framework can be used to create a task
that spawns additional tasks to solve problems recursively.

-Identify potential threading problems.    
Deadlock, starvation, and livelock are three threading problems 
that can occur and result in threads never completing their task.

+Deadlock occurs when two or more threads are blocked forever. 
+Starvation occurs when a single thread is perpetually denied access to a shared resource. 
+Livelock is a form of starvation where two or more threads are active but conceptually blocked forever. 

-Finally, race conditions occur when two threads execute at the same time, 
resulting in an unexpected outcome.
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

class LionPenManager {
	private void removeAnimals() { System.out.println("Removing animals"); }
	private void cleanPen() { System.out.println("Cleaning the pen"); }
	private void addAnimals() { System.out.println("Adding animals"); }

	public void performTask() {
		removeAnimals();
		cleanPen();
		addAnimals();
	}
	
}

class LionPenManager2 {
	private void removeAnimals() { System.out.println(Thread.currentThread().getId() + ":Removing animals"); }
	private void cleanPen() { System.out.println(Thread.currentThread().getId() + ":Cleaning the pen"); }
	private void addAnimals() { System.out.println(Thread.currentThread().getId() + ":Adding animals"); }

	public void performTask(CyclicBarrier c1, CyclicBarrier c2) {
		try {
			removeAnimals();
			c1.await();
			cleanPen();
			c2.await();
			addAnimals();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
	}
}