package ocp;

import static  java.util.Collections.*;

public class OcpChapter1Test {

	
	interface TheInterface{
		default void go(){};
		
	}
	
	static class Parent implements TheInterface{
		
		@Override
		public void go(){}
		
	}
	static class Child extends Parent{}
	static class AnotherChild extends Parent{}
	
	private int pageNumber;
	public void method(){

		class InnerLocal{
			
			private int x = OcpChapter1Test.this.num1;
			
			public int getPage() {
				return OcpChapter1Test.this.pageNumber;
			}
			
		}
		
		 InnerLocal  innerLocal = new  InnerLocal();
		 innerLocal.getPage();
		
		
	}
	
	private int num1;
	private static class StaticMemberInnerClass{
		private int num1 = 1;
		
	}
	private class InstanceMemberInnerClass{
		public static final int num1 = 1;

		public void go() {
			System.out.println(num1);
		}
	}
	private String cadena;
	{
		String s3 = "Canada";
		String s3_2 = new String(s3);
		
		System.out.println(s3 == s3_2);
		System.out.println( s3.equals(s3_2) );
		
	}
	
	public boolean equals(OcpChapter1Test a){
		return false;
	}
	
	public boolean equals(Object a){
		return false;
	}
	
	public static void main(String[] args) {
		
		Parent parent = new Child();
		
		boolean i12 = null instanceof Parent;
		boolean i134 = parent instanceof Runnable;
		
		AnotherChild anotherChild = (AnotherChild) parent;
		
		
		OcpChapter1Test ocpChapter1Test = new OcpChapter1Test();
		System.out.println(ocpChapter1Test.num1);
		OcpChapter1Test.InstanceMemberInnerClass instanceInner = ocpChapter1Test.new InstanceMemberInnerClass();
		instanceInner.go();
		
		OcpChapter1Test.StaticMemberInnerClass staticInner = new OcpChapter1Test.StaticMemberInnerClass();
		StaticMemberInnerClass staticInner2 = new OcpChapter1Test.StaticMemberInnerClass();
		
	}
	
}
