package textgen;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList; 
	
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator;
	
	//@S
	private String text;
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}
	
	
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{
		// TODO: Implement this method
		if(sourceText!=null && sourceText.length()>0){
			text = sourceText;
			List<String> words = getTokens("[A-Za-z,]+");
			starter = words.get(0);
			boolean alreadyAdded;
			int i ;
			String w;
			for(int j=0; j<words.size()-1;j++){
				w = words.get(j);
				alreadyAdded = false;
				i = 0;
				while(i<wordList.size()){
					if(wordList.get(i).getWord().equalsIgnoreCase(w)){
						alreadyAdded = true;
						break;
					}
					else
						i++;
				}
				if(alreadyAdded){
					wordList.get(i).addNextWord(words.get(j+1));
				}
				else{
	//				ListNode temp = new ListNode(w);
	//				temp.addNextWord(words.get(j+1));
	//				wordList.add(temp);
					wordList.add(new ListNode(w));
					int k = wordList.size()-1;
					wordList.get(k).addNextWord(words.get(j+1));
				
			}
		}
		ListNode temp = new ListNode(words.get(words.size()-1));
		temp.addNextWord(starter);
		wordList.add(temp);
		}
		
		
		
	}
	
	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
	    // TODO: Implement this method
		if(wordList.size()>0 && numWords>0){
			String output = "";
			output = output+starter;
			int i = 1;
			String curword = starter;
			ListNode temp;
			while(i<numWords){
				for(int j=0;j<wordList.size();j++){
					temp = wordList.get(j);
					if(temp.getWord().equals(curword)){
						// choose the next word randomly
						curword = temp.getRandomNextWord(rnGenerator);
						output = output+" "+curword;
						break;
					}
				}
				i++;
			}
			return output;
		}
		else
			return "";
	}
	
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		// TODO: Implement this method.
		//re-initialize the parameters
		wordList = new LinkedList<ListNode>();
		starter = "";
		//rnGenerator = generator;
		text = sourceText;
		train(text);
	}
	
	// TODO: Add any private helper methods you need here.
	
	
	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		System.out.println(textString);
		gen.train(textString);
		//System.out.println("1");
		System.out.println(gen);
		//System.out.println("2");
		System.out.println(gen.generateText(20));
		System.out.println();
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println();
		System.out.println(gen);
		System.out.println(gen.generateText(20));
	}

	protected List<String> getTokens(String pattern)
	{	
		ArrayList<String> tokens = new ArrayList<String>();
		Pattern tokSplitter = Pattern.compile(pattern);
		Matcher m = tokSplitter.matcher(text);
		
		while (m.find()) {
			tokens.add(m.group());
		}
		
		return tokens;
	}
	
	
}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator)
	{
		// TODO: Implement this method
	    // The random number generator should be passed from 
	    // the MarkovTextGeneratorLoL class
		int index = generator.nextInt(this.nextWords.size());
		
	    return this.nextWords.get(index);
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
	
}


