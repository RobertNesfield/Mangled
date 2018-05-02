public class Stack{
	private Item first=null,last=null;
	private int size=0,maxSize;
	
	private class Item{
		private Item next=null,prev=null;
		private char val;
		
		private Item(char val,Item next){
			this.val=val;
			this.next=next;
		}
	}
	
	public void push(char val){
		if(size==0)first=new Item(val,first);
		else{
			first.prev=new Item(val,first);
			first=first.prev;
		}
		size++;
		
		if(size==1)last=first;
		
		if(size>maxSize){
			last=last.prev;
			last.next=null;
			size--;
		}
	}
	
	public char pop(){
		if(size==0)return 0;
		
		char val=first.val;
		first=first.next;
		size--;
		return val;
	}
	
	public char peek(){
		if(size==0)return 0;
		
		return first.val;
	}
	
	public Stack(int size){
		if(size>0)maxSize=size;else maxSize=1;
	}
}