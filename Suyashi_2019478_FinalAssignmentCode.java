//By Suyashi Singhal 
//Roll no - 2019478
//Section A , Group 2 
import java.util.*;
public class Main
{
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.print("Enter the number of Cache Lines:     ");
		int CL = in.nextInt();                                  // no. of cache lines = no. of blocks 
		System.out.print("Enter the block size:     "); 
		int Block = in.nextInt();                               //amount of data in each block 
        int x = (int)(Math.log(Block)/Math.log(2));
		int y = (int)(Math.log(CL)/Math.log(2));
		String[] tag = new String[CL];
		String[][] Data = new String[CL][Block];
		cache q = new cache(); 
		for(int w=0; w< CL; w++)
		{
		    tag[w] = "";
		    for(int o=0; o<Block ; o++)
		    {
		        Data[w][o] = ""; 
		    }
		}
		System.out.print("Enter the type of cache you would like to choose : 'FA' - Fully Associative Cache, 'DM' - Direct Mapped Cache, 'SA' - Set Associative Cache -   ");
		String s = in.next();
		if(s.equals("FA"))
		{
		    q.fully(tag, Data, CL, x, Block);
		}
		if(s.equals("DM"))
		{
		    q.direct(tag, Data, CL, x, Block ); 
		}
		if(s.equals("SA"))
		{
		    System.out.print("Enter K for a K-way Set Associative cache:   ");
		    int k = in.nextInt();
		    q.Set(tag, Data, CL, x, k, Block ); 
		}
	}
}
class cache
{
    Scanner in = new Scanner(System.in); 
    public void fully(String[] T, String[][] data, int C, int X, int B )
    {
        System.out.print("What would u like to do - R: read, W: write ,P: print ,Q: quit -      "); 
        LinkedList<String> LRU = new LinkedList<String>();
        char s1 = in.next().charAt(0);
        while(s1!='Q')
        {
            if(s1=='W')
            {
                System.out.print("Enter the memory address:    "); 
                String ad = in.next();                            //memory address
                System.out.print("Enter the data to be written:     ");
                String a = in.next();                                       //data
                String off = ad.substring(ad.length()-X);                    //offset bits
                int offset = Integer.parseInt(off, 2);         
                String tagad = ad.substring(0, ad.length()-X);              //block address
                int n=0; 
                boolean put = false;
                while(n<C && put==false)
                {
                    if(T[n].equals(tagad))
                   {
                       if(data[n][offset].isEmpty()==false)
                       {
                           System.out.println("The data being replaced is :   " + data[n][offset]); 
                       }
                    System.out.println("WRITE HIT "); 
                    data[n][offset] = a;
                    put = true; 
                    LRU.remove(tagad);
                    LRU.addLast(tagad); 
                   }
                    n++; 
                }
                n=0; 
                while(n<C && put==false)
                { 
                    if(T[n].isEmpty()==true)
                    {
                        T[n] = tagad; 
                        data[n][offset] = a; 
                        put = true; 
                      System.out.println("WRITE MISS"); 
                      LRU.addLast(tagad); 
                    }
                    n++; 
                }
                if(put==false)
                {
                    String rem = LRU.getFirst(); 
                    LRU.removeFirst(); 
                    System.out.print("The removed tag and data is : " + rem +"            "); 
                    int remove = 0; 
                    for(int i=0; i<C; i++)
                    {
                        if(T[i].equals(rem))
                       {
                           remove = i; 
                           break; 
                       }
                    }
                    for(int j=0; j<B ; j++)
                    {
                        if(data[remove][j].isEmpty()==true)
                        {
                            System.out.print("-" + "            "); 
                        }
                        else
                        {System.out.print(data[remove][j] + "            ");
                        data[remove][j] = ""; 
                        }
                    }
                    System.out.println(); 
                    T[remove] = tagad; 
                    data[remove][offset] = a;
                    put = true; 
                    LRU.addLast(tagad); 
                    System.out.println("WRITE MISS"); 
                }
            }
            if(s1 =='R')
            {
                System.out.print("Enter the address of data to be read :   ");
                String read = in.next();
                String off = read.substring(read.length()-X); 
                int offset = Integer.parseInt(off, 2);
                String tagad = read.substring(0, read.length()-X);
                int count=0;
                boolean found = false; 
                while(count<C && found==false)
                {
                    if(T[count].equals(tagad)==true)
                    {
                        if(data[count][offset].isEmpty()==true)
                        {
                            break; 
                        }
                        else
                        {
                        System.out.println("READ HIT ");
                        System.out.println("Data is : " + data[count][offset]);
                        found = true; 
                        LRU.remove(tagad); 
                        LRU.addLast(tagad);
                        }
                    }

                    count++; 
                }
                if(found == false)
                System.out.println("READ MISS");
            }
            if(s1=='P')
            {
                System.out.print("BLOCK ADDRESS                           " );
                
                  System.out.println("DATA"); 
               for(int i=0; i<C; i++)
                {
                    if(T[i].isEmpty()==true)
                    System.out.print(i+1 + ") "+ " -  "+"                     ");
                    else
                    System.out.print(i+1 + ") "+  T[i]  +"                     ");
                    for(int j=0; j<B ; j++)
                    {
                        if(data[i][j].isEmpty()==true)
                        {
                            System.out.print("-" + "             "); 
                        }
                        else
                        System.out.print(data[i][j] + "         ");
                    }
                    System.out.println(); 
                }
            }
            if(s1=='Q')
            continue; 
             System.out.print("What would u like to do - R: read, W: write ,P: print ,Q: quit -   ");
            s1 = in.next().charAt(0);
        }
    }
    public void direct(String[] T, String[][] data, int C, int X, int B)
    {
        System.out.print("What would u like to do - R: read, W: write ,P: print ,Q: quit -   ");
        char s2  = in.next().charAt(0);
        while(s2!='Q')
        {
            if(s2=='W')
            {
                System.out.print("Enter the memory address:   "); 
                String A = in.next();
                System.out.print("Enter the data to be written:     ");
                String d = in.next();
                String off = A.substring(A.length()-X); 
                int cache = (int)(Math.log(C)/Math.log(2));
                int offset = Integer.parseInt(off, 2);
                String in = A.substring(A.length()-cache-X, A.length()-X); 
                int index = Integer.parseInt(in, 2);
                
                String add = A.substring(0,A.length()-cache-X); 
                if(T[index].isEmpty()==false)
                {
                    if(T[index].equals(add)==false)
                    {
                        System.out.print("The tag and data being replaced is : " + T[index] + "   -->    "); 
                        for(int q=0; q<B ; q++)
                    {
                        if(data[index][q].isEmpty()==true)
                        {
                            System.out.print("-" + "            "); 
                        }
                        else
                        {System.out.print(data[index][q] + "            ");
                        data[index][q] = ""; 
                        }
                    }
                    System.out.println(); 
                        T[index] = add;
                        data[index][offset] = d;
                        System.out.println("WRITE MISS "); 
                    }
                    else
                    {
                        if(data[index][offset].isEmpty()==false)
                        {
                            System.out.println("The tag is the same and the data being replaced is: " + data[index][offset]);
                            data[index][offset] = d; 
                            System.out.println("WRITE HIT "); 
                        }
                        else
                       { data[index][offset] = d; 
                        System.out.println("WRITE HIT "); 
                       }
                     }
                }
                else
               {
                   T[index] = add;
                    data[index][offset] = d;
                    System.out.println("WRITE MISS "); 
               }
                
                
            }
            if(s2=='R')
            {
                System.out.print("Enter the address from which data has to be read:     ");
                String ad = in.next();
                String off = ad.substring(ad.length()-X); 
                int offset = Integer.parseInt(off, 2);
                int cache =(int)( Math.log(C)/Math.log(2)); 
                String in = ad.substring(ad.length()-cache-X, ad.length()-X); 
                int index = Integer.parseInt(in, 2);
                String add = ad.substring(0, ad.length()-cache-X); 
                boolean found = false; 
                if(T[index].isEmpty()==false)
                {
                    if(T[index].equals(add)==true)
                    {
                    if(data[index][offset].isEmpty()==false)
                    
                    {   System.out.println("READ HIT");
                        System.out.println("The data in the given address is: " + data[index][offset]);
                    }
                    else
                    System.out.println("READ MISS");
                    }
                    else
                    System.out.println("READ MISS");
                }
                else
                System.out.println("READ MISS");
                
            }
            if(s2=='P')
            {

                System.out.print("BLOCK ADDRESS                           " );
                
                  System.out.println("DATA"); 
               for(int i=0; i<C; i++)
                {
                    if(T[i].isEmpty()==true)
                    System.out.print(i+1 + ") "+ " -  "+"                     ");
                    else
                    System.out.print(i+1 + ") "+  T[i]  +"                     ");
                    for(int j=0; j<B ; j++)
                    {
                        if(data[i][j].isEmpty()==true)
                        {
                            System.out.print("-" + "             "); 
                        }
                        else
                        System.out.print(data[i][j] + "         ");
                    }
                    System.out.println(); 
                }
            }
            if(s2=='Q')
            continue;
             System.out.print("What would u like to do - R: read, W: write ,P: print ,Q: quit -     ");
            s2 = in.next().charAt(0);
        }
    }
    
    public void Set(String[] T, String[][] data, int C, int X, int K, int B )
    {
        System.out.println("This is a "+ K + " way associative mapped cache  ");
        int u=C/K; 
        int set = (int)(Math.log(u)/Math.log(2));
        // set is the number of bytes required for indexing of each set 
        // u is the number of sets 
         System.out.print("What would u like to do - R: read, W: write ,P: print ,Q: quit -   ");
        char s3  = in.next().charAt(0);
        while(s3!='Q')
        {
            if(s3=='W')
            {
                System.out.print("Enter the memory address : ");
                String ad = in.next(); 
                System.out.print("Enter the data to be written:     ");
                String a = in.next(); 
                String off = ad.substring(ad.length()-X); 
                int offset = Integer.parseInt(off, 2);
                String in = ad.substring(ad.length()-set-X, ad.length()-X); 
                int index = Integer.parseInt(in, 2);// in*K to in*K + K -1
                int n = index*K ; 
                String add = ad.substring(0,ad.length()-set-X ); 
                boolean write  = false;
                int end = index*K+K -1; 
                while(n<=end && write ==false )
                {
                    if(T[n].equals(add)==true)
                    {if(data[n][offset].isEmpty()==false)
                    {
                        System.out.println("The tag is the same and the data being replaced is: " + data[n][offset]); 
                    }
                        data[n][offset]=a; 
                        write = true; 
                        System.out.println("WRITE HIT "); 
                    }
                    n++; 
                }
                n=index*K; 
                while(n<=end && write ==false )
                {
                    if(T[n].isEmpty()==true)
                    {
                        T[n] = add; 
                        data[n][offset]=a; 
                        write  = true; 
                        System.out.println("WRITE MISS "); 
                    }
                    n++; 
                }
                if(write == false)
                {
                    System.out.print("The removed tag and data is :  " + T[index*K ] + "   -->   " ); 
                    T[index*K] = add;
                     for(int q=0; q<B ; q++)
                    {
                        if(data[index*K][q].isEmpty()==true)
                        {
                            System.out.print("-" + "            "); 
                        }
                        else
                        {System.out.print(data[index*K][q] + "            ");
                        data[index*K][q] = ""; 
                    }
                    }
                    System.out.println(); 
                    data[index*K][offset] = a;
                    write  = true;
                    System.out.println("WRITE MISS "); 
                } 
            }
            if(s3=='R')
            {
                System.out.print("Enter the address to be read:      ");
                String ad = in.next(); 
                String off = ad.substring(ad.length()-X); 
                int offset = Integer.parseInt(off, 2);
                String in = ad.substring(ad.length()-set-X, ad.length()-X); 
                int index = Integer.parseInt(in, 2);// index*K to index*K + K -1
                int n = index*K ; 
                String add = ad.substring(0,ad.length()-set-X ); 
                boolean found = false; 
                while(n<= index*K+K-1)
                {
                    if(T[n].equals(add)==true)
                    {
                        if(data[n][offset].isEmpty()==false)
                        {found = true ; 
                        System.out.println("READ HIT"); 
                        System.out.println("The data is : " + data[n][offset]); 
                        }
                    }
                    n++;
                }
                if(found==false)
                {
                    System.out.println("READ MISS"); 
                }
                
            }
            if(s3=='P')
            {
                System.out.print("BLOCK ADDRESS                           " );
                
                  System.out.println("DATA"); 
               for(int i=0; i<C; i++)
                {
                    if(T[i].isEmpty()==true)
                    System.out.print(i+1 + ") "+ " -  "+"                     ");
                    else
                    System.out.print(i+1 + ") "+  T[i]  +"                     ");
                    for(int j=0; j<B ; j++)
                    {
                        if(data[i][j].isEmpty()==true)
                        {
                            System.out.print("-" + "             "); 
                        }
                        else
                        System.out.print(data[i][j] + "         ");
                    }
                    System.out.println(); 
                }
            }
            if(s3=='Q')
            continue;
             System.out.print("What would u like to do - R: read, W: write ,P: print ,Q: quit -    ");
            s3 = in.next().charAt(0);
        }
        
    }
}