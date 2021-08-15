package Q1Answer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class FileParser {
	public Map<String, Map<String,String>> readTransactions(String fileName) {

		InputStream iStream = null;
		String line = null;
		Map<String, String> keyAndValueMap = Collections.emptyMap();
		String transactionName = " ";
		Map<String, Map<String,String>> transactionNameAndValueMap = new HashMap<>();
		try {
			iStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
			if (iStream != null) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(iStream));
				int count_SZ = 0;
				while ((line = reader.readLine()) != null && count_SZ < 2) {
					if (!line.contains("record") && line.contains("\"")) {
						//replace [ ] " with empty space
						line = line.replaceAll("[\\[\\]\"]","").trim();
						//System.out.println("### New Line ###"+line);
						StringTokenizer tokenizer = new StringTokenizer(line);
						String key = null;
						String value = null;
						while (tokenizer.hasMoreTokens()) {
							key = tokenizer.nextToken().trim();
							//System.out.println("Key : = " + key);
							if (tokenizer.hasMoreTokens()) {
								value = tokenizer.nextToken().trim();
								//System.out.println("Value : = " + value.trim());
							}
						}

						if((key !=null && !key.isEmpty()) && (value !=null &&!value.isEmpty())) {
							keyAndValueMap.put(key, value);
							if(key.contains(transactionName)) {
								if(transactionNameAndValueMap.containsKey(transactionName)) {
									Map<String, String> transactionValue = transactionNameAndValueMap.get(transactionName);
									transactionValue.putAll(keyAndValueMap);
								}else {
									transactionNameAndValueMap.put(transactionName, keyAndValueMap);

								}
								//System.out.println("transaction Map "+transactionNameAndValueMap);
							}
						}
					}else if(line.contains("record") && !line.contains("SZ")) {
						line = line.replaceAll("\\[record","").trim();
						if(!line.contains(transactionName)) {
							transactionName = line;
							keyAndValueMap  = new  HashMap<String, String>();
							System.out.println("Transactions Name = "+transactionName);
						}

					}else if(line.contains("SZ")) {
						count_SZ ++;  //count is 2 then end of transaction
					}

				}

				// transaction name and the data 
				System.out.println("TransactionNameAndValueMap :::" + transactionNameAndValueMap);
				//System.out.println(count_SZ);

			} else {
				System.out.println("File not found " + fileName);
			}

		} catch (IOException e) {;
		e.printStackTrace();
		}

		return  transactionNameAndValueMap;

	}

	public static void main(String[] args) throws IOException {

		final String FILE_NAME = "Q1TestData.txt";
		FileParser parser = new FileParser();
		Map<String, Map<String, String>> transactionData = parser.readTransactions(FILE_NAME);
		System.out.println("Transaction Data = "+transactionData);
		for (Map.Entry<String,Map<String, String>> entry : transactionData.entrySet()) {
			WriteTransactionToexcel.writeToExcel(entry.getKey(), entry.getValue());
		}

	}
}
