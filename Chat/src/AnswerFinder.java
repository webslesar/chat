import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AnswerFinder {
	public String getAnswer(String line){
		String[] words = line.toLowerCase().split("[^А-Яа-яІі]+");
		String[] wordstemplated = getTemplatedWords(words);
		return answer(wordstemplated);
	}
	private String[] getTemplatedWords(String[] originalWords) {
		String[] templatedWords = new String[originalWords.length];
		for (int i = 0; i < originalWords.length; i++) {
			templatedWords[i] = getMarkForLeftLenght(i) + originalWords[i] + getMarkForRightLenght(originalWords.length - 1 - i);
		}
		return templatedWords;
	}

	private String getMarkForLeftLenght(int size) {
		if (size == 1) {
			return "? ";
		} else {
			return "* ";
		}
	}

	private String getMarkForRightLenght(int size) {
		if (size == 1) {
			return " ?";
		} else {
			return " *";
		}
	}

	private String answer(String[] wordtemplated) {
		// ключ - id відповіді, значення - кількість повторень цієї відповіді
		HashMap<Integer, Integer> counts = new HashMap<Integer, Integer>();
		for (int i = 0; i < wordtemplated.length; ++i) {
			ArrayList<Integer> results = (ArrayList<Integer>) Data.getInstance().getTepmaptes().get(wordtemplated[i]);
			if (results != null) 
				for (Integer g : results) 
					// якщо в counts вже є дана відповідь, то збільшуємо кількість її повторень +1
					if (counts.containsKey(g)) 
						counts.put(g, counts.get(g) + 1);				
					// якщо ні, то створюємо нову відповідь з лічильником 1
					else 
						counts.put(g, 1);
		}
		
		int f = 0, ad = -1;
		Iterator it = counts.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			if ((int) pair.getValue() > f) {
				f = (int) pair.getValue();
				ad = (int) pair.getKey();
			}
		}
		if (ad == -1)
			ad = Data.getInstance().getRandomUnknownMessageID();
		return (String) Data.getInstance().getAnswers().get(ad);
	}
}