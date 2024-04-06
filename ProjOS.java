// Ozbirn, 09/24/13
// Reads a name and prints Hello name

import java.util.Scanner;

public class ProjOS
{
	public static void main(String args[])
	{
      
		Scanner sc = new Scanner(System.in);
		int PC = 0;
		int SP = 1000;
		int AC = 0;
		int X = 0;
		int Y = 0;
		int IR = 0;
		
		int mode = 0;
	
		//request in the form Sout(Read/Write, Addr, Value, Mode)
		
		//temp vars for clean looking code
		int addr = 0;
		int nextAddr = 0;
		
		//get the timer
		int n = 0;
		String str = args[0];
		for (int i = 0; i < str.length(); i++) {
			n = n + (((int) str.charAt(i) - 48) * (int) Math.pow(10, str.length() - 1 - i));
		}
		int timer = n;
		int timerCt = 0;
		while (true){   
			
			if( (timerCt >= timer) && (mode != 1) ){
				mode = 1; //switch to kernel
				System.out.println("1, 1999, "+SP+", "+mode);
				if(sc.nextInt() != 0){
					System.out.println("4"); //write error
				}
				System.out.println("1, 1998, "+PC+", "+mode);
				if(sc.nextInt() != 0){
					System.out.println("4"); //write error
				}
				SP = 1998;
				PC = 1000;	
				timerCt = 0;
				continue;
			}
			if(mode != 1){
				timerCt++;
			}

			System.out.println("0, "+PC+", 0, "+mode);
			IR = sc.nextInt();
			PC++; 
			
			switch(IR){
				case 1:
					System.out.println("0, "+PC+", 0, "+mode);
					AC = sc.nextInt();
					PC++;
					break;

				case 2:
					System.out.println("0, "+PC+", 0, "+mode);
					addr = sc.nextInt();
					if( ( (addr > 999) && (mode==0) ) || ( (addr > 1999) && (mode==1) ) ){
						System.out.println("3");
						System.exit(2);
					} else {
						System.out.println("0, "+addr+", 0, "+mode);
						AC = sc.nextInt();
						PC++;
						break;
					}

				case 3:
					System.out.println("0, "+PC+", 0, "+mode);
					addr = sc.nextInt();
					System.out.println("0, "+addr+", 0, "+mode);
					nextAddr = sc.nextInt();
					System.out.println("0, "+nextAddr+", 0, "+mode);
					AC = sc.nextInt();
					PC++;
					break;

				case 4:
					System.out.println("0, "+PC+", 0, "+mode);
					addr = sc.nextInt();
					System.out.println("0, "+(addr+X)+", 0, "+mode);
					AC = sc.nextInt();
					PC++;
					break;

				case 5:
					System.out.println("0, "+PC+", 0, "+mode);
					addr = sc.nextInt();
					System.out.println("0, "+(addr+Y)+", 0, "+mode);
					AC = sc.nextInt();
					PC++;
					break;
				
				case 6:
					if( ( ((SP+X) > 999) && (mode==0) ) || ( ((SP+X) > 1999) && (mode==1) )){
						System.out.println("3"); //memory error
					}
					else {
						System.out.println("0, "+(SP + X)+", 0, "+mode);
						AC = sc.nextInt();
						PC++;
						break;
					}
				
				case 7:
					System.out.println("0, "+PC+", 0, "+mode);
					addr = sc.nextInt();
					System.out.println("1, "+addr+", "+AC+", "+mode);
					if(sc.nextInt() != 0){
						System.out.println("4"); //write error
					}
					PC++;
					break;

				case 8:
					AC = (int)(Math.random()*100) + 1;
					break;
				
				case 9:
					System.out.println("0, "+PC+", 0, "+mode);
					int port = sc.nextInt();
					System.out.println("2, "+port+", "+AC+", "+mode);
					if(sc.nextInt() != 0){
						System.out.println("5"); //write to screen error
					}
					PC++;
					break;
				
				case 10:
					AC += X;
					break;
				
				case 11:
					AC += Y;
					break;
				
				case 12:
					AC -= X;
					break;

				case 13:
					AC -= Y;
					break;
				
				case 14:
					X = AC;
					break;
				
				case 15:
					AC = X;
					break;

				case 16:
					Y = AC;
					break;
				
				case 17:
					AC = Y;
					break;
				
				case 18:
					SP = AC;
					break;
				
				case 19:
					AC = SP;
					break;
				
				case 20:
					System.out.println("0, "+PC+", 0, "+mode);
					PC = sc.nextInt();
					break;
				
				case 21:
					if(AC == 0){
						System.out.println("0, "+PC+", 0, "+mode);
						PC = sc.nextInt();
					}
					else {
						PC++;
					}
					break;
				
				case 22:
					if(AC != 0){
						System.out.println("0, "+PC+", 0, "+mode);
						PC = sc.nextInt();
					}
					else {
						PC++;
					}
					break;
				
				case 23:
					System.out.println("0, "+PC+", 0, "+mode);
					addr = sc.nextInt();
					SP--;
					PC++;
					System.out.println("1, "+SP+", "+PC+", "+mode);
					if(sc.nextInt() != 0){
						System.out.println("4"); //write error
					}
					PC = addr;
					break;
				
				case 24:
					System.out.println("0, "+SP+", 0, "+mode);
					PC = sc.nextInt();
					System.out.println("1, "+SP+", 0, "+mode);
					if(sc.nextInt() != 0){
						System.out.println("4"); //write error
					}
					SP++;
					break;
				
				case 25:
					X++;
					break;
				
				case 26:
					X--;
					break;
				
				case 27:
					SP--;
					System.out.println("1, "+SP+", "+AC+", "+mode);
					if(sc.nextInt() != 0){
						System.out.println("4"); //write error
					}
					AC = 0;
					break;
				
				case 28:
					System.out.println("0, "+SP+", 0, "+mode);
					AC = sc.nextInt();
					System.out.println("1, "+SP+", 0, "+mode);
					if(sc.nextInt() != 0){
						System.out.println("4"); //write error
					}
					SP++;
					break;

				case 29:
					if(mode != 1){
						mode = 1; //switch to kernel
						System.out.println("1, 1999, "+SP+", "+mode);
						if(sc.nextInt() != 0){
							System.out.println("4"); //write error
						}
						System.out.println("1, 1998, "+PC+", "+mode);
						if(sc.nextInt() != 0){
							System.out.println("4"); //write error
						}
						SP = 1998;
						PC = 1500;
					}
					break;
					
				case 30:
					System.out.println("0, "+SP+", 0, "+mode);
					PC = sc.nextInt();
					System.out.println("1, "+SP+", 0, "+mode);
					if(sc.nextInt() != 0){
						System.out.println("4"); //write error
					}
					SP++;
					
					System.out.println("0, "+SP+", 0, "+mode);
					int temp = sc.nextInt();
					System.out.println("1, "+SP+", 0, "+mode);
					if(sc.nextInt() != 0){
						System.out.println("4"); //write error
					}
					SP =  temp;
					mode = 0;
					break;

				case 50:
					System.out.println("6");
					System.exit(100);
					System.out.println("2, 2, Program failed to end, 0");
					break;
			}
			
		}
	}
}
