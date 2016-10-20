
public class Page {
	int virtualPageNum; //The virtual page number
	byte[] data; //The bytes of data that I am storing. Each index is a byte.
	
	public byte getData(int offset) throws Exception{
	    //Returns the byte at position offset.
		//Throws exception if the offset is negative or greater than the size
		//of the array.
		System.out.println("First Offset Value Check Before If Condition: " + offset);
		
			if(offset>=0 && offset<=data.length){ // Checking that while the conditions are met, we can continue.
				System.out.println("Second Offset Value Check inside If Condition: " + offset);
			}
			else{
				throw new Exception("invalid offset");
			}
		 
		return data[offset];
		 //Returning the byte at value "offset"
		
	     
	  }
	
	public Page(byte[] bytes){
		data = bytes;
		//virtualPageNum = vpn;
	}
	
}
