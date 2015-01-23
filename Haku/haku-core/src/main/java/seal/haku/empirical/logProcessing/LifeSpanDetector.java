package seal.haku.empirical.logProcessing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * @Author Lisa
 * @Date: Jan 22, 2015
 */
public class LifeSpanDetector {

	public void detectPreservedFiles(String fileSnap, String fileLatest,String outputPath) {
		HashMap<String, String> latestMap = getNamePathMap(fileLatest);
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					fileSnap));
			PrintWriter writer = new  PrintWriter(outputPath);
			String line = "";
			while ((line = reader.readLine()) != null) {

				String fileName = line.substring(line.lastIndexOf("/")+1);
				String filePath = line.substring(line.indexOf("/")+1);
				String project = line.substring(0,line.indexOf("/"));
				if (latestMap.containsKey(project+"-"+fileName)) {
					if (filePath.equals(latestMap.get(fileName))) 
						writer.println(project+"-"+fileName+","+filePath+",2,");
					else 
					writer.println(project+"-"+fileName+","+filePath+",1,"+latestMap.get(fileName));
				}
				else 
					writer.println(project+"-"+fileName+","+filePath+",0,");
			}
			writer.close();
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			
		}
	}

	private HashMap<String, String> getNamePathMap(String filePath) {
		HashMap<String, String> namePath = new HashMap<String, String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			String line = "";
			while ((line = reader.readLine()) != null) {
				int firstDir = line.indexOf("/");
				namePath.put(
						line.substring(0, firstDir) + "-"
								+ line.substring(line.lastIndexOf("/")+1),
						line.substring(firstDir+1));
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return namePath;
	}

}
