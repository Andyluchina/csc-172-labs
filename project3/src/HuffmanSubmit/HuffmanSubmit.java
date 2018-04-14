package HuffmanSubmit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

import HuffmanSubmit.BinaryIn;
import HuffmanSubmit.BinaryOut;

public class HuffmanSubmit implements Huffman {
  
	public class Node {
		public Node(Byte b) {
			this.val = b;
		}

		public Node(Byte b, Node right) {
			this.left = new Node(b);
			this.right = right;
		}

		private void annotatePrefixCodes(HashMap<Byte, boolean[]> prefixCodes, boolean[] pcode, boolean _left) {
			prefixCode = Arrays.copyOf(pcode, pcode.length+1);
			// Left tree branches get 1, right tree branches get 0
			prefixCode[pcode.length] = _left ? true : false;

			// Leafs have values
			if (val != null) {
				prefixCodes.put(val, prefixCode);
				return;
			}

			this.left.annotatePrefixCodes(prefixCodes, prefixCode, true);
			this.right.annotatePrefixCodes(prefixCodes, prefixCode, false);
		}

		public void annotatePrefixCodes(HashMap<Byte, boolean[]> prefixCodes) {
			left.annotatePrefixCodes(prefixCodes, new boolean[0], true);
			right.annotatePrefixCodes(prefixCodes, new boolean[0], false);
		}

		Node left;
		Node right;
		Byte val;
		boolean[] prefixCode;
	}
 
	public void encode(String inputFile, String outputFile, String freqFile){
		BinaryIn in = new BinaryIn(inputFile);
		BinaryOut freqOut = new BinaryOut(freqFile);
		HashMap<Byte, Integer> freq = new HashMap<Byte, Integer>();
		while (!in.isEmpty()) {
			try {
				byte b = in.readByte();
				freq.merge(b, 1, (_a, _b) -> _a+_b);
			} catch (NoSuchElementException e) {
				// TODO
			}
		}

		ArrayList<Entry<Byte, Integer>> freqEntries = new ArrayList<Entry<Byte, Integer>>();
		freqEntries.addAll(freq.entrySet());
		Collections.sort(freqEntries, Entry.comparingByValue());
		Node htree = null;
		// XXX produce flatter trees
		for (Entry<Byte, Integer> e: freqEntries) {
			if (htree == null) {
				htree = new Node(e.getKey());
			} else {
				htree = new Node(e.getKey(), htree);
			}
		}

		HashMap<Byte, boolean[]> prefixCodes = new HashMap<Byte, boolean[]>();
		htree.annotatePrefixCodes(prefixCodes);

		// Reopen the file stream since we exhausted it to construct the frequency table
		in = new BinaryIn(inputFile);
		BinaryOut out = new BinaryOut(outputFile);
		while (!in.isEmpty()) {
			try {
				byte b = in.readByte();
				for (boolean bit: prefixCodes.get(b)) out.write(bit);
			} catch (NoSuchElementException e) {
				// TODO
			}
		}
		out.flush();
		out.close();

		// Write freq file
		for (Entry<Byte, Integer> e: freq.entrySet()) {
			// TODO this should be 8 digit bit representation
			freqOut.write(e.getKey());
			freqOut.write(':');
			freqOut.write(e.getValue().toString());
			freqOut.write('\n');
		}
		freqOut.flush();
		freqOut.close();
   }


   public void decode(String inputFile, String outputFile, String freqFile){
		// TODO: Your code here
   }




   public static void main(String[] args) {
      Huffman  huffman = new HuffmanSubmit();
		huffman.encode("ur.jpg", "ur.enc", "freq.txt");
		huffman.decode("ur.enc", "ur_dec.jpg", "freq.txt");
		// After decoding, both ur.jpg and ur_dec.jpg should be the same. 
		// On linux and mac, you can use `diff' command to check if they are the same. 
   }

}
