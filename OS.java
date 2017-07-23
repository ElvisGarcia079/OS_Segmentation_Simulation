import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.lang.Math;

public class OS {
	Page[] physicalMemory;; //Represents pages in memory, each index is a different page. 
	HashMap<Integer, Integer> pageTable = new HashMap<Integer, Integer>(); //Obj created in order to map vpn to ppn.
	String str = null; // null String obj to be used later to read lines in files. 
	byte[] strBytes; //Initialized in order to use later.
	Page byteData;//Initialized in order to use later. 	
	int numPages;//Initialized in order to use later. 
	int numBytes;//Initialized in order to use later. 
	
	public static void main(String[] args) throws Exception{
		OS os = new OS("C:\\Users\\Elvis\\Desktop\\TestCasesForOS\\proj2_data3.txt");
		System.out.println("Solution in Main should be: " + os.getDataAtVirtAddress(201));
	}
	
	
	public int getPPN(int vpn){
		//This method simply gets the value of the given key: vpn, which is it's corresponding ppn. 
		System.out.println("VPN: " + vpn);
		System.out.println("Check PageTable: " + pageTable.get(vpn));
		return pageTable.get(vpn);
		
	}
	
	public Page getPage(int ppn){
		//This method returns the page instance given the physical page number.
		System.out.println("PPN in getPage method " + ppn);
		System.out.println("This is the physical page in GetPage: " +physicalMemory[ppn]);
		return physicalMemory[ppn];
	}
	 
	
	
	public byte getDataAtVirtAddress(int virtAddress) throws Exception {
		//This method will get the vitual address given bit information provided in file.
		int numVpnBits = (int) Math.ceil(Math.log(numPages) / Math.log(2));
		int numOffsetBits = (int) Math.ceil(Math.log(numBytes) / Math.log(2));
		int numBitsNeeded = numVpnBits + numOffsetBits;
		int numNeededValue = (int) Math.pow(2, numBitsNeeded)-1;
		int extractedValue = virtAddress & numNeededValue;
		int offsetValue = (int) Math.pow(2, numOffsetBits)-1;
		int offset = extractedValue & offsetValue;
		int vpnValue = (int) Math.pow(2, numVpnBits) -1;
		int shiftedVpnValue = vpnValue << numOffsetBits;
		int vpn = extractedValue & shiftedVpnValue;
		int finalVpn = vpn >> numOffsetBits;
		int ppn = getPPN(finalVpn);
		return physicalMemory[ppn].getData(offset);
	}
	
	//Constructor method to initialize objects
	public OS(String filename){
		
		try {
			//When initializing an OS object given a file I will first create an object of the file using FileReader.
			FileReader file = new FileReader(filename);
			//I will use the BufferedReader in order to read the file object I created. 
			BufferedReader fileName = new BufferedReader(file);
			//I will store the first line of the file into firstLine using the readLine() method of BufferedReader.
			String firstLine = fileName.readLine();
			/*The first token at the first index of the line, will be what represents the pageValue. I extract it
			 * using the substring method to get the first (or if it is double digit second) character of the text
			 * and I trim in case there's extra white space and store that value as the String "PageValue"*/
			String[] firstLineParts = firstLine.split(" ");
			String pagenumValue = firstLineParts[0];  
			System.out.println("PageNumValue is: " + pagenumValue);
			/*I do the same process for the following 2 characters, and trim in case I take in any extra white space.*/
			String bytenumValue = firstLineParts[1];
			System.out.println("ByteNumValue is: " + bytenumValue);
			// I parse the value of the string pageValue into an integer and call it the numPages
			numPages = Integer.parseInt(pagenumValue);
			System.out.println("Number of Pages: " + numPages);
			//I parse the value of the byteValue string into an integer and call it bytes. 
			numBytes = Integer.parseInt(bytenumValue);
			System.out.println("Number of Bytes: " + numBytes);
			/*Now that I have the first line in order, which represents the pageNumber and the byte value I proceed 
			 * to the next lines.*/
			while((str = fileName.readLine()) != null){ 
				/*My next task is that the next "numPages" of lines will represent what vpn maps to what ppn.
				 * I have to find a way to record that into my pageTable.*/
				/*The lines all have a "->" on it. I have to by pass*/
				for( int i = 0; i < numPages; i++){
					//Creating a string array object that has tokens of strings before and after being split with the "->".
					String[] parts = str.split("->");
					//Storing the first token as a string that represents the vpn value.
					String vpnValue = parts[0];
					//Storing the second token as a string that represents the ppn value.
					String ppnValue = parts[1];
					//Parsing both strings in order to get their integer value.
					int vpn = Integer.parseInt(vpnValue);
					int ppn = Integer.parseInt(ppnValue);
					System.out.println("VPN -> PPN: " + vpn + "->" + ppn);
					//I am adding the vpn value as the key, and the ppn value as the value in my HashMap object.
					pageTable.put(vpn, ppn);
					//I am calling the next line so that my loop can work with that ever comes next.
					str = fileName.readLine();
			
				}
				//I am initializing the size of my Page array here before the loop in order to use it inside the loop without having the loop recreate the array each time it goes around.
				physicalMemory = new Page[numPages];
				//For the next numPages lines, I will be storing the byte data into each page instance in my Page object array.
				for(int i = 0; i < numPages; i++){
					System.out.println("This is the length of physicalMemoryObj: " + physicalMemory.length);//check
					System.out.println(Arrays.toString(physicalMemory));//check
					strBytes = str.getBytes(); //Getting the byte data of the string and storing it into my byte[] type variable.
					System.out.println("This is the strBytes: " + strBytes);//check
					byteData = new Page(strBytes); //storing my byte data into my page object.
					System.out.println("This is the byteData: " + byteData);//check
					physicalMemory[i] = byteData;//adding each bytedata instance into my page instance.
					System.out.println("This is the array with the byteData: " + physicalMemory[i]);//check
					String info4physicalMem = Arrays.toString(physicalMemory);//check
					System.out.println("This is the info on the PhysicalMemory" + info4physicalMem);//check
					str = fileName.readLine(); //Calling to read the next line before the loop ends.
					
				}
				
				
				
			}
		} catch (Exception e) {
			//Handles exception
			System.out.println(e.getMessage());
		}
		
		 
		
		
		
	}
	
	public double log2(int n)
	{
		//Helper method in order to calculate the value of numbers base 2.
	    return (Math.log(n) / Math.log(2));
	}
}
