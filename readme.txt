Hello, I am Asa Mcdaniel

This is my submission for Project 1 of SE 4348.501: Operating Systems Concepts

Contained here is:
- The memory and console output java file, Proc.java
	Reads in the program to memory, then forks the CPU.
	Afterwards takes Read/Write Commands from the CPU,
	and due to the nature of Java processes, 
	also displays data for the CPU.

- The CPU java file, ProjOS.java
	On birth, reads the first instruction from memory,
	then loops executing and fetching those instructions.
	Also maintains kernel vs user mode, excluding itself
	from either half of the memory

- 5 Sample Programs
	1: 	Tests the indexed load instructions.
		Prints two tables, one of A-Z, the other of 1-10.
	
	2:	Tests the call/ret instructions.
		Prints a face where the lines are printed using subroutine calls.
	
	3:	Tests the int/iret instructions.
		The main loop is printing the letter A followed by a number
		that is being periodically incremented by the timer.
		The number will increment faster if the timer period is
		shorter.
	
	4:	Tests the proper operation of the user stack and system
		stack, and also tests that accessing system memory in 
		user mode gives an error and exits.
	
	5:	Continuously displays a triangular number sequence 
		until timer interrupt is called. (Original)

To use this program 
- put all the files in the same folder
- On the Command Prompt or alternative, navigate to the folder with all the program files
- call 'java Proc.java ProjOS.java'
- then call the Proc program with any of the included text files in the following format
	'java Proc nameOfTxtFile timerNum' 
  for example
	'java Proc sample5.txt 100'