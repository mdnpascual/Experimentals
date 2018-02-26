import java.io.*;


public class Keyparse {
	
	public volatile String K_array = "0, ";
	public volatile String Z_array = "";
	public volatile String I_array = "";
	public volatile int i = 0;
	
	public Keyparse (File input) throws IOException{
		
		
		FileInputStream fstream = new FileInputStream(input);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		String strLine;
		String[] parts;
		boolean needDelay = false;
		boolean reduceDelay = false;
		int customDelay = 0;

		//Read File Line By Line
		while ((strLine = br.readLine()) != null)   {
		  parts = strLine.split(" : ");
		  if(parts[0].equals("Keyboard")){
			  if(needDelay){
				  K_array = K_array.concat("0, ");
				  needDelay = false;
				  reduceDelay = true;
				  customDelay++;
			  }
			  if(parts[2].equals("KeyDown")){
				  I_array = I_array.concat("1, ");
				  Z_array = Z_array.concat(String.valueOf(returnVal(parts[1])) + ", ");
				  needDelay = true;
				  i++;
			  }
			  else if(parts[2].equals("KeyUp")){
				  I_array = I_array.concat("0, ");
				  Z_array = Z_array.concat(String.valueOf(returnVal(parts[1])) + ", ");
				  needDelay = true;
				  i++;
			  }
		  }
		  else if(parts[0].equals("DELAY")){
			  if (reduceDelay && Integer.parseInt(parts[1]) > 0 && customDelay % 2 == 0){
				  K_array = K_array.concat(String.valueOf(Integer.parseInt(parts[1])-1) + ", ");
				  reduceDelay = false;
			  }
			  else
				  K_array = K_array.concat(parts[1] + ", ");
			  needDelay = false;
		  }
		  else{
			  System.out.println("Error?");
		  }
			  
		}

		//Close the input stream
		br.close();
	}
	
	private int returnVal(String s){
		if (s.equals("D"))
			return 68;
		else if (s.equals("F"))
			return 70;
		else if (s.equals("J"))
			return 74;
		else if (s.equals("K"))
			return 75;
		else if (s.equals("L"))
			return 76;
		else if (s.equals("S"))
			return 83;
		else if (s.equals("Space"))
			return 32;
		return 0;
	}
}
