import java.io.*;
import java.util.Scanner;
import java.lang.Runtime;

public class Proc 
{
	public static void main(String args[])
	{  
		int[] memory = new int[2000];
		
		//Read files into memory
		try 
		{
			File setup = new File(args[0]);
			Scanner sc = new Scanner(setup);
			int index = 0;

			//read in raw lines consecutively
			while (sc.hasNextLine()) {
				String data = sc.nextLine();
				//System.out.println("Raw Read Data: "+data+", Index = "+index);

				//skip empty lines
				if (data.isEmpty()) {
					continue;
				}
				//skip lines with no number
				if(data.startsWith(" ")){
					continue;
				}

				//if the line is a jump starter, jump to that index
				if (data.startsWith(".")) {
					
					//clean the jump line
					int end = -1;
					for (int j = 1; j < data.length(); j++) {
						if (!charIsNum(data.charAt(j))){		
							end = j;
							break;
						}
						
                    		}
					
					if( end < 0 ){
						data = data.substring(1);
					}
					else{
						data = data.substring(1, end);
					}

					//jump to new index
					int addr = 0;
					addr = strToInt(data);
					index = addr;
				} 
				//else read the input into the current index
				else {
					//clean the instruction line
					for (int j = 0; j < data.length(); j++) {
						if (!charIsNum(data.charAt(j))) {
							data = data.substring(0, j);
						}
					}

					//read clean instruction line
					int readNum = strToInt(data);
					memory[index] = readNum;
					//System.out.println("memory["+index+"] got "+readNum);
					index++;
				}

			}
            
			//System.out.println("End of Readin");
			sc.close();
			index = 0;

		} 
		catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		
		//Run Memory for the CPU
		try
		{            
			int mode = 0;
			int addr = 0;
			Runtime rt = Runtime.getRuntime();

			Process proc = rt.exec("java ProjOS "+args[1]);

			InputStream is = proc.getInputStream();
			OutputStream os = proc.getOutputStream();

			PrintWriter pw = new PrintWriter(os);
			Scanner sc = new Scanner(is);
			while(proc.isAlive()){
		
				String inCmd = sc.nextLine();
				int rw = charToInt(inCmd, 0);
				//System.out.println("Raw String Command: " + inCmd);
				
				//read
				if( rw == 0 ){
					String modeStr = inCmd.substring(inCmd.length() - 1);
					String addrStr = inCmd.substring(3, inCmd.indexOf(",", 3));					
					
					mode = strToInt(modeStr);
					addr = strToInt(addrStr);

					if( (mode == 0 && (addr >= 0 && addr < 1000)) || (mode == 1 && (addr >= 1000 && addr < 2000)) ){
						pw.println(memory[addr]);
						pw.flush();
						//System.out.print("Read and sent to CPU: "+memory[addr]+" ");
					}
				}
				//write
				else if( rw == 1 ){
					String modeStr = inCmd.substring(inCmd.length() - 1);
					int valueInd = inCmd.indexOf(",", 3);
					String addrStr = inCmd.substring(3, valueInd);
					String valStr = inCmd.substring(valueInd + 2, inCmd.lastIndexOf(","));
					
					mode = strToInt(modeStr);
					addr = strToInt(addrStr);
					int val = strToInt(valStr);
					
					if( (mode == 0 && (addr >= 0 && addr < 1000)) || (mode == 1 && (addr >= 1000 && addr < 2000)) ){
						memory[addr] = val;
						pw.println(0);
						pw.flush();
						//System.out.print("Stored "+val+" at "+addr+" and sent confirmation to CPU");
					}
				} 
				else if( rw == 2 ){
					int valueInd = inCmd.indexOf(",", 3);
					String addrStr = inCmd.substring(3, valueInd);
					addr = strToInt(addrStr); //addr is port variable for this function
					if(addr == 1){
						String valStr = inCmd.substring(valueInd + 2, inCmd.lastIndexOf(","));
						int val = strToInt(valStr);
						System.out.println(val);
						pw.println(0);
						pw.flush();
					}
					else if(addr == 2){
						String valStr = inCmd.substring(valueInd + 2, inCmd.lastIndexOf(","));
						char letter = (char) strToInt(valStr);
						System.out.print(letter);
						
						pw.println(0);
						pw.flush();
					}
					else {
						pw.println(-1);
						pw.flush();
					}	
				}
				else if( rw == 3 ){
					System.out.println("CPU tried to access memory outside of allocated range");
					break;

				}
				else if( rw == 4 ){
					System.out.println("CPU saw a write error");
					break;

				}
				else if( rw == 5 ){
					System.out.println("CPU saw a write to screen error");
					break;

				}
				else if( rw == 6 ){
					System.out.println("CPU called end");
					break;

				}
				else if( rw < 0 ){
					System.out.println("charAtInt failed or produced a non-Integer");

				}
		
				//System.out.println("addr: "+addr+", mode: "+mode);
		
			}

			proc.waitFor();

			int exitVal = proc.exitValue();

			System.out.println("Process exited: " + exitVal);

		} 
		catch (Throwable t){
			t.printStackTrace();
		}

	} // end of main

	public static int charToInt(String str, int index){
		int n = (int)str.charAt(index) - 48;
		if(n < 48 || n > 57){
			return n;
		}
		else {
			return -1;
		}
	}

	public static int strToInt(String str){
		int n = 0;
		for (int i = 0; i < str.length(); i++) {
			n = n + (((int) str.charAt(i) - 48) * (int) Math.pow(10, str.length() - 1 - i));
		}
		return n;
	}

	public static boolean charIsNum(char ch){
		return (!((int) ch < 48) && !((int) ch > 57));
	}
	
}

