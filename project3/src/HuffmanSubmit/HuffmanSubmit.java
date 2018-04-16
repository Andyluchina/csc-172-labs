package HuffmanSubmit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

import HuffmanSubmit.BinaryIn;
import HuffmanSubmit.BinaryOut;

public class HuffmanSubmit implements Huffman {

	public class Node implements Comparable<Node> {
		public Node(Byte b, int freq) {
			this.val = b;
			this.cumulFreq = freq;
		}

		public Node(Byte b, int freq, Node right) {
			this.left = new Node(b, freq);
			this.right = right;
		}

		public Node(Node a, Node b) {
			this.left = a;
			this.right = b;
			this.cumulFreq = a.cumulFreq + b.cumulFreq;
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
		int cumulFreq;
		boolean[] prefixCode;

		@Override
		public int compareTo(Node arg0) {
			if (this.cumulFreq == arg0.cumulFreq) return 0;
			if (this.cumulFreq < arg0.cumulFreq) return -1;
			return 1;
		}
	}

	public Node treeFromFreqMap(HashMap<Byte, Integer> freq) {
		ArrayList<Entry<Byte, Integer>> freqEntries = new ArrayList<Entry<Byte, Integer>>();
		freqEntries.addAll(freq.entrySet());
		ArrayList<Node> trees = new ArrayList<Node>();

		// Turn all entries into tree leafs
		for (Entry<Byte, Integer> e: freqEntries) {
			Node n = new Node(e.getKey(), e.getValue());
			trees.add(n);
		}

		Collections.sort(trees);

		while (trees.size() > 1) {
			Node t1 = trees.get(0);
			Node t2 = trees.get(1);
			trees.add(new Node(t1, t2));
			trees.remove(t1);
			trees.remove(t2);
			Collections.sort(trees);
		}

		return trees.get(0);
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

		Node htree = treeFromFreqMap(freq);

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
			byte[] arr = {e.getKey()};
			BitSet bitset = BitSet.valueOf(arr);
			for (int i = 0; i < 8; i++) {
				freqOut.write(bitset.get(i) ? '1' : '0');
			}
			freqOut.write(':');
			freqOut.write(e.getValue().toString());
			freqOut.write('\n');
		}
		freqOut.flush();
		freqOut.close();
	}


	public void decode(String inputFile, String outputFile, String freqFile){
		// XXX prefixCode should really be some sort of number
		HashMap<Byte, Integer> freq = new HashMap<Byte, Integer>();
		BinaryIn freqIn = new BinaryIn(freqFile);
		// Whether or not we're handling the bit array record or the frequency record
		boolean handlingFreq = false;
		String record = "";
		String characterData = "";
		while (!freqIn.isEmpty()) {
			char c = freqIn.readChar();
			if (!handlingFreq) {
				if (c == '1' || c == '0') {
					record += c;
					continue;
				} else if (c == ':') {
					characterData = record;
					handlingFreq = true;
					record = "";
					continue;
				}
				// XXX better error message
				throw new RuntimeException("unexpected frequency file bit array character while decoding");
			} else {
				if (c == '\n') {
					handlingFreq = false;
					// Reconstruct the character byte
					BitSet bitset = new BitSet(8);
					for (int i = 0; i < 8; i++) {
						if (characterData.charAt(i) == '1') {
							bitset.set(i);
						} else {
							bitset.clear(i);
						}
					}

					// We special-case this because if the bitset is empty, it returns a byte array of length 0
					byte b;
					if (bitset.isEmpty()) {
						b = 0;
					} else {
						b = bitset.toByteArray()[0];
					}

					freq.put(b, Integer.valueOf(record));
					record = "";
				} else {
					record += c;
				}
			}
		}

		Node htree = treeFromFreqMap(freq);

		HashMap<Byte, boolean[]> _prefixCodes = new HashMap<Byte, boolean[]>();
		htree.annotatePrefixCodes(_prefixCodes);

		// Switch keys and values
		// XXX just do it right the first time
		HashMap<Boolean[], Byte> prefixCodes = new HashMap<Boolean[], Byte>();
		for (Entry<Byte, boolean[]> e: _prefixCodes.entrySet()) {
			// This is ridiculous
			Boolean[] l = new Boolean[e.getValue().length];
			int i = 0;
			for (boolean b: e.getValue()) {
				l[i] = Boolean.valueOf(b);
				i++;
			}
			prefixCodes.put(l, e.getKey());
		}

		BinaryIn in = new BinaryIn(inputFile);
		BinaryOut out = new BinaryOut(outputFile);
		ArrayList<Boolean> bits = new ArrayList<Boolean>();
		while (!in.isEmpty()) {
			boolean bit = in.readBoolean();
			bits.add(bit);
			boolean foundKey = false;
			Boolean[] keyWeFound = null;
			Boolean[] cmp = new Boolean[bits.size()];
			// Can't use HashMap#contains because it uses object id, not value
			// Java didn't like a Boolean[] cast so here we fuckin go
			for (int i = 0; i < cmp.length; i++) {
				cmp[i] = bits.get(i);
			}
			for (Boolean[] key: prefixCodes.keySet()) {
				if (key.length != cmp.length) continue;
				if (Arrays.equals(key, cmp)) {
					foundKey = true;
					// The entire premise of this variable is ridiculous
					keyWeFound = key;
					break;
				}
			}
			if (foundKey) {
				out.write(prefixCodes.get(keyWeFound));
				// XXX remove
				out.flush();
			}
		}
		out.flush();
		out.close();
	}




	public static void main(String[] args) {
		Huffman  huffman = new HuffmanSubmit();
		huffman.encode("alice30.txt", "alice30.enc", "freq.txt");
		huffman.decode("alice30.enc", "alice30_dec.txt", "freq.txt");
		// After decoding, both ur.jpg and ur_dec.jpg should be the same. 
		// On linux and mac, you can use `diff' command to check if they are the same. 
	}

}
