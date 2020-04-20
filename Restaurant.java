import java.util.Scanner;

class customerOrder
{
    boolean valueset=false;
    String str[]=new String[3];

    synchronized void d_takeOrder(Thread t)
    {
        if(valueset)
        {
            try
            {
                wait();        
            }catch(InterruptedException e)
            {
                System.out.println(e);
            }
        }
        System.out.println("\n"+t);
        try
        {
            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
            for(int i=0;i<3;i++)
            {
                System.out.print("\n Take an Order "+(i+1)+" :: ");
                str[i]=br.readLine();
            }
        }catch(IOException e)
        {
            System.out.println(e);
        }

        valueset=true;
        notify();
    }
    synchronized void d_dispOrder(Thread t)
    {
        if(!valueset)
        {
            try
            {
                wait();        
            }catch(InterruptedException e)
            {
                System.out.println(e);
            }
        }
        System.out.println("\n"+t);
        for(int i=0;i<3;i++)
        {
            System.out.print("\n Place an Order "+(i+1)+" :: "+str[i]);
        }
        valueset=false;
        notify();
    }
    
}
class takeOrder implements Runnable
{
    customerOrder d;
    Thread t;
    takeOrder(customerOrder d)
    {
        this.d=d;
        t=new Thread(this,"Manager take an order");
        t.start();
    }
    public void run()
    {
        for(int i=0;i<2;i++)
        {
            d.d_takeOrder(t);
        }
    }
}
class dispOrder implements Runnable
{
    customerOrder d;
    Thread t;
    dispOrder(customerOrder d)
    {
        this.d=d;
        t=new Thread(this,"Manager place an order");
        t.start();
    }
    public void run()
    {
        for(int i=0;i<2;i++)
        {
            d.d_dispOrder(t);
        }
    }
}
class Restaurant
{
    public static void main(String args[])
    {
        customerOrder d=new customerOrder();
        new takeOrder(d);
        new dispOrder(d);
    }
}