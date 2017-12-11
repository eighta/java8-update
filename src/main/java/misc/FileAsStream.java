package misc;

//https://dzone.com/articles/split-a-file-as-stream
public class FileAsStream {

	/**
	 * I will show a practical application of splitAsStream, where it really makes sense to process 
	 * the stream and not just split the whole string into an array and work on that.
	 */
	
	// A file can be represented as a CharSequence as long it is not longer than 2GB.
	// The limit comes from the fact that the length of a CharSequence is an int value, and that is 32 bits in Java.
	// The file length is long, which is 64-bit.
	
	//Since reading from a file is much slower than reading from a string that is already in memory, 
	//it makes sense to use the laziness of stream handling.
	
	//All we need is a character sequence implementation that is backed up by a file. 
	//If we can have that, we can write a program like the following:
	
	
	public static void main(String[] args) {
		
	}
}
