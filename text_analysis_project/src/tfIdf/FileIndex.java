package il.ac.tau.cs.sw1.ex8.tfIdf;

import il.ac.tau.cs.sw1.ex8.histogram.HashMapHistogram;
import il.ac.tau.cs.sw1.ex8.histogram.HashMapHistogramIterator;

import java.io.File;
import java.io.IOException;
import java.util.*;


/**************************************
 *  Add your code to this class !!!   *
 **************************************/

public class FileIndex {
	
	private boolean isInitialized = false;
	private HashMap <String, HashMapHistogram<String>> index = new HashMap<>();
	private HashMap<String,List<Map.Entry<String,Double>>> sorted_files = new HashMap<>();

	/*
	 * @pre: the directory is no empty, and contains only readable text files
	 * @pre: isInitialized() == false;
	 */
  	public void indexDirectory(String folderPath) { //Q1
		//This code iterates over all the files in the folder. add your code wherever is needed

		File folder = new File(folderPath);
		File[] listFiles = folder.listFiles();
		for (File file : listFiles) {
			// for every file in the folder
			if (file.isFile()) {
				try {
					List<String> tokens_lst = FileUtils.readAllTokens(file);
					HashMapHistogram<String> map = new HashMapHistogram<>();
					map.addAll(tokens_lst);
					String file_name = file.getName();
					index.put(file_name, map);
//					sorted_files.put(file_name, sortFile(file_name));
				}
				catch (IOException e) {
					throw new RuntimeException(e);
				}
				isInitialized = true;
			}
		}
			sortFiles();	// sorting all files by tfidf right after indexing
	}


	public void sortFiles() {
		for (Map.Entry<String,HashMapHistogram<String>> doc: index.entrySet()){  // for each (file,his histogram)
			List<Map.Entry<String,Double>> sorted_doc = new ArrayList<>();	// list to be added to sorted_files
			Set<Map.Entry<String,Integer>> words_set = doc.getValue().histogram.entrySet();
			String file_name = doc.getKey();
			for (Map.Entry<String,Integer> entry : words_set){
				String word = entry.getKey();
				double tfidf = 0.0;
				try {
					tfidf = getTFIDF(word, file_name);
				} catch (FileIndexException e) {
					throw new RuntimeException(e);
				}
				sorted_doc.add(new AbstractMap.SimpleEntry<>(word,tfidf));
			}
			Collections.sort(sorted_doc, new Comparator<Map.Entry<String, Double>>() {
				@Override
				public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
					if (!Objects.equals(o1.getValue(),o2.getValue()))
						return o2.getValue().compareTo(o1.getValue());
					return o1.getKey().compareTo(o2.getKey());
				}
			});
			sorted_files.put(file_name,sorted_doc); 	// (file_name, words in file sorted by tfidf)
		}
	}

	// Q2
  	
	/* @pre: isInitialized() */
	public int getCountInFile(String word, String fileName) throws FileIndexException{
		if (index.containsKey(fileName)){
			String word_lc = word.toLowerCase();
			HashMapHistogram<String> histo = index.get(fileName);
			if (histo == null)
				return 0;
			else
				return histo.getCountForItem(word_lc);
		}
		else{
			FileIndexException ex = new FileIndexException("there is no such word in the file!");
			throw ex;
		}
	}
	
	/* @pre: isInitialized() */
	public int getNumOfUniqueWordsInFile(String fileName) throws FileIndexException{
		HashMapHistogram<String> histo = index.get(fileName);
		if (histo != null)
			return histo.getItemsSet().size();
		return 0;
	}
	
	/* @pre: isInitialized() */
	public int getNumOfFilesInIndex(){
		return index.size();
	}

	
	/* @pre: isInitialized() */
	public double getTF(String word, String fileName) throws FileIndexException{ // Q3
		String word_lc = word.toLowerCase();
		int num_of_rep = getCountInFile(word_lc, fileName);
		int total_words = index.get(fileName).getCountsSum();
		return calcTF(num_of_rep,total_words);
	}
	
	/* @pre: isInitialized() 
	 * @pre: exist fileName such that getCountInFile(word) > 0*/
	public double getIDF(String word){ //Q4
		int num_of_docs = getNumOfFilesInIndex();
		int contain_word = 0;
		try{
		String word_lc = word.toLowerCase();
		Set<String> docs = index.keySet();
		for (String doc: docs) {
			if (getCountInFile(word_lc,doc) > 0)
				contain_word ++;
			}
		}
		catch (FileIndexException e) {
			throw new RuntimeException(e);
		}

		return calcIDF(num_of_docs, contain_word);
	}
	
	
	
	/*
	 * @pre: isInitialized()
	 * @pre: 0 < k <= getNumOfUniqueWordsInFile(fileName)
	 * @post: $ret.size() = k
	 * @post for i in (0,k-2):
	 * 		$ret[i].value >= $ret[i+1].value
	 */
	public List<Map.Entry<String, Double>> getTopKMostSignificantWords(String fileName, int k) 
													throws FileIndexException{ //Q5
		List<Map.Entry<String,Double>> most_list = new ArrayList<>();
		List<Map.Entry<String,Double>> file_lst = sorted_files.get(fileName);
		for (int i=0;i<k;i++){
			if (i < file_lst.size())
				most_list.add(file_lst.get(i));
			else
				break;
		}
		return most_list; //replace this with the correct value
	}

	/* @pre: isInitialized() */
	public double getCosineSimilarity(String fileName1, String fileName2) throws FileIndexException{ //Q6
		double numerator = 0.0,denominator  = 0.0, sigmaA = 0.0, sigmaB = 0.0;
		double result = 0.0;
		List<Map.Entry<String,Double>> f1 = sorted_files.get(fileName1);	// f1 after being sorted by tfidf
		List<Map.Entry<String,Double>> f2 = sorted_files.get(fileName2);	// f1 after being sorted by tfidf
		for (Map.Entry<String,Double> entry1:f1) {
			sigmaA += Math.pow(entry1.getValue(),2.0);	// calculate SigmaA during the calc of denominator
			for (Map.Entry<String,Double> entry2:f2) {
				if (Objects.equals(entry2.getKey(),entry1.getKey())) {
					numerator += entry1.getValue() * entry2.getValue();
				}
			}
		}
		for (Map.Entry<String,Double> entry2:f2) {		// calculate sigmaB separately
			sigmaB += Math.pow(entry2.getValue(),2.0);
		}
		denominator = Math.sqrt(sigmaA*sigmaB);
		try{
			result = numerator/denominator;
		}
		catch (ArithmeticException ex) {
			ArithmeticException m = new ArithmeticException("divided by 0");
			throw m;
		}
		return result;
	}
	
	/*
	 * @pre: isInitialized()
	 * @pre: 0 < k <= getNumOfFilesInIndex()-1
	 * @post: $ret.size() = k
	 * @post for i in (0,k-2):
	 * 		$ret[i].value >= $ret[i+1].value
	 */
	public List<Map.Entry<String, Double>> getTopKClosestDocuments(String fileName, int k) 
			throws FileIndexException{ //Q6
		List<Map.Entry<String, Double>> cosim_lst = new ArrayList<>();
		for (Map.Entry<String,List<Map.Entry<String, Double>>> sort_file:sorted_files.entrySet()) {
			String curr_file_name = sort_file.getKey();
			if (!Objects.equals(curr_file_name,fileName)){
				double cossim = getCosineSimilarity(curr_file_name,fileName);
				cosim_lst.add(new AbstractMap.SimpleEntry<>(curr_file_name,cossim));
			}
		}
		Collections.sort(cosim_lst, new Comparator<Map.Entry<String, Double>>() {
			@Override
			public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		List<Map.Entry<String, Double>> k_most = cosim_lst.subList(0,k); // result list
		return k_most;
	}

	
	
	//add private methods here, if needed

	
	/*************************************************************/
	/********************* Don't change this ********************/
	/*************************************************************/
	
	public boolean isInitialized(){
		return this.isInitialized;
	}
	
	/* @pre: exist fileName such that getCountInFile(word) > 0*/
	public double getTFIDF(String word, String fileName) throws FileIndexException{
		return this.getTF(word, fileName)*this.getIDF(word);
	}
	
	private static double calcTF(int repetitionsForWord, int numOfWordsInDoc){
		return (double)repetitionsForWord/numOfWordsInDoc;
	}
	
	private static double calcIDF(int numOfDocs, int numOfDocsContainingWord){
		return Math.log((double)numOfDocs/numOfDocsContainingWord);
	}
	
}
