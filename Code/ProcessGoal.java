package org.eclipse.acceleo.mydsl2text.main.services;
import java.io.*;
import java.util.*;

import com.google.common.io.Files;

import java.lang.*;
class Node{
	String val; // Stores the goal name
	int state;
	int type; // Stores the decomposition type of the goal
	variable start; // Link to the child list of the goal if any
	variable rear;
	variable front;
	Node next; // Link to next goal in the model
}
class variable{
	String val;
	int state;
	variable next;
}
class parentPath{
	String val;
	String actor;
	parentPath next;
}
class Node2{
	String variable;
	int state;
	Node2 next;
}
class list
{
	Node2 ps;
	Node2 ns;
	list next;
}
class variable2{
	int state1;
	int state2;
}

public class ProcessGoal {

	static Node head=null;
	static int type=0;
	static String[][] ctlList= new String[1000][3];
	static String[][] ctlListfinal= new String[1000][5];
	static int ctlrow=0;
	static String depender=null;
	static String dependee=null;
	static variable start=null;
	static parentPath phead=null;
	static list lhead=null;
	static int a[]=new int[25000];
	static int noOfTerms;
	static int permute[][]=new int[30000][1000];
	static int row=0,column=0,valid=0;
	//static FileOutputStream fpermute=null;
	//static BufferedWriter fpwrite=null;
	static int flag1=0,flag2=0,flag3=0,flag4=0,flag5=0,flag6=0,check=0;
	static int count1=0;
	static int transitions=0;
	static String existential=null;
	static int typepath[]= new int[1000];
	static int tp=0;
	static int cp=0;
	static int pathtemp=0;
	static int actcount=0;
	static String filename=null;
	static long startTime;
	static int ctlflag=0;
	public static void delete_files()
	{
        File file = new File("c://Users/Mandira Roy/eclipse-workspace/novadsl1/stt.ndsl");
        if(file.delete()){
           // System.out.println("Cleared1");
        }else {
        	// System.out.println("File doesn't exist1");
        	}
        int i=1;
       int  flag=1;
        while(flag==1)
        {
        	String name="C:\\Users\\Mandira Roy\\dsl\\VAR"+i+".txt";
        	  file=new File(name);
        	  if(file.delete()){
                //  System.out.println("Cleared2");
              }else flag=0;
        	  i++;
        }
        file = new File("c://Users/Mandira Roy/eclipse-workspace/novadsl1/NUSMV_input.smv");
        file.delete();
      
       
	}
	public static void count_Actor(String a)
	{
		//startTime = System.currentTimeMillis();
		actcount++;
		filename="C:\\Users\\Mandira Roy\\dsl\\VAR"+actcount+".txt";
	//	System.out.println("The filename is"+filename);
		//System.out.println("The actor is"+a);
		//System.out.println("The count is"+actcount);
	}
	public static void goalProcess(String s,String type,String t)
	{
	//System.out.println("hello");	
	//System.out.println("Type is: "+t);
	Node root;
	variable child;
	//count_Actor("Doctor");
	BufferedWriter fvar=null;
	if(t.compareTo("root")==0)
	{
		root=new Node();
		root.val=s;
		if(type.compareTo("and")==0)
			root.type=1;
		else if(type.compareTo("or")==0)
			root.type=0;
		else root.type=2;
		root.state=0;
		root.start=null;
		root.front=null;
		root.rear=null;
		root.next=null;
		if(head==null)
		{
			head=root;
			//System.out.println("Inif");
		}
		else {
			//System.out.println("Inelse");
		Node p=head;
		while(p.next!=null) {
			//System.out.println("Entering while");
			p=p.next;
			
		}
		p.next=root;
		}
		try {
			//setting the append mode for the output file
		fvar=new BufferedWriter(new FileWriter(filename,true));
		fvar.write(s);
		fvar.newLine();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}finally {
			if(fvar!=null) try {
				fvar.close();
			}catch(IOException ioe2) {
			}
		
	}
	}
	else if(t.compareTo("child")==0)
	{
		Node p;
		child=new variable();
		child.val=s;
		p=head;
		while(p.next!=null)
			p=p.next;
		variable q=p.start;
		if(q==null) {
			p.start=child;
		}
		else {
		while(q.next!=null)
			q=q.next;
		q.next=child;
		}
	}

	Node p=head;
	variable v;
	//Displaying the tree
	/*while(p!=null) {
		System.out.println("root="+p.val+" type= "+p.type);
		v=p.start;
		while(v!=null) {
			System.out.print(" child="+v.val+" ");
			v=v.next;
		}
		System.out.println();
	p=p.next;	
	}*/
			
	}
	public static void check_CTL_type(String ctl)
	{
		FileReader f1=null;
		int i,flag;
		int len=ctl.length();
		char c;
		ctlrow=0;
		int idx=0,count=0;
		char[] charCTL=new char[1000];
		charCTL=ctl.toCharArray();
		System.out.println("The extarcted CTL is : "+ctl);
		//NOT(EF(((EnterLoginId=FU) AND EX(NOT(EnterPassword=FU))) AND ((EnterLoginId=FU) AND NOT(EF(Get_E_book=FU)))))
		//NOT(EF(((Use_locker=FU) AND (NOT(Get_accesss=FU))) AND ((Close_locker=FU) AND (NOT(Get_accesss=FU))) AND ((Use_locker=FU) AND EX(NOT(Get_accesss=FU))) AND ((enter_access_code=FU) AND NOT(EF(verify_code=FU)))))
		  String substring="AND";

		     while ((idx = ctl.indexOf(substring, idx)) != -1)
		     {
		        idx++;
		        count++;
		     }
		     System.out.println("The count is:"+count);
		     String temp=new String();
		     i=0;
				temp="";
				while(charCTL[i]!='(') //Loop to read NOT
				{
					i++;
				}
				i++;
				while(charCTL[i]!='(') //Loop to read EF
				{
					i++;
				}
				
			if(count==1)
			{
				System.out.println("Here");
				i=i+2; //second (
				dependee="";
				while(charCTL[i]!='=') //Loop to read variable
				{
					dependee=dependee.concat(Character.toString(charCTL[i]));
					i++;
					
				}
				System.out.println(dependee);
				ctlList[ctlrow][0]=dependee;
				//System.out.println(dependee);
				i+=5;
				temp="";
				while(charCTL[i]!=' ') //Loop to read AND
				{
					i++;
				}
				check++;
				//System.out.println(temp);
				i++; //space
				//System.out.println("here "+charCTL[i]);
				if(charCTL[i]=='E')
				{
					//next state constraint
					type=3;
					if(charCTL[i+1]=='X') {
						ctlList[ctlrow][2]="3";
						typepath[tp]=3;
						tp++;
					}
					else {
						ctlList[ctlrow][2]="1";
						typepath[tp]=1;
						tp++;
					}
					i+=3;
					temp="";
					while(charCTL[i]!='(') //Loop to read NOT
					{
						i++;
					}
					depender="";
					i++;
					while(charCTL[i]!='=') //Loop to read variable
					{
					
						//System.out.println("i="+i);
						depender=depender.concat(Character.toString(charCTL[i]));
						i++;
						
					}
					ctlList[ctlrow][1]=depender;
					//i+=5;
					//System.out.println("done3");
				}
				else if(charCTL[i]=='(')
				{
					//untill constraint
					type=2;
					ctlList[ctlrow][2]="2";
					typepath[tp]=2;
					tp++;
					ctlList[ctlrow][1]=ctlList[ctlrow][0];
					ctlList[ctlrow][0]="";
					//System.out.println(ctlList[ctlrow][1]);
					depender=dependee;
					dependee="";
					i++;
					while(charCTL[i]!='(') //Loop to read NOT
					{
						i++;
					}
					i++;
					while(charCTL[i]!='=') //Loop to read Variable
					{
					
						dependee=dependee.concat(Character.toString(charCTL[i]));
						i++;
					
					}
					ctlList[ctlrow][0]=dependee;
					//i+=5;
					//System.out.println("done2");
				}
				ctlrow++;
			}
				//System.out.println("hi");
			else {
				int check=0;
				while(check<count && i<len) // Loop to read the entire input file
				{
					//System.out.println("hi");
				
					i=i+3; //second (
					dependee="";
					while(charCTL[i]!='=') //Loop to read variable
					{
						dependee=dependee.concat(Character.toString(charCTL[i]));
						i++;
						
					}
					ctlList[ctlrow][0]=dependee;
					//System.out.println(dependee);
					i+=5;
					temp="";
					while(charCTL[i]!=' ') //Loop to read AND
					{
						i++;
					}
					check++;
					//System.out.println(temp);
					i++; //space
					//System.out.println("here "+charCTL[i]);
					if(charCTL[i]=='E')
					{
						//next state constraint
						type=3;
						if(charCTL[i+1]=='X')
						{
							ctlList[ctlrow][2]="3";
							typepath[tp]=3;
							tp++;
						}
						else
						{
							ctlList[ctlrow][2]="1";
							typepath[tp]=1;
							tp++;
						}
						i+=3;
						temp="";
						while(charCTL[i]!='(') //Loop to read NOT
						{
							i++;
						}
						depender="";
						i++;
						while(charCTL[i]!='=') //Loop to read variable
						{
						
							//System.out.println("i="+i);
							depender=depender.concat(Character.toString(charCTL[i]));
							i++;
							
						}
						ctlList[ctlrow][1]=depender;
						//i+=5;
						//System.out.println("done3");
					}
					else if(charCTL[i]=='(')
					{
						//untill constraint
						type=2;
						ctlList[ctlrow][2]="2";
						typepath[tp]=2;
						tp++;
						ctlList[ctlrow][1]=ctlList[ctlrow][0];
						ctlList[ctlrow][0]="";
						//System.out.println(ctlList[ctlrow][1]);
						depender=dependee;
						dependee="";
						i++;
						while(charCTL[i]!='(') //Loop to read NOT
						{
							i++;
						}
						i++;
						while(charCTL[i]!='=') //Loop to read Variable
						{
						
							dependee=dependee.concat(Character.toString(charCTL[i]));
							i++;
						
						}
						ctlList[ctlrow][0]=dependee;
						//i+=5;
						//System.out.println("done2");
					}
					
					
		
				check++;
				if(check<count)
					i+=10;
				ctlrow++;
				//System.out.println(ctlrow);
					
			}
			}
				i=0;
				/*System.out.println("The content is:");
				while(i<ctlrow)
				{
					System.out.println("dependee is"+ctlList[i][0]+"depender is"+ctlList[i][1]+"And type is"+ctlList[i][2]);
					i++;
				}*/
			create_final_ctl_list();
		}

	
	static void generateFSM(String s) {
		Node temp=head; // Assigning to first node in the list
		variable child;
		BufferedWriter fstt=null;
		int state1,state2;
		String swrite=new String();
		try {
			//setting the append mode for the output file
			fstt=new BufferedWriter(new FileWriter("c://Users/Mandira Roy/eclipse-workspace/novadsl1/stt.ndsl",true));
			//fstt.write("fsm");
			//fstt.newLine();
			//fstt.write("path present ");
			if((temp.val.compareTo(s))==0)
			{
			state1=temp.state;
			swrite="";
			//System.out.println(temp.val);
			swrite=swrite.concat(temp.val);
			//System.out.println("String="+swrite);
			swrite=swrite.concat("=NC, ");
			//System.out.println(swrite);
			fstt.write(swrite); // writing initial not created state
			temp.state=1;		//changing state to created but not fulfilled
			state1=0;
			state2=temp.state;	
			swrite="";
			swrite=swrite.concat(temp.val);
			swrite=swrite.concat(":NC->CNF ");
			//fstt.write("transit ");
			fstt.write(swrite);
			//fstt.write("next ");
			swrite="";
			swrite=swrite.concat(temp.val);
			swrite=swrite.concat("=CNF,");
			fstt.write(swrite);	//writing created not fulfilled state 
			transitions++;
			child=temp.start;
			while(child!=null)
			{
				swrite="";
				swrite=swrite.concat(child.val);
				swrite=swrite.concat("=NC,");
				fstt.write(swrite); // writing each child in not created state
				child=child.next;
			}
			fstt.newLine();
			//fstt.write("path present ");
			if(temp.type==0) // If decomposition type is OR then children are in separate lines
			{
				temp.state=2;
				child=temp.start;
				while(child!=null) {
					swrite="";
					swrite=swrite.concat(temp.val);
					swrite=swrite.concat("=CNF,");
					fstt.write(swrite);
					swrite="";
					swrite=swrite.concat(child.val);
					swrite=swrite.concat("=FU, "); // showing that when each child is fulfilled
					fstt.write(swrite);
					swrite="";
					swrite=swrite.concat(temp.val);
					swrite=swrite.concat(":CNF->FU ");	// Root goal makes transition from not fulfilled to fulfilled state
					fstt.write("transit ");
					fstt.write(swrite);
					//fstt.write("next ");
					fstt.write(" ");
					swrite="";
					swrite=swrite.concat(temp.val);
					swrite=swrite.concat("=FU,");
					fstt.write(swrite);
					fstt.newLine();
					child=child.next;
					transitions++;
					
				}
			}
			else if(temp.type==1) //If decomposition type is AND then in one line all the children
			{
				swrite="";
				swrite=swrite.concat(temp.val);
				swrite=swrite.concat("=CNF,");
				fstt.write(swrite);
				child=temp.start;
				while(child!=null)
				{
					swrite="";
					swrite=swrite.concat(child.val);
					swrite=swrite.concat("=FU,");	//When all the child are fulfilled 
					fstt.write(swrite);
					child=child.next;
					
				}
				temp.state=2;
				fstt.write(" ");
				swrite="";
				swrite=swrite.concat(temp.val);
				swrite=swrite.concat(":CNF->FU ");	//Root goal makes transition from not fulfilled to fulfilled state
				//fstt.write("transit ");
				fstt.write(swrite);
				//fstt.write("next ");
				fstt.write(" ");
				swrite="";
				swrite=swrite.concat(temp.val);
				swrite=swrite.concat("=FU,");
				fstt.write(swrite);
				fstt.newLine();
				
				transitions++;
			}
			}
		fstt.close();
		while((temp.val.compareTo(s))!=0)
			temp=temp.next;
		if(temp.type==0)
		{
			insertp(temp.val);
			meansEnd(temp.val);
			
		}
		else if(temp.type==1)
		{
			insertp(temp.val);
			task(temp.val);
		}
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}finally {
			if(fstt!=null) try {
				fstt.close();
			}catch(IOException ioe2) {
			}
			else {
			}
			}
		
		
	}
	static void insertp(String s)
	{
		parentPath temp,p;
		temp=new parentPath();
		temp.val=s;
		temp.next=null;
		
		if(phead==null)
			phead=temp;
		else {
			p=phead;
			while(p.next!=null)
				p=p.next;
			p.next=temp;
		}
		parentPath t=phead;
		while(t.next!=null)
		{
			//System.out.println("hello");
			t=t.next;
		}
		
	}
	static void meansEnd(String s)
	{
		Node temp=head,temp2;
		variable child,grand_child;
		parentPath ptemp;
		BufferedWriter fstt=null;
		String swrite="";
		int flag=0;
		try {
			fstt=new BufferedWriter(new FileWriter("c://Users/Mandira Roy/eclipse-workspace/novadsl1/stt.ndsl",true));
			
			while((temp.val.compareTo(s))!=0)
				temp=temp.next;
			child=temp.start;
			while(child!=null)
			{
				//System.out.println("stuck here");
				//fstt.write("path present ");
				temp2=head;
				while((temp2.val.compareTo(child.val))!=0)
					temp2=temp2.next;
				ptemp=phead;
				swrite="";
				while(ptemp!=null)
				{
				
					swrite=swrite.concat(ptemp.val);
					swrite=swrite.concat("=CNF,");
					//fstt.write(swrite);
					ptemp=ptemp.next;
				}
				temp2.state=0;
				//swrite="";
				swrite=swrite.concat(temp2.val);
				swrite=swrite.concat("=NC, ");
				//fstt.write(swrite);
				temp2.state=1;
				//swrite="";
				//swrite.concat("transit ");
				swrite=swrite.concat(temp2.val);
				swrite=swrite.concat(":NC->CNF ");
				//fstt.write("transit ");
				//fstt.write(swrite);
				//fstt.write("next ");
				//swrite=swrite.concat("next ");
				ptemp=phead;
				while(ptemp!=null)
				{
					//swrite="";
					swrite=swrite.concat(ptemp.val);
					swrite=swrite.concat("=CNF,");
					//fstt.write(swrite);
					ptemp=ptemp.next;
				}
				//swrite="";
				swrite=swrite.concat(temp2.val);
				swrite=swrite.concat("=CNF,");
				//fstt.write(swrite);
				grand_child=temp2.start;
				while(grand_child!=null)
				{
					//swrite="";
					swrite=swrite.concat(grand_child.val);
					swrite=swrite.concat("=NC,");
					//fstt.write(swrite);
					grand_child=grand_child.next;
				}
				fstt.write(swrite);
				fstt.newLine();
				transitions++;
			//	fstt.write("path present ");
				if(temp2.type==2)
				{
					flag=1;
					ptemp=phead;
					swrite="";
					while(ptemp!=null)
					{
				
						swrite=swrite.concat(ptemp.val);
						swrite=swrite.concat("=CNF,");
						//fstt.write(swrite);
						ptemp=ptemp.next;
					}
					//swrite="";
					swrite=swrite.concat(temp2.val);
					swrite=swrite.concat("=CNF, ");
					//fstt.write(swrite);
					temp2.state=2;
					//swrite="";
					//swrite=swrite.concat("transit ");
					swrite=swrite.concat(temp2.val);
					swrite=swrite.concat(":CNF->FU ");
					//fstt.write("transit ");
					//fstt.write(swrite);
					//swrite=swrite.concat("next ");
					//fstt.write("next ");
					ptemp=phead;
					while(ptemp!=null)
					{
						//swrite="";
						swrite=swrite.concat(ptemp.val);
						swrite=swrite.concat("=CNF,");
						//fstt.write(swrite);
						ptemp=ptemp.next;
					}
					//swrite="";
					swrite=swrite.concat(temp2.val);
					swrite=swrite.concat("=FU,");
					fstt.write(swrite);
					fstt.newLine();
					transitions++;
					
				}
				else if(temp2.type==1)
				{	swrite="";
					ptemp=phead;
					while(ptemp!=null)
					{
						
						swrite=swrite.concat(ptemp.val);
						swrite=swrite.concat("=CNF,");
						//fstt.write(swrite);
						ptemp=ptemp.next;
					}
					//swrite="";
					swrite=swrite.concat(temp2.val);
					swrite=swrite.concat("=CNF,");
					//fstt.write(swrite);
					grand_child=temp2.start;
					while(grand_child!=null)
					{
						//swrite="";
						swrite=swrite.concat(grand_child.val);
						swrite=swrite.concat("=FU,");
						//fstt.write(swrite);
						grand_child=grand_child.next;
					}
				temp2.state=2;	
				swrite=swrite.concat("transit ");
				//swrite="";
				//swrite=swrite.concat(" ");
				swrite=swrite.concat(temp2.val);
				swrite=swrite.concat(":CNF->FU ");
				//fstt.write(" transit ");
				//fstt.write(swrite);
				//fstt.write("next ");
				swrite=swrite.concat("next ");
				ptemp=phead;
				while(ptemp!=null)
				{
					//swrite="";
					swrite=swrite.concat(ptemp.val);
					swrite=swrite.concat("=CNF,");
					//fstt.write(swrite);
					ptemp=ptemp.next;
				}
				//swrite="";
				swrite=swrite.concat(temp2.val);
				swrite=swrite.concat("=FU,");
				fstt.write(swrite);
				fstt.newLine();
				transitions++;
				
				}
				else if(temp2.type==0)
				{
					grand_child=temp2.start;
					while(grand_child!=null)
					{
						//fstt.write("path present");
						ptemp=phead;
						swrite="";
						while(ptemp!=null)
						{
							
							swrite=swrite.concat(ptemp.val);
							swrite=swrite.concat("=CNF,");
							//fstt.write(swrite);
							ptemp=ptemp.next;
						}
						//swrite="";
						swrite=swrite.concat(temp2.val);
						swrite=swrite.concat("=CNF,");
						//fstt.write(swrite);
						//swrite="";
						swrite=swrite.concat(grand_child.val);
						swrite=swrite.concat("=FU, ");
						//fstt.write(swrite);
						//swrite="";
						//swrite=swrite.concat("transit ");
						swrite=swrite.concat(temp2.val);
						swrite=swrite.concat(":CNF->FU ");
						//swrite=swrite.concat("next ");
						//fstt.write("transit ");
						//fstt.write(swrite);
						//fstt.write("next ");
						ptemp=phead;
						while(ptemp!=null)
						{
							//swrite="";
							swrite=swrite.concat(ptemp.val);
							swrite=swrite.concat("=CNF,");
							//fstt.write(swrite);
							ptemp=ptemp.next;
						}
						//swrite="";
						swrite=swrite.concat(temp2.val);
						swrite=swrite.concat("=FU,");
						fstt.write(swrite);
						fstt.newLine();
						
						grand_child=grand_child.next;
						
						
						
						
					}
					transitions++;
				}
				child=child.next;
				
			}
			
			
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}finally {
			if(fstt!=null) try {
				fstt.close();
			}catch(IOException ioe2) {
				
			}
			else {
				
			}
		}
		temp=head;
		while((temp.val.compareTo(s))!=0)
			temp=temp.next;
		child=temp.start;
		while(child!=null)
		{
			generateFSM(child.val);
			temp=head;
			while((temp.val.compareTo(child.val))!=0)
				temp=temp.next;
			if(temp.start!=null)
				deletep();
			
			child=child.next;
		}
		
		
	}
	static void task(String s)
	{
		Node t=head;
		variable child,grand_child;
		parentPath ptemp;
		variable2 var[]=new variable2[20000];
		list temp,temp2,temp3;
		Node2 p1,p2,n1,n2,index,temp1;
		BufferedWriter fstt=null;
		String swrite=null;
		String temps=null;
		String temptransit=null;
		String[] arr=new String[25000];
		int n=0,k,i,is=0,j,count=0;
		int num;
		int index1,index2;
		//String parent1=dependee.concat("=NC");
		//String parent2=dependee.concat("=CNF");
		//String childs=depender.concat("=FU");
		//String parent3=dependee.concat("=FU");
		//String childs2=depender.concat("=NC");
		//String childs3=depender.concat("=CNF");
		//String ctransit=depender.concat(":CNF->FU ");
		long checkindex[]=new long[20000];
		int ci=0;
		
		//Scanner fpermu=null;
		try {
			fstt=new BufferedWriter(new FileWriter("c://Users/Mandira Roy/eclipse-workspace/novadsl1/stt.ndsl",true));
			//fstt.write("path presentss");
			k=100;
			while((t.val.compareTo(s))!=0)
				t=t.next;
			//System.out.println("Value of t is: "+t.val);
			child=t.start;
			while(child!=null)
			{
				n++;
				//System.out.println("Value of child is and n: "+child.val+n);
				arr[n]=child.val;
				child=child.next;
			}
			/*int d=1;
			System.out.println("Content of arr");
			while(d<=n) {
				System.out.println(arr[d]);
				d++;
			}*/
			for(i=0,j=0;i<n;i++)
		  	{
		  	  var[i]=new variable2();
		      var[i].state1=k+1;
		      a[j++]=var[i].state1;
		      var[i].state2=k+2;
		      a[j++]=var[i].state2;
		      k=k+100;
		  	}
			//for(int d=0;d<j;d++)
				//System.out.print("here="+a[d]);
			
			noOfTerms=j;
			//System.out.println("The terms="+noOfTerms);
			permute(noOfTerms);
			lhead=null;
			try {
				DataInputStream fpermu = new DataInputStream(new FileInputStream("permute.txt"));
				i=0;
				column=0;
				index1=0;
				index2=0;
				int flagc=0;
				int invalid_paths=0;
				//Getting invalid paths for type 1 and type 2
				int c=0;
				while(c<tp)
				{
					//System.out.println(typepath[c]);
					c++;
				}
				int countcol=0;
				index1=0;
				while(permute[index1][column]!=0)
				{
					countcol++;
					column++;
				}
				//System.out.println("Number of columns is"+(countcol-1)/2);
				int c1=102;
				int c2=202;
				int c3=201;
				//System.out.println(tp);
				countcol=(countcol-1)/2;
				while(countcol>0 && cp<tp)
				{
				index1=0;
				column=0;
				flagc=0;
				//System.out.println("Entering loop");
				int flagexists;
				if(typepath[cp]==1 || typepath[cp]==2)
				{
					//System.out.println("Checking for type 1");
					while(index1<row)
					{
						flagexists=0;
						int q=0;
						while(q<ci)
						{
							if(checkindex[q]==permute[index1][0])
							{
								flagexists=1;
								break;
							}
							else q++;
								
						}
						if(flagexists==0)
						{
						if(permute[index1][column]!=0) {
							//System.out.print(permute[index1][column]+" ");
							column++;
						if(permute[index1][column]==c1)
							flagc=1;
						if(permute[index1][column]==c2 && flagc!=1)
						{
							invalid_paths++;
							//System.out.println("yes");
							checkindex[ci]=permute[index1][0];
									ci++;
						}
							
						}
						else {
							//System.out.println();
							index1++;
							column=0;
							flagc=0;
						}
						}
						else
						{
							index1++;
							column=0;
							flagc=0;
						}
						
					}
				}
				//Getting invalid paths for type 3
			
				else if(typepath[cp]==3)
				{
					//System.out.println("Checking for type 3");
					int flagp=0;
					while(index1<row)
					{
						flagexists=0;
						int q=0;
						while(q<ci)
						{
							if(checkindex[q]==permute[index1][0])
							{
								flagexists=1;
								break;
							}
							else q++;
								
						}
						if(flagexists==0)
						{
								if(permute[index1][column]!=0) {
							//System.out.print(permute[index1][column]+" ");
							column++;
						if(permute[index1][column]==c3)
							flagc=1;
						if(permute[index1][column]==c1 && flagc!=1)
						{
							invalid_paths++;
							//System.out.println("invalid path");
							checkindex[ci]=permute[index1][0];
							ci++;
							
						}
						if(permute[index1][column]==c1)
							flagp=1;
						if(permute[index1][column]==c2 && flagp!=1)
						{
							//System.out.println("invalid path");
							int z=0;
							/*while(permute[index1][z]!=0)
							{
							System.out.print(permute[index1][z]+" ");
							z++;
							}*/
							invalid_paths++;
							checkindex[ci]=permute[index1][0];
									ci++;
							
						}
							
						}
						else {
							//System.out.println();
							index1++;
							column=0;
							flagc=0;
							flagp=0;
						}
						}
						else
						{
							index1++;
							column=0;
							flagc=0;
							flagp=0;
							
						}
						
					}
				}
				c1=c1+100;
				c2=c2+100;
				c3=c3+100;
				countcol-=2;
				cp++;
				//System.out.println("the count now is"+invalid_paths);
				int no=0;
				/*while(no<ci)
				{
					System.out.println(checkindex[no]);
					no++;
				}*/
				}
				ci=0;
				
				index1=0;
				column=0;
				while(index1<row)
				{
					if(permute[index1][column]!=0) {
						//System.out.print(permute[index1][column]+" ");
						column++;
					}
						else {
							//System.out.println();
							index1++;
							column=0;
							flagc=0;
						}
						
				}
				index1=0;
				column=0;
				
				int paths=row-invalid_paths;
				
				pathtemp=paths+pathtemp;
				//System.out.println("Number of valid paths= "+paths);
				//System.out.println("Number of path is"+pathtemp);
			while(index1<row) {
				if(permute[index1][column]!=0)
				{
					num=permute[index1][column];
					column++;
				}
				else {
					column=0;
					index1++;
					num=permute[index1][column];
					column++;
				}
				//System.out.println(num);
			createlist();	
			//fpermu.next();
			temp=lhead;
			while(temp.next!=null)
				temp=temp.next;
	  	  	for(i=1;i<=n;i++)
	  	  	{
	  	  		//System.out.println("arra="+arr[i]);
	  	  		temp.ps=create(temp.ps,arr[i],0);
	  	  	}
	  	  	/*Node2 z=temp.ps;
	  	  	while(z!=null)
	  	  	{
	  	  	System.out.println("z="+z.variable);
	  	  	z=z.next;
	  	  	}*/
	  	  	
	  	  	count=0;
	  	  	temp=lhead;
	  	  while(count!=j)
	  	  	{
	  	  		//System.out.println("COUNT="+count);
	  	//	num=fpermu.readInt();
	  		if(permute[index1][column]!=0)
			{
				num=permute[index1][column];
				column++;
			}
			else {
				column=0;
				index1++;
				num=permute[index1][column];
				column++;
			}
	  		//System.out.println("NUM="+num);
		 	   	temp=lhead;
	            while(temp.next!=null)
	            	temp=temp.next;
	            	if(arr[num/100]!=null)
			   	temp.ns=create(temp.ns,arr[num/100],num%100);
			   //	z=temp.ns;
			  /* 	System.out.println("zns=");
		  	  	while(z!=null)
		  	  	{
		  	  	System.out.println(z.variable);
		  	  	z=z.next;
		  	  	}*/
		  	  	
				temp1=temp.ps;
	            while(temp1!=null)
	            {
	              	temp1=temp1.next;
	            }
	                
	            count++;
				temp1=temp.ps;
				while(temp1!=null)
				{
					if(temp1.variable!=arr[num/100])
					{	//System.out.println("temp1.variable="+temp1.variable);
							if(temp1.variable!=null)
						temp.ns=create(temp.ns,temp1.variable,temp1.state);
					}
					    temp1=temp1.next;
				}
				//z=temp.ns;
				/*System.out.println("zns=");
		  	  	while(z!=null)
		  	  	{
		  	  	System.out.println(z.variable);
		  	  	z=z.next;
		  	  	}*/
	
				temp1=temp.ns;
	            while(temp1!=null)
	            {
	            	//printf("V%d=%d,",(-1)*temp1->variable,temp1->state);
					temp1=temp1.next;
	            }
				     
				if(count!=j)
				{
					createlist();
				    temp1=temp.ns;
				    temp=lhead;
				    while(temp.next!=null)
	                	temp=temp.next;
				    while(temp1!=null)
				    {
					   	temp.ps=create(temp.ps,temp1.variable,temp1.state);
					    temp1=temp1.next;
				    }
				}    
		  	}
	  	  	index1++;
	  	  	column=0;
	  	  	
			}
			row=0;
			}catch (FileNotFoundException e)
			{
				System.out.println("File not found1");
			}
			catch (IOException ioe)
			{
				System.out.println(ioe);
			}
			temp=lhead;
		  	while(temp!=null)
		  	{
				temp2=temp;
				p1=temp.ps;
				n1=temp.ns;
				//System.out.println("p1="+p1.variable);
				//System.out.println("n1="+n1.variable);
				while(temp2.next!=null)
			    {
					temp3=temp2.next;
				    p2=temp3.ps;
					n2=temp3.ns;
					//System.out.println("p2="+p2.variable);
					//System.out.println("n2="+n2.variable);
					int c1,c2;
		    		if((c1=check(p1,p2))==1 && (c2=check(n1,n2))==1)
					{
						temp2.next=temp3.next;
						continue;
					}
					temp2=temp2.next;          
			    }
				temp=temp.next;
		  	}
		  	count=0;
		  	temp=lhead;
		  	while(temp!=null)
		  	{
				count++;
			  	//printf("\nPRESENT STATE\n");
			   	temp1=temp.ps;
		       	while(temp1!=null)
		       	{
		        	//printf("V%d=%d",(-1)*temp1->variable,temp1->state);
		            temp1=temp1.next;
		       	}
			   	//printf("\nNEXT STATE\n");
			   	temp1=temp.ns;
		    	while(temp1!=null)
		       	{
		        	//printf("V%d=%d,",(-1)*temp1->variable,temp1->state);
					temp1=temp1.next;
		       	}
				//getchar();
			   	temp=temp.next; 
		  	}
		  	temp=lhead;
		  	/*temp1=temp.ns;
		  	System.out.print("here ns:");
		  	while(temp1!=null)
		  	{
		  		System.out.print(temp1.variable);
		  		temp1=temp1.next;
		  	}*/
		  	while(temp!=null)
		  	{
		  		swrite="";
		  		//swrite.concat("path present ");
		  		flag1=0;
		  		flag2=0;
		  		flag3=0;
		  		flag4=0;
		  		flag5=0;
		  		flag6=0;
		  		check=0;
				p1=temp.ps;
				n1=temp.ns;
				index=identify(p1,n1);
				//System.out.println("the state is"+index.state);
				t=head;
				while((t.val.compareTo(index.variable))!=0)
			   		t=t.next;
				if(index.state==1)
				{
					if(t.type==2)
					{
						ptemp=phead;
						
						while(ptemp!=null)
						{
							temps="";
							swrite=swrite.concat(ptemp.val);
							swrite=swrite.concat("=CNF,");
							temps=temps.concat(ptemp.val);
							temps=temps.concat("=CNF");
							//fstt.write(swrite);
							/*if(type==1 || type==2)
								check_f_Or_U(temps,parent1,parent2,childs);
							else if(type==3)
								check_X(temps,parent1,parent2,childs,parent3,childs2,childs3);*/
							//System.out.println("please check");
							check_compliance(temps,0);
							ptemp=ptemp.next;
						}
					
					temp1=temp.ps;
					while(temp1!=null)
					{
						//swrite="";
						temps="";
						swrite=swrite.concat(temp1.variable);
						temps=temps.concat(temp1.variable);
						if(temp1.state==0) {
							swrite=swrite.concat("=NC,");
							temps=temps.concat("=NC");}
						else if(temp1.state==1) {
							swrite=swrite.concat("=CNF,");
							temps=temps.concat("=CNF");}
						else {
							swrite=swrite.concat("=FU,");
							temps=temps.concat("=FU");}
						/*if(type==1 || type==2)
							check_f_Or_U(temps,parent1,parent2,childs);
						else if(type==3)
							check_X(temps,parent1,parent2,childs,parent3,childs2,childs3);*/
						//System.out.println("please check");
						check_compliance(temps,0);
						//fstt.write(swrite);
						temp1=temp1.next;
					}
					//swrite="";
					swrite=swrite.concat(" ");
					//swrite=swrite.concat(" transit ");
					swrite=swrite.concat(index.variable);
					swrite=swrite.concat(":CNF->FU ");
					temptransit="";
					temptransit=temptransit.concat(index.variable);
					temptransit=temptransit.concat(":CNF->FU ");
					//X_Next_state(temptransit,ctransit);
					//System.out.println("please check");
					check_compliance(temptransit,1);
					if(flag3==1 && flag5==1 && flag6==1)
					{
						check=1;
						//System.out.println("here");
					}
					
					//fstt.write(" transit ");
					//fstt.write(swrite);
					//fstt.write("next ");
					swrite=swrite.concat("next ");
					ptemp=phead;
					while(ptemp!=null)
					{
						//swrite="";
						temps="";
						swrite=swrite.concat(ptemp.val);
						swrite=swrite.concat("=CNF,");
						temps=temps.concat(ptemp.val);
						temps=temps.concat("=CNF");
						/*if(type==1 || type==2)
							check_f_Or_U(temps,parent1,parent2,childs);
						else if(type==3)
							check_X(temps,parent1,parent2,childs,parent3,childs2,childs3);*/
						//System.out.println("please check");
						check_compliance(temps,0);
						//fstt.write(swrite);
						ptemp=ptemp.next;
					}
				
				temp1=temp.ns;
				while(temp1!=null)
				{
					//swrite="";
					temps="";
					swrite=swrite.concat(temp1.variable);
					temps=temps.concat(temp1.variable);
					if(temp1.state==0) {
						swrite=swrite.concat("=NC,");
						temps=temps.concat("=NC");}
					else if(temp1.state==1) {
						swrite=swrite.concat("=CNF,");
						temps=temps.concat("=CNF");
					}
					else {
						swrite=swrite.concat("=FU,");
						temps=temps.concat("=FU");
					}
					//fstt.write(swrite);
					/*if(type==1 || type==2)
						check_f_Or_U(temps,parent1,parent2,childs);
					else if(type==3)
						check_X(temps,parent1,parent2,childs,parent3,childs2,childs3);*/
					//System.out.println("please check");
					check_compliance(temps,0);
					temp1=temp1.next;
				}
				/*if(flag1==1 && flag2==1)
				{ 
					System.out.println("true");
				}
				else if(flag3==1 && flag4==1)
				{
					System.out.println("true");
				}
				else if(check==1) {
					System.out.println("true");
					//System.out.println("yippee");
				}*/
				//System.out.println("please check validity");
				//System.out.println("true1"+swrite);
				check_validity();
				if(valid==0)
				{
					
				fstt.write(swrite);
					fstt.newLine();
					transitions++;
					//System.out.println("true1"+swrite);
				}
				valid=0;
					//fstt.write("path present ");
					}
					else if(t.type==1)
					{
						ptemp=phead;
						swrite="";
						while(ptemp!=null)
						{
							temps="";
							swrite=swrite.concat(ptemp.val);
							swrite=swrite.concat("=CNF,");
							temps=temps.concat(ptemp.val);
							temps=temps.concat("=CNF");
							//fstt.write(swrite);
							/*if(type==1 || type==2)
								check_f_Or_U(temps,parent1,parent2,childs);
							else if(type==3)
								check_X(temps,parent1,parent2,childs,parent3,childs2,childs3);*/
							//System.out.println("please check");
							check_compliance(temps,0);
							ptemp=ptemp.next;
						}
						temp1=temp.ps;
						while(temp1!=null)
						{
							//swrite="";
							temps="";
							swrite=swrite.concat(temp1.variable);
							temps=temps.concat(temp1.variable);
							if(temp1.state==0) {
								swrite=swrite.concat("=NC,");
								temps=temps.concat("=NC");
							}
							else if(temp1.state==1) {
								swrite=swrite.concat("=CNF,");
								temps=temps.concat("=CNF");
							}
							else {
								swrite=swrite.concat("=FU,");
								temps=temps.concat("=FU");}
							//fstt.write(swrite);
						/*	if(type==1 || type==2)
								check_f_Or_U(temps,parent1,parent2,childs);
							else if(type==3)
								check_X(temps,parent1,parent2,childs,parent3,childs2,childs3);*/
						//	System.out.println("please check");
							check_compliance(temps,0);
							temp1=temp1.next;
						}
						grand_child=t.start;
						while(grand_child!=null)
						{
							//swrite="";
							temps="";
							swrite=swrite.concat(grand_child.val);
							swrite=swrite.concat("=FU,");
							temps=temps.concat(grand_child.val);
							temps=temps.concat("=FU");
							/*if(type==1 || type==2)
								check_f_Or_U(temps,parent1,parent2,childs);
							else if(type==3)
								check_X(temps,parent1,parent2,childs,parent3,childs2,childs3);*/
							//System.out.println("please check");
							check_compliance(temps,0);
							//fstt.write(swrite);
							grand_child=grand_child.next;
						}
						//swrite="";
						swrite=swrite.concat(" ");
						//swrite=swrite.concat(" transit ");
						swrite=swrite.concat(index.variable);
						swrite=swrite.concat(":CNF->FU ");
						temptransit="";
						temptransit=temptransit.concat(index.variable);
						temptransit=temptransit.concat(":CNF->FU ");
					//	X_Next_state(temptransit,ctransit);
						//System.out.println("please check");
						check_compliance(temptransit,1);
						if(flag3==1 && flag4==1 && flag6==1)
						{
							check=1;
							//System.out.println("here");
						}
						//fstt.write(" transit ");
						//fstt.write(swrite);
						//fstt.write("next ");
						swrite=swrite.concat("next ");
						ptemp=phead;
						while(ptemp!=null)
						{
							//swrite="";
							temps="";
							swrite=swrite.concat(ptemp.val);
							swrite=swrite.concat("=CNF,");
							temps=temps.concat(ptemp.val);
							temps=temps.concat("=CNF");
							/*if(type==1 || type==2)
								check_f_Or_U(temps,parent1,parent2,childs);
							else if(type==3)
								check_X(temps,parent1,parent2,childs,parent3,childs2,childs3);*/
							//System.out.println("please check");
							check_compliance(temps,0);
							//fstt.write(swrite);
							ptemp=ptemp.next;
						}
						temp1=temp.ns;
						while(temp1!=null)
						{
							//swrite="";
							temps="";
							swrite=swrite.concat(temp1.variable);
							temps=temps.concat(temp1.variable);
							if(temp1.state==0) {
								swrite=swrite.concat("=NC,");
								temps=temps.concat("=NC");
							}
							else if(temp1.state==1) {
								swrite=swrite.concat("=CNF,");
								temps=temps.concat("=CNF");
							}
							else {
								swrite=swrite.concat("=FU,");
								temps=temps.concat("=FU");
							}
						/*	if(type==1 || type==2)
								check_f_Or_U(temps,parent1,parent2,childs);
							else if(type==3)
								check_X(temps,parent1,parent2,childs,parent3,childs2,childs3);*/
							//System.out.println("please check");
							check_compliance(temps,0);
							//fstt.write(swrite);
							temp1=temp1.next;
						}
						/*if(flag1==1 && flag2==1)
						{
							System.out.println("true");
						}
						else if(flag3==1 && flag4==1) {
							System.out.println("true");
						}
						else if(check==1) {
							System.out.println("true");
						}
						else {*/
						//System.out.println("please check validity");
						//System.out.println("true1"+swrite);
						check_validity();
						if(valid==0)
						{
						//fstt.write("path present "+swrite);
							fstt.write(swrite);
						fstt.newLine();
						transitions++;
						//System.out.println("true2"+swrite);
						}
						}
						//fstt.write("path present ");
				
					else if(t.type==0)
					{
						//System.out.println("stuck here");
						grand_child=t.start;
						while(grand_child!=null)
						{
							//System.out.println("stuck here");
							ptemp=phead;
							swrite="";
							while(ptemp!=null)
							{
								temps="";
								swrite=swrite.concat(ptemp.val);
								swrite=swrite.concat("=CNF,");
								//System.out.println("Here it is:"+swrite);
								temps=temps.concat(ptemp.val);
								temps=temps.concat("=CNF");
								//fstt.write(swrite);
								/*if(type==1 || type==2)
									check_f_Or_U(temps,parent1,parent2,childs);
								else if(type==3)
									check_X(temps,parent1,parent2,childs,parent3,childs2,childs3);*/
									//System.out.println("please check");
								check_compliance(temps,0);
								ptemp=ptemp.next;
							}
							temp1=temp.ps;
							while(temp1!=null)
							{
								//swrite="";
								temps="";
								swrite=swrite.concat(temp1.variable);
								//System.out.println("Here it is:"+swrite);
								temps=temps.concat(temp1.variable);
								
								if(temp1.state==0) {
									swrite=swrite.concat("=NC,");
									temps.concat("=NC");}
								else if(temp1.state==1) {
									swrite=swrite.concat("=CNF,");
									temps=temps.concat("=CNF");
								}
								else {
									swrite=swrite.concat("=FU,");
									temps=temps.concat("=FU");}
								//fstt.write(swrite);
							/*	if(type==1 || type==2)
									check_f_Or_U(temps,parent1,parent2,childs);
								else if(type==3)
									check_X(temps,parent1,parent2,childs,parent3,childs2,childs3);*/
								//System.out.println("please check");
								check_compliance(temps,0);
								//System.out.println(temps);
								temp1=temp1.next;
							}
							//swrite="";
							swrite=swrite.concat(grand_child.val);
							swrite=swrite.concat("=FU,");
							temps="";
							temps=temps.concat(grand_child.val);
							temps=temps.concat("=FU");
						/*	if(type==1 || type==2)
								check_f_Or_U(temps,parent1,parent2,childs);
							else if(type==3)
								check_X(temps,parent1,parent2,childs,parent3,childs2,childs3);*/
							//System.out.println("please check");
							check_compliance(temps,0);
							//fstt.write(swrite);
							//swrite="";
							swrite=swrite.concat(" ");
							//swrite=swrite.concat(" transit ");
							swrite=swrite.concat(index.variable);
							swrite=swrite.concat(":CNF->FU ");
							//swrite=swrite.concat("next ");
							temptransit="";
							temptransit=temptransit.concat(index.variable);
							temptransit=temptransit.concat(":CNF->FU ");
							//X_Next_state(temptransit,ctransit);
							if(flag3==1 && flag5==1 && flag6==1)
							{
								check=1;
								//System.out.println("here");
							}
							//fstt.write(" transit ");
							//fstt.write(swrite);
							//fstt.write("next ");
							ptemp=phead;
							while(ptemp!=null)
							{
								//swrite="";
								temps="";
								swrite=swrite.concat(ptemp.val);
								swrite=swrite.concat("=CNF,");
								temps=temps.concat(ptemp.val);
								temps=temps.concat("=CNF");
								/*if(type==1 || type==2)
									check_f_Or_U(temps,parent1,parent2,childs);
								else if(type==3)
									check_X(temps,parent1,parent2,childs,parent3,childs2,childs3);*/
								//System.out.println("please check");
								check_compliance(temps,0);
								//fstt.write(swrite);
								ptemp=ptemp.next;
							}
							temp1=temp.ns;
							while(temp1!=null)
							{
								//swrite="";
								temps="";
								swrite=swrite.concat(temp1.variable);
								temps=temps.concat(temp1.variable);
								if(temp1.state==0) {
									swrite=swrite.concat("=NC,");
									temps=temps.concat("=NC");}
								else if(temp1.state==1) {
									swrite=swrite.concat("=CNF,");
									temps=temps.concat("=CNF");
								}
								else {
									swrite=swrite.concat("=FU,");
									temps=temps.concat("=FU");
								}
								//fstt.write(swrite);
								/*if(type==1 || type==2)
									check_f_Or_U(temps,parent1,parent2,childs);
								else if(type==3)
									check_X(temps,parent1,parent2,childs,parent3,childs2,childs3);*/
								//System.out.println("please check");
								check_compliance(temps,0);
								temp1=temp1.next;
							}
						/*	if(flag1==1 && flag2==1)
							{
								
							}
							else if(flag3==1 && flag4==1) {
								System.out.println("true");
							}
							else if(check==1) {
								System.out.println("true");
							}
							else {*/
							check_validity();
							//System.out.println("true1"+swrite);
							if(valid==0)
							{
							//fstt.write("path present "+swrite);
								fstt.write(swrite);
							fstt.newLine();
							transitions++;
							//System.out.println("true3"+swrite);
							}
							grand_child=grand_child.next;
						}
					}
				}
				else if(index.state==0)
				{
					//System.out.println("I am here");
					ptemp=phead;
					swrite="";
					while(ptemp!=null)
					{
						temps="";
						swrite=swrite.concat(ptemp.val);
						swrite=swrite.concat("=CNF,");
						temps=temps.concat(ptemp.val);
						temps=temps.concat("=CNF");
						/*if(type==1 || type==2)	
							check_f_Or_U(temps,parent1,parent2,childs);
						else if(type==3)
							check_X(temps,parent1,parent2,childs,parent3,childs2,childs3);*/
						//System.out.println("please check");
						check_compliance(temps,0);
						//fstt.write(swrite);
						ptemp=ptemp.next;
					}
					temp1=temp.ps;
					while(temp1!=null)
					{
						//swrite="";
						temps="";
						swrite=swrite.concat(temp1.variable);
						temps=temps.concat(temp1.variable);
						if(temp1.state==0) {
							swrite=swrite.concat("=NC,");
							temps=temps.concat("=NC");}
						else if(temp1.state==1) {
							swrite=swrite.concat("=CNF,");
							temps=temps.concat("=CNF");}
						else {
							swrite=swrite.concat("=FU,");
							temps=temps.concat("=FU");
						}
						/*if(type==1 || type==2)
							check_f_Or_U(temps,parent1,parent2,childs);
						else if(type==3)
							check_X(temps,parent1,parent2,childs,parent3,childs2,childs3);*/
						//System.out.println("please check");
						check_compliance(temps,0);
						//System.out.println(temps);
						//fstt.write(swrite);
						temp1=temp1.next;
					}
				
					//swrite="";
					swrite=swrite.concat(" ");
					//swrite=swrite.concat(" transit ");
					swrite=swrite.concat(index.variable);
					swrite=swrite.concat(":NC->CNF ");
					temptransit="";
					temptransit=temptransit.concat(index.variable);
					temptransit=temptransit.concat(":NC->CNF ");
					//X_Next_state(temptransit,ctransit);
					//System.out.println("please check");
					check_compliance(temptransit,1);
					//System.out.println(swrite);
					//System.out.println(flag3+" "+flag5+" "+flag6);
					if(flag3==1 && flag5==1 && flag6==1)
					{
						check=1;
						//System.out.println("should be");
					}
					//swrite=swrite.concat("next ");
					//fstt.write(" transit ");
					//fstt.write(swrite);
					//fstt.write("next ");
					ptemp=phead;
					while(ptemp!=null)
					{
						//swrite="";
						temps="";
						swrite=swrite.concat(ptemp.val);
						swrite=swrite.concat("=CNF,");
						temps=temps.concat(ptemp.val);
						temps=temps.concat("=CNF");
						/*if(type==1 || type==2)
							check_f_Or_U(temps,parent1,parent2,childs);
						else if(type==3)
							check_X(temps,parent1,parent2,childs,parent3,childs2,childs3);*/
						//System.out.println("please check");
						check_compliance(temps,0);
						//fstt.write(swrite);
						ptemp=ptemp.next;
					}
					temp1=temp.ns;
					while(temp1!=null)
					{
						//swrite="";
						temps="";
						swrite=swrite.concat(temp1.variable);
						temps=temps.concat(temp1.variable);
						if(temp1.state==0) {
							swrite=swrite.concat("=NC,");
							temps=temps.concat("=NC");
							}
						else if(temp1.state==1) {
							swrite=swrite.concat("=CNF,");
							temps=temps.concat("=CNF");}
						else {
							swrite=swrite.concat("=FU,");
							temps=temps.concat("=FU");}
						/*if(type==1 || type==2)
							check_f_Or_U(temps,parent1,parent2,childs);
						else if(type==3)
							check_X(temps,parent1,parent2,childs,parent3,childs2,childs3);*/
						//System.out.println("please check");
						check_compliance(temps,0);
						//fstt.write(swrite);
						temp1=temp1.next;
					}
					grand_child=t.start;
					while(grand_child!=null)
					{
						//swrite="";
						temps="";
						swrite=swrite.concat(grand_child.val);
						swrite=swrite.concat("=NC,");
						temps=temps.concat(grand_child.val);
						temps=temps.concat("=NC");
						/*if(type==1 || type==2)
							check_f_Or_U(temps,parent1,parent2,childs);
						else if(type==3)
							check_X(temps,parent1,parent2,childs,parent3,childs2,childs3);*/
						//System.out.println("please check");
						check_compliance(temps,0);
						//fstt.write(swrite);
						grand_child=grand_child.next;
					}
					/*if(flag1==1 && flag2==1)
					{
						System.out.println("true");
						
					}
					else if(flag3==1 && flag4==1) {
						System.out.println("true");
					}
					else if(check==1) {
						System.out.println("true");
					}
					else {*/
					//System.out.println("please check validity");
					//System.out.println("true1"+swrite);
					check_validity();
					if(valid==0)
					{
					//fstt.write("path present "+swrite);
						fstt.write(swrite);
					fstt.newLine();
				transitions++;
				//System.out.println("true4"+swrite);
					}
					//fstt.write("path present ");
					

					
				}
				temp=temp.next;
		  	}
		}
			

		
		catch(IOException ioe) {
			ioe.printStackTrace();
		}finally {
			if(fstt!=null) try {
				fstt.close();
			}catch(IOException ioe2) {
				
			}
			else {
				
			}
		}
		t=head;
		while((t.val.compareTo(s))!=0)
			t=t.next;
		child=t.start;
		while(child!=null)
		{
			//printf("\nWorking with child %d.", child->val*(-1));
			generateFSM(child.val);
			t=head;
			while((t.val.compareTo(child.val))!=0)
				t=t.next;
			if(t.start != null)
				deletep();
			child=child.next;
		}
		
		
		
		
	}
	static void deletep()
	{
		parentPath p,q,r;
		p=phead;
		if(p==null)
			return;
		else if(p.next==null)
		{
			phead=null;
			
			return;
		}
		else
		{
			r=p.next;
			while(r.next!=null)
			{
				p=p.next;
				r=p.next;
			}
			q=p.next;
			p.next=null;
				
		}
		
	}
	static void permute(int num)
	{
		int i;
	    if(num==0)
	 	   display_td();
	    else
	    {
	    	for(i=num-1;i>=0;i--)
	      	{
		      	swap(i,num-1);
	          	permute(num-1);
	          	swap(i,num-1);
	      	}
	    }
	}
	static void display_td()
	{
		//System.out.println("In displaying");
		int x, y, z, flag, count_fl=0;
		byte b;
		column=0;
		BufferedWriter ftemp=null;
		DataOutputStream fp=null;
		try {
			//fpermute=new FileOutputStream("permute.txt");	
			 //fpwrite = new BufferedWriter(new FileWriter("permute.txt"));
		 fp = new DataOutputStream(new FileOutputStream("permute.txt"));
	    for(x=0;x<noOfTerms;x++)
	    {  
			flag = 0;
		    if(a[x]%10 == 1)
			{
		 	  	y = a[x] / 100;
		     	for(z=x+1; z<noOfTerms; z++)
		     	{
					if(a[z]/100==y && a[z]%10==2)
					{
						flag=1;
						break;
					}
		     	}
			}
			else if(a[x]%10 == 2)
			{
		    	y = a[x] / 100;
		     	for(z=0; z<=(x-1); z++)
		     	{
					if(a[z]/100==y && a[z]%10==1)
					{
						flag= 1;
						break;
					}
		     	}
			}
			if(flag ==1)
				count_fl++;
		}
	    if(count_fl==noOfTerms)
	    {
	    	//System.out.println("count equal="+count_fl);
	    permute[row][column]=++count1;
	    column=column+1;
			for(x=0; x<noOfTerms; x++)
			{
			    permute[row][column]=a[x];
			    column++;
		  		//fpwrite.writeChars(" ");
			}
			  row++;
	    }
	  
	    //fpwrite.write(123);
	    //fpwrite.close();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}finally {
			if(fp!=null) try {
				fp.close();
			}catch(IOException ioe2) {
				
			}
			else {
				
			}
		}
		/*try {
		//ftemp=new BufferedWriter(new FileWriter("permute.txt",true));
			//ftemp.newLine();
			//ftemp.close();
			
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}finally {
			if(ftemp!=null) try {
				ftemp.close();
			}catch(IOException ioe2) {
				
			}
			else {
				
			}
		}*/
	}
	static void swap(int i, int j)
	{
		int temp;
		temp=a[i];
		a[i]=a[j];
		a[j]=temp;
	}
	static void createlist()
	{
		list temp=new list();
		list ptr;
	    temp.ps=null;
	    temp.ns=null;
	    temp.next=null;
	    if(lhead==null)
	    	lhead=temp;
	    else
	    {
	       	ptr=lhead;
	       	while(ptr.next!=null)
	          	ptr=ptr.next;
	      	ptr.next=temp;
	    }
    	ptr=lhead;
    	
	    
	}
	static Node2 create(Node2 start,String val,int num)
	{ 
		Node2 temp,ptr;
	    temp=new Node2();
	    temp.variable=val;
	    temp.state=num;
	    temp.next=null;
	    if(start==null)
	       	start=temp;
	    else
	    {
	       	ptr=start;
	       	while(ptr.next!=null)
	          	ptr=ptr.next;
	      	ptr.next=temp;
	    }
	    return start;
	} 
	static int check(Node2 p1,Node2 p2)
	{
		Node2 temp1,temp2;
		int flag=0,st;
		String var;
		temp1=new Node2();
		temp1=p1;
		while(temp1!=null)
		{	temp2=new Node2();
			temp2=p2;
			var=temp1.variable;
			st=temp1.state;
			//System.out.println("temp1="+temp1.variable);
			//System.out.println("state="+temp1.state);
			//System.out.println("temp2ss="+p2.variable);
			while(temp2!=null)
	        {
				//System.out.println("temp2s="+temp2.variable);
				//System.out.println("state2="+temp2.state);
				if((var.compareTo(temp2.variable))==0 && st!=temp2.state)
				{
			    	flag=1;
			     	break;
			   	}
			   	temp2=temp2.next;
			}
			
			if(flag == 1)
				break;

			temp1=temp1.next;
		}
		if(flag==0)
			return 1;
		else
			return 0;
	}
	static Node2 identify(Node2 p1,Node2 p2)
	{
		Node2 temp1,temp2;
		int st;
		String var;
			
		temp1=p1;
		while(temp1!=null)
		{
			temp2=p2;
			var=temp1.variable;
			st=temp1.state;
			while(temp2!=null)
	        {
				if((var.compareTo(temp2.variable))==0 && st!=temp2.state)
				   	return temp1;
			 	else if((var.compareTo(temp2.variable))==0 && st==temp2.state)
					break;
				else if((var.compareTo(temp2.variable))!=0)
			   		temp2=temp2.next;
			}
			temp1=temp1.next;
		}
		return null;
	}
	static void insert_dependency()
	{
		String depender="ghi:CNF->FU";
		String dependee="mno=FU";
		FileReader f1=null;
		BufferedWriter temp=null;
		int i;
		char c;
		String swrite;
		try {
			f1=new FileReader("c://Users/Mandira Roy/eclipse-workspace/novadsl1/stt.ndsl");
			temp=new BufferedWriter(new FileWriter("temp.txt",true));
			while((i=f1.read())!=-1) // Loop to read the entire input file
			{
				c=(char)i;
				while(c!=' ')
				{
					temp.write(c);
					i=f1.read();
					c=(char)i;
				}
				i=f1.read();
				c=(char)i;
				swrite="";
				while(c!=' ')
				{
					swrite=swrite.concat(Character.toString(c));
					i=f1.read();
					c=(char)i;
				}
				if(swrite.compareTo(depender)==0)
				{
					temp.write(dependee);
					temp.write(",");
					temp.write(" ");
					temp.write(depender);
					temp.write(" ");
				}
				i=f1.read();
				c=(char)i;
				while(c!='\n')
				{
					temp.write(c);
					i=f1.read();
					c=(char)i;
				}
				temp.write("\n");
				
			}
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}finally {
			if(f1!=null) try {
				f1.close();
				temp.close();
			}catch(IOException ioe2) {
			}
			else {
			}
			}
		
		
	}

	public static void check_f_Or_U(String temp1, String parent1, String parent2, String childs)
	{
		if(temp1.compareTo(parent1)==0)
			flag1=1;
		else if(temp1.compareTo(parent2)==0)
			flag1=1;
		else if(temp1.compareTo(childs)==0)
			flag2=1;
		
	}
	public static void check_X(String temp1, String parent1, String parent2, String childs,String parent3,String childs2,String childs3)
	{
		if(temp1.compareTo(parent1)==0)
			flag1=1;
		else if(temp1.compareTo(parent2)==0)
			flag1=1;
		else if(temp1.compareTo(childs)==0)
			flag2=1;
		else if(temp1.compareTo(parent3)==0)
			flag3=1;
		else if(temp1.compareTo(childs2)==0)
			flag4=1;
		else if(temp1.compareTo(childs3)==0)
			flag5=1;	
	}
	public static void X_Next_state(String temp1,String ctransit)
	{
		if(temp1.compareTo(ctransit)!=0)
		{
			flag6=1;
			//System.out.println(temp1);
			//System.out.println(ctransit);
		}
	}
	public static void convert_to_global()
	{
		String temp="";
		if(type==1)
		{
		//AG((G1=FU) -> AF(G2=FU)) 
			temp=temp.concat("AG((");
			temp=temp.concat(dependee);
			temp=temp.concat("=FU) -> AF(");
			temp=temp.concat(depender);
			temp=temp.concat("=FU))");
			
		}
		else if(type==2)
		{
			//AG(NOT(G2=FU)U(G1=FU) 
			temp=temp.concat("AG(NOT(");
			temp=temp.concat(depender);
			temp=temp.concat("=FU) U (");
			temp=temp.concat(dependee);
			temp=temp.concat("=FU))");		
		}
		else if(type==3)
		{
			//AG((G1=FU) -> AX(G2=FU)) 
			temp=temp.concat("AG((");
			temp=temp.concat(dependee);
			temp=temp.concat("=FU) -> AX(");
			temp=temp.concat(depender);
			temp=temp.concat("=FU))");
		}
		System.out.println("The corresponding version of the CTL formula using global operator: "+temp);
		
	}
	
	public static void check_CTL_global_or_existential(String str)
	{
		startTime = System.currentTimeMillis();
		char[] temp=str.toCharArray();
		if(temp[0]=='A')
			check_CTL_type2(str);
		else
		{
			//System.out.println(str);
			check_CTL_type(str);
		}
		head=null;
	}
	public static void check_CTL_type2(String str)
	{
		  int count = 0;
		  int idx = 0;
		  ctlrow=0;
		  String substring="AND";
		  System.out.println("The extracted CTL is "+str);
		     while ((idx = str.indexOf(substring, idx)) != -1)
		     {
		        idx++;
		        count++;
		     }
		   // System.out.println(count);
		     int formula=count+1;
		//AG((G1=FU) -> AF(G2=FU)) :type1
		//AG(NOT(G2=FU) U (G1=FU) 	:type2
		//AG((G1=FU) -> AX(G2=FU)) 	:type3
		char[] temp=str.toCharArray();
		if(count==0)
		{
			//System.out.println("Only one");
		int i=0;
		while(temp[i]!='(')
		{
			i++;
		}
		if(temp[i+1]=='(')
		{
			i+=2;
			dependee="";
			while(temp[i]!='=')
			{
				dependee=dependee.concat(Character.toString(temp[i]));
				i++;
			}
			ctlList[ctlrow][0]=dependee;
			i+=9;
			if(temp[i]=='F')
			{
				ctlList[ctlrow][2]="1";
				typepath[tp]=1;
				tp++;
			}
			else 
			{
				ctlList[ctlrow][2]="3";
				typepath[tp]=3;
				tp++;
			}
			i+=2;
			depender="";
			while(temp[i]!='=')
			{
				depender=depender.concat(Character.toString(temp[i]));
				i++;
			}	
			ctlList[ctlrow][1]=depender;
			
		}
		else if(temp[i+1]=='N')
		{
			ctlList[ctlrow][2]="2";
			typepath[tp]=2;
			tp++;
			i+=5;
			depender="";
			while(temp[i]!='=')
			{
				depender=depender.concat(Character.toString(temp[i]));
				i++;
			}
			ctlList[ctlrow][1]=depender;
			i+=8;
			dependee="";
			while(temp[i]!='=')
			{
				dependee=dependee.concat(Character.toString(temp[i]));
				i++;
			}	
			ctlList[ctlrow][0]=dependee;
		}
		ctlrow++;
		//System.out.println("The index is:"+ctlrow);
	}
		else if(count>0)
		{
			int i=0;
			
			int index=0;
			//AG(((EnterLoginId=FU) -> AX(EnterPassword=FU)) AND ((Login=FU) -> AF(Get_E_Book=FU)))
			//AG((NOT(UseLocker=FU) U (GetAccess=FU)) AND (NOT(Close_Locker=FU) U (GetAccess=FU)) AND ((CloseLocker=FU)->NOT(AF(UseLocker=FU)))  
			//System.out.println("The number of formulas is "+formula);
			while(i<formula)
			{
				//System.out.println("hello");
			
				while(temp[index]!='(')
				{
					index++;
				}
				if(i==0)
					index+=2;
				else index++;
				if(temp[index]=='(')
				{
					
					dependee="";
					index++;
					while(temp[index]!='=')
					{
						dependee=dependee.concat(Character.toString(temp[index]));
						index++;
					}
					ctlList[ctlrow][0]=dependee;
					index+=9;
					if(temp[index]=='F')
					{
						//System.out.println("yes1");
						type=1;
						ctlList[ctlrow][2]=Integer.toString(type);
						//System.out.println(ctlList[ctlrow][2]);
						typepath[tp]=1;
						tp++;
					}
					else 
					{
						//System.out.println("yes2");
						type=3;
						ctlList[ctlrow][2]=Integer.toString(type);
						//System.out.println(ctlList[ctlrow][2]);
						typepath[tp]=3;
						tp++;
					}
			
					index+=2;
					depender="";
					while(temp[index]!='=')
					{
						depender=depender.concat(Character.toString(temp[index]));
						index++;
					}
					ctlList[ctlrow][1]=depender;
					
				}
				else if(temp[index]=='N')
				{
					type=2;
					ctlList[ctlrow][2]="2";
					typepath[tp]=2;
					tp++;
					index+=4;
					depender="";
					while(temp[index]!='=')
					{
						depender=depender.concat(Character.toString(temp[index]));
						index++;
					}
					ctlList[ctlrow][1]=depender;
					index+=8;
					dependee="";
					while(temp[index]!='=')
					{
						dependee=dependee.concat(Character.toString(temp[index]));
						index++;
					}
					ctlList[ctlrow][0]=dependee;
				}
				
				//System.out.println("Dependee is :"+dependee+"Depender is"+depender);
				
				ctlrow++;
				i++;
			}
			
		}
		int i=0;
		/*System.out.println("The content is:");
		while(i<ctlrow)
		{
			System.out.println("dependee is"+ctlList[i][0]+"depender is"+ctlList[i][1]+"And type is"+ctlList[i][2]);
			i++;
		}*/
		//System.out.println("The extracted CTL is: "+str);
		//System.out.println("The type is: "+type);
		//System.out.println("Dependee is :"+dependee+"Depender is"+depender);
		//convert_to_existential();
		convert_to_existential();
		create_final_ctl_list();
		
	}
	public static void convert_to_existential()
	{
		String temp="";
	
		if(ctlrow==1)
		{
			temp=temp.concat("NOT(EF((");
			if((ctlList[0][2].compareTo("1")==0))
			{
				//NOT(EF((G1=FU) AND NOT( EF(G2=FU))))
				temp=temp.concat(dependee);
				temp=temp.concat("=FU) AND EF(NOT(");
				temp=temp.concat(depender);
				temp=temp.concat("=FU))))");
				
			}
			else if((ctlList[0][2].compareTo("2")==0))
			{
				//NOT(EF((G2=FU) AND (NOT(G1=FU))))
				temp=temp.concat(depender);
				temp=temp.concat("=FU) AND (NOT(");
				temp=temp.concat(dependee);
				temp=temp.concat("=FU))))");
				
				
			}
			else if((ctlList[0][2].compareTo("3")==0))
			{
				//NOT(EF((G1=FU) AND EX(NOT(G2=FU))))
				temp=temp.concat(dependee);
				temp=temp.concat("=FU) AND EX(NOT(");
				temp=temp.concat(depender);
				temp=temp.concat("=FU))))");	
			}
		}
		else if(ctlrow>1)
		{
			int i=0;
			//NOT(EF(((EnterLoginId=FU) AND EX(NOT(EnterPassword=FU))) AND ((EnterLoginId=FU) AND NOT(EF(Get_E_book=FU)))))
			//NOT(EF(((Use_locker=FU) AND (NOT(Get_accesss=FU))) AND ((Close_locker=FU) AND (NOT(Get_accesss=FU))) AND ((Use_locker=FU) AND EX(NOT(Get_accesss=FU))) AND ((enter_access_code=FU) AND NOT(EF(verify_code=FU)))))
			temp="";
			temp=temp.concat("NOT(EF(");
			while(i<ctlrow)
			{
				temp=temp.concat("((");
				//System.out.println("The variable is"+ctlList[i][0]);
				//System.out.println("The variable is"+ctlList[i][2]);
				if((ctlList[i][2].compareTo("1")==0))
				{
					//System.out.println("The variable is");
					temp=temp.concat(ctlList[i][0]);
					temp=temp.concat("=FU) AND EF(NOT(");
					temp=temp.concat(ctlList[i][1]);
					temp=temp.concat("=FU)))");
				}
				else if((ctlList[i][2].compareTo("2")==0))
				{
					temp=temp.concat(ctlList[i][1]);
					temp=temp.concat("=FU) AND (NOT(");
					temp=temp.concat(ctlList[i][0]);
					temp=temp.concat("=FU)))");
				}
				else if((ctlList[i][2].compareTo("3")==0))
				{
					temp=temp.concat(ctlList[i][0]);
					temp=temp.concat("=FU) AND EX(NOT(");
					temp=temp.concat(ctlList[i][1]);
					temp=temp.concat("=FU)))");
				}
			
				i++;
				if(i<ctlrow)
					temp=temp.concat(" AND ");
				
			}
			temp=temp.concat("))");
		}
		System.out.println("The corresponding existential version of the formula: ");
		System.out.println(temp);
		
		
	}
	public static void create_final_ctl_list()
	{
		int index=0;
		int k=0;
		while(index<ctlrow)
		{
			if((ctlList[index][2].compareTo("1")==0))
			{
				String temp="";
				temp=ctlList[index][0].concat("=NC");
				ctlListfinal[k][0]=temp;
				temp="";
				temp=ctlList[index][1].concat("=FU");
				ctlListfinal[k][1]=temp;
				k++;
				temp="";
				temp=ctlList[index][0].concat("=CNF");
				ctlListfinal[k][0]=temp;
				temp="";
				temp=ctlList[index][1].concat("=FU");
				ctlListfinal[k][1]=temp;
				k++;
				
			}
			else if((ctlList[index][2].compareTo("2")==0))
			{
				String temp="";
				temp=ctlList[index][0].concat("=NC");
				ctlListfinal[k][0]=temp;
				temp="";
				temp=ctlList[index][1].concat("=FU");
				ctlListfinal[k][1]=temp;
				k++;
				temp="";
				temp=ctlList[index][0].concat("=CNF");
				ctlListfinal[k][0]=temp;
				temp="";
				temp=ctlList[index][1].concat("=FU");
				ctlListfinal[k][1]=temp;
				k++;
			}
			else if((ctlList[index][2].compareTo("3")==0))
			{
				String temp="";
				temp=ctlList[index][0].concat("=NC");
				ctlListfinal[k][0]=temp;
				temp="";
				temp=ctlList[index][1].concat("=FU");
				ctlListfinal[k][1]=temp;
				k++;
				temp="";
				temp=ctlList[index][0].concat("=CNF");
				ctlListfinal[k][0]=temp;
				temp="";
				temp=ctlList[index][1].concat("=FU");
				ctlListfinal[k][1]=temp;
				k++;
				temp="";
				temp=ctlList[index][0].concat("=FU");
				ctlListfinal[k][0]=temp;
				temp="";
				temp=ctlList[index][1].concat(":CNF->FU");
				ctlListfinal[k][1]=temp;
				k++;
				temp="";
				temp=ctlList[index][0].concat("=FU");
				ctlListfinal[k][0]=temp;
				temp="";
				temp=ctlList[index][1].concat("=NC");
				ctlListfinal[k][1]=temp;
				k++;		
				
			}
				
			index++;
		}
		ctlrow=k;
		int i=0;
		/*while(i<ctlrow)
		{
			System.out.println(ctlListfinal[i][0]);
			System.out.print(ctlListfinal[i][1]);
			System.out.print(ctlListfinal[i][2]);
			System.out.print(ctlListfinal[i][3]);
			i++;
		}*/
		index=0;
		while(index<ctlrow)
		{
			ctlListfinal[index][2]="0";
			ctlListfinal[index][3]="0";
		
			index++;
		}
		Node p=head;
		//long startTime = System.currentTimeMillis();
		generateFSM(p.val);
		System.out.println("Number of transitions generated: "+transitions);
		System.out.println("Number of valid paths= "+pathtemp);	
		  long endTime = System.currentTimeMillis();
		  long duration = (endTime - startTime);  //Total execution time in milli seconds
		     
		    System.out.println("Execution Time is : "+duration+"milliseconds");
		//skeleton();
	}
	public static void check_compliance(String temp,int type)
	{
		int index=0;
		//System.out.println("temp="+temp);
		while(index<ctlrow)
		{
			if(type==0)
			{
				//System.out.println("temp="+temp);
				//System.out.println(ctlListfinal[index][0]);
				//System.out.println(ctlListfinal[index][1]);
			if(temp.compareTo(ctlListfinal[index][0])==0)
			{
				ctlListfinal[index][2]="1";
				//System.out.println("matched1");
			}
			else if(temp.compareTo(ctlListfinal[index][1])==0)
			{
				ctlListfinal[index][3]="1";
				//System.out.println("matched2");
			}
			}
		
			index++;
		}
		
	}
	public static void check_validity()
	{
		int index=0;
		valid=0;
		int i=0;
		/*System.out.println("before validitry");
		while(i<ctlrow)
		{
			System.out.println(ctlListfinal[i][0]);
			System.out.print(ctlListfinal[i][1]);
			System.out.print(ctlListfinal[i][2]);
			System.out.print(ctlListfinal[i][3]);
			i++;
		}*/
		while(index<ctlrow)
		{
			if((ctlListfinal[index][2]=="1") && (ctlListfinal[index][3]=="1"))
			{
				valid=1;
				break;
			}
			index++;
		}
		index=0;
		while(index<ctlrow)
		{
			ctlListfinal[index][2]="0";
			ctlListfinal[index][3]="0";
		
			index++;
		}
		
	}
	public static void setFSM_name(String s)
	{
		BufferedWriter fstt=null;
		try {
			fstt=new BufferedWriter(new FileWriter("c://Users/Mandira Roy/eclipse-workspace/novadsl1/stt.ndsl",true));
			fstt.write("fsm ");
			fstt.write(s);
			fstt.newLine();
			//fstt.write("path present ");
			fstt.close();
		}
		catch (FileNotFoundException e)
		{
			
			System.out.println("File not found");
		}
		catch (IOException ioe)
		{
			System.out.println(ioe);
		}
		
	}
	public static void skeleton()
	{
		BufferedWriter fstt=null;
		FileReader f1=null;
		String fname;
		char c;
		String temp=null;
		System.out.println("hello skeleton");
		System.out.println("The actor count is"+actcount);
		try {
			fstt=new BufferedWriter(new FileWriter("c://Users/Mandira Roy/eclipse-workspace/novadsl1/NUSMV_input.smv",true));
			fstt.write("MODULE main VAR");
			fstt.newLine();
		
			
			int j=1;
			while(j<=actcount)
			{
				
				//fstt=new BufferedWriter(new FileWriter("c://Users/Mandira Roy/eclipse-workspace/novadsl1/NUSMV_input.smv",true));
				fname="C:\\Users\\Mandira Roy\\dsl\\VAR"+j+".txt";
				f1=new FileReader(fname);
				System.out.println(fname);
				int i;
				while((i=f1.read())!=-1) // Loop to read the entire input file
				{
					c=(char)i;
					//System.out.println(c);
					//System.out.println(c+"int="+i);
					temp="";
					temp=temp.concat(Character.toString(c));
					while((i=f1.read())!=10)
					{
						c=(char)i;
						//System.out.println(c+"int="+i);
						temp=temp.concat(Character.toString(c));
						
						
					}
					temp=temp.concat(" : {NC, CNF, FU};");
					System.out.println(temp);
					fstt.write(temp);
					fstt.newLine();
				}
				f1.close();
				j++;
				//fstt.close();
				System.out.println("The value of i is"+j);
				
			}
			fstt.newLine();
			fstt.write("ASSIGN");
			fstt.newLine();
			j=1;
			while(j<=actcount)
			{
				
				//fstt=new BufferedWriter(new FileWriter("c://Users/Mandira Roy/eclipse-workspace/novadsl1/NUSMV_input.smv",true));
				fname="C:\\Users\\Mandira Roy\\dsl\\VAR"+j+".txt";
				f1=new FileReader(fname);
				System.out.println(fname);
				int i;
				while((i=f1.read())!=-1) // Loop to read the entire input file
				{
					c=(char)i;
					//System.out.println(c);
					//System.out.println(c+"int="+i);
					temp="init(";
					temp=temp.concat(Character.toString(c));
					while((i=f1.read())!=10)
					{
						c=(char)i;
						//System.out.println(c+"int="+i);
						temp=temp.concat(Character.toString(c));
						
						
					}
					temp=temp.concat(") :=NC;");
					System.out.println(temp);
					fstt.write(temp);
					fstt.newLine();
				}
				f1.close();
				j++;
				//fstt.close();
				//System.out.println("The value of i is"+j);
				
			}
			j=1;
			fstt.newLine();
			while(j<=actcount)
			{
				
				//fstt=new BufferedWriter(new FileWriter("c://Users/Mandira Roy/eclipse-workspace/novadsl1/NUSMV_input.smv",true));
				fname="C:\\Users\\Mandira Roy\\dsl\\VAR"+j+".txt";
				f1=new FileReader(fname);
				System.out.println(fname);
				int i;
				while((i=f1.read())!=-1) // Loop to read the entire input file
				{
					c=(char)i;
					//System.out.println(c);
					//System.out.println(c+"int="+i);
					temp="next(";
					temp=temp.concat(Character.toString(c));
					String input="";
					input=input.concat(Character.toString(c));
					
					while((i=f1.read())!=10)
					{
						c=(char)i;
						//System.out.println(c+"int="+i);
						temp=temp.concat(Character.toString(c));
						input=input.concat(Character.toString(c));
						
						
					}
					temp=temp.concat(") :=");
					fstt.write(temp);
					fstt.newLine();
					fstt.write("case");
					fstt.newLine();
					fstt.write("\tTrue: ");
					fstt.write(input);
					fstt.write(";");
					fstt.newLine();
					fstt.write("esac;");
					fstt.newLine();
					fstt.newLine();
					
					
					
				}
				f1.close();
				j++;
				//fstt.close();
				//System.out.println("The value of i is"+j);
				
			}
			fstt.close();
		}
		catch (FileNotFoundException e)
		{
			
			System.out.println("File not found");
		}
		catch (IOException ioe)
		{
			System.out.println(ioe);
		}
	map();
	}
	public static void map()
	{
		FileReader fstt=null,fsmv=null;
		BufferedWriter ftemp=null;
		String fname,fname2;
		char c;
		int iteration=0;
		fname="C://Users/Mandira Roy/eclipse-workspace/novadsl1/stt.ndsl";
		try {
		fstt=new FileReader(fname);
		int i;
		while((i=fstt.read())!=-1) {
			String str1="";
			String str2="";
			String str3="";
			String str4="";
			System.out.println("hello");
			c=(char)i;
			str1=str1.concat(Character.toString(c));
			while((i=fstt.read())!=32)
			{
				c=(char)i;
				str1=str1.concat(Character.toString(c));
			}
			System.out.println("str1 is"+str1);
			while((i=fstt.read())!=32)
			{
				c=(char)i;
				str2=str2.concat(Character.toString(c));
			}
			System.out.println("str2 is"+str2);
			while((i=fstt.read())!=10)
			{
				c=(char)i;
			
				str3=str3.concat(Character.toString(c));
			}
			System.out.println("str3 is"+str3);
			int j=0;
			char[] temp = new char[100];
			temp=str2.toCharArray();
			System.out.println("temp is ");
			System.out.print(temp);
			while(temp[j]!=':')
			{
				c=temp[j];
				str4=str4.concat(Character.toString(c));
				j++;
			}
			System.out.println("str4 is"+str4);
			
		
		fname2="C://Users/Mandira Roy/eclipse-workspace/novadsl1/NUSMV_input.smv";
		fsmv=new FileReader(fname2);
		ftemp=new BufferedWriter(new FileWriter("c://Users/Mandira Roy/eclipse-workspace/novadsl1/Temp.smv",false));
		int flag=0;
		while((i=fsmv.read())!=-1)
		{
			//System.out.println("Hello2");
			c=(char)i;
			ftemp.write(c);
			if(c=='n' && flag==0)
			{
				i=fsmv.read();
				c=(char)i;
				ftemp.write(c);
				if(c=='e')
				{
					//flag=1;
					for(int jp=1;jp<=3;jp++)
					{
						i=fsmv.read();
						c=(char)i;
						//System.out.println("c="+c);
						ftemp.write(c);
					}
					String ncase="";
			
					while((i=fsmv.read())!=13 )
					{
						c=(char)i;
						ftemp.write(c);
						if(c==')')
							break;
						//System.out.println("c="+c+"i="+i);
						ncase=ncase.concat(Character.toString(c));
						
					}
					System.out.println("ncase is:"+ncase);
					for(int jp=1;jp<=13;jp++)
					{
						i=fsmv.read();
						
						c=(char)i;
						if(c=='V')
							break;
						ftemp.write(c);
						System.out.print("c="+c);
					}
					System.out.println("ncase is:"+ncase);
					System.out.println("str4 is:"+str4);
					if(ncase.equals(str4))
					{
						System.out.println("Found equal");
						flag=1;
						temp=null;
						System.out.println("str1 is"+str1);
						char[] temp1=new char[100];
						//System.out.println("temps is"+temp1);
						temp1=str1.toCharArray();
						//System.out.println("temps is"+temp1);
						System.out.println("temp is ");
						System.out.print(temp1);
						int k=0;
						int len=str1.length();
						//for(int p=0;p<len)
						String str6;
						while(k<len)
						{
							c=temp1[k];
							if(temp1[k]==',' && (k+1)<len )
								ftemp.write(" & ");
							else if(temp1[k]==',' && (k+1)==len )
							{
								
							}
							else
								ftemp.write(c);
							k++;
						}
						ftemp.write(" : ");
						temp=null;
						temp=str2.toCharArray();
						len=str2.length();
						//System.out.println("len is"+len);
						//System.out.println(temp);
						System.out.println("temp is ");
						System.out.println(temp);
						//temp[len]='\0';
						k=0;
						while(temp[k]!='>')
						{
							System.out.println(temp[k]);
							k++;
						}
						k=k+1;
						//System.out.println(temp[k]);
						//System.out.println("Now k= "+k);
						String state=null;
						state="";
						while(k<len)
						{
							c=temp[k];
							//System.out.println("Now k= "+k);
							//System.out.println(temp[k]);
							state=state.concat(Character.toString(c));
							k++;
						}
						ftemp.write(state);
						ftemp.write(";");
						ftemp.newLine();
						ftemp.write("\t");
							
						
					}
					i=fsmv.read();
					c=(char)i;
					if(c!='T')
						ftemp.write("V");
					ftemp.write(c);
				}
				
			}
			
		}
		fsmv.close();
		ftemp.close();
		fname2="C://Users/Mandira Roy/eclipse-workspace/novadsl1/Temp.smv";
		fsmv=new FileReader(fname2);
		ftemp=new BufferedWriter(new FileWriter("c://Users/Mandira Roy/eclipse-workspace/novadsl1/NUSMV_input.smv",false));
		while((i=fsmv.read())!=-1)
		{
			c=(char)i;
			ftemp.write(c);
		}
		fsmv.close();
		ftemp.close();
		//i=fstt.read();
		//c=(char)i;
		//System.out.println("Next is "+c);
		iteration++;
		//if(iteration==4)
			//break;
		}
		fstt.close();
		}catch (FileNotFoundException e)
		{
			
			System.out.println("File not founds");
		}
		catch (IOException ioe)
		{
			System.out.println(ioe);
		}
		
	}
	public static void call_generate()
	{
		Node p=head;
		//long startTime = System.currentTimeMillis();
		startTime = System.currentTimeMillis();
		generateFSM(p.val);
		 long endTime = System.currentTimeMillis();
		  long duration = (endTime - startTime);  //Total execution time in milli seconds
		     
		    System.out.println(duration);
	}
}




		
	

