import java.io.File;
import java.util.Scanner;

public class Interpreter{
	private static char acc;
	private static byte ptr;
	private static Stack[]mem=new Stack[256];
	private static String out;
	
	public static void run(String code,String inp)throws Exception{
		for(int i=0;i<256;i++)mem[i]=new Stack(256);
		acc=0;
		ptr=0;
		out="";
		
		for(int pos=0;pos<code.length();pos++){
			switch(code.charAt(pos)){
				case'O':
					acc++;
					break;
				case'o':
					acc--;
					break;
				case'T':
					acc+=10;
					break;
				case't':
					acc-=10;
					break;
				case'H':
					acc+=100;
					break;
				case'h':
					acc-=100;
					break;
				case'a':
					acc+=mem[ptr&0xFF].peek();
					break;
				case's':
					acc-=mem[ptr&0xFF].peek();
					break;
				case'P':
					mem[ptr&0xFF].push(acc);
					break;
				case'p':
					acc=mem[ptr&0xFF].pop();
					break;
				case'i':
					if(inp.length()>0){
						acc=inp.charAt(0);
						inp=inp.substring(1);
					}else acc=0;
					break;
				case'I':
					out+=(int)acc;
					break;
				case'S':
					out+=acc;
					break;
				case'l':
					ptr--;
					break;
				case'r':
					ptr++;
					break;
				case'Q':
					pos=code.length();
					break;
				case'z':
					if(acc!=0)pos=jump(code,mem[ptr&0xFF].peek());
					break;
				case'Z':
					if(acc==0)pos=jump(code,mem[ptr&0xFF].peek());
					break;
				case'j':
					pos=jump(code,mem[ptr&0xFF].peek());
					break;
				case'{':
					pos=code.indexOf('}',pos);
					break;
				case'\n':
					break;
				case'\t':
					break;
				case' ':
					break;
				case'J':
					break;
				default:
					throw new Exception("Invalid instruction: "+code.charAt(pos));
			}
		}
	}
	
	private static int jump(String code,int jumps)throws Exception{
		if(count(code)==0)throw new Exception("Attempt to jump without destination jump point");
		
		jumps%=count(code);
		int pos=-1;
		
		while(jumps>=0){
			do pos=code.indexOf('J',pos+1);
			while(code.indexOf('}',pos+1)>=0&&code.indexOf('}',pos+1)>code.indexOf('{',pos+1));
			jumps--;
		}
		
		return pos;
	}
	
	private static int count(String code){
		int num=0;
		for(int i=0;i<code.length();i++)if(code.charAt(i)=='J'&&(code.indexOf('}',i)<0||(code.indexOf('{',i)>0&&code.indexOf('{',i)<code.indexOf('}',i))))num++;
		return num;
	}
	
	public static void main(String[]args){
		try{
			run(new Scanner(new File("Program.mngl")).useDelimiter("\\Z").next(),"Test Input");
			System.out.println(out);
		}catch(Exception e){
			System.out.println(e);
		}
	}
}