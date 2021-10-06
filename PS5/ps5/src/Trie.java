import java.util.ArrayList;

public class Trie {

    // Wildcards
    final char WILDCARD = '.';    

    private class TrieNode {
        // TODO: Create your TrieNode class here.
	   int[] present_chars = new int[62];
	   TrieNode[] children = new TrieNode[62];
	   boolean isEndOfWord;

	   TrieNode() {
	       isEndOfWord = false;
	       for (int i = 0; i < 62; i++) {
	           present_chars[i] = -1;
           }
       };
    }

    // attributes for class Trie
    private TrieNode root;

    // constructor for class Trie
    public Trie() {
        // TODO: Initialise a trie class here.
        root = new TrieNode();
    }

    public int getIndex(int ascii) {
        // character [0,9] at index [0,9]
        if (ascii >= 48 && ascii <= 57) {
            return ascii - 48;

            // character [A,Z] at index [10,35]
        } else if (ascii >= 65 && ascii <= 90) {
            return ascii - 55;

            // character [a,z] at index [36,61]
        } else {
            return ascii - 61;
        }
    }

    // inserts string s into the Trie
    void insert(String s) {
        // TODO
        int len = s.length();

        TrieNode node = root;

        for (int i = 0; i < len; i++) {
            char character = s.charAt(i);
            int ascii = (int) character;
            int index = getIndex(ascii);

            if (node.present_chars[index] == -1) {
                node.present_chars[index] = ascii;
                node.children[index] = new TrieNode();
            }

            node = node.children[index];
        }

        node.isEndOfWord = true;
    }

    // checks whether string s exists inside the Trie or not
    boolean contains(String s) {
        // TODO
        TrieNode node = root;

        if (node == null) {
            return false;
        } else {
            return containsHelper(s, node);
        }
    }

    boolean containsHelper(String s, TrieNode node) {
        if (s.equals("")) {
            return node.isEndOfWord;
        } else {
            char character = s.charAt(0);
            int ascii = (int) character;
            int index = getIndex(ascii);

            if (node.present_chars[index] != -1) {
                return containsHelper(s.substring(1), node.children[index]);
            } else {
                return false;
            }
        }
    }

    // Search for string with prefix matching the specified pattern sorted by lexicographical order.
    // Return results in the specified ArrayList.
    // Only return at most the first limit results.
    void prefixSearch(String s, ArrayList<String> results, int limit) {
        // TODO
        TrieNode node = root;
        int size = s.length();
        String word = "";

        if (s.contains(".")) {
            if (!s.equals("") && limit != 0) {
                prefixWithDot(s, node, "", results, limit);
            }
        } else {
            for (int i = 0; i < size; i++) {
                char character = s.charAt(i);
                int ascii = (int) character;
                int index = getIndex(ascii);

                if (node.present_chars[index] != -1) {
                    word += character;
                    node = node.children[index];
                } else {
                    return;
                }
            }
            findAllChildren(node, word, results, limit);
        }
    }

    void prefixWithDot(String s, TrieNode node, String word,
                            ArrayList<String> results, int limit) {
        if (s.equals("")) {
            findAllChildren(node, word, results, limit);
        } else {
            while (results.size() <= limit) {
                char character = s.charAt(0);

                if (character == WILDCARD) {
                    for (int i=0; i<62; i++) {
                        if (node.present_chars[i] != -1) {
                            int ascii = node.present_chars[i];
                            char letter = (char) ascii;
                            word += letter;
                            prefixWithDot(s.substring(1), node.children[i], word, results, limit);
                            word = word.substring(0, word.length() - 1);
                        }
                    }
                } else {
                    int ascii = (int) character;
                    int index = getIndex(ascii);

                    if (node.present_chars[index] != -1) {
                        word += character;
                        prefixWithDot(s.substring(1), node.children[index], word, results, limit);
                    }
                }
                
                break;
            }
        }
    }

    void findAllChildren(TrieNode node, String word, ArrayList<String> results, int limit) {
        while (results.size() <= limit) {
            if (node.isEndOfWord) {
                results.add(word);
                limit -= 1;
            }

            for (int i=0; i<62; i++) {
                if (node.present_chars[i] != -1) {
                    word += (char) node.present_chars[i];
                    findAllChildren(node.children[i], word, results, limit);
                    word = word.substring(0, word.length() - 1);
                }
            }

            break;
        }
    }

    // Simplifies function call by initializing an empty array to store the results.
    // PLEASE DO NOT CHANGE the implementation for this function as it will be used
    // to run the test cases.
    String[] prefixSearch(String s, int limit) {
        ArrayList<String> results = new ArrayList<String>();
        prefixSearch(s, results, limit);
        return results.toArray(new String[0]);
    }

    public String printTrie(TrieNode node) {
        int[] arr = node.present_chars;
        String str = "";
        for (int i = 0; i < arr.length; i++) {
            str = i + ": " + arr[i] + ", ";
            System.out.println(str);
        }
        return str;
    }

    public static void main(String[] args) {
        Trie t = new Trie();

        t.insert("peter");
        t.insert("piper");
        t.insert("picked");
        t.insert("a");
        t.insert("peck");
        t.insert("of");
        t.insert("pickled");
        t.insert("peppers");
        t.insert("pepppito");
        t.insert("pepi");
        t.insert("pik");

        String[] arr = t.prefixSearch("p.ck", 10);
        // ["peck", "pepi", "peppers", "pepppito", "peter"]
        for (String s : arr) {
            System.out.println(s);
        }

        // String[] result2 = t.patternSearch("pe.*", 10);
        // result2 should contain the same elements with result1 but may be ordered arbitrarily
    }
}
