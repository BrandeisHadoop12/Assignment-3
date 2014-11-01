package code.lemma;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.tartarus.snowball.ext.englishStemmer;

public class Tokenizer {
	
	public ArrayList<String> tokens = new ArrayList<String>();
	public HashSet<String> stopWords = new HashSet<String>();
	
	public Tokenizer() {
		tokens = new ArrayList<String>();
		createStopWords("Resources/stopwords.txt");
	}
	
	// Add stop words to a hashset
	private void createStopWords (String path) {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (scanner.hasNextLine()) {
			stopWords.add(scanner.nextLine());
		}
	}
	
	// Removes special characters, stems tokens, and removes stop words from the input.
	public ArrayList<String> tokenize(String sentence) {
		englishStemmer stemmer = new englishStemmer();
		for(String s:sentence.split("[\\., /\\|\\\\~\\!\\?—\\(\\)–\\t\\[\\]\\{\\}\\+\\=\\*\\-\\_\\&\"\\;\\:\\<\\>]")){
			if(!s.equals("")) 
			{
				stemmer.setCurrent(s.toLowerCase());
				stemmer.stem();
				String curr = stemmer.getCurrent();
				if(!stopWords.contains(curr))
				{
					if(curr.contains("\n"))
					{
						curr = curr.replace("\n", "");
						tokens.add(curr);
					}
					
				}
			}
		}
		return tokens;
	}
}
